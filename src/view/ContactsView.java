package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ContactsView {
    @FXML
    ListView lvContacts;

    private static Controller controller;

    static void setController(Controller controller1) {
        controller = controller1;
    }
}
