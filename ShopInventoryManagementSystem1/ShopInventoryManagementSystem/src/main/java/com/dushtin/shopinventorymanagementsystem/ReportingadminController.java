/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
/**
 * FXML Controller class
 *
 * @author dusht
 */
public class ReportingadminController{

    //as always, connect them fxml to this controller by this type of code
    @FXML private TableView<Product> inventoryReportTable;
    @FXML private TableView<Sale> salesReportTable;
    @FXML private TableView<Product> lowStockTable;
    @FXML private Label totalInventoryValueLabel;

    public void initialize() {
        inventoryReport();
        salesReport();
        lowStockReport();
    } 
    
    private void inventoryReport(){
        //as always, connect them fxml to this controller by this type of code
        TableColumn<Product, String> invProductCol = (TableColumn<Product, String>) inventoryReportTable.getColumns().get(0);
        TableColumn<Product, String> invCategoryCol = (TableColumn<Product, String>) inventoryReportTable.getColumns().get(1);
        TableColumn<Product, Integer> invStockCol = (TableColumn<Product, Integer>) inventoryReportTable.getColumns().get(2);
        TableColumn<Product, Double> invValueCol = (TableColumn<Product, Double>) inventoryReportTable.getColumns().get(3);

        invProductCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        invCategoryCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCategory()));
        invStockCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getQuantity()).asObject());
        invValueCol.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getQuantity() * d.getValue().getPrice()).asObject());

        List<Product> all = DatabaseManager.getAllProducts();
        inventoryReportTable.setItems(FXCollections.observableArrayList(all));

        // compute total inventory value
        double totalValue = 0;
        for (Product p : all) {
            totalValue += p.getQuantity() * p.getPrice();
        }
        totalInventoryValueLabel.setText(String.format("Total Inventory Value: ₱%.2f", totalValue));
    }
    
    private void salesReport(){
    TableColumn<Sale, Integer> saleId = (TableColumn<Sale, Integer>) salesReportTable.getColumns().get(0);
    TableColumn<Sale, String> product = (TableColumn<Sale, String>) salesReportTable.getColumns().get(1);
    TableColumn<Sale, Integer> quantity = (TableColumn<Sale, Integer>) salesReportTable.getColumns().get(2);
    TableColumn<Sale, Double> price = (TableColumn<Sale, Double>) salesReportTable.getColumns().get(3);
    
    
    saleId.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getSaleId()).asObject());
    product.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getProductName()));
    quantity.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getQuantitySold()).asObject());
    price.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getTotal()).asObject());

    List<Sale> sales = DatabaseManager.getAllSales();
    salesReportTable.setItems   (FXCollections.observableArrayList(sales));
    }
    
    private void lowStockReport(){
    TableColumn<Product, String> lowproduct = (TableColumn<Product, String>) lowStockTable.getColumns().get(0);
    TableColumn<Product, Integer> productquantity = (TableColumn<Product, Integer>) lowStockTable.getColumns().get(1);
    
    lowproduct.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
    productquantity.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getQuantity()).asObject());
    
    List<Product> all = DatabaseManager.getAllProducts();
    ObservableList<Product> lowstokc = FXCollections.observableArrayList();
    for (Product p: all) {
        if (p.getQuantity() <= 10) lowstokc.add(p);
    }
    lowStockTable.setItems(lowstokc);
    }
}
