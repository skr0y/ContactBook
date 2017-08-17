package model.dao;

import model.Model;
import model.entities.Contact;
import model.entities.EntityFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContactDAODatabaseImpl implements ContactDAO {
    private String url = "jdbc:postgresql:ContactDB";
    private String username = "postgres";
    private String password = "postgres";

    ContactDAODatabaseImpl() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean add(Contact contact) {
        String query = "SELECT \"AddContact\"(?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, contact.getUserId());
            preparedStatement.setString(2, contact.getFirstName());
            preparedStatement.setString(3, contact.getLastName());
            preparedStatement.setString(4, contact.getPhoneNumber());
            preparedStatement.setInt(5, contact.getGroupId());
            ResultSet resultSet = preparedStatement.executeQuery();
            contact.setId(resultSet.getInt("ContactID"));
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized boolean update(Contact contact) {
        String query = "SELECT \"UpdateContact\"(?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, contact.getId());
            preparedStatement.setInt(2, contact.getUserId());
            preparedStatement.setString(3, contact.getFirstName());
            preparedStatement.setString(4, contact.getLastName());
            preparedStatement.setString(5, contact.getPhoneNumber());
            preparedStatement.setInt(6, contact.getGroupId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized boolean delete(Contact contact) {
        String query = "SELECT \"DeleteContact\"(?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, contact.getUserId());
            preparedStatement.setInt(2, contact.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized boolean deleteGroup(int groupId) {
        String query = "SELECT \"RemoveGroupIdFromContacts\"(?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, groupId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized Contact get(int id) {
        Contact contact = null;
        String query = "SELECT * FROM \"Contacts\" WHERE \"ContactID\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            contact = new ContactMapper().getContact(resultSet);
        } catch (SQLException e) {
            return null;
        }
        return contact;
    }

    public synchronized Set<Contact> getAll() {
        Set<Contact> contacts = new HashSet<>();
        String query = "SELECT * FROM \"Contacts\"";
        ContactMapper mapper = new ContactMapper();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contacts.add(mapper.getContact(resultSet));
            }
        } catch (SQLException e) {
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
                params.put("id", resultSet.getInt("ContactID"));
                params.put("firstName", resultSet.getString("FirstName"));
                params.put("lastName", resultSet.getString("LastName"));
                params.put("phoneNumber", resultSet.getString("PhoneNumber"));
                params.put("groupId", resultSet.getInt("GroupID"));
                params.put("userId", resultSet.getInt("UserID"));
            } catch (SQLException e) {
                return null;
            }

            contact = (Contact) entityFactory.getContact(params);
            return contact;
        }
    }
}
