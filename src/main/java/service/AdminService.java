package service;

import dao.AdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("adminService")
@Transactional
public class AdminService {

    @Autowired
    private AdminDAO adminDAO;

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
