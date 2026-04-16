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
import javafx.scene.layout.VBox;

public class ManagerdashboardController {
    

    @FXML 
private Button buttonInventory, buttonSalesRecording, buttonStockMonitoring, buttonReporting, logoutButton, homeButton, burgerButton, buttonUserManagement;
    @FXML private VBox sidebar;
    @FXML private StackPane Centerpane;
    
    

    @FXML
    public void initialize() {
        if (buttonInventory != null) buttonInventory.setOnAction(e -> { showInventory(); setActive(buttonInventory); });
        if (buttonUserManagement != null) buttonUserManagement.setOnAction(e -> { showUserManagement(); setActive(buttonUserManagement); });
        if (buttonSalesRecording != null) buttonSalesRecording.setOnAction(e -> { showSalesRecording(); setActive(buttonSalesRecording); });
        if (buttonStockMonitoring != null) buttonStockMonitoring.setOnAction(e -> { showStockMonitoring(); setActive(buttonStockMonitoring); });
        if (buttonReporting != null) buttonReporting.setOnAction(e -> { showReporting(); setActive(buttonReporting); });
        if (homeButton != null) homeButton.setOnAction(e -> { showHome(); setActive(homeButton); });
        if (burgerButton != null && sidebar != null) burgerButton.setOnAction(e -> toggleSidebar());
        //showHome();
        showInventory();
        if (logoutButton != null) {
            logoutButton.setOnAction(e -> {
                try {
                    App.setRoot("Login"); 
                    setActive(logoutButton);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
    
    private void toggleSidebar() {
        boolean showing = sidebar.isVisible();
        sidebar.setVisible(!showing);
        sidebar.setManaged(!showing);
    }
    
    private void showUserManagement() {
    Centerpane.getChildren().clear();
    try {
        Parent root = FXMLLoader.load(getClass().getResource("usermanagement.fxml"));
        Centerpane.getChildren().add(root);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    private void setActive(Button activeButton) {
        homeButton.getStyleClass().remove("active");
        buttonUserManagement.getStyleClass().remove("active");
        buttonInventory.getStyleClass().remove("active");
        buttonSalesRecording.getStyleClass().remove("active");
        buttonStockMonitoring.getStyleClass().remove("active");
        buttonReporting.getStyleClass().remove("active");
        logoutButton.getStyleClass().remove("active");
        
        
        if (!activeButton.getStyleClass().contains("active")) {
            activeButton.getStyleClass().add("active");
        }
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
