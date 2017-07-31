package model.dao;

import model.Model;
import model.entities.EntityFactory;
import model.entities.Group;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.StringUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
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

public class GroupDAOXmlDomImpl implements GroupDAO {
    private final String DEFAULT_FILENAME = "groups.xml";

    private String fileName = DEFAULT_FILENAME;

    private static EntityFactory entityFactory = Model.getInstance().getEntityFactory();

    private boolean save(Set<Group> groups) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("groups");
            document.appendChild(root);

            for (Group group : groups) {
                Element contactElement = document.createElement("Group");
                root.appendChild(contactElement);

                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(String.valueOf(group.getId())));
                contactElement.appendChild(id);
                Element groupName = document.createElement("groupName");
                groupName.appendChild(document.createTextNode(StringUtils.emptyIfNull(group.getGroupName())));
                contactElement.appendChild(groupName);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(fileName);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
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
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Group group = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new FileInputStream(fileName));
            Map<String, Object> params = new HashMap<>();
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = String.format("/groups/Group[id=%1s]", id);
            Node groupNode = (Node) xPath.compile(expression).evaluate(document, XPathConstants.NODE);
            if (groupNode != null) {
                NodeList nodes = groupNode.getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE) {
                        switch (node.getNodeName()) {
                            case "id":
                                params.put("groupId", Integer.parseInt(node.getTextContent()));
                                break;
                            case "groupName":
                                params.put("groupName", node.getTextContent());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            group = (Group) entityFactory.getGroup(params);
        } catch (Exception e) {
            //
        }
        return group;
    }

    public Set<Group> getAll() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Set<Group> groups = new HashSet<>();
        int lastId = 0;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(fis);
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/groups/Group";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList groupNodes = nodeList.item(i).getChildNodes();
                Map<String, Object> params = new HashMap<>();
                for (int j = 0; j < groupNodes.getLength(); j++) {
                    Node node = groupNodes.item(j);
                    if(node.getNodeType() == Node.ELEMENT_NODE) {
                        switch (node.getNodeName()) {
                            case "id":
                                params.put("groupId", Integer.parseInt(node.getTextContent()));
                                break;
                            case "groupName":
                                params.put("groupName", node.getTextContent());
                                break;
                            default:
                                break;
                        }
                    }
                }
                Group group = (Group) entityFactory.getGroup(params);
                if (group.getId() > lastId) {
                    lastId = group.getId();
                }
                groups.add(group);
            }
        } catch (Exception e) {
            //
        }
        Group.lastId = lastId;
        return groups;
    }
}
