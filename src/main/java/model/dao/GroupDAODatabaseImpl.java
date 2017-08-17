package model.dao;

import model.Model;
import model.entities.EntityFactory;
import model.entities.Group;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GroupDAODatabaseImpl implements GroupDAO {
    private String url = "jdbc:postgresql:ContactDB";
    private String username = "postgres";
    private String password = "postgres";

    GroupDAODatabaseImpl() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean add(Group group) {
        String query = "SELECT \"AddGroup\"(?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setInt(2, group.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            group.setId(resultSet.getInt("GroupID"));
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized boolean update(Group group) {
        String query = "SELECT \"UpdateGroup\"(?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, group.getId());
            preparedStatement.setString(2, group.getGroupName());
            preparedStatement.setInt(3, group.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized boolean delete(Group group) {
        String query = "SELECT \"DeleteGroup\"(?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, group.getUserId());
            preparedStatement.setInt(2, group.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public synchronized Group get(int id) {
        Group group = null;
        String query = "SELECT * FROM \"Groups\" WHERE \"GroupID\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            group = new GroupMapper().getGroup(resultSet);
        } catch (SQLException e) {
            return null;
        }
        return group;
    }

    public synchronized Set<Group> getAll() {
        Set<Group> groups = new HashSet<>();
        String query = "SELECT * FROM \"Groups\"";
        GroupMapper mapper = new GroupMapper();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groups.add(mapper.getGroup(resultSet));
            }
        } catch (SQLException e) {
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
                params.put("id", resultSet.getInt("GroupID"));
                params.put("groupName", resultSet.getString("GroupName"));
                params.put("userId", resultSet.getInt("UserID"));
            } catch (SQLException e) {
                return null;
            }

            group = (Group) entityFactory.getGroup(params);
            return group;
        }
    }
}
