import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(getClass().getResource("/views/Mockup_SceneBuilder_Start.fxml"));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Mockup_SceneBuilder_Start.fxml"));
        Parent root = loader.load();


        primaryStage.setTitle("WaterWatch Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
