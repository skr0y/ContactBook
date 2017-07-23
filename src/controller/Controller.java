package controller;

import model.Model;
import view.View;

public class Controller {
    private GroupController groupController;
    private ContactController contactController;

    public Controller(Model model, View view) {
        groupController = new GroupController(model, view);
        contactController = new ContactController(model, view);
    }

    public GroupController getGroupController() {
        return groupController;
    }

    public ContactController getContactController() {
        return contactController;
    }
}
