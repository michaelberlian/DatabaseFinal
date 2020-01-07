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

public class CustomerController{

    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> col_id;
    @FXML
    private TableColumn<Customer, String> col_name;
    @FXML
    private TableColumn<Customer, String> col_address;
    @FXML
    private TextField CustomerName;
    @FXML
    private TextField CustomerAddress;
    @FXML
    private Label CustomerIdLoad;
    @FXML
    private TextField CustomerNameLoad;
    @FXML
    private TextField CustomerAddressLoad;
    @FXML
    private TextField ACName;
    @FXML
    private TextField ACAddress;
    @FXML
    private Label ACLabel;
    @FXML
    private Label CLabel;

    private Boolean load;

    ObservableList<Customer> oblist = FXCollections.observableArrayList();


    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }

    public void Search(){
        oblist.clear();
        String srcName = CustomerName.getText();
        String srcAddress = CustomerAddress.getText();
        try {
            ResultSet rs = stm.executeQuery("select * from Customer where CustomerName like \"%" + srcName +
                "%\" and CustomerAddress like \"%" + srcAddress + "%\"");
            while (rs.next()){
                oblist.add(new Customer(rs.getString("CustomerID"), rs.getString("CustomerName"),
                    rs.getString("CustomerAddress")));
            }
        } catch (SQLException e) {
            CLabel.setText(e.getMessage());
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        CustomerTable.setItems(oblist);
    }


    public void Add(ActionEvent event) {
        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerAdd.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setTitle("Add Customer");
//            stage.setScene(new Scene(root1));
//            stage.show();


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CustomerAdd.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            CustomerController controller = loader.getController();
            controller.passconn(stm);


            //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            Stage window = new Stage();
            window.setTitle("New Customer");

            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e){
            CLabel.setText(e.getMessage());
        }
    }

    public void Save(ActionEvent event) {
        String name = ACName.getText();
        String address = ACAddress.getText();
        try {
            stm.executeUpdate("INSERT INTO Customer (CustomerName,CustomerAddress) Value (\"" + name + "\",\"" + address +"\");");
            ACName.clear();
            ACAddress.clear();
            ACLabel.setText("SUCCESSFUL");
        } catch (SQLException e) {
            ACLabel.setText(e.getMessage());
        }
    }

    public void Load(ActionEvent event){
        try {
            Customer item = CustomerTable.getSelectionModel().getSelectedItem();
            CustomerNameLoad.setText(item.getName());
            CustomerAddressLoad.setText(item.getAddress());
            CustomerIdLoad.setText(item.getId());
            load=true;
        }catch (Exception e){
            CLabel.setText("Please choose one of the row");
        }
    }

    public void Edit(ActionEvent event){
        if (load) {
            String id = CustomerIdLoad.getText();
            String name = CustomerNameLoad.getText();
            String address = CustomerAddressLoad.getText();
            try {
                stm.executeUpdate("UPDATE Customer SET CustomerName=\"" + name + "\",CustomerAddress=\"" + address + "\" WHERE CustomerID=" + id);
                CustomerIdLoad.setText("");
                CustomerNameLoad.clear();
                CustomerAddressLoad.clear();
                CLabel.setText("SUCCESSFUL");
                load = false;
                Search();
            } catch (SQLException e) {
                CLabel.setText(e.getMessage());
            }
        } else {
            CLabel.setText("Please choose and load the data");
        }
    }

    public void Delete(ActionEvent event) {
        if (load) {
            String id = CustomerIdLoad.getText();
            try {
                stm.executeUpdate("Delete From Customer where CustomerID=" + id + ";");
                CustomerIdLoad.setText("");
                CustomerNameLoad.clear();
                CustomerAddressLoad.clear();
                CLabel.setText("SUCCESSFUL");
                load = false;
                Search();
            } catch (SQLException e) {
                CLabel.setText(e.getMessage());
            }
        } else {
            CLabel.setText("Please choose and load the data");
        }
    }
}
