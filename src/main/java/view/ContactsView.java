package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ContactsView {
    @FXML
    private ListView<Map<String, Object>> lvContacts;

    private Controller controller = View.getController();

    @FXML
    public void initialize(){
        controller.getContactController().addObserver(new ContactsObserver());

        loadContacts();
    }

    private void loadContacts() {
        lvContacts.setItems(FXCollections.observableArrayList(controller.getContactController().getAll()));
        lvContacts.setCellFactory(x -> new ListCell<Map<String, Object>>() {
            protected void updateItem(Map<String, Object> contact, boolean empty) {
                super.updateItem(contact, empty);
                if (contact != null) {
                    if (contact.get("lastName") == null) {
                        setText((String) contact.get("firstName"));
                    } else {
                        setText(String.format("%1s %2s", contact.get("firstName"), contact.get("lastName")).trim());
                    }
                }
            }
        });
    }

    private class ContactsObserver implements Observer {
        public void update(Observable o, Object arg) {
            loadContacts();
        }
    }
}
