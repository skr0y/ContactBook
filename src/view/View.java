package view;

import controller.Controller;

public class View {
    public View(Controller controller) {
        MainView.setController(controller);
        ContactsView.setController(controller);
    }
}
