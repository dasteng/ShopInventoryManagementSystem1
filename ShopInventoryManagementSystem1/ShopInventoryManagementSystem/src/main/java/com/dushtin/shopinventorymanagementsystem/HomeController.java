/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.List;
/**
 * FXML Controller class
 *
 * @author dusht
 */
public class HomeController {
    
    @FXML private Label totalTransactionsLabel;
    @FXML private Label totalItemsSoldLabel;
    @FXML private Label totalProfitLabel;
    @FXML private Label totalStocksLabel;
    @FXML private Label totalProductsLabel;
    
    


    
    public void initialize() {
        int totalTransactions = DatabaseManager.getAllSales().size();
        totalTransactionsLabel.setText(String.valueOf(totalTransactions));
        
        List<Sale> sales = DatabaseManager.getAllSales();
        int totalitemsold = 0;
        for (Sale s : sales) {
            totalitemsold += s.getQuantitySold();
        }
        totalItemsSoldLabel.setText(String.valueOf(totalitemsold));
        
        double totalProfit = DatabaseManager.getTotalSaleAmount();
        totalProfitLabel.setText(String.format("₱%.2f", totalProfit));
        
        List<Product> stocks = DatabaseManager.getAllProducts();
        int totalStocks = 0;
        for (Product s : stocks) {
            totalStocks += s.getQuantity();
        }
        totalStocksLabel.setText(String.valueOf(totalStocks));
        
        totalProductsLabel.setText(String.valueOf(DatabaseManager.getTotalProductCount()));
        
        
    }    
    
}
