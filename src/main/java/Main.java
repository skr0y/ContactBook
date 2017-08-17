import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view.View;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        Model model = Model.getInstance();
        Controller controller = new Controller(model);
        View.setController(controller);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.runLater(new Runnable() {
            public void run() {
                showStage();
                showStage();
                showStage();
            }
        });
    }

    private void showStage() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
