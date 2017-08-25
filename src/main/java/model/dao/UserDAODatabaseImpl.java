package model.dao;

import model.Model;
import model.entities.EntityFactory;
import model.entities.User;

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

public class UserDAODatabaseImpl implements UserDAO {
    private static UserDAODatabaseImpl instance;

    static synchronized UserDAODatabaseImpl getInstance() {
        if (instance == null) {
            instance = new UserDAODatabaseImpl();
        }
        return instance;
    }

    public synchronized boolean add(User user) {
        String query = "SELECT \"AddUser\"(?, ?)";
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            user.setId(resultSet.getInt("UserID"));
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public synchronized boolean update(User user) {
        String query = "SELECT \"UpdateUserPasswordById\"(?, ?)";
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public synchronized boolean delete(User user) {
        String query = "SELECT \"DeleteUser\"(?)";
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public synchronized User get(int id) {
        User user = null;
        String query = "SELECT * FROM \"Users\" WHERE \"UserID\" = ?";
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = new UserMapper().getUser(resultSet);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public synchronized User get(String login) {
        User user = null;
        String query = "SELECT * FROM \"Users\" WHERE \"Login\" = ?";
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = new UserMapper().getUser(resultSet);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public boolean checkCredentials(String login, String password) {
        User user = get(login);
        return user != null && user.getPassword().equals(password);
    }

    public synchronized Set<User> getAll() {
        Set<User> users = new HashSet<>();
        String query = "SELECT * FROM \"Users\"";
        UserMapper mapper = new UserMapper();
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(mapper.getUser(resultSet));
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
