package controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        // Deze gegevens moeten aangepast worden gebaseerd op de inloggegevens van jouw SQL workbench
        String databaseName = "waterwatch";
        String databaseUser = "root";
        String databasePassword = "Qwerty";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            System.out.println("WRONG");
            throw new RuntimeException(e);
        }
        return databaseLink;
    }
}
