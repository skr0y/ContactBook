package view;

import controller.Controller;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.entities.Contact;
import util.Observer;

public class ContactsView {
    @FXML
    private ListView<Contact> lvContacts;

    private Controller controller = View.getController();

    @FXML
    public void initialize(){
        controller.getContactController().addObserver(new ContactsObserver());
    }

    private class ContactsObserver implements Observer {
        public void update(Observable o) {
            ObservableList<Contact> list = FXCollections.observableArrayList();
            list.addAll((ObservableSet<Contact>) o);
            lvContacts.setItems(list);
            lvContacts.setCellFactory(x -> new ListCell<Contact>() {
                protected void updateItem(Contact contact, boolean empty) {
                    super.updateItem(contact, empty);
                    if (contact != null) {
                        setText(contact.toString());
                    }
                }
            });
        }
    }
}
