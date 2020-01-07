package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UsernameController {
    @FXML
    private TableView<Username> UserTable;
    @FXML
    private TableColumn<Username, String> col_user;
    @FXML
    private TableColumn<Username, String> col_grant;
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;
    @FXML
    private PasswordField Password1;
    @FXML
    private ComboBox<String> privileges;
    @FXML
    private Label error_label;
    @FXML
    private Label error_label1;

    ObservableList<Username> oblist = FXCollections.observableArrayList();
    Statement stm;

    public void passconn(Statement state){
        stm = state;
        privileges.getItems().add("read");
        privileges.getItems().add("read and write");
        privileges.getItems().add("all");
        privileges.setValue("read and write");
    }

    public void Add(){
        String username = Username.getText();
        String password = Password.getText();
        String password1 = Password1.getText();
        String pvl = privileges.getValue();
        if (password.equals(password1)){
            try {
                System.out.println("CREATE USER '" + username + "'@'%' IDENTIFIED BY '" + password +"' ;");
                stm.executeUpdate("CREATE USER '" + username + "'@'%' IDENTIFIED BY '" + password +"' ;");
                if (pvl.equals("read")) {
                    stm.executeUpdate("GRANT select on Store.* to '" + username + "'@'%' ;");
                } else if (pvl.equals("read and write")) {
                    stm.executeUpdate("GRANT select,insert on Store.* to'" + username + "'@'%' ;");
                } else if (pvl.equals("all")){
                    stm.executeUpdate("GRANT ALL PRIVILEGES on Store.* to'" + username + "'@'%' ;");
                }
                stm.executeUpdate("Flush privileges;");
                error_label.setText("SUCCESSFUL");
                error_label1.setText("");
                Username.clear();
                Password.clear();
                Password1.clear();
            } catch (SQLException e) {
                error_label.setText(e.getMessage());
                error_label1.setText("Please use root username");
            }
        }else {
            error_label.setText("Check Password");
        }
    }

    public void Load(){
        List<String> user = null;
        oblist.clear();
        try {
            ResultSet rs = stm.executeQuery( "select user,host from mysql.user where host = '%'") ;

            oblist.add(new Username("root","Super Admin"));
            while (rs.next()){
                oblist.add(new Username(rs.getString("user")));
            }
            for (int i = 1 ; i < oblist.size() ; i++){
                ResultSet rs1 = stm.executeQuery("show grants for '"+oblist.get(i).getUsername()+"'@'%' ;");
                rs1.next();
                rs1.next();
                oblist.get(i).setGrants(rs1.getString(1));
            }
        } catch (SQLException e) {
            error_label.setText(e.getMessage());
            error_label1.setText("Please use root Username");
        }

        col_user.setCellValueFactory(new PropertyValueFactory<>("Username"));
        col_grant.setCellValueFactory(new PropertyValueFactory<>("Grants"));

        UserTable.setItems(oblist);
    }

    public void Delete(){
        sample.Username user = UserTable.getSelectionModel().getSelectedItem();
        if (!user.getUsername().equals("root")) {
            try {
                stm.executeUpdate("drop user '" + user.getUsername() + "'@'%' ;");
                error_label.setText("SUCCESSFUL");
            } catch (SQLException e) {
                error_label.setText(e.getMessage());
            }
        } else {
            error_label.setText("you can't delete root");
        }
    }
}
