package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
    @FXML
    private ComboBox<String> dataSelector;

    public void goToHome(ActionEvent event) {
        SceneController.goToHome();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (AccountInfo.getCurrentUser() == -1) {
            areaData.setVisible(false);
            dataSelector.setVisible(false);
            return;
        }
        dataSelector.setVisible(true);
        areaData.setVisible(true);


        dataSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String previous, String current) {
                makeSQL(current);
            }
        });
    }

    private void makeSQL(String option){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String findGem = "Select * FROM Locatie WHERE Lo_id = " + AccountInfo.getCurrentArea() + ";";
        String foundName = "";
        if (option.equals("Gemeente")){
            System.out.println("gemeente");
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryOutput = statement.executeQuery(findGem);
                if (queryOutput.next()){
                    foundName = queryOutput.getString("gemeente");
                }
            }
            catch (Exception var13) {
                Exception e = var13;
                e.printStackTrace();
            }
            String connectQuery = "Select date_format(datum, '%H:00 %d-%m-%Y') as Datum, AVG(tds) as TDS, AVG(troebelheid) as Troebelheid, kwaliteit\n" +
                    "                FROM SensorData S JOIN ACCOUNT A ON A.Ac_id = S.gebruiker JOIN Locatie L ON L.Lo_id = A.gemeente\n" +
                    "                WHERE L.gemeente = '" + foundName + "'" +
                    "                Group by hour(datum);";
            fillTable(connectQuery, connectDB);
            return;
        }
        System.out.println("postcode");
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(findGem);
            if (queryOutput.next()){
                foundName = queryOutput.getString("postcode");
            }
        }
        catch (Exception var13) {
            Exception e = var13;
            e.printStackTrace();
        }
        String connectQuery = "Select date_format(datum, '%H:00 %d-%m-%Y') as Datum, AVG(tds) as TDS, AVG(troebelheid) as Troebelheid, kwaliteit\n" +
                "                FROM SensorData S JOIN ACCOUNT A ON A.Ac_id = S.gebruiker JOIN Locatie L ON L.Lo_id = A.gemeente\n" +
                "                WHERE L.postcode = '" + foundName + "'" +
                "                Group by hour(datum);";
        fillTable(connectQuery, connectDB);
    }
    private void fillTable(String SQL, Connection connectDB){
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(SQL);
            ObservableList<WaterData> dataList = FXCollections.observableArrayList();

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
                this.areaData.setEditable(false);
            }
        }
        catch (Exception var13) {
            Exception e = var13;
            e.printStackTrace();
        }
    }
}
