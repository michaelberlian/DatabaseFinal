package sample;

import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SaleController {

    @FXML
    private TableView<Sale> SaleTable;
    @FXML
    private TableColumn<Sale, String> col_id;
    @FXML
    private TableColumn<Sale, String> col_cid;
    @FXML
    private TableColumn<Sale, String> col_name;
    @FXML
    private TableColumn<Sale, String> col_dnt;
    @FXML
    private TextField CustomerName;
    @FXML
    private Label SaleLabel;


    ObservableList<Sale> oblist = FXCollections.observableArrayList();


    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }

    public void Search(){
        oblist.clear();

        String srcName = CustomerName.getText();
        try {
            ResultSet rs = stm.executeQuery("select s.SaleID, s.CustomerID, c.CustomerName, s.DatenTime  from  Sale s " +
                "LEFT JOIN Customer c using (CustomerID) where CustomerName like \"%" + srcName +"%\";");
            while (rs.next()){
                oblist.add(new Sale(rs.getString("SaleID"), rs.getString("CustomerID"), rs.getString("CustomerName"),
                    rs.getString("DatenTime")));
            }
        } catch (SQLException e) {
            SaleLabel.setText(e.getMessage());
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("saleid"));
        col_cid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("customername"));
        col_dnt.setCellValueFactory(new PropertyValueFactory<>("datentime"));

        SaleTable.setItems(oblist);
    }

    public void Load(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SaleDetail.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        SaleDetailController controller = loader.getController();
        controller.initData(SaleTable.getSelectionModel().getSelectedItem());
        controller.passconn(stm);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Sale Detail");

        window.setScene(tableViewScene);
        window.show();
    }

    public void NewSale() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewSale.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        NewSaleController controller = loader.getController();
        controller.passconn(stm);

        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("New Sale");

        window.setScene(tableViewScene);
        window.show();
    }

}
