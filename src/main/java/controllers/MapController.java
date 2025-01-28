package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    private TableView<WaterData> areaData;
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

    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        if (AccountInfo.getCurrentUser() == -1) {
            areaData.setVisible(false);
            return;
        }
        String connectQuery = "Select date_format(datum, '%H:00 %d-%m-%Y') as Datum, AVG(tds) as TDS, AVG(troebelheid) as Troebelheid, kwaliteit \n" +
                "FROM SensorData S JOIN ACCOUNT A ON A.Ac_id = S.gebruiker\n" +
                "WHERE A.gemeente = '" + AccountInfo.getCurrentArea() + "'\n" +
                "Group by hour(datum);";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            ObservableList<WaterData> dataList = FXCollections.observableArrayList();
            areaData.setVisible(true);

            while(queryOutput.next()) {
                String datum = queryOutput.getString("datum");
                float tds = queryOutput.getInt("tds");
                float troebelheid = queryOutput.getInt("troebelheid");
                boolean kwaliteit = tds < 1100 && troebelheid <= 750;
                dataList.add(new WaterData(datum, tds, troebelheid, kwaliteit));
                this.areaData.setEditable(true);
                this.datumCol.setCellValueFactory(new PropertyValueFactory("datum"));
                this.TDSCol.setCellValueFactory(new PropertyValueFactory("TDSdata"));
                this.troebelheidCol.setCellValueFactory(new PropertyValueFactory("troebelheid"));
                this.kwaliteitCol.setCellValueFactory(new PropertyValueFactory("kwaliteit"));
                this.areaData.setItems(dataList);
            }
        }
        catch (Exception var13) {
            Exception e = var13;
            e.printStackTrace();
        }
    }
}
