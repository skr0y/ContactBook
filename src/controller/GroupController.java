package controller;

import model.Model;
import model.dao.GroupDAO;
import model.entities.Group;
import view.View;

import java.util.Set;

public class GroupController {
    private Model model;
    private View view;

    private GroupDAO dao;

    GroupController(Model model, View view) {
        this.view = view;
        this.model = model;
        dao = model.getDAOProvider().getGroupDAO();
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

}
