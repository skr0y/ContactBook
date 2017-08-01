package model.dao;

import util.PropertyUtils;

public class DAOFactory {
    private final DAOType DEFAULT_DAO = DAOType.SERIALIZED;
    private final String PROPERTY_NAME = "daotype";

    private DAOType daoType;

    public DAOFactory() {
        String propertyValue = PropertyUtils.readProperty(PROPERTY_NAME);
        if (propertyValue == null) {
            daoType = DEFAULT_DAO;
        } else {
            switch (propertyValue) {
                case "serialized":
                    daoType = DAOType.SERIALIZED;
                    break;
                case "xmldom":
                    daoType = DAOType.XMLDOM;
                    break;
                case "xmlsax":
                    daoType = DAOType.XMLSAX;
                    break;
                case "xmljackson":
                    daoType = DAOType.XMLJACKSON;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", propertyValue));
            }
        }
    }

    public GroupDAO getGroupDAO() {
        GroupDAO groupDAO = null;
        switch (daoType) {
            case SERIALIZED:
                groupDAO = new GroupDAOFileImpl();
                break;
            case XMLDOM:
                groupDAO = new GroupDAOXmlDomImpl();
                break;
            case XMLSAX:
                groupDAO = new GroupDAOXmlSaxImpl();
                break;
            case XMLJACKSON:
                groupDAO = new GroupDAOXmlJacksonImpl();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", daoType));
        }
        return groupDAO;
    }

    public ContactDAO getContactDAO() {
        ContactDAO contactDAO = null;
        switch (daoType) {
            case SERIALIZED:
                contactDAO = new ContactDAOFileImpl();
                break;
            case XMLDOM:
                contactDAO = new ContactDAOXmlDomImpl();
                break;
            case XMLSAX:
                contactDAO = new ContactDAOXmlSaxImpl();
                break;
            case XMLJACKSON:
                contactDAO = new ContactDAOXmlJacksonImpl();
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported DAO type: %1s", daoType));
        }
        return contactDAO;
    }

    public enum DAOType {
        SERIALIZED,
        XMLDOM,
        XMLSAX,
        XMLJACKSON
    }
}
