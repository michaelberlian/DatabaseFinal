package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PurchaseDetailViewController {

    @FXML
    private TextField VEID;
    @FXML
    private TextField VIP;
    @FXML
    private Label VPDID;
    @FXML
    private Label VPID;
    @FXML
    private Label VIID;
    @FXML
    private Label VIQ;
    @FXML
    private Label VTot;
    @FXML
    private Label error_label;
    @FXML
    private Button delete;
    @FXML
    private Label max_price;

    private PurchaseDetail selectedpurchasedetail;

    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }
    public void initData(PurchaseDetail purchasedetail){
        selectedpurchasedetail = purchasedetail;
        VEID.setText(selectedpurchasedetail.getEmployeeid());
        VIP.setText(selectedpurchasedetail.getUnitprice());
        VPDID.setText(selectedpurchasedetail.getPurchasedetailid());
        VPID.setText(selectedpurchasedetail.getPurchaseid());
        VIID.setText(selectedpurchasedetail.getItemid());
        VIQ.setText(selectedpurchasedetail.getItemquantity());
        VTot.setText(Integer.toString(Integer.parseInt(VIQ.getText()) * Integer.parseInt(VIP.getText())));
        try {
            ResultSet rs = stm.executeQuery("select Price from Item where itemID ="+selectedpurchasedetail.getItemid()+";");
            rs.next();
            max_price.setText(rs.getString("Price"));
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
    }

    public void Save(){
        try {
            if (Integer.parseInt(max_price.getText()) > Integer.parseInt(VIP.getText())) {
                stm.executeUpdate("UPDATE PurchaseDetail SET EmployeeID='" + VEID.getText() + "',Price='" + VIP.getText() + "' WHERE PurchaseDetailID=" + VPDID.getText());
                error_label.setText("SUCCESSFUL");
            } else {
                error_label.setText("Price should be lower than Max Price");
            }
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
    }

    public void Delete(){
        Stage stage = (Stage) delete.getScene().getWindow();
        try {
            stm.executeUpdate("Delete From PurchaseDetail where PurchaseDetailId='" + VPDID.getText() + "';");
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
        stage.close();
    }

    public void Sum(){
        VTot.setText(Integer.toString(Integer.parseInt(VIQ.getText()) * Integer.parseInt(VIP.getText())));
    }

}
