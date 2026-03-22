package com.dushtin.shopinventorymanagementsystem;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ManagerdashboardController {

    @FXML
    private StackPane centerPane;

    @FXML
    private Button inventoryButton, salesRecordingButton, stockMonitoringButton, reportingButton, logoutButton, homeButton;

    @FXML
    public void initialize() {
        // Sidebar buttons
        inventoryButton.setOnAction(e -> showInventory());
        salesRecordingButton.setOnAction(e -> showSalesRecording());
        stockMonitoringButton.setOnAction(e -> showStockMonitoring());
        reportingButton.setOnAction(e -> showReporting());
        homeButton.setOnAction(e -> showHome());
        showHome();

        // Logout button HAHHAHAHAHAHAHAH
        logoutButton.setOnAction(e -> {
            try {
                App.setRoot("login"); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // to basically show the table in rightparts

    private void showInventory() {
        centerPane.getChildren().clear();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("inventoryadmin.fxml"));
                centerPane.getChildren().add(root);
            } catch (IOException e){
                e.printStackTrace();
            }
    }

    private void showSalesRecording() {
        centerPane.getChildren().clear();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("salesrecordingadmin.fxml"));
                centerPane.getChildren().add(root);
            } catch (IOException e){
                e.printStackTrace();
            }
    }

    private void showStockMonitoring() {
        centerPane.getChildren().clear();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("stockmonitoringadmin.fxml"));
                centerPane.getChildren().add(root);
            } catch (IOException e){
                e.printStackTrace();
            }        
    }

    private void showReporting() {
        centerPane.getChildren().clear();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("reportingadmin.fxml"));
                centerPane.getChildren().add(root);
            } catch (IOException e){
                e.printStackTrace();
            }        
        
    }

        private void showHome(){
            centerPane.getChildren().clear();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("homeadmin.fxml"));
                centerPane.getChildren().add(root);
            } catch (IOException e){
                e.printStackTrace();
            }



        }

    
}