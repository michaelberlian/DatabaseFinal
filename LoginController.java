package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Label label;

    private Connection conn;
    private Statement stm;


    public void login(ActionEvent event) throws IOException {
        String port = "3306";
        String dbName = "store";
        String user = username.getText();
        String pass = password.getText();

        try{

            Stage stage = (Stage) login.getScene().getWindow();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true", user, pass);
            stm = conn.createStatement();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Main.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            Controller controller = loader.getController();
            controller.passconn(stm);
            //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            Stage window = new Stage();
            window.setTitle("Store");
            window.setScene(tableViewScene);
            window.show();
            stage.close();
        } catch (SQLException ex){
            label.setText(ex.getMessage());
        }
    }
}
