package controller;

import model.Model;
import model.dao.ContactDAO;
import model.dao.GroupDAO;
import model.entities.EntityFactory;
import model.entities.Group;

import java.util.*;

public class GroupController extends Observable {
    private Model model;

    private GroupDAO groupDAO;
    private ContactDAO contactDAO;
    private EntityFactory entityFactory;

    GroupController(Model model) {
        this.model = model;
        groupDAO = model.getDaoFactory().getGroupDAO();
        contactDAO = model.getDaoFactory().getContactDAO();
        entityFactory = model.getEntityFactory();
    }

    public boolean add(Map<String, Object> params) {
        Group group = (Group) entityFactory.getGroup(params);
        boolean result = groupDAO.add(group);
        setChanged();
        notifyObservers();
        return result;
    }

    public boolean delete(Map<String, Object> params) {
        Group group = groupDAO.get((int) params.get("groupId"));
        boolean result = group != null && contactDAO.deleteGroup(group) && groupDAO.delete(group);
        setChanged();
        notifyObservers();
        return result;
    }

    public boolean update(Map<String, Object> params) {
        Group group = (Group) entityFactory.getGroup(params);
        boolean result = groupDAO.update(group);
        setChanged();
        notifyObservers();
        return result;
    }

    public Set<Map<String, Object>> getAll() {
        Set<Map<String, Object>> all = new HashSet<>();
        for (Group group : groupDAO.getAll()) {
            Map<String, Object> groupMap = new HashMap<>();
            groupMap.put("groupId", group.getId());
            groupMap.put("groupName", group.getGroupName());
            all.add(groupMap);
        }
        return all;
    }
}
