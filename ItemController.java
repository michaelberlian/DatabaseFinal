package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ItemController  {

    @FXML
    private TableView<Item> ItemTable;
    @FXML
    private TableColumn<Item, String> col_id;
    @FXML
    private TableColumn<Item, String> col_name;
    @FXML
    private TableColumn<Item, String> col_qty;
    @FXML
    private TableColumn<Item, String> col_price;
    @FXML
    private TableColumn<Item, String> col_desc;
    @FXML
    private TextField ItemName;
    @FXML
    private TextField ItemNameLoad;
    @FXML
    private TextField ItemQuantityLoad;
    @FXML
    private TextField ItemPriceLoad;
    @FXML
    private TextField ItemDescriptionLoad;
    @FXML
    private TextField AIName;
    @FXML
    private TextField AIQuantity;
    @FXML
    private TextField AIPrice;
    @FXML
    private TextField AIDescription;
    @FXML
    private Label ItemIdLoad;
    @FXML
    private Label ILabel;
    @FXML
    private Label AILabel;

    private boolean load;
    private boolean delete;


    ObservableList<Item> oblist = FXCollections.observableArrayList();
    Statement stm;

    public void passconn(Statement state){

        stm = state;
    }

    public void Search(){
        oblist.clear();
        String srcName = ItemName.getText();
        try {
            ResultSet rs = stm.executeQuery("select * from Item where ItemName like \"%" + srcName +
                "%\"");
            while (rs.next()){
                oblist.add(new Item(rs.getString("ItemID"), rs.getString("ItemName"),rs.getString("Price"),
                    rs.getString("Description"),rs.getString("ItemQty")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));

        ItemTable.setItems(oblist);
    }


    public void Add(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ItemAdd.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            ItemController controller = loader.getController();
            controller.passconn(stm);


            //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            Stage window = new Stage();
            window.setTitle("New Item");

            window.setScene(tableViewScene);
            window.show();
        } catch (Exception e){
            ILabel.setText(e.getMessage());
        }
    }

    public void Save(ActionEvent event)  {
        String name = AIName.getText();
        String quantity = AIQuantity.getText();
        String price = AIPrice.getText();
        String description = AIDescription.getText();
        try {
            stm.executeUpdate("INSERT INTO Item (ItemName,ItemQty,Price,Description) Value (\"" + name + "\",'"+quantity+"','"+price+"',\"" + description +"\");");
            AIName.clear();
            AIQuantity.clear();
            AIPrice.clear();
            AIDescription.clear();
            AILabel.setText("SUCCESSFUL");
        } catch (SQLException e) {
            AILabel.setText(e.getMessage());
        }
    }

    public void Load(ActionEvent event){

        try {
            Item item = ItemTable.getSelectionModel().getSelectedItem();
            ItemIdLoad.setText(item.getId());
            ItemNameLoad.setText(item.getName());
            ItemQuantityLoad.setText(item.getQuantity());
            ItemPriceLoad.setText(item.getPrice());
            ItemDescriptionLoad.setText(item.getDescription());
            ILabel.setText("");
            load = true;
        } catch (Exception e){
            ILabel.setText("Please choose one of the row");
        }
    }

    public void Edit(ActionEvent event){
        if (load) {
            String id = ItemIdLoad.getText();
            String name = ItemNameLoad.getText();
            String quantity = ItemQuantityLoad.getText();
            String price = ItemPriceLoad.getText();
            String description = ItemDescriptionLoad.getText();
            try {
                stm.executeUpdate("UPDATE Item SET ItemName=\"" + name + "\",ItemQty='" + quantity + "',Price='" + price + "',Description=\"" + description + "\"" + " WHERE ItemID=" + id);
                ItemIdLoad.setText("");
                ItemNameLoad.clear();
                ItemQuantityLoad.clear();
                ItemPriceLoad.clear();
                ItemDescriptionLoad.clear();
                ILabel.setText("SUCCESSFUL");
                load = false;
                Search();
            } catch (SQLException e) {
                ILabel.setText(e.getMessage());
            }
        }
        else {
            ILabel.setText("Please choose and load the data");
        }
    }

    public void Delete(ActionEvent event){
        if (load) {
            if (delete) {
                String id = ItemIdLoad.getText();
                try {
                    stm.executeUpdate("Delete From Item where ItemID=" + id + ";");
                    ItemIdLoad.setText("");
                    ItemNameLoad.clear();
                    ItemQuantityLoad.clear();
                    ItemPriceLoad.clear();
                    ItemDescriptionLoad.clear();
                    ILabel.setText("SUCCESSFUL");
                    load = false;
                    delete = false;
                    Search();
                } catch (SQLException e) {
                    ILabel.setText(e.getMessage());
                }
            }
            else {
                ILabel.setText("Click Delete once more TO DELETE the LOADED DATA");
                delete = true;
            }
        }
        else{
            ILabel.setText("Please choose and load the data");
        }
    }
}
