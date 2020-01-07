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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NewSaleController{

    @FXML
    private Label CustomerName;
    @FXML
    private ComboBox<String> CustomerID;
    @FXML
    private Button Next;

    private boolean comboboxload = false;


    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }

    public void Load () {
        if (!comboboxload) {
            ResultSet rs = null;
            try {
                rs = stm.executeQuery("Select CustomerID from Customer");
                while (rs.next()) {
                    CustomerID.getItems().add(rs.getString("CustomerID"));
                }
                comboboxload = true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void Choose(){
        ResultSet rs = null;
        try {
            rs = stm.executeQuery("Select CustomerName from Customer where CustomerID= " + CustomerID.getValue() + ";");
            rs.next();
            CustomerName.setText(rs.getString("CustomerName"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Next(ActionEvent event) throws IOException, SQLException {

        Stage stage = (Stage) Next.getScene().getWindow();
        try {
            stm.executeUpdate("Insert Into Sale (CustomerID) values (\""+CustomerID.getValue()+"\");");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ResultSet rs = stm.executeQuery("Select MAX(SaleID) from Sale");
        rs.next();
        String saleid = rs.getString(1);
        rs = stm.executeQuery("select s.SaleID, s.CustomerID, c.CustomerName, s.DatenTime  from  Sale s " +
            "LEFT JOIN Customer c using (CustomerID) where SaleID = " + saleid +";");
        rs.next();
        Sale sale = new Sale(rs.getString("SaleID"),rs.getString("CustomerID"),rs.getString("CustomerName"),
            rs.getString("DatenTime"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SaleDetail.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        SaleDetailController controller = loader.getController();
        controller.initData(sale);
        controller.passconn(stm);

        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Sale Detail");

        window.setScene(tableViewScene);
        window.show();
        stage.close();
    }

}
