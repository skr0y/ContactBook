package controller;

import model.Model;
import model.dao.UserDAO;
import model.entities.EntityFactory;
import model.entities.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserController {
    private Model model;

    private UserDAO userDAO;
    private EntityFactory entityFactory;

    UserController(Model model) {
        this.model = model;
        userDAO = model.getDaoFactory().getUserDAO();
        entityFactory = model.getEntityFactory();
    }

    public boolean add(Map<String, Object> params) {
        User user = (User) entityFactory.getUser(params);
        return userDAO.add(user);
    }

    public boolean delete(int userId) {
        User user = userDAO.get(userId);
        return userDAO.delete(user);
    }

    public boolean update(Map<String, Object> params) {
        User user = (User) entityFactory.getUser(params);
        return userDAO.update(user);
    }

    public boolean checkCredentials(String login, String password) {
        return userDAO.checkCredentials(login, password);
    }

    public Map<String, Object> get(int userId) {
        User user = userDAO.get(userId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("login", user.getLogin());
        params.put("password", user.getPassword());
        return params;
    }

    public Map<String, Object> get(String login) {
        User user = userDAO.get(login);
        return get(user.getId());
    }

    public Set<Map<String, Object>> getAll() {
        Set<Map<String, Object>> all = new HashSet<>();
        for (User user : userDAO.getAll()) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", user.getId());
            params.put("login", user.getLogin());
            params.put("password", user.getPassword());
            all.add(params);
        }
        return all;
    }
}
