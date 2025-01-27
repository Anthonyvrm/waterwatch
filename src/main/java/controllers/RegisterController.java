package controllers;


import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField gebruikersnaamField;
    @FXML
    private TextField wachtwoordField;
    @FXML
    private TextField gemeenteField;

    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }
    public void addAccount(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String gebruiker = gebruikersnaamField.getText().trim();
        String wachtwoord = wachtwoordField.getText().trim();
        String gemeente = gemeenteField.getText().trim().toUpperCase();
        if (gebruiker.isEmpty() || wachtwoord.isEmpty() || gemeente.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Vul alle velden in.");
            return;
        }
        String checkDupes = "SELECT * FROM Account WHERE gebruikersnaam = '" + gebruiker + "';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(checkDupes);

            if (queryOutput.next()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Die gebruikersnaam is al in gebruik, probeer een andere.");
                return;
            }
        }
        catch (Exception var13) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Er is iets fout gegaan");
            Exception e = var13;
            e.printStackTrace();
        }
        String sql = "INSERT INTO Account  (gebruikersnaam, wachtwoord, gemeente) " +
                "VALUES('" + gebruiker + "', '" + wachtwoord + "', '" + gemeente + "');";
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Data inserted into database: " + sql);
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Account aangemaakt");
        } catch (SQLException e) {
            System.err.println("Failed to insert data: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Er is iets fout gegaan, probeer het opnieuw");
            throw new RuntimeException(e);
        }
        SceneController.goToHome();
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
