package controller;

import model.Model;

public class Controller {
    private GroupController groupController;
    private ContactController contactController;

    public Controller(Model model) {
        groupController = new GroupController(model);
        contactController = new ContactController(model);
    }

    public GroupController getGroupController() {
        return groupController;
    }

    public ContactController getContactController() {
        return contactController;
    }
}
