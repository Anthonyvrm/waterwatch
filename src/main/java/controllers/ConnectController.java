package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectController implements Initializable {
    @FXML Text Accountname;
    @FXML Button loginButton;
    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }
    public void goToLogin() {
        SceneController.sceneswitch("/views/Login.fxml"); // Switch to the login scene
    }

    public void goToRegister() {
        SceneController.sceneswitch("/views/registreren.fxml"); // Switch to the registration scene
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (AccountInfo.getCurrentUsername().isEmpty()){
            Accountname.setText("U bent nog niet ingelogd");
        }
        else {
            Accountname.setText(AccountInfo.getCurrentUsername());
            loginButton.setText("Verander account");
        }

    }
}
