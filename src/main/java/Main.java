import controllers.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements Runnable{
    @Override
    public void start(Stage primaryStage)

    {
        SceneController.setStage(primaryStage);
        SceneController.sceneswitch("/main/resources/views/StartScherm_Scenebuilder.fxml");

        primaryStage.setTitle("WaterWatch Application");
        primaryStage.show();
        Main obj = new Main();
        Thread thread = new Thread(obj);
        thread.start();
        // dit is eigenlijk hoe we nu de scenes switchen maar dan ff in 1 regel door de scenecontroller
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        SQLInserter.begin();
    }

    @Override
    public void stop() throws Exception {
        SQLInserter.setRunning(false);
        super.stop();
    }
}
