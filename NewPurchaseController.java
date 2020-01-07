package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NewPurchaseController {

    @FXML
    private Label SupplierName;
    @FXML
    private Label error_label;
    @FXML
    private ComboBox<String> SupplierID;
    @FXML
    private Button Next;

    private boolean load = false;

//    Database db = new Database();
//    Statement stm = db.getStm();

    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }
    public void Load() {
        if (!load) {
            ResultSet rs = null;
            try {
                rs = stm.executeQuery("Select SupplierID from Supplier");
                while (rs.next()) {
                    SupplierID.getItems().add(rs.getString("SupplierID"));
                }
                load = true;

            } catch (SQLException e) {
                error_label.setText(e.getMessage());
            }
        }
    }

    public void Choose(){
        ResultSet rs = null;
        try {
            rs = stm.executeQuery("Select SupplierName from Supplier where SupplierID= " + SupplierID.getValue() + ";");
            rs.next();
            SupplierName.setText(rs.getString("SupplierName"));
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
    }

    public void Next(ActionEvent event) throws IOException, SQLException {

        Stage stage = (Stage) Next.getScene().getWindow();
        try {
            stm.executeUpdate("Insert Into Purchase (SupplierID) values (\""+SupplierID.getValue()+"\");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ResultSet rs = stm.executeQuery("Select MAX(PurchaseID) from Purchase");
        rs.next();
        String purchaseid = rs.getString(1);
        rs = stm.executeQuery("select s.PurchaseID, s.SupplierID, c.SupplierName, s.DatenTime  from  Purchase s " +
            "LEFT JOIN Supplier c using (SupplierID) where PurchaseID = " + purchaseid +";");
        rs.next();
        Purchase purchase = new Purchase(rs.getString("PurchaseID"),rs.getString("SupplierID"),rs.getString("SupplierName"),
            rs.getString("DatenTime"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PurchaseDetail.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        PurchaseDetailController controller = loader.getController();
        controller.initData(purchase);
        controller.passconn(stm);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Purchase Detail");

        window.setScene(tableViewScene);
        window.show();
        stage.close();
    }
}
