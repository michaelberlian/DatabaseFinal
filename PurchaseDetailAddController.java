package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PurchaseDetailAddController {
    @FXML
    private TextField Price;
    @FXML
    private TextField ItemNameSearch;
    @FXML
    private Label ItemId;
    @FXML
    private Label error_label;
    @FXML
    private Label ItemName;
    @FXML
    private Label SupplierName;
    @FXML
    private Label PurchaseID;
    @FXML
    private TextField ItemQty;
    @FXML
    private Label Max_Price;
    @FXML
    private ComboBox<String> EmployeeName;
    @FXML
    private ComboBox<String> EmployeeID;
    @FXML
    private Label EmployeeAddress;
    @FXML
    private Label ItemDescription;
    @FXML
    private TableView<Item> ItemTable;
    @FXML
    private TableColumn<Item, String> col_id;
    @FXML
    private TableColumn<Item, String> col_name;
    @FXML
    private TableColumn<Item, String> col_qty;
    @FXML
    private TableColumn<Item, String> col_price;
    @FXML
    private TableColumn<Item, String> col_desc;

    private boolean load;

    ObservableList<Item> oblist = FXCollections.observableArrayList();
    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }
    public void initdata(String purchaseid, String suppliername){
        PurchaseID.setText(purchaseid);
        SupplierName.setText(suppliername);
    }

    public void Search(){
        oblist.clear();
        String srcName = ItemNameSearch.getText();
        try {
            ResultSet rs = stm.executeQuery("select * from Item where ItemName like \"%" + srcName +
                "%\"");
            while (rs.next()){
                oblist.add(new Item(rs.getString("ItemID"), rs.getString("ItemName"),rs.getString("Price"),
                    rs.getString("Description"),rs.getString("ItemQty")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));

        ItemTable.setItems(oblist);
    }

    public void Load(ActionEvent event){

        try {
            Item item = ItemTable.getSelectionModel().getSelectedItem();
            ItemId.setText(item.getId());
            ItemName.setText(item.getName());
            Price.setText(item.getPrice());
            Max_Price.setText(item.getPrice());
            ItemDescription.setText(item.getDescription());
            error_label.setText("");
            load = true;
        } catch (Exception e){
            error_label.setText("Please choose one of the row");
        }
        ResultSet rs = null;
        try {
            EmployeeName.getItems().clear();
            rs = stm.executeQuery("Select EmployeeName from Employee");
            while (rs.next()){
                EmployeeName.getItems().add(rs.getString("EmployeeName"));
            }
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
    }


    public void EmployeeNameChoose(){
        ResultSet rs = null;
        try {
            EmployeeID.getItems().clear();
            rs = stm.executeQuery("Select EmployeeID from Employee where EmployeeName= \"" + EmployeeName.getValue() + "\";");

            while(rs.next()) {
                EmployeeID.getItems().add(rs.getString("EmployeeID"));
            }
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
    }

    public void EmployeeIDChoose(){
        ResultSet rs = null;
        try {
            rs = stm.executeQuery("Select EmployeeAddress from Employee where EmployeeID= " + EmployeeID.getValue() + ";");
            rs.next();
            EmployeeAddress.setText(rs.getString("EmployeeAddress"));
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
    }

    public void Add(){
        try {
            if (Integer.parseInt(Price.getText()) < Integer.parseInt(Max_Price.getText())) {
                stm.executeUpdate("Insert Into PurchaseDetail (PurchaseID,EmployeeID,ItemID,ItemQty,Price) Values('" + PurchaseID.getText() + "','" +
                    EmployeeID.getValue() + "','" + ItemId.getText() + "','" + ItemQty.getText() + "','" + Price.getText() + "');");
                error_label.setText("SUCCESSFUL");
                stm.executeUpdate("Update Item set ItemQty = ItemQty+" + ItemQty.getText() + " Where ItemId=" + ItemId.getText() +";");
            } else {
                error_label.setText("Price cannot be higher than minimum price.");
            }
        } catch (SQLException e) {
            error_label.setText((e.getMessage()));
        }
    }

}
