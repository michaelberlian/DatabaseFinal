package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EmployeeController{

    @FXML
    private TableView<Employee> EmployeeTable;
    @FXML
    private TableColumn<Employee, String> col_id;
    @FXML
    private TableColumn<Employee, String> col_name;
    @FXML
    private TableColumn<Employee, String> col_position;
    @FXML
    private TableColumn<Employee, String> col_salary;
    @FXML
    private TableColumn<Employee, String> col_address;
    @FXML
    private TextField EmployeeName;
    @FXML
    private TextField Position;
    @FXML
    private TextField EmployeeAddress;
    @FXML
    private TextField EmployeeNameLoad;
    @FXML
    private TextField PositionLoad;
    @FXML
    private TextField SalaryLoad;
    @FXML
    private TextField EmployeeAddressLoad;
    @FXML
    private Label EmployeeIdLoad;
    @FXML
    private Label ELabel;
    @FXML
    private TextField AEName;
    @FXML
    private TextField AEPosition;
    @FXML
    private Label AELabel;
    @FXML
    private TextField AESalary;
    @FXML
    private TextField AEAddress;

    private boolean load;

    ObservableList<Employee> oblist = FXCollections.observableArrayList();

    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }
    public void Search(){
        oblist.clear();
        String srcName = EmployeeName.getText();
        String srcPosition = Position.getText();
        String scrAddress = EmployeeAddress.getText();
        try {
            ResultSet rs = stm.executeQuery("select * from Employee where EmployeeName like \"%" + srcName +
                "%\" and Position like \"%" + srcPosition + "%\" and EmployeeAddress like \"%" + scrAddress + "%\"");
            while (rs.next()){
                oblist.add(new Employee(rs.getString("EmployeeID"), rs.getString("EmployeeName"),rs.getString("Position"),
                    rs.getString("Salary"),rs.getString("EmployeeAddress")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        EmployeeTable.setItems(oblist);
    }


    public void Add(ActionEvent event) {
        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeAdd.fxml"));
//            Parent root1 = (Parent) fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setTitle("New Employee");
//            stage.setScene(new Scene(root1));
//            stage.show();


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EmployeeAdd.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            EmployeeController controller = loader.getController();
            controller.passconn(stm);


            //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            Stage window = new Stage();
            window.setTitle("New Employee");

            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e){
            e.getMessage();
        }
    }

    public void Save(ActionEvent event){
        String name = AEName.getText();
        String position = AEPosition.getText();
        String salary = AESalary.getText();
        String address = AEAddress.getText();
        try {
            stm.executeUpdate("INSERT INTO Employee (EmployeeName,Position,Salary,EmployeeAddress) Value (\"" + name + "\",\""+position+"\",'"+salary+"',\"" + address +"\");");
            AEName.clear();
            AEPosition.clear();
            AESalary.clear();
            AEAddress.clear();
            AELabel.setText("SUCCESSFUL");
        } catch (SQLException e) {
            AELabel.setText(e.getMessage());
        }
    }

    public void Load(ActionEvent event){

        try {
            Employee item = EmployeeTable.getSelectionModel().getSelectedItem();
            EmployeeIdLoad.setText(item.getId());
            EmployeeNameLoad.setText(item.getName());
            PositionLoad.setText(item.getPosition());
            SalaryLoad.setText(item.getSalary());
            EmployeeAddressLoad.setText(item.getAddress());
            ELabel.setText("");
            load = true;
        } catch (Exception e){
            ELabel.setText("Please choose one of the row");
        }
    }

    public void Edit(ActionEvent event) {
        if (load) {
            String id = EmployeeIdLoad.getText();
            String name = EmployeeNameLoad.getText();
            String position = PositionLoad.getText();
            String salary = SalaryLoad.getText();
            String address = EmployeeAddressLoad.getText();
            try {
                stm.executeUpdate("UPDATE Employee SET EmployeeName=\"" + name + "\",Position=\"" + position + "\",Salary=\'" + salary + "\',EmployeeAddress=\"" + address + "\"" + " WHERE EmployeeID=" + id);
                EmployeeIdLoad.setText("");
                EmployeeNameLoad.clear();
                PositionLoad.clear();
                SalaryLoad.clear();
                EmployeeAddressLoad.clear();
                ELabel.setText("SUCCESSFUL");
                load = false;
            } catch (SQLException e) {
                ELabel.setText(e.getMessage());
            }
            Search();
        }
        else {
            ELabel.setText("Please choose and load the data");
        }
    }

    public void Delete(ActionEvent event) {
        if (load) {
            String id = EmployeeIdLoad.getText();
            try {
                stm.executeUpdate("Delete From Employee where EmployeeID=" + id + ";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            EmployeeIdLoad.setText("");
            EmployeeNameLoad.clear();
            PositionLoad.clear();
            SalaryLoad.clear();
            EmployeeAddressLoad.clear();
            ELabel.setText("SUCCESSFUL");
            load = false;
            Search();
        }
        else{
            ELabel.setText("Please choose and load the data");
        }
    }
}
