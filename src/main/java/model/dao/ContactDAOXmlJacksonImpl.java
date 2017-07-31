package model.dao;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.entities.Contact;
import model.entities.Group;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

public class ContactDAOXmlJacksonImpl implements ContactDAO {
    private final String DEFAULT_FILENAME = "contacts.xml";

    private String fileName = DEFAULT_FILENAME;

    private XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
    private XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();

    private boolean save(Set<Contact> contacts) {
        if (contacts.isEmpty()) {
            return true;
        }
        XMLStreamWriter xmlStreamWriter;
        XmlMapper xmlMapper = new XmlMapper(xmlInputFactory);
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(fileName));
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeStartElement("contacts");
            for (Contact contact : contacts) {
                xmlMapper.writeValue(xmlStreamWriter, contact);
            }
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean add(Contact contact) {
        Set<Contact> contacts = getAll();
        contacts.add(contact);
        return save(contacts);
    }

    public boolean update(Contact contact) {
        Set<Contact> contacts = getAll();
        contacts.stream().filter(x -> x.getId() == contact.getId()).findFirst().ifPresent(contacts::remove);
        contacts.add(contact);
        return save(contacts);
    }

    public boolean delete(Contact contact) {
        Set<Contact> contacts = getAll();
        contacts.stream().filter(x -> x.getId() == contact.getId()).findFirst().ifPresent(contacts::remove);
        return save(contacts);
    }

    public boolean deleteGroup(Group group) {
        Set<Contact> contacts = getAll();
        for (Contact contact : contacts) {
            if (contact.getGroupId() == group.getId()) {
                contact.removeGroup();
            }
        }
        return true;
    }

    public Contact get(int id) {
        return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public Set<Contact> getAll() {
        int lastId = 0;
        Set<Contact> contacts = new HashSet<>();
        XMLStreamReader xmlStreamReader = null;
        try {
            xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(fileName));
            XmlMapper xmlMapper = new XmlMapper();
            xmlStreamReader.next();
            Contact input;
            int event = 0;
            while ((event = xmlStreamReader.next()) != XMLStreamReader.END_DOCUMENT) {
                input = xmlMapper.readValue(xmlStreamReader, Contact.class);
                contacts.add(input);
                if (input.getId() > lastId) {
                    lastId = input.getId();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (xmlStreamReader != null) {
                try {
                    xmlStreamReader.close();
                } catch (XMLStreamException e) {
                    //
                }
            }
        }
        Contact.lastId = lastId;
        return contacts;
    }
}
