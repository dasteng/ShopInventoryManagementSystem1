    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class AdmindashboardController {
    
// this finds the id=button in the fxml which is in the admindashboard.fxml and the others thats has an fx:id =""
    @FXML 
    private Button buttonInventory, buttonSalesReport, buttonProfile, buttonOrders, logoutButton;
    
    @FXML
    private StackPane Centerpane;
    
// this is the logic
    @FXML
    public void initialize() {
        // to give function for the buttons in the left sidepart
        buttonInventory.setOnAction(e -> showInventory());
     //   buttonSalesReport.setOnAction(e -> showSales());
     //   buttonOrders.setOnAction(e -> showOrders());
      //  buttonProfile.setOnAction(e -> showProfile());
        logoutButton.setOnAction(e -> {
            try {
                App.setRoot("lOGin"); // itll work with or without strings ince the app.setroot is already in the app.java named login
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    

    private void showInventory() {
    
    Centerpane.getChildren().clear();

    TableView<String> table = new TableView<>();
    TableColumn<String, String> nameCol = new TableColumn<>("Product Name");
    TableColumn<String, String> qtyCol = new TableColumn<>("Quantity");

    table.getColumns().addAll(nameCol, qtyCol);
    
    Centerpane.getChildren().add(table);
}

    
    
}