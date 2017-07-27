package controller;

import model.Model;
import model.dao.GroupDAO;
import model.entities.EntityFactory;
import model.entities.Group;
import util.Observer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GroupController {
    private Model model;

    private GroupDAO dao;
    private EntityFactory factory;

    GroupController(Model model) {
        this.model = model;
        dao = model.getDaoFactory().getGroupDAO();
        factory = model.getEntityFactory();
    }

    public void addObserver(Observer observer) {
        dao.addObserver(observer);
    }

    public boolean add(Map<String, Object> params) {
        Set<Group> groups = new HashSet<>(dao.getAll());
        Group newGroup = (Group) factory.getGroup(params);
        groups.add(newGroup);
        return dao.update(groups);
    }

    public boolean delete(Map<String, Object> params) {
        Set<Group> groups = new HashSet<>(dao.getAll());
        Group delGroup = null;
        for (Group group : groups) {
            if (group.getGroupName().equals(params.get("groupName"))) {
                delGroup = group;
                break;
            }
        }
        if (delGroup != null) {
            groups.remove(delGroup);
            return dao.update(groups);
        }
        return false;
    }

    public boolean update(Map<String, Object> params) {
        Set<Group> groups = new HashSet<>(dao.getAll());
        Group oldGroup = null;
        Group newGroup;

        for (Group group : groups) {
            if (group.getGroupName().equals(params.get("oldGroupName"))) {
                oldGroup = group;
                break;
            }
        }
        if (oldGroup == null) {
            return false;
        }
        groups.remove(oldGroup);
        newGroup = (Group) factory.getGroup(params);
        groups.add(newGroup);
        return dao.update(groups);
    }

    public Set<Group> getAll() {
        return dao.getAll();
    }
}
