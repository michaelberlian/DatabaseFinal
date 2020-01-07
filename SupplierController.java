package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SupplierController{

    @FXML
    private TableView<Supplier> SupplierTable;
    @FXML
    private TableColumn<Supplier, String> col_id;
    @FXML
    private TableColumn<Supplier, String> col_name;
    @FXML
    private TableColumn<Supplier, String> col_address;
    @FXML
    private TextField SupplierName;
    @FXML
    private TextField SupplierAddress;
    @FXML
    private Label SupplierIdLoad;
    @FXML
    private TextField SupplierNameLoad;
    @FXML
    private TextField SupplierAddressLoad;
    @FXML
    private TextField ASName;
    @FXML
    private TextField ASAddress;
    @FXML
    private Label ASLabel;
    @FXML
    private Label SLabel;

    private boolean load;

    ObservableList<Supplier> oblist = FXCollections.observableArrayList();
    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }

    public void Search(){
        oblist.clear();

        String srcName = SupplierName.getText();
        String srcAddress = SupplierAddress.getText();
        try {
            ResultSet rs = stm.executeQuery("select * from Supplier where SupplierName like \"%" + srcName +
                "%\" and SupplierAddress like \"%" + srcAddress + "%\"");
            while (rs.next()){
                oblist.add(new Supplier(rs.getString("SupplierID"), rs.getString("SupplierName"),
                    rs.getString("SupplierAddress")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        SupplierTable.setItems(oblist);
    }


    public void Add(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SupplierAdd.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            SupplierController controller = loader.getController();
            controller.passconn(stm);


            //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            Stage window = new Stage();
            window.setTitle("New Supplier");

            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e){
            System.out.println("sorry");
            e.getMessage();
        }
    }

    public void Save(ActionEvent event)  {
        String name = ASName.getText();
        String address = ASAddress.getText();
        try {
            stm.executeUpdate("INSERT INTO SUPPLIER (SupplierName,SupplierAddress) Value (\"" + name + "\",\"" + address +"\");");
            ASName.clear();
            ASAddress.clear();
            ASLabel.setText("SUCCESSFUL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Load(ActionEvent event){
        try {
            Supplier item = SupplierTable.getSelectionModel().getSelectedItem();
            SupplierNameLoad.setText(item.getName());
            SupplierAddressLoad.setText(item.getAddress());
            SupplierIdLoad.setText(item.getId());
            load = true;
        } catch (Exception e){
            SLabel.setText("Please choose one of the row");
        }
    }

    public void Edit(ActionEvent event) {
        if (load) {
            String id = SupplierIdLoad.getText();
            String name = SupplierNameLoad.getText();
            String address = SupplierAddressLoad.getText();
            try {
                stm.executeUpdate("UPDATE Supplier SET SupplierName=\"" + name + "\",SupplierAddress=\"" + address + "\" WHERE SupplierID=" + id);
                SupplierIdLoad.setText("");
                SupplierNameLoad.clear();
                SupplierAddressLoad.clear();
                SLabel.setText("SUCCESSFUL");
                load = false;
                Search();
            } catch (SQLException e) {
                SLabel.setText(e.getMessage());
            }
        } else {
            SLabel.setText("Please choose and load the data");
        }
    }

    public void Delete(ActionEvent event) {
        if(load) {
            String id = SupplierIdLoad.getText();
            try {
                stm.executeUpdate("Delete From Supplier where SupplierID=" + id + ";");
                SupplierIdLoad.setText("");
                SupplierNameLoad.clear();
                SupplierAddressLoad.clear();
                SLabel.setText("SUCCESSFUL");
                load = false;
                Search();
            } catch (SQLException e) {
                SLabel.setText(e.getMessage());
            }
        } else {
            SLabel.setText("Please choose and load the data");
        }
    }
}
