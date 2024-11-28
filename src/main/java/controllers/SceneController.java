package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;




public class SceneController {
    private static Stage stage;

    // hier wordt de stage geset
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    // hier worden de scenes geswitched ngl ik probeerder de stacktrace te vermeiden maar copilot will heel graag dat je die foutcodes krijgt valid i guess
    public static void sceneswitch(String fxmlpath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlpath));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}