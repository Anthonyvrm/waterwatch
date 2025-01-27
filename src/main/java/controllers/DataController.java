package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class DataController implements Initializable {
    @FXML
    private TableView<WaterData> microbitData;
    @FXML
    private TableColumn<WaterData, String> datumCol;
    @FXML
    private TableColumn<WaterData, Float> TDSCol;
    @FXML
    private TableColumn<WaterData, Float> troebelheidCol;
    @FXML
    private TableColumn<WaterData, Boolean> kwaliteitCol;
    @FXML
    private Text loginWarning;

    public DataController() {
    }

    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        if (AccountInfo.getCurrentUser() == -1) {
           microbitData.setVisible(false);
//           loginWarning.setVisible(true);
           return;
        }
        String connectQuery = "Select date_format(datum, '%H:00 %d-%m-%Y') as Datum, AVG(tds) as TDS, AVG(troebelheid) as Troebelheid " +
                "FROM SensorData " + "WHERE gebruiker = '" + AccountInfo.getCurrentUser() + "'" +
                "Group by hour(datum);";
        //(opgelost) probleem: als het een keer false tegen is gekomen dan zal het altijd false blijven
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            ObservableList<WaterData> dataList = FXCollections.observableArrayList();
            microbitData.setVisible(true);
//            loginWarning.setVisible(false);

            while(queryOutput.next()) {
                String datum = queryOutput.getString("datum");
                float tds = queryOutput.getInt("tds");
                float troebelheid = queryOutput.getInt("troebelheid");
                boolean kwaliteit = tds < 4000 && troebelheid < 700;
                dataList.add(new WaterData(datum, tds, troebelheid, kwaliteit));
                this.microbitData.setEditable(true);
                this.datumCol.setCellValueFactory(new PropertyValueFactory("datum"));
                this.TDSCol.setCellValueFactory(new PropertyValueFactory("TDSdata"));
                this.troebelheidCol.setCellValueFactory(new PropertyValueFactory("troebelheid"));
                this.kwaliteitCol.setCellValueFactory(new PropertyValueFactory("kwaliteit"));
                this.microbitData.setItems(dataList);
            }
        } catch (Exception var13) {
            Exception e = var13;
            e.printStackTrace();
        }

    }
}
