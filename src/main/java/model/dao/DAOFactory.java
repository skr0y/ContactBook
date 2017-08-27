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
                groupDAO = GroupDAODatabaseImpl.getInstance();
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
                contactDAO = ContactDAODatabaseImpl.getInstance();
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
                userDAO = UserDAODatabaseImpl.getInstance();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", daoType));
        }
        return userDAO;
    }

    public AdminDAO getAdminDAO() {
        AdminDAO adminDAO = null;
        switch (daoType) {
            case DATABASE:
                adminDAO = AdminDAODatabaseImpl.getInstance();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", daoType));
        }
        return adminDAO;
    }

    public enum DAOType {
        DATABASE
    }
}
