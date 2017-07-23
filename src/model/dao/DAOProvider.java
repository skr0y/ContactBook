package model.dao;

public class DAOProvider {
    private final String DEFAULT_DAO = "File";

    private static DAOProvider instance = new DAOProvider();

    private String typeDAO = DEFAULT_DAO;
    private ContactDAO contactDAO;
    private GroupDAO groupDAO;

    public static DAOProvider getInstance() {
        return instance;
    }

    private DAOProvider() {
        initialize();
    }

    private void initialize() {
        if (typeDAO.equals("File")) {
            contactDAO = new ContactDAOFileImpl();
            groupDAO = new GroupDAOFileImpl();
        } else {
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
