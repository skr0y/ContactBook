package controller;

import model.Model;
import model.dao.AdminDAO;

public class AdminController {
    private Model model;

    private AdminDAO adminDAO;

    AdminController(Model model) {
        this.model = model;
        adminDAO = model.getDaoFactory().getAdminDAO();
    }

    public int totalUsers() {
        return adminDAO.totalUsers();
    }

    public int contactsCountByUser(int userId) {
        return adminDAO.contactsCountByUser(userId);
    }

    public int groupsCountByUser(int userId) {
        return adminDAO.groupsCountByUser(userId);
    }

    public int averageContactsPerGroup() {
        return adminDAO.averageContactsPerGroup();
    }

    public int averageContactsPerUser() {
        return adminDAO.averageContactsPerUser();
    }

    public int inactiveUsersCount() {
        return adminDAO.inactiveUsersCount();
    }

}
