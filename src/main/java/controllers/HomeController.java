package controllers;

import javafx.event.ActionEvent;
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
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "Select * From Product";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()) {
                System.out.println(queryOutput.getString("naam"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void goToSupport(ActionEvent event) {
        SceneController.sceneswitch("/views/Mockup_SceneBuilder_Support.fxml");
    }
    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }
}
