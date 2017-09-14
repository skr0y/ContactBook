package service;

import dao.UserDAO;
import entities.EntityFactory;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EntityFactory entityFactory;

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

    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> all = new ArrayList<>();
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
