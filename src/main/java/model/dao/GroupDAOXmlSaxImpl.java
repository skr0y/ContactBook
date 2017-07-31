package model.dao;


import model.Model;
import model.entities.EntityFactory;
import model.entities.Group;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import util.XmlUtils;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GroupDAOXmlSaxImpl implements GroupDAO {
    private final String DEFAULT_FILENAME = "groups.xml";

    private String fileName = DEFAULT_FILENAME;
    private String xsdFileName = "groups.xsd";

    private static EntityFactory entityFactory = Model.getInstance().getEntityFactory();

    public boolean add(Group group) {
        return false;
    }

    public boolean update(Group group) {
        return false;
    }

    public boolean delete(Group group) {
        return false;
    }

    public Group get(int id) {
        return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public Set<Group> getAll() {
        if (!XmlUtils.xsdValidate(fileName, xsdFileName)) {
            throw new IllegalArgumentException("Illegal XML format");
        }
        SAXParserFactory saxParserFactory;
        SAXParser saxParser;
        GroupHandler handler;
        try {
            saxParserFactory = SAXParserFactory.newInstance();
            saxParser = saxParserFactory.newSAXParser();
            handler = new GroupHandler();
            saxParser.parse(fileName, handler);
            return handler.getResult();
        } catch (Exception e) {
            return null;
        }
    }

    private class GroupHandler extends DefaultHandler {
        Set<Group> groups = new HashSet<>();

        int id;
        String groupName;

        String currElement;

        private Set<Group> getResult() {
            return groups;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currElement = qName;
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            switch (currElement) {
                case "id":
                    id = new Integer(new String(ch, start, length));
                    break;
                case "groupName":
                    groupName = new String(ch, start, length);
                    break;
                default:
                    break;
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("Group")) {
                Map<String, Object> params = new HashMap<>();
                params.put("groupId", id);
                params.put("groupName", groupName);
                Group group = (Group) entityFactory.getGroup(params);
                groups.add(group);
            }
        }
    }
}
