package model.dao;

import model.Model;
import model.entities.EntityFactory;
import model.entities.Group;

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

public class GroupDAODatabaseImpl implements GroupDAO {
    private static GroupDAODatabaseImpl instance;

    static synchronized GroupDAODatabaseImpl getInstance() {
        if (instance == null) {
            instance = new GroupDAODatabaseImpl();
        }
        return instance;
    }

    public synchronized boolean add(Group group) {
        String query = "SELECT \"AddGroup\"(?, ?)";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setInt(2, group.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            group.setId(resultSet.getInt("GroupID"));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public synchronized boolean update(Group group) {
        String query = "SELECT \"UpdateGroup\"(?, ?, ?)";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, group.getId());
            preparedStatement.setString(2, group.getGroupName());
            preparedStatement.setInt(3, group.getUserId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public synchronized boolean delete(Group group) {
        String query = "SELECT \"DeleteGroup\"(?, ?)";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, group.getUserId());
            preparedStatement.setInt(2, group.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public synchronized Group get(int id) {
        Group group = null;
        String query = "SELECT * FROM \"Groups\" WHERE \"GroupID\" = ?";
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            group = new GroupMapper().getGroup(resultSet);
        } catch (Exception e) {
            return null;
        }
        return group;
    }

    public synchronized Set<Group> getAll() {
        Set<Group> groups = new HashSet<>();
        String query = "SELECT * FROM \"Groups\"";
        GroupMapper mapper = new GroupMapper();
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groups.add(mapper.getGroup(resultSet));
            }
        } catch (Exception e) {
            return null;
        }
        return groups;
    }

    private class GroupMapper {
        Group getGroup(ResultSet resultSet) {
            EntityFactory entityFactory = Model.getInstance().getEntityFactory();
            Group group = null;
            Map<String, Object> params = new HashMap<>();

            try {
                if (resultSet.next()) {
                    params.put("id", resultSet.getInt("GroupID"));
                    params.put("groupName", resultSet.getString("GroupName"));
                    params.put("userId", resultSet.getInt("UserID"));
                }
            } catch (SQLException e) {
                return null;
            }

            group = (Group) entityFactory.getGroup(params);
            return group;
        }
    }
}
