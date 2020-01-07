package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SaleDetailViewController {

    @FXML
    private TextField VEID;
    @FXML
    private TextField VIP;
    @FXML
    private Label VSDID;
    @FXML
    private Label VSID;
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
    private Label min_price;

    private SaleDetail selectedsaledetail;

    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }
    public void initData(SaleDetail saledetail){
        selectedsaledetail = saledetail;
        VEID.setText(selectedsaledetail.getEmployeeid());
        VIP.setText(selectedsaledetail.getUnitprice());
        VSDID.setText(selectedsaledetail.getSaledetailid());
        VSID.setText(selectedsaledetail.getSaleid());
        VIID.setText(selectedsaledetail.getItemid());
        VIQ.setText(selectedsaledetail.getItemquantity());
        VTot.setText(Integer.toString(Integer.parseInt(VIQ.getText()) * Integer.parseInt(VIP.getText())));

        try {
            ResultSet rs = stm.executeQuery("select Price from Item where itemID ="+selectedsaledetail.getItemid()+";");
            rs.next();
            min_price.setText(rs.getString("Price"));
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
    }

    public void Save(){
        try {
            if (Integer.parseInt(min_price.getText()) < Integer.parseInt(VIP.getText())) {
                stm.executeUpdate("UPDATE SaleDetail SET EmployeeID='" + VEID.getText() + "',Price='" + VIP.getText() + "' WHERE SaleDetailID=" + VSDID.getText());
                error_label.setText("SUCCESSFUL");
            }
            else {
                error_label.setText("Price should be higher than Min price");
            }
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
    }

    public void Delete(){
        Stage stage = (Stage) delete.getScene().getWindow();
        try {
            stm.executeUpdate("Delete From SaleDetail where SaleDetailId='" + VSDID.getText() + "';");
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
        }
        stage.close();
    }

    public void Sum(){
        VTot.setText(Integer.toString(Integer.parseInt(VIQ.getText()) * Integer.parseInt(VIP.getText())));
    }

}
