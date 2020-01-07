package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Database {
    private Connection conn;
    private Statement stm;
    private Scanner sc = new Scanner(System.in);
    public Database(String user, String pass){
        String port = "3306";
        String dbName = "store";
        String username = user;
        String password = pass;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + dbName + "?useSSL=false", username, password);
            stm = conn.createStatement();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Database(){
        String port = "3306";
        String dbName = "store";
        String username = "root";
        String password = "";
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + dbName + "?useSSL=false", username, password);
            stm = conn.createStatement();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public Statement getStm(){
        return stm;
    }
}
