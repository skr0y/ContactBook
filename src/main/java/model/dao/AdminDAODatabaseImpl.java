package model.dao;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAODatabaseImpl implements AdminDAO {
    private static AdminDAODatabaseImpl instance;

    static synchronized AdminDAODatabaseImpl getInstance() {
        if (instance == null) {
            instance = new AdminDAODatabaseImpl();
        }
        return instance;
    }

    public synchronized int totalUsers() {
        String query = "SELECT \"UserCount\"()";
        int result = 0;
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.getInt(1);
        } catch (Exception e) {
            return result;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public synchronized int contactsCountByUser(int userId) {
        String query = "SELECT \"ContactsCountByUser\"(?)";
        int result = 0;
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (Exception e) {
            return result;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public synchronized int groupsCountByUser(int userId) {
        String query = "SELECT \"GroupsCountByUser\"(?)";
        int result = 0;
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (Exception e) {
            return result;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public synchronized int averageContactsPerGroup() {
        String query = "SELECT \"AverageContactsPerGroup\"()";
        int result = 0;
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.getInt(1);
        } catch (Exception e) {
            return result;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public synchronized int averageContactsPerUser() {
        String query = "SELECT \"AverageContactsPerUser\"()";
        int result = 0;
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.getInt(1);
        } catch (Exception e) {
            return result;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public synchronized int inactiveUsersCount() {
        String query = "SELECT \"InactiveUsersCount\"()";
        int result = 0;
        Connection connection = null;
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.getInt(1);
        } catch (Exception e) {
            return result;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
