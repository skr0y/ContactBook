package model.dao;

import util.PropertyUtils;

public class DAOFactory {
    private final DAOType DEFAULT_DAO = DAOType.DATABASE;
    private final String PROPERTY_NAME = "daotype";

    private DAOType daoType;

    public DAOFactory() {
        String propertyValue = PropertyUtils.readProperty(PROPERTY_NAME);
        if (propertyValue == null) {
            daoType = DEFAULT_DAO;
        } else {
            switch (propertyValue) {
                case "database":
                    daoType = DAOType.DATABASE;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", propertyValue));
            }
        }
    }

    public GroupDAO getGroupDAO() {
        GroupDAO groupDAO = null;
        switch (daoType) {
            case DATABASE:
                groupDAO = new GroupDAODatabaseImpl();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", daoType));
        }
        return groupDAO;
    }

    public ContactDAO getContactDAO() {
        ContactDAO contactDAO = null;
        switch (daoType) {
            case DATABASE:
                contactDAO = new ContactDAODatabaseImpl();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", daoType));
        }
        return contactDAO;
    }

    public UserDAO getUserDAO() {
        UserDAO userDAO = null;
        switch (daoType) {
            case DATABASE:
                userDAO = new UserDAODatabaseImpl();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", daoType));
        }
        return userDAO;
    }

    public enum DAOType {
        DATABASE
    }
}
