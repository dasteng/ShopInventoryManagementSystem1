/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * FXML Controller class
 *
 * @author dusht
 */
public class StockmonitoringadminController {

    @FXML private Label totalProductsLabel;
    @FXML private Label lowStockLabel;
    @FXML private Label outOfStockLabel;
    
    @FXML private TextField searchField;
    
    @FXML private TableView<Product> stockTable;
    @FXML private TableColumn<Product, String> nameproductColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;
    @FXML private TableColumn<Product, String> statusColumn;
    
    private ObservableList<Product> stockList  = FXCollections.observableArrayList();
    
    public void initialize() {
        nameproductColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
               statusColumn.setCellValueFactory(data -> {
            int qty = data.getValue().getQuantity();
            String status;
            if (qty == 0)       status = "Out of Stock";
            else if (qty <= 10) status = "Low Stock";
            else                status = "In Stock";
            return new javafx.beans.property.SimpleStringProperty(status);
        });

        
        statusColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch (item) {
                        case "Out of Stock" -> setStyle("-fx-text-fill: #F44336; -fx-font-weight: bold;");
                        case "Low Stock"    -> setStyle("-fx-text-fill: #FF9800; -fx-font-weight: bold;");
                        default             -> setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                    }
                }
            }
        });

        stockTable.setItems(stockList);

        
        searchField.textProperty().addListener((obs, o, newVal) -> {
            if (newVal == null || newVal.isBlank()) {
                stockList.setAll(DatabaseManager.getAllProducts());
            } else {
                stockList.setAll(DatabaseManager.searchProduct(newVal.trim()));
            }
        });

        
        refreshTable();
    }

    private void refreshTable() {
        stockList.setAll(DatabaseManager.getAllProducts());
        updateSummaryCards();
    }

    private void updateSummaryCards() {
        totalProductsLabel.setText(String.valueOf(DatabaseManager.getTotalProductCount()));
        lowStockLabel.setText(String.valueOf(DatabaseManager.getLowStock()));
        outOfStockLabel.setText(String.valueOf(DatabaseManager.getOutofStock()));
    }
}
