package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Statement;

public class Controller {

    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }
    public void Item(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Item.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        ItemController controller = loader.getController();
        controller.passconn(stm);


        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Item List");

        window.setScene(tableViewScene);
        window.show();
    }
    public void Employee(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Employee.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);


        EmployeeController controller = loader.getController();
        controller.passconn(stm);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Employee List");

        window.setScene(tableViewScene);
        window.show();
    }
    public void Supplier(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Supplier.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        SupplierController controller = loader.getController();
        controller.passconn(stm);

        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Supplier List");

        window.setScene(tableViewScene);
        window.show();
    }
    public void Customer(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Customer.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);


        CustomerController controller = loader.getController();
        controller.passconn(stm);

        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Customer List");

        window.setScene(tableViewScene);
        window.show();
    }
    public void Sale(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Sale.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);


        SaleController controller = loader.getController();
        controller.passconn(stm);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Item List");

        window.setScene(tableViewScene);
        window.show();
    }
    public void Purchase(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Purchase.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);


        PurchaseController controller = loader.getController();
        controller.passconn(stm);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("Purchase List");

        window.setScene(tableViewScene);
        window.show();
    }

    public void Username(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Username.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);


        UsernameController controller = loader.getController();
        controller.passconn(stm);
        //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage window = new Stage();
        window.setTitle("User List");

        window.setScene(tableViewScene);
        window.show();
    }
}
