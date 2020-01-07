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

public class PurchaseController {

    @FXML
    private TableView<Purchase> PurchaseTable;
    @FXML
    private TableColumn<Purchase, String> col_id;
    @FXML
    private TableColumn<Purchase, String> col_sid;
    @FXML
    private TableColumn<Purchase, String> col_name;
    @FXML
    private TableColumn<Purchase, String> col_dnt;
    @FXML
    private TextField SupplierName;
    @FXML
    private Label PurchaseLabel;


    ObservableList<Purchase> oblist = FXCollections.observableArrayList();


    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }

    public void Search(){
        oblist.clear();

        String srcName = SupplierName.getText();
        try {
            ResultSet rs = stm.executeQuery("select s.PurchaseID, s.SupplierID, c.SupplierName, s.DatenTime  from  Purchase s " +
                "LEFT JOIN Supplier c using (SupplierID) where SupplierName like \"%" + srcName +"%\";");
            while (rs.next()){
                oblist.add(new Purchase(rs.getString("PurchaseID"), rs.getString("SupplierID"), rs.getString("SupplierName"),
                    rs.getString("DatenTime")));
            }
        } catch (SQLException e) {
            PurchaseLabel.setText(e.getMessage());
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("purchaseid"));
        col_sid.setCellValueFactory(new PropertyValueFactory<>("supplierid"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("suppliername"));
        col_dnt.setCellValueFactory(new PropertyValueFactory<>("datentime"));

        PurchaseTable.setItems(oblist);
    }

    public void Load(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PurchaseDetail.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        PurchaseDetailController controller = loader.getController();
        controller.initData(PurchaseTable.getSelectionModel().getSelectedItem());
        controller.passconn(stm);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Purchase Detail");

        window.setScene(tableViewScene);
        window.show();
    }

    public void NewPurchase() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewPurchase.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);
        NewPurchaseController controller = loader.getController();
        controller.passconn(stm);

        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("New Purchase");

        window.setScene(tableViewScene);
        window.show();
    }

}
