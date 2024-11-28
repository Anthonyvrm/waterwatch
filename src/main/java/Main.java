import controllers.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage)

    {
        SceneController.setStage(primaryStage);
        SceneController.sceneswitch("/main/resources/views/StartScherm_Scenebuilder.fxml");

        primaryStage.setTitle("WaterWatch Application");
        primaryStage.show();
        // dit is eigenlijk hoe we nu de scenes switchen maar dan ff in 1 regel door de scenecontroller
    }

    public static void main(String[] args) {
        launch(args);
    }
}
