package model.dao;

import model.Model;
import model.entities.EntityFactory;
import model.entities.User;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserDAODatabaseImpl implements UserDAO {
    private static UserDAODatabaseImpl instance;

    static synchronized UserDAODatabaseImpl getInstance() {
        if (instance == null) {
            instance = new UserDAODatabaseImpl();
        }
        return instance;
    }

    private String url = "jdbc:postgresql:ContactDB";
    private String username = "postgres";
    private String password = "postgres";

    private UserDAODatabaseImpl() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean add(User user) {
        String query = "SELECT \"AddUser\"(?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            user.setId(resultSet.getInt("UserID"));
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized boolean update(User user) {
        String query = "SELECT \"UpdateUserPasswordById\"(?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized boolean delete(User user) {
        String query = "SELECT \"DeleteUser\"(?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized User get(int id) {
        User user = null;
        String query = "SELECT * FROM \"Users\" WHERE \"UserID\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = new UserMapper().getUser(resultSet);
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    public synchronized User getByLogin(String login) {
        User user = null;
        String query = "SELECT * FROM \"Users\" WHERE \"Login\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = new UserMapper().getUser(resultSet);
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    public boolean checkCredentials(String login, String password) {
        User user = getByLogin(login);
        return user != null && user.getPassword().equals(password);
    }

    public synchronized Set<User> getAll() {
        Set<User> users = new HashSet<>();
        String query = "SELECT * FROM \"Users\"";
        UserMapper mapper = new UserMapper();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(mapper.getUser(resultSet));
            }
        } catch (SQLException e) {
            return null;
        }
        return users;
    }

    private class UserMapper {
        User getUser(ResultSet resultSet) {
            EntityFactory entityFactory = Model.getInstance().getEntityFactory();
            User user = null;
            Map<String, Object> params = new HashMap<>();

            try {
                if (resultSet.next()) {
                    params.put("id", resultSet.getInt("UserID"));
                    params.put("login", resultSet.getString("Login"));
                    params.put("password", resultSet.getString("Password"));
                }
            } catch (SQLException e) {
                return null;
            }

            user = (User) entityFactory.getUser(params);
            return user;
        }
    }
}
