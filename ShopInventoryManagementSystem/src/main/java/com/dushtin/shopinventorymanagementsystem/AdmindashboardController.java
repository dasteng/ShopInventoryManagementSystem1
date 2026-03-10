    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TableColumn;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.util.converter.IntegerStringConverter;
//import javafx.util.converter.DefaultStringConverter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class AdmindashboardController {
    
// this finds the id=button in the fxml which is in the admindashboard.fxml and the others thats has an fx:id =""
    @FXML 
    private Button buttonInventory, buttonSalesRecording, buttonStockMonitoring, buttonReporting, logoutButton, homeButton;
    
    @FXML
    private StackPane Centerpane;
    
// this is the logic
    @FXML
    public void initialize() {
        // to give function for the buttons in the left sidepart
        buttonInventory.setOnAction(e -> showInventory());
        buttonSalesRecording.setOnAction(e -> showSalesRecording());
        buttonStockMonitoring.setOnAction(e -> showStockMonitoring());
        buttonReporting.setOnAction(e -> showReporting());
        homeButton.setOnAction (e -> showHome());
        showHome();
        logoutButton.setOnAction(e -> {
            try {
                App.setRoot("lOGin"); // itll work with or without strings ince the app.setroot is already in the app.java named login
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public void showInventory() {
        
    Centerpane.getChildren().clear();
    try {
        Parent root = FXMLLoader.load(getClass().getResource("inventoryadmin.fxml"));
        Centerpane.getChildren().add(root);
    } catch (IOException e) {
        e.printStackTrace();
    }  
          

    }
    


    private void showSalesRecording() {
    Centerpane.getChildren().clear();
    try {
        Parent root = FXMLLoader.load(getClass().getResource("salesrecordingadmin.fxml"));
        Centerpane.getChildren().add(root);
    } catch (IOException e) {
        e.printStackTrace();
    }  
      
    }
    
    private void showStockMonitoring() {
    Centerpane.getChildren().clear();
    try {
        Parent root = FXMLLoader.load(getClass().getResource("stockmonitoringadmin.fxml"));
        Centerpane.getChildren().add(root);
    } catch (IOException e) {
        e.printStackTrace();
    }  
         
    
    }
    
    private void showReporting() {
    Centerpane.getChildren().clear();
    
    try {
        Parent root = FXMLLoader.load(getClass().getResource("reportingadmin.fxml"));
        Centerpane.getChildren().add(root);
    } catch (IOException e) {
        e.printStackTrace();
    }  
       
    
    }
    
    private void showHome() {
    Centerpane.getChildren().clear();

    try {
        Parent root = FXMLLoader.load(getClass().getResource("homeadmin.fxml"));
        Centerpane.getChildren().add(root);
    } catch (IOException e) {
        e.printStackTrace();
    }  
        
    }



    
    
    
}