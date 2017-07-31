package model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DAOFactory {
    private final String DEFAULT_DAO = "serialized";
    private final String PROPERTIES_FILE = "config.properties";

    private String typeDAO = DEFAULT_DAO;

    public DAOFactory() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            prop.load(input);
            typeDAO = prop.getProperty("daotype");
        } catch (IOException e) {
            typeDAO = DEFAULT_DAO;
        }
    }

    public GroupDAO getGroupDAO() {
        GroupDAO groupDAO = null;
        switch (typeDAO) {
            case "serialized":
                groupDAO = new GroupDAOFileImpl();
                break;
            case "xmldom":
                groupDAO = new GroupDAOXmlDomImpl();
                break;
            case "xmlsax":
                groupDAO = new GroupDAOXmlSaxImpl();
                break;
            case "xmljackson":
                groupDAO = new GroupDAOXmlJacksonImpl();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", typeDAO));
        }
        return groupDAO;
    }

    public ContactDAO getContactDAO() {
        ContactDAO contactDAO = null;
        switch (typeDAO) {
            case "serialized":
                contactDAO = new ContactDAOFileImpl();
                break;
            case "xmldom":
                contactDAO = new ContactDAOXmlDomImpl();
                break;
            case "xmlsax":
                contactDAO = new ContactDAOXmlSaxImpl();
                break;
            case "xmljackson":
                contactDAO = new ContactDAOXmlJacksonImpl();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", typeDAO));
        }
        return contactDAO;
    }
}
