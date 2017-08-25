package controller;

import model.Model;
import model.dao.ContactDAO;
import model.dao.GroupDAO;
import model.entities.EntityFactory;
import model.entities.Group;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupController {
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
        return groupDAO.add(group);
    }

    public boolean delete(int groupId) {
        Group group = groupDAO.get(groupId);
        return group != null && contactDAO.deleteGroup(group.getId()) && groupDAO.delete(group);
    }

    public boolean update(Map<String, Object> params) {
        Group group = (Group) entityFactory.getGroup(params);
        return groupDAO.update(group);
    }

    public Set<Map<String, Object>> getAll() {
        Set<Map<String, Object>> all = new HashSet<>();
        for (Group group : groupDAO.getAll()) {
            Map<String, Object> groupMap = new HashMap<>();
            groupMap.put("id", group.getId());
            groupMap.put("groupName", group.getGroupName());
            all.add(groupMap);
        }
        return all;
    }

    public Set<Map<String, Object>> getAll(int userId) {
        return getAll().stream().filter(x -> (int) x.get("userId") == userId).collect(Collectors.toSet());
    }
}
