package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginController {
    @FXML
    private TextField gebruikersnaamField;
    @FXML
    private TextField wachtwoordField;

    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }

    public void logIn(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String gebruiker = gebruikersnaamField.getText().trim();
        String wachtwoord = wachtwoordField.getText().trim();
        if (gebruiker.isEmpty() || wachtwoord.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Vul alle velden in.");
            return;
        }
        String sql = "SELECT * FROM Account WHERE gebruikersnaam = '" + gebruiker + "' AND wachtwoord = '" + wachtwoord + "';";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(sql);

            if(queryOutput.next()) {
                AccountInfo.setCurrentUser(queryOutput.getInt("Ac_id"));
                AccountInfo.setCurrentUsername(queryOutput.getString("gebruikersnaam"));
                AccountInfo.setCurrentArea(queryOutput.getInt("gemeente"));
                if(queryOutput.next()){
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Er zijn meerdere accounts met die naam");
                    return;
                }
                SceneController.goToHome();
                return;
            }
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Verkeerde gebruikersnaam of wachtwoord");
            return;
        }
        catch (Exception var13) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Er is iets fout gegaan");
            Exception e = var13;
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
