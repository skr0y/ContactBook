package controller;

import model.Model;
import model.dao.UserDAO;
import model.entities.EntityFactory;
import model.entities.User;

import java.util.*;

public class UserController extends Observable {
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
        boolean result = userDAO.add(user);
        setChanged();
        notifyObservers();
        return result;
    }

    public boolean delete(Map<String, Object> params) {
        User user = userDAO.get((int) params.get("id"));
        boolean result = user != null && userDAO.delete(user);
        setChanged();
        notifyObservers();
        return result;
    }

    public boolean update(Map<String, Object> params) {
        User user = (User) entityFactory.getUser(params);
        boolean result = userDAO.update(user);
        setChanged();
        notifyObservers();
        return result;
    }

    public boolean checkCredentials(String login, String password) {
        return userDAO.checkCredentials(login, password);
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
