package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.*;
import java.util.stream.Collectors;

public class MainView {
    @FXML
    public ComboBox<Map<String, Object>> cmbGroupFilter;
    @FXML
    public TextField txtSearch;
    @FXML
    private Label lblContactID;
    @FXML
    private Label lblGroupID;
    @FXML
    private TextField txtGroupName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhone;
    @FXML
    private ListView<Map<String, Object>> lvContacts;
    @FXML
    private ListView<Map<String, Object>> lvGroups;
    @FXML
    private ComboBox<Map<String, Object>> cmbGroup;

    private Controller controller;

    @FXML
    public void initialize(){
        controller = View.getController();

        controller.getContactController().addObserver(new ContactsObserver());
        controller.getGroupController().addObserver(new GroupsObserver());

        loadContacts();
        loadGroups();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch();
        });
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
            params.put("groupId", cmbGroup.getSelectionModel().selectedItemProperty().get().get("groupId"));
        }
        if (!controller.getContactController().add(params)) {
            showAlert("An error occurred while adding new contact");
        }
    }

    public void onSaveContactPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", Integer.parseInt(lblContactID.getText()));
        params.put("firstName", txtFirstName.getText());
        params.put("lastName", txtLastName.getText());
        params.put("phoneNumber", txtPhone.getText());
        if (!cmbGroup.getSelectionModel().isEmpty()) {
            params.put("groupId", cmbGroup.getSelectionModel().selectedItemProperty().get().get("groupId"));
        }
        if (!controller.getContactController().update(params)) {
            showAlert("An error occurred while saving contact");
        }
    }

    public void onDeleteContactPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", Integer.parseInt(lblContactID.getText()));
        params.put("firstName", txtFirstName.getText());
        params.put("lastName", txtLastName.getText());
        params.put("phoneNumber", txtPhone.getText());
        if (!cmbGroup.getSelectionModel().isEmpty()) {
            params.put("groupId", cmbGroup.getSelectionModel().selectedItemProperty().get().get("groupId"));
        }
        if (!controller.getContactController().delete(params)) {
            showAlert("An error occurred while deleting contact");
        }
    }

    public void onContactSelect(MouseEvent mouseEvent) {
        if (lvContacts.getSelectionModel().isEmpty()) {
            return;
        }
        Map<String, Object> contact = lvContacts.getSelectionModel().getSelectedItem();
        lblContactID.setText(Integer.toString((int)contact.get("id")));
        txtFirstName.setText((String) contact.get("firstName"));
        txtLastName.setText((String) contact.get("lastName"));
        txtPhone.setText((String) contact.get("phoneNumber"));
        cmbGroup.getSelectionModel().select(cmbGroup.getItems().stream().filter(x -> x.get("groupId") == contact.get("groupId")).findFirst().orElse(null));
    }

    public void onNewGroupPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupName", txtGroupName.getText());
        if (!controller.getGroupController().add(params)) {
            showAlert("An error occurred while adding new group");
        }
    }

    public void onSaveGroupPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", Integer.parseInt(lblGroupID.getText()));
        params.put("groupName", txtGroupName.getText());
        if (!controller.getGroupController().update(params)) {
            showAlert("An error occurred while saving group");
        }
    }

    public void onDeleteGroupPressed(ActionEvent actionEvent) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", Integer.parseInt(lblGroupID.getText()));
        params.put("groupName", txtGroupName.getText());
        if (!controller.getGroupController().delete(params)) {
            showAlert("An error occurred while deleting group");
        }
    }

    public void onGroupSelect(MouseEvent mouseEvent) {
        if (lvGroups.getSelectionModel().isEmpty()) {
            return;
        }
        Map<String, Object> group = lvGroups.getSelectionModel().getSelectedItem();
        lblGroupID.setText(Integer.toString((int)group.get("id")));
        txtGroupName.setText((String) group.get("groupName"));
    }

    private void onSearch() {
        String query = txtSearch.getText();
        Set<Map<String, Object>> result = controller.getContactController().getAll().stream().filter(x -> ((String) x.get("firstName")).contains(query)).collect(Collectors.toSet());
        ObservableList<Map<String, Object>> list = FXCollections.observableArrayList(result);
        lvContacts.setItems(list);
    }

    public void onGroupFilter(ActionEvent actionEvent) {
        if (!cmbGroupFilter.getSelectionModel().getSelectedItem().isEmpty()) {
            int groupId = (int) cmbGroupFilter.getSelectionModel().getSelectedItem().get("id");
            Set<Map<String, Object>> result = controller.getContactController().getAll().stream().filter(x -> ((int) x.get("id")) == groupId).collect(Collectors.toSet());
            ObservableList<Map<String, Object>> list = FXCollections.observableArrayList(result);
            lvContacts.setItems(list);
        }
    }

    private void loadGroups() {
        cmbGroup.setItems(FXCollections.observableArrayList(controller.getGroupController().getAll()));
        cmbGroup.setCellFactory(x -> new ListCell<Map<String, Object>>() {
            protected void updateItem(Map<String, Object> group, boolean empty) {
                super.updateItem(group, empty);
                if (group != null) {
                    setText((String)group.get("groupName"));
                } else {
                    setText("");
                }
            }
        });
        cmbGroupFilter.setItems(FXCollections.observableArrayList(controller.getGroupController().getAll()));
        cmbGroupFilter.setCellFactory(x -> new ListCell<Map<String, Object>>() {
            protected void updateItem(Map<String, Object> group, boolean empty) {
                super.updateItem(group, empty);
                if (group != null) {
                    setText((String)group.get("groupName"));
                } else {
                    setText("");
                }
            }
        });
        lvGroups.setItems(FXCollections.observableArrayList(controller.getGroupController().getAll()));
        lvGroups.setCellFactory(x -> new ListCell<Map<String, Object>>() {
            protected void updateItem(Map<String, Object> group, boolean empty) {
                super.updateItem(group, empty);
                if (group != null) {
                    setText((String)group.get("groupName"));
                } else {
                    setText("");
                }
            }
        });
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
                } else {
                    setText("");
                }
            }
        });
    }

    private class ContactsObserver implements Observer {
        public void update(Observable o, Object arg) {
            loadContacts();
        }
    }

    private class GroupsObserver implements Observer {
        public void update(Observable o, Object arg) {
            loadGroups();
        }
    }
}
