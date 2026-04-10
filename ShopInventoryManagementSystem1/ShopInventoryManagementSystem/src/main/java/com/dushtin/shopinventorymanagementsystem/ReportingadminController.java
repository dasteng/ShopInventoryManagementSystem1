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
    @FXML private TableView<int[]> inventoryReportTable;
    @FXML private TableView<Sale> salesReportTable;
    @FXML private TableView<Product> lowStockTable;

    public void initialize() {
        inventoryReport();
        salesReport();
        lowStockReport();
    } 
    
    private void inventoryReport(){
        int total      = DatabaseManager.getTotalProductCount();
        int outOfStock = DatabaseManager.getOutofStock();
        int lowStock   = DatabaseManager.getLowStock();
        
        String status;
        if (outOfStock == 0 && lowStock == 0) status = "All Good";
        else if (outOfStock > 0) status = "Need to Restock this Product";
        else status = "Low Stock Warning";
        
        TableColumn<int[], Integer> ttlProducts = (TableColumn<int[], Integer>) inventoryReportTable.getColumns().get(0);
        TableColumn<int[], Integer> oosProducts = (TableColumn<int[], Integer>) inventoryReportTable.getColumns().get(1);
        TableColumn<int[], String> statss = (TableColumn<int[], String>) inventoryReportTable.getColumns().get(2);
    
        ttlProducts.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue()[0]).asObject());
        oosProducts.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue()[1]).asObject());
        statss.setCellValueFactory(d -> new SimpleStringProperty(status));
        
        ObservableList<int[]> data = FXCollections.observableArrayList();
        data.add(new int[]{total, outOfStock});
        inventoryReportTable.setItems(data);
        
        
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
