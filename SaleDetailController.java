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

public class SaleDetailController {

    @FXML
    private Label SaleID;
    @FXML
    private Label grand;
    @FXML
    private TextField CustomerID;
    @FXML
    private Label SDLabel;
    @FXML
    private Label CustomerName;
    @FXML
    private TextField DatenTime;
    @FXML
    private TableView<SaleDetail> SaleDetailTable;
    @FXML
    private TableColumn<SaleDetail,String> col_sdid;
    @FXML
    private TableColumn<SaleDetail,String> col_id;
    @FXML
    private TableColumn<SaleDetail,String> col_name;
    @FXML
    private TableColumn<SaleDetail,String> col_uprice;
    @FXML
    private TableColumn<SaleDetail,String> col_quantity;
    @FXML
    private TableColumn<SaleDetail,String> col_price;
    @FXML
    private TableColumn<SaleDetail,String> col_employeename;
    @FXML
    private Button delete;

    private Sale selectedSale;


    ObservableList<SaleDetail> oblist = FXCollections.observableArrayList();

    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }
    public void initData(Sale sale){
        selectedSale = sale;
        SaleID.setText(selectedSale.getSaleid());
        CustomerID.setText(selectedSale.getCustomerid());
        CustomerName.setText(selectedSale.getCustomername());
        DatenTime.setText(selectedSale.getDatentime());
    }

    public void Load(){
        oblist.clear();
        try{
            ResultSet rs = stm.executeQuery("select d.SaleDetailId, d.SaleID, d.EmployeeID, e.EmployeeName, d.ItemID, i.ItemName, " +
                "d.Price as UnitPrice, d.ItemQty, d.price * d.ItemQty as Price from SaleDetail d  " +
                "Left join Employee e using (employeeID) Left join Item i using (ItemID) Where SaleID = "+ SaleID.getText() +";" );
            while(rs.next()){
                oblist.add(new SaleDetail(rs.getString("SaleDetailId"),rs.getString("SaleID"),rs.getString("EmployeeID"),rs.getString("EmployeeName"),rs.getString("ItemID"),
                    rs.getString("ItemName"), rs.getString("UnitPrice"), rs.getString("ItemQty"),
                    rs.getString("Price")));
            }
        } catch (SQLException e) {
            SDLabel.setText(e.getMessage());
        }

        try {
            ResultSet rs1 = stm.executeQuery("SELECT SUM(ItemQty * Price) FROM saledetail where saleid = "+SaleID.getText()+";");
            rs1.next();
            grand.setText(rs1.getString("SUM(ItemQty * Price)"));
        } catch (SQLException e) {
            SDLabel.setText(e.getMessage());
        }

        col_sdid.setCellValueFactory(new PropertyValueFactory<>("saledetailid"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        col_uprice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("itemquantity"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_employeename.setCellValueFactory(new PropertyValueFactory<>("employeename"));

        SaleDetailTable.setItems(oblist);


    }

    public void Edit(){
        try {
            stm.executeUpdate( "Update Sale set CustomerID = \"" + CustomerID.getText() + "\", DatenTime = \"" + DatenTime.getText() + "\" Where SaleID =" + SaleID.getText());
            ResultSet rs = stm.executeQuery("Select CustomerName from Customer where CustomerID =" +CustomerID.getText()+";");
            rs.next();
            CustomerName.setText(rs.getString("CustomerName"));
            SDLabel.setText("SUCCESSFUL");
        } catch (SQLException e) {
            SDLabel.setText(e.getMessage());
        }
    }

    public void Delete(){
        Stage stage = (Stage) delete.getScene().getWindow();

        try {
            stm.executeUpdate("Delete From Sale where SaleID=" + SaleID.getText() + ";");
        } catch (SQLException e) {
            SDLabel.setText(e.getMessage());
        }
        stage.close();
    }

    public void View() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SaleDetailView.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        SaleDetailViewController controller = loader.getController();
        controller.passconn(stm);
        controller.initData(SaleDetailTable.getSelectionModel().getSelectedItem());
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Sale Detail View");
        window.setScene(tableViewScene);
        window.show();
    }

    public void Add() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SaleDetailAdd.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        SaleDetailAddController controller = loader.getController();
        controller.initdata(SaleID.getText(),CustomerName.getText());
        controller.passconn(stm);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("New Sale Detail");
        window.setScene(tableViewScene);
        window.show();

    }

}
