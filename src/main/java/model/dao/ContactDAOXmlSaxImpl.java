package model.dao;

import model.Model;
import model.entities.Contact;
import model.entities.EntityFactory;
import model.entities.Group;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContactDAOXmlSaxImpl implements ContactDAO {
    private final String DEFAULT_FILENAME = "contacts.xml";

    private String fileName = DEFAULT_FILENAME;

    private static EntityFactory entityFactory = Model.getInstance().getEntityFactory();

    public boolean add(Contact contact) {
        return false;
    }

    public boolean update(Contact contact) {
        return false;
    }

    public boolean delete(Contact contact) {
        return false;
    }

    public boolean deleteGroup(Group group) {
        return false;
    }

    public Contact get(int id) {
        return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public Set<Contact> getAll() {
        SAXParserFactory saxParserFactory;
        SAXParser saxParser;
        ContactHandler handler;
        try {
            saxParserFactory = SAXParserFactory.newInstance();
            saxParser = saxParserFactory.newSAXParser();
            handler = new ContactHandler();
            saxParser.parse(fileName, handler);
            return handler.getResult();
        } catch (Exception e) {
            return null;
        }
    }

    private class ContactHandler extends DefaultHandler {
        Set<Contact> contacts = new HashSet<>();

        int id;
        String firstName;
        String lastName;
        String phoneNumber;
        int groupId;

        String currElement;

        private Set<Contact> getResult() {
            return contacts;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currElement = qName;
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            switch (currElement) {
                case "id":
                    id = new Integer(new String(ch, start, length));
                    break;
                case "firstName":
                    firstName = new String(ch, start, length);
                    break;
                case "lastName":
                    lastName = new String(ch, start, length);
                    break;
                case "phoneNumber":
                    phoneNumber = new String(ch, start, length);
                    break;
                case "groupId":
                    groupId = new Integer(new String(ch, start, length));
                    break;
                default:
                    break;
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("Contact")) {
                Map<String, Object> params = new HashMap<>();
                params.put("contactId", id);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("phoneNumber", phoneNumber);
                params.put("groupId", groupId);
                Contact contact = (Contact) entityFactory.getContact(params);
                contacts.add(contact);
            }
        }
    }
}
