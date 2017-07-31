package model.dao;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.entities.Group;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

public class GroupDAOXmlJacksonImpl implements GroupDAO {
    private final String DEFAULT_FILENAME = "groups.xml";

    private String fileName = DEFAULT_FILENAME;

    private XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
    private XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();

    private boolean save(Set<Group> groups) {
        if (groups.isEmpty()) {
            return true;
        }
        XMLStreamWriter xmlStreamWriter;
        XmlMapper xmlMapper = new XmlMapper(xmlInputFactory);
        try {
            xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(fileName));
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeStartElement("groups");
            for (Group group : groups) {
                xmlMapper.writeValue(xmlStreamWriter, group);
            }
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean add(Group group) {
        Set<Group> groups = getAll();
        groups.add(group);
        return save(groups);
    }

    public boolean update(Group group) {
        Set<Group> groups = getAll();
        groups.stream().filter(x -> x.getId() == group.getId()).findFirst().ifPresent(groups::remove);
        groups.add(group);
        return save(groups);
    }

    public boolean delete(Group group) {
        Set<Group> groups = getAll();
        groups.stream().filter(x -> x.getId() == group.getId()).findFirst().ifPresent(groups::remove);
        return save(groups);
    }

    public Group get(int id) {
        return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public Set<Group> getAll() {
        Set<Group> groups = new HashSet<>();
        int lastId = 0;
        XMLStreamReader xmlStreamReader = null;
        try {
            xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(fileName));
            XmlMapper xmlMapper = new XmlMapper();
            xmlStreamReader.next();
            Group input;
            while (xmlStreamReader.next() != XMLStreamReader.END_ELEMENT) {
                input = xmlMapper.readValue(xmlStreamReader, Group.class);
                groups.add(input);
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
        Group.lastId = lastId;
        return groups;
    }
}
