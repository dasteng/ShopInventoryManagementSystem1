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
import java.util.Optional;
/**
 * FXML Controller class
 *
 * @author dusht
 */
public class SalesrecordingadminController {
    @FXML private Label totalSalesLabel;
    @FXML private Label totalProfitLabel;
    @FXML private Label transactionsLabel;
    
    @FXML private TableView<Sale> salesTable;
    @FXML private TableColumn<Sale, Integer> saleIdColumn;
    @FXML private TableColumn<Sale, String> productNameColumn;
    @FXML private TableColumn<Sale, Integer> quantitySoldColumn;
    @FXML private TableColumn<Sale, Double> priceColumn;
    @FXML private TableColumn<Sale, Double> totalColumn;
    
    @FXML private ComboBox<String> productComboBox;
    @FXML private TextField searchField;
    @FXML private Label currentStockLabel;
    @FXML private Button addSaleBtn;

     private ObservableList<Sale> saleList = FXCollections.observableArrayList();
    
    public void initialize() {
        // bind from sale.java
        saleIdColumn.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantitySoldColumn.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceEach"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        salesTable.setItems(saleList);
        
        refreshProductComboBox();
        productComboBox.setOnAction(e -> updateCurrentStock());
        
        searchField.textProperty().addListener((obs, o, newVal) -> {
            if(newVal == null || newVal.isBlank()) {
                saleList.setAll(DatabaseManager.getAllSales());
            } else {
                saleList.setAll(DatabaseManager.searchSales(newVal.trim()));
            }
        });
        
        addSaleBtn.setOnAction(e -> showAddSaleDialog());
        
        refreshTable();
    }    
    
    private void refreshProductComboBox(){
        List<Product> products = DatabaseManager.getAllProducts();
        ObservableList<String> productsIds = FXCollections.observableArrayList();
        
        for (Product p : products) {
            productsIds.add(p.getProductID() + " - " + p.getName());
        }
        productComboBox.setItems(productsIds);
    }
    
    
    private void updateCurrentStock(){
    String selected = productComboBox.getValue();
    if (selected == null ) return;
    String productId = selected.split(" - ")[0].trim();
    List<Product> all = DatabaseManager.getAllProducts();
    for (Product p : all) {
        if (p.getProductID().equals(productId)) {
            currentStockLabel.setText(String.valueOf(p.getQuantity()));
            
            return;
        }
    
    }
    }
    
    
    private void showAddSaleDialog(){
    String selected = productComboBox.getValue();
    if (selected == null ){
        showAlert("No product selected!", "Please do select a product from the Dropdown");
        return;
    }
    String productId = selected.split(" - ")[0].trim();
    
        TextInputDialog qtyDialog = new TextInputDialog("1");
        qtyDialog.setTitle("Record Sale");
        qtyDialog.setHeaderText("Selling: " + selected);
        qtyDialog.setContentText("Enter quantity sold:");
    
    Optional<String> result = qtyDialog.showAndWait();
        result.ifPresent(qtyStr -> {
            try {
                int qty = Integer.parseInt(qtyStr.trim());
                if (qty <= 0) {
                    showAlert("Invalid Quantity", "Quantity must be greater than 0.");
                    return;
                }
                if (DatabaseManager.recordSale(productId, qty)) {
                    refreshTable();
                    refreshProductComboBox();
                    updateCurrentStock();
                } else {
                    showAlert("Not Enough Stock", "Cannot record sale” insufficient stock for this product.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid whole number for quantity.");
            }
        });
    
    }
    
    
    private void refreshTable(){
        saleList.setAll(DatabaseManager.getAllSales());
        updateSummaryCards();   
    }
    
    
    private void updateSummaryCards(){
        
        double todaySales = DatabaseManager.getTodaySaleAmount(); // total sales for today only
        double totalProfit = DatabaseManager.getTotalSaleAmount(); // alltime total profit
        totalSalesLabel.setText(String.format("₱%.2f", todaySales));
        totalProfitLabel.setText(String.format("₱%.2f", totalProfit));
        transactionsLabel.setText(String.valueOf(DatabaseManager.getTransactionTodayCount()));
        
    }
    
    
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    
    }
    
}
