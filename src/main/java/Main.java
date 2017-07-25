import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view.View;

public class Main extends Application {
    public static void main(String[] args) {
        Model model = Model.getInstance();
        Controller controller = new Controller(model);
        View.setController(controller);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Parent secondary = FXMLLoader.load(getClass().getResource("contactList.fxml"));
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Contacts");
        secondaryStage.setScene(new Scene(secondary));
        secondaryStage.show();
    }
}
