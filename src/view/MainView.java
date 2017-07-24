package view;

import controller.Controller;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.entities.Contact;
import model.entities.Group;
import util.Observer;

import java.util.ArrayList;

public class MainView {
    @FXML
    TextField txtFirstName;
    @FXML
    TextField txtLastName;
    @FXML
    TextField txtPhone;
    @FXML
    ListView<Contact> lvContacts;
    @FXML
    ListView<Group> lvGroups;

    private static Controller controller;
    private Contact contact;
    private Group group;

    static void setController(Controller controller1) {
        controller = controller1;
    }

    private void showAlert(String errorMessage) {
        new Alert(Alert.AlertType.ERROR, errorMessage).showAndWait();
    }

    public void onNewContactPressed(ActionEvent actionEvent) {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhone.setText("");
        contact = controller.getContactController().getNew();
    }

    public void onSaveContactPressed(ActionEvent actionEvent) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String phone = txtPhone.getText();
        if (firstName == null || firstName.isEmpty()) {
            showAlert("First name cannot be empty");
        }
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setPhoneNumber(phone);
        controller.getContactController().update(contact);
    }

    public void onDeleteContactPressed(ActionEvent actionEvent) {
        if (contact == null) {
            showAlert("Select a contact from the list first");
        } else {
            controller.getContactController().delete(contact);
        }
    }

    public void onContactSelect(MouseEvent mouseEvent) {
        contact = (Contact)lvContacts.getSelectionModel().getSelectedItem();
        txtFirstName.setText(contact.getFirstName());
        txtLastName.setText(contact.getLastName());
        txtPhone.setText(contact.getPhoneNumber());
    }

    private class ContactsObserver implements Observer {
        public void update(Observable o) {
            lvContacts.setItems((ObservableList<Contact>) new ArrayList<Contact>((ObservableSet<Contact>) o));
            lvContacts.setCellFactory(x -> new ListCell<Contact>() {
                protected void updateItem(Contact contact, boolean empty) {
                    super.updateItem(contact, empty);
                    setText(contact.toString());
                }
            });
        }
    }

    private class GroupsObserver implements Observer {
        public void update(Observable o) {
            lvGroups.setItems((ObservableList<Group>) new ArrayList<Group>((ObservableSet<Group>) o));
            lvGroups.setCellFactory(x -> new ListCell<Group>() {
                protected void updateItem(Group group, boolean empty) {
                    super.updateItem(group, empty);
                    setText(group.toString());
                }
            });
        }
    }
}
