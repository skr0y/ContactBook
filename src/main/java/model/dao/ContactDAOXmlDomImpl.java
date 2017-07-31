package model.dao;

import model.Model;
import model.entities.Contact;
import model.entities.EntityFactory;
import model.entities.Group;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.StringUtils;
import util.XmlUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContactDAOXmlDomImpl implements ContactDAO {
    private final String DEFAULT_FILENAME = "contacts.xml";

    private String fileName = DEFAULT_FILENAME;
    private String xsdFileName = "contacts.xsd";

    private static EntityFactory entityFactory = Model.getInstance().getEntityFactory();

    private boolean save(Set<Contact> contacts) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("contacts");
            document.appendChild(root);

            for (Contact contact : contacts) {
                Element contactElement = document.createElement("Contact");
                root.appendChild(contactElement);

                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(String.valueOf(contact.getId())));
                contactElement.appendChild(id);
                Element firstName = document.createElement("firstName");
                firstName.appendChild(document.createTextNode(StringUtils.emptyIfNull(contact.getFirstName())));
                contactElement.appendChild(firstName);
                Element lastName = document.createElement("lastName");
                lastName.appendChild(document.createTextNode(StringUtils.emptyIfNull(contact.getLastName())));
                contactElement.appendChild(lastName);
                Element phoneNumber = document.createElement("phoneNumber");
                phoneNumber.appendChild(document.createTextNode(StringUtils.emptyIfNull(contact.getPhoneNumber())));
                contactElement.appendChild(phoneNumber);
                Element groupId = document.createElement("groupId");
                groupId.appendChild(document.createTextNode(String.valueOf(contact.getGroupId())));
                contactElement.appendChild(groupId);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(fileName);
           //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
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
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Contact contact = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new FileInputStream(fileName));
            Map<String, Object> params = new HashMap<>();
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = String.format("/contacts/Contact[id=%1s]", id);
            Node contactNode = (Node) xPath.compile(expression).evaluate(document, XPathConstants.NODE);
            if (contactNode != null) {
                NodeList nodes = contactNode.getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE) {
                        switch (node.getNodeName()) {
                            case "id":
                                params.put("contactId", Integer.parseInt(node.getTextContent()));
                                break;
                            case "firstName":
                                params.put("firstName", node.getTextContent());
                                break;
                            case "lastName":
                                params.put("lastName", node.getTextContent());
                                break;
                            case "phoneNumber":
                                params.put("phoneNumber", node.getTextContent());
                                break;
                            case "groupId":
                                params.put("groupId", Integer.parseInt(node.getTextContent()));
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            contact = (Contact) entityFactory.getContact(params);
        } catch (Exception e) {
            //
        }
        return contact;
    }

    public Set<Contact> getAll() {
        if (!XmlUtils.xsdValidate(fileName, xsdFileName)) {
            throw new IllegalArgumentException("Illegal XML format");
        }
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Set<Contact> contacts = new HashSet<>();
        int lastId = 0;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(fis);
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/contacts/Contact";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList contactNodes = nodeList.item(i).getChildNodes();
                Map<String, Object> params = new HashMap<>();
                for (int j = 0; j < contactNodes.getLength(); j++) {
                    Node node = contactNodes.item(j);
                    if(node.getNodeType() == Node.ELEMENT_NODE) {
                        switch (node.getNodeName()) {
                            case "id":
                                params.put("contactId", Integer.parseInt(node.getTextContent()));
                                break;
                            case "firstName":
                                params.put("firstName", node.getTextContent());
                                break;
                            case "lastName":
                                params.put("lastName", node.getTextContent());
                                break;
                            case "phoneNumber":
                                params.put("phoneNumber", node.getTextContent());
                                break;
                            case "groupId":
                                params.put("groupId", Integer.parseInt(node.getTextContent()));
                                break;
                            default:
                                break;
                        }
                    }
                }
                Contact contact = (Contact) entityFactory.getContact(params);
                if (contact.getId() > lastId) {
                    lastId = contact.getId();
                }
                contacts.add(contact);
            }
        } catch (Exception e) {
            //
        }
        Contact.lastId = lastId;
        return contacts;
    }
}
