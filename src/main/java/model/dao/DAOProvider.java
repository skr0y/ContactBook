package model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DAOProvider {
    private final String DEFAULT_DAO = "serialized";
    private final String PROPERTIES_FILE = "config.properties";

    private static DAOProvider instance = new DAOProvider();

    private String typeDAO = DEFAULT_DAO;
    private ContactDAO contactDAO;
    private GroupDAO groupDAO;

    public static DAOProvider getInstance() {
        return instance;
    }

    private DAOProvider() {
        Properties prop = new Properties();
        String typeDAO;

        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            prop.load(input);
            typeDAO = prop.getProperty("daotype");
        } catch (IOException e) {
            typeDAO = DEFAULT_DAO;
        }

        switch (typeDAO) {
            case "serialized":
                contactDAO = new ContactDAOFileImpl();
                groupDAO = new GroupDAOFileImpl();
                break;
            case "xmldom":
            case "xmlsax":
            case "xmljackson":
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", typeDAO));
        }
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public ContactDAO getContactDAO() {
        return contactDAO;
    }
}
