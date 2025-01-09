package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeController {

    public void goToConnect(ActionEvent event) {
        SceneController.sceneswitch("/views/Mockup_SceneBuilder_Connect.fxml");
    }
    public void goToMap(ActionEvent event) {
        SceneController.sceneswitch("/views/Mockup_SceneBuilder_Map.fxml");
    }
    public void goToData(ActionEvent event) {
        SceneController.sceneswitch("/views/Mockup_SceneBuilder_Data.fxml");
    }


    public void goToSupport(ActionEvent event) {
        SceneController.sceneswitch("/views/Mockup_SceneBuilder_Support.fxml");
    }
    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }
}
