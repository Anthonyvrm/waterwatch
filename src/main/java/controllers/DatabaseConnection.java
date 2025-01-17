
package controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public DatabaseConnection() {
    }

    public Connection getConnection() {
        //Dit moet worden vervangen met de gegevens uit jouw locale sql database.
        String databaseName = "waterwatch";
        String databaseUser = "root";
        String databasePassword = "Qwerty";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception var6) {
            Exception e = var6;
            System.out.println("WRONG");
            throw new RuntimeException(e);
        }

        return this.databaseLink;
    }
}
