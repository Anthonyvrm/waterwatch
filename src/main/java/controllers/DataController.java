package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class DataController implements Initializable {
    @FXML private TableView<WaterData> microbitData;
    @FXML private TableColumn<WaterData, String> datumCol;
    @FXML private TableColumn<WaterData, Integer> TDSCol;
    @FXML private TableColumn<WaterData, Integer> troebelheidCol;
    @FXML private TableColumn<WaterData, Boolean> kwaliteitCol;

    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "Select * From SensorData";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            ObservableList<WaterData> dataList = FXCollections.observableArrayList();
            while (queryOutput.next()) {
                String datum = queryOutput.getString("datum");
                int tds = queryOutput.getInt("tds");
                int troebelheid = queryOutput.getInt("troebelheid");
                boolean kwaliteit = queryOutput.getBoolean("kwaliteit");
                dataList.add(new WaterData(datum, tds, troebelheid, kwaliteit));

                microbitData.setEditable(true);
                datumCol.setCellValueFactory(new PropertyValueFactory<WaterData, String>("datum"));
                TDSCol.setCellValueFactory(new PropertyValueFactory<WaterData, Integer>("TDSdata"));
                troebelheidCol.setCellValueFactory(new PropertyValueFactory<WaterData, Integer>("troebelheid"));
                kwaliteitCol.setCellValueFactory(new PropertyValueFactory<WaterData, Boolean>("kwaliteit"));
                microbitData.setItems(dataList);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
