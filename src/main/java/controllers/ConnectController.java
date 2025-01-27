package controllers;

import javafx.event.ActionEvent;

public class ConnectController {
    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }
    public void goToLogin() {
        SceneController.sceneswitch("/views/Login.fxml"); // Switch to the login scene
    }

    public void goToRegister() {
        SceneController.sceneswitch("/views/registreren.fxml"); // Switch to the registration scene
    }
}
