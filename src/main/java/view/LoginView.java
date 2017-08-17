package view;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginView {
    public TextField txtLogin;
    public TextField txtPassword;

    private Controller controller;

    public void initialize() {
        controller = View.getController();
    }

    private void showAlert(String errorMessage) {
        new Alert(Alert.AlertType.ERROR, errorMessage).showAndWait();
    }

    public void onStartPressed(MouseEvent mouseEvent) {
        String login = txtLogin.getText();
        String password = txtPassword.getText();
        if (controller.getUserController().checkCredentials(login, password)) {
            try {
                startApp();
                Stage stage = (Stage) txtLogin.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                showAlert("Error starting application");
            }
        } else {
            showAlert("Invalid login or password");
        }
    }

    private void startApp() throws IOException {
        Parent main = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
        Stage mainStage = new Stage();
        mainStage.setTitle("Contact Book");
        mainStage.setScene(new Scene(main));
        mainStage.show();

        Parent secondary = FXMLLoader.load(getClass().getClassLoader().getResource("contactList.fxml"));
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Contacts");
        secondaryStage.setScene(new Scene(secondary));
        secondaryStage.show();
    }
}
