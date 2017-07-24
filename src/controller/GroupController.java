package controller;

import model.Model;
import model.dao.GroupDAO;
import model.entities.EntityFactory;
import model.entities.Group;
import util.Observer;

import java.util.Set;

public class GroupController {
    private Model model;

    private GroupDAO dao;
    private EntityFactory factory;

    GroupController(Model model) {
        this.model = model;
        dao = model.getDAOProvider().getGroupDAO();
        factory = model.getFactory();
    }

    public void addObserver(Observer observer) {
        dao.addObserver(observer);
    }

    public Group getNew() {
        return (Group)model.getFactory().getGroup();
    }

    public boolean add(Group group) {
        Set<Group> groups = dao.getAll();
        dao.update(groups);
        return dao.save();
    }

    public boolean delete(Group group) {
        Set<Group> groups = dao.getAll();
        groups.remove(group);
        return dao.update(groups);
    }

    public boolean update(Group group) {
        Set<Group> groups = dao.getAll();
        groups.remove(group);
        groups.add(group);
        return dao.update(groups);
    }

    public Set<Group> getAll() {
        return dao.getAll();
    }
}
