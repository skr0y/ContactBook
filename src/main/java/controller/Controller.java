package controller;

import model.Model;

public class Controller {
    private GroupController groupController;
    private ContactController contactController;
    private UserController userController;

    public Controller(Model model) {
        groupController = new GroupController(model);
        contactController = new ContactController(model);
        userController = new UserController(model);
    }

    public GroupController getGroupController() {
        return groupController;
    }

    public ContactController getContactController() {
        return contactController;
    }

    public UserController getUserController() {
        return userController;
    }
}
