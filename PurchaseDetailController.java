package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PurchaseDetailController {

    @FXML
    private Label PurchaseID;
    @FXML
    private Label grand;
    @FXML
    private TextField SupplierID;
    @FXML
    private Label SDLabel;
    @FXML
    private Label SupplierName;
    @FXML
    private TextField DatenTime;
    @FXML
    private TableView<PurchaseDetail> PurchaseDetailTable;
    @FXML
    private TableColumn<PurchaseDetail,String> col_sdid;
    @FXML
    private TableColumn<PurchaseDetail,String> col_id;
    @FXML
    private TableColumn<PurchaseDetail,String> col_name;
    @FXML
    private TableColumn<PurchaseDetail,String> col_uprice;
    @FXML
    private TableColumn<PurchaseDetail,String> col_quantity;
    @FXML
    private TableColumn<PurchaseDetail,String> col_price;
    @FXML
    private TableColumn<PurchaseDetail,String> col_employeename;
    @FXML
    private Button delete;

    private Purchase selectedSale;


    ObservableList<PurchaseDetail> oblist = FXCollections.observableArrayList();

    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }

    public void initData(Purchase sale){
        selectedSale = sale;
        PurchaseID.setText(selectedSale.getPurchaseid());
        SupplierID.setText(selectedSale.getSupplierid());
        SupplierName.setText(selectedSale.getSuppliername());
        DatenTime.setText(selectedSale.getDatentime());
    }

    public void Load(){
        oblist.clear();
        try{
            ResultSet rs = stm.executeQuery("select d.PurchaseDetailId, d.PurchaseID, d.EmployeeID, e.EmployeeName, d.ItemID, i.ItemName, " +
                "d.Price as UnitPrice, d.ItemQty, d.price * d.ItemQty as Price from PurchaseDetail d  " +
                "Left join Employee e using (employeeID) Left join Item i using (ItemID) Where PurchaseID = "+ PurchaseID.getText() +";" );
            while(rs.next()){
                oblist.add(new PurchaseDetail(rs.getString("PurchaseDetailId"),rs.getString("PurchaseID"),rs.getString("EmployeeID"),rs.getString("EmployeeName"),rs.getString("ItemID"),
                    rs.getString("ItemName"), rs.getString("UnitPrice"), rs.getString("ItemQty"),
                    rs.getString("Price")));
            }
        } catch (SQLException e) {
            SDLabel.setText(e.getMessage());
        }

        try {
            ResultSet rs1 = stm.executeQuery("SELECT SUM(ItemQty * Price) FROM purchasedetail where purchaseid = "+PurchaseID.getText()+";");
            rs1.next();
            grand.setText(rs1.getString("SUM(ItemQty * Price)"));
        } catch (SQLException e) {
            SDLabel.setText(e.getMessage());
        }

        col_sdid.setCellValueFactory(new PropertyValueFactory<>("purchasedetailid"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        col_uprice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("itemquantity"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_employeename.setCellValueFactory(new PropertyValueFactory<>("employeename"));

        PurchaseDetailTable.setItems(oblist);


    }

    public void Edit(){
        try {
            stm.executeUpdate( "Update Purchase set SupplierID = \"" + SupplierID.getText() + "\", DatenTime = \"" + DatenTime.getText() + "\" Where PurchaseID =" + PurchaseID.getText());
            ResultSet rs = stm.executeQuery("Select SupplierName from Supplier where SupplierID =" +SupplierID.getText()+";");
            rs.next();
            SupplierName.setText(rs.getString("SupplierName"));
            SDLabel.setText("SUCCESSFUL");
        } catch (SQLException e) {
            SDLabel.setText(e.getMessage());
        }
    }

    public void Delete(){
        Stage stage = (Stage) delete.getScene().getWindow();

        try {
            stm.executeUpdate("Delete From Purchase where PurchaseID=" + PurchaseID.getText() + ";");
        } catch (SQLException e) {
            SDLabel.setText(e.getMessage());
        }
        stage.close();
    }

    public void View() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PurchaseDetailView.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        PurchaseDetailViewController controller = loader.getController();
        controller.passconn(stm);
        controller.initData(PurchaseDetailTable.getSelectionModel().getSelectedItem());
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Purchase Detail View");
        window.setScene(tableViewScene);
        window.show();
    }

    public void Add() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PurchaseDetailAdd.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        PurchaseDetailAddController controller = loader.getController();
        controller.passconn(stm);
        controller.initdata(PurchaseID.getText(),SupplierName.getText());
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("New Purchase Detail");
        window.setScene(tableViewScene);
        window.show();

    }

}
