package model.dao;

import model.Model;
import model.entities.Contact;
import model.entities.EntityFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContactDAODatabaseImpl implements ContactDAO {
    private static ContactDAODatabaseImpl instance;

    static synchronized ContactDAODatabaseImpl getInstance() {
        if (instance == null) {
            instance = new ContactDAODatabaseImpl();
        }
        return instance;
    }

    public synchronized boolean add(Contact contact) {
        String query = "SELECT \"AddContact\"(?, ?, ?, ?, ?)";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, contact.getUserId());
            preparedStatement.setString(2, contact.getFirstName());
            preparedStatement.setString(3, contact.getLastName());
            preparedStatement.setString(4, contact.getPhoneNumber());
            preparedStatement.setInt(5, contact.getGroupId());
            ResultSet resultSet = preparedStatement.executeQuery();
            contact.setId(resultSet.getInt("ContactID"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public synchronized boolean update(Contact contact) {
        String query = "SELECT \"UpdateContact\"(?, ?, ?, ?, ?, ?)";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, contact.getId());
            preparedStatement.setInt(2, contact.getUserId());
            preparedStatement.setString(3, contact.getFirstName());
            preparedStatement.setString(4, contact.getLastName());
            preparedStatement.setString(5, contact.getPhoneNumber());
            preparedStatement.setInt(6, contact.getGroupId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public synchronized boolean delete(Contact contact) {
        String query = "SELECT \"DeleteContact\"(?, ?)";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, contact.getUserId());
            preparedStatement.setInt(2, contact.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public synchronized boolean deleteGroup(int groupId) {
        String query = "SELECT \"RemoveGroupIdFromContacts\"(?)";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public synchronized Contact get(int id) {
        Contact contact = null;
        String query = "SELECT * FROM \"Contacts\" WHERE \"ContactID\" = ?";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            contact = new ContactMapper().getContact(resultSet);
        } catch (Exception e) {
            return null;
        }
        return contact;
    }

    public synchronized Set<Contact> getAll() {
        Set<Contact> contacts = new HashSet<>();
        String query = "SELECT * FROM \"Contacts\"";
        ContactMapper mapper = new ContactMapper();
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contacts.add(mapper.getContact(resultSet));
            }
        } catch (Exception e) {
            return null;
        }
        return contacts;
    }

    private class ContactMapper {
        Contact getContact(ResultSet resultSet) {
            EntityFactory entityFactory = Model.getInstance().getEntityFactory();
            Contact contact = null;
            Map<String, Object> params = new HashMap<>();

            try {
                if (resultSet.next()) {
                    params.put("id", resultSet.getInt("ContactID"));
                    params.put("firstName", resultSet.getString("FirstName"));
                    params.put("lastName", resultSet.getString("LastName"));
                    params.put("phoneNumber", resultSet.getString("PhoneNumber"));
                    params.put("groupId", resultSet.getInt("GroupID"));
                    params.put("userId", resultSet.getInt("UserID"));
                }
            } catch (SQLException e) {
                return null;
            }

            contact = (Contact) entityFactory.getContact(params);
            return contact;
        }
    }
}
