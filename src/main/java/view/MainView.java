package view;

import controller.Controller;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.entities.Contact;
import model.entities.Group;
import util.Observer;

import java.util.HashMap;
import java.util.Map;

public class MainView {
    @FXML
    private TextField txtGroupName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhone;
    @FXML
    private ListView<Contact> lvContacts;
    @FXML
    private ListView<Group> lvGroups;
    @FXML
    private ComboBox<Group> cmbGroup;

    private Controller controller = View.getController();

    private Map<String, Object> oldContact = new HashMap<>();
    private Map<String, Object> oldGroup = new HashMap<>();

    @FXML
    public void initialize(){
        controller.getContactController().addObserver(new ContactsObserver());
        controller.getGroupController().addObserver(new GroupsObserver());
    }

    private void showAlert(String errorMessage) {
        new Alert(Alert.AlertType.ERROR, errorMessage).showAndWait();
    }

    public void onNewContactPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", txtFirstName.getText());
        params.put("lastName", txtLastName.getText());
        params.put("phoneNumber", txtPhone.getText());
        if (!cmbGroup.getSelectionModel().isEmpty()) {
            params.put("groupName", cmbGroup.getSelectionModel().selectedItemProperty().get().getGroupName());
        }
        controller.getContactController().add(params);
    }

    public void onSaveContactPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        if (oldContact == null) {
            showAlert("Select a contact from the list first");
            return;
        }
        params.put("oldFirstName", oldContact.get("firstName"));
        params.put("oldLastName", oldContact.get("lastName"));
        params.put("oldPhoneNumber", oldContact.get("phoneNumber"));
        params.put("oldGroupName", oldContact.get("groupName"));
        params.put("firstName", txtFirstName.getText());
        params.put("lastName", txtLastName.getText());
        params.put("phoneNumber", txtPhone.getText());
        if (!cmbGroup.getSelectionModel().isEmpty()) {
            params.put("groupName", cmbGroup.getSelectionModel().selectedItemProperty().get().getGroupName());
        }
        oldContact = params;
        controller.getContactController().update(params);
    }

    public void onDeleteContactPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstName", txtFirstName.getText());
        params.put("lastName", txtLastName.getText());
        params.put("phoneNumber", txtPhone.getText());
        if (!cmbGroup.getSelectionModel().isEmpty()) {
            params.put("groupName", cmbGroup.getSelectionModel().selectedItemProperty().get().getGroupName());
        }
        controller.getContactController().delete(params);
    }

    public void onContactSelect(MouseEvent mouseEvent) {
        if (lvContacts.getSelectionModel().isEmpty()) {
            return;
        }
        Contact contact = (Contact)lvContacts.getSelectionModel().getSelectedItem();
        txtFirstName.setText(contact.getFirstName());
        txtLastName.setText(contact.getLastName());
        txtPhone.setText(contact.getPhoneNumber());
        cmbGroup.getSelectionModel().select(contact.getGroup());
        oldContact.put("firstName", contact.getFirstName());
        oldContact.put("lastName", contact.getLastName());
        oldContact.put("phoneNumber", contact.getPhoneNumber());
        if (contact.getGroup() != null) {
            oldContact.put("groupName", contact.getGroup().getGroupName());
        }
    }

    public void onNewGroupPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupName", txtGroupName.getText());
        controller.getGroupController().add(params);
    }

    public void onSaveGroupPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("oldGroupName", oldGroup.get("groupName"));
        params.put("groupName", txtGroupName.getText());
        oldGroup = params;
        controller.getGroupController().update(params);
    }

    public void onDeleteGroupPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupName", txtGroupName.getText());
        controller.getGroupController().delete(params);
    }

    public void onGroupSelect(MouseEvent mouseEvent) {
        if (lvGroups.getSelectionModel().isEmpty()) {
            return;
        }
        Group group = (Group) lvGroups.getSelectionModel().getSelectedItem();
        txtGroupName.setText(group.getGroupName());
        oldGroup.put("groupName", group.getGroupName());
    }

    private class ContactsObserver implements Observer {
        public void update(Observable o) {
            lvContacts.setItems(FXCollections.observableArrayList((ObservableSet<Contact>) o));
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

    private class GroupsObserver implements Observer {
        public void update(Observable o) {
            cmbGroup.setItems(FXCollections.observableArrayList((ObservableSet<Group>) o));
            cmbGroup.setCellFactory(x -> new ListCell<Group>() {
                protected void updateItem(Group group, boolean empty) {
                    super.updateItem(group, empty);
                    if (group != null) {
                        setText(group.toString());
                    }
                }
            });
            lvGroups.setItems(FXCollections.observableArrayList((ObservableSet<Group>) o));
            lvGroups.setCellFactory(x -> new ListCell<Group>() {
                protected void updateItem(Group group, boolean empty) {
                    super.updateItem(group, empty);
                    if (group != null) {
                        setText(group.toString());
                    }
                }
            });
        }
    }
}
