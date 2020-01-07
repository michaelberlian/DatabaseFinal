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

public class SaleDetailAddController {
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
    private Label CustomerName;
    @FXML
    private Label SaleID;
    @FXML
    private ComboBox<String> ItemQty;
    @FXML
    private Label Min_Price;
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
    public void initdata(String saleid, String customername){
        SaleID.setText(saleid);
        CustomerName.setText(customername);
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
            for (int i = 1 ; i <= Integer.parseInt(item.getQuantity()); i++) {
                ItemQty.getItems().add(Integer.toString(i));
            }
            Price.setText(item.getPrice());
            Min_Price.setText(item.getPrice());
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
            EmployeeID.getItems().clear();;
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
            if (Integer.parseInt(Price.getText()) > Integer.parseInt(Min_Price.getText())) {
                stm.executeUpdate("Insert Into SaleDetail (SaleID,EmployeeID,ItemID,ItemQty,Price) Values('" + SaleID.getText() + "','" +
                    EmployeeID.getValue() + "','" + ItemId.getText() + "','" + ItemQty.getValue() + "','" + Price.getText() + "');");
                error_label.setText("SUCCESSFUL");
                stm.executeUpdate("Update Item set ItemQty = ItemQty-" + ItemQty.getValue() + " Where ItemId=" + ItemId.getText() +";");
            } else {
                error_label.setText("Price cannot be lower than minimum price.");
            }
        } catch (SQLException e) {
            error_label.setText((e.getMessage()));
        }
    }

}
