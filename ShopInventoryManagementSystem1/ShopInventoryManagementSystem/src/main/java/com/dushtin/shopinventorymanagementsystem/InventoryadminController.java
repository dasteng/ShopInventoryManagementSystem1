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



public class InventoryadminController {

    @FXML private Label totalProductsLabel;
    @FXML private Label lowStockLabel;
    @FXML private Label outOfStockLabel;
    
    @FXML private TableView <Product> inventoryTable;
    
    @FXML private TableColumn <Product, String> idColumn;
    @FXML private TableColumn <Product, String> nameColumn;
    @FXML private TableColumn <Product, String> categoryColumn;
    @FXML private TableColumn <Product, Integer> quantityColumn;
    @FXML private TableColumn <Product, Double> priceColumn;
    
    @FXML private TextField searchField;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    
    private ObservableList <Product> productList = FXCollections.observableArrayList();

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        inventoryTable.setItems(productList);
        
        
        refreshTable();
        
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isBlank()) {
                loadAllProducts();
            } 
            else {
                List<Product> results = DatabaseManager.searchProduct(newVal.trim());
                productList.setAll(results);
            }
        });
        
        addButton.setOnAction(e -> showAddDialog());
        editButton.setOnAction(e -> showEditDialog());
        deleteButton.setOnAction(e -> deleteSelected());
    }     
        private void refreshTable() {
            loadAllProducts();
            updateSummaryCards();
        }
        
        private void loadAllProducts(){
            productList.setAll(DatabaseManager.getAllProducts());
        }
        
        private void updateSummaryCards(){
            totalProductsLabel.setText(String.valueOf(DatabaseManager.getTotalProductCount()));
            lowStockLabel.setText(String.valueOf(DatabaseManager.getLowStock()));
            outOfStockLabel.setText(String.valueOf(DatabaseManager.getLowStock()));
        
        }
        
        public void showAddDialog() {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Add New Product");
        dialog.setHeaderText("Enter Product Details:");
        
        ButtonType saveBtn = new ButtonType("Save!", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);
        
        // input of useaaa and also that setPromptText is the gray shi u see inside the userinput box
        TextField idField = new TextField(); 
        idField.setPromptText("Product ID (EX: P001)");
        TextField nameField = new TextField(); 
        nameField.setPromptText("Product Name");
        TextField categoryField = new TextField(); 
        categoryField.setPromptText("Category");
        TextField quantityField = new TextField(); 
        quantityField.setPromptText("Quantity");
        TextField priceField = new TextField(); 
        priceField.setPromptText("Price");
        
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        
        grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0); grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1); grid.add(nameField, 1, 1);
        grid.add(new Label("Category:"), 0, 2); grid.add(categoryField, 1, 2);
        grid.add(new Label("Quantity:"), 0, 3); grid.add(quantityField, 1, 3);
        grid.add(new Label("Price:"), 0, 4); grid.add(priceField, 1, 4);
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter (btn -> {
            if (btn == saveBtn) {
                try{
                    String id = idField.getText().trim();
                    String name = nameField.getText().trim();
                    String cat = categoryField.getText().trim();
                    int qty  = Integer.parseInt(quantityField.getText().trim());
                    double pr = Double.parseDouble(priceField.getText().trim());
                    
                    if(id.isEmpty() || name.isEmpty() || cat.isEmpty()) return null;
                    return new Product(id, name, cat, qty, pr);
                    
                
                
                }
                catch (NumberFormatException ex) {
                    showAlert("Invalid Input", "Quantity must be a whole number and Price must be a number.");
                    return null;
                } 
            }
            return null;
        });
            // this will run if the upper code work. this just mean that do something with the result from the top
        Optional<Product> result = dialog.showAndWait();
        result.ifPresent(p -> {
            if (DatabaseManager.updateProduct(p)) {
                refreshTable();
            }
            else {
                showAlert("Error", "Failed to update product.");
            }
        });
    
        
        
        
        }
        
        public void showEditDialog(){
            Product selected = inventoryTable.getSelectionModel().getSelectedItem();
                if(selected == null) {
                    showAlert("No Selection", "Please select a Product to edit");
                    return;
                }
            Dialog<Product> dialog = new Dialog<>();    
            dialog.setTitle("Editing Product");
            dialog.setHeaderText("Update Product Details:");
            
            ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);
            
            TextField nameField = new TextField(selected.getName());
            TextField categoryField = new TextField(selected.getCategory());
            TextField qtyField = new TextField(String.valueOf(selected.getQuantity()));
            TextField priceField = new TextField(String.valueOf(selected.getPrice()));
            
            Label idLabel = new Label(selected.getProductID());
            idLabel.setStyle("-fx-font-weight: bold;");
            
            javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            
            grid.add(new Label("ID:"), 0,0);    grid.add(idLabel, 1,0);
            grid.add(new Label("Name:"), 0,1);  grid.add(nameField, 1,1);
            grid.add(new Label("Category:"), 0,2);  grid.add(categoryField, 1,2);
            grid.add(new Label("Quantity:"), 0,3);  grid.add(qtyField, 1,3);
            grid.add(new Label("Price:"), 0,4);  grid.add(nameField, 1,4);
            dialog.getDialogPane().setContent(grid);
            
            dialog.setResultConverter(btn -> {
                if(btn == saveBtn) {
                    try{
                        selected.setProductName(nameField.getText().trim());
                        selected.setCategory(categoryField.getText().trim());
                        selected.setQuantity(Integer.parseInt(qtyField.getText().trim()));
                        selected.setPrice(Double.parseDouble(priceField.getText().trim()));
                    return selected;
                    }
                    catch(NumberFormatException ex) {
                        showAlert("Invalid Input","Quantity must be a Whole Number and Price must be a Number");
                    }
                }
                return null;
            });
            
            Optional<Product> result = dialog.showAndWait();
            result.ifPresent(p -> {
                if (DatabaseManager.updateProduct(p)) {
                    refreshTable();
                } else {
                    showAlert("Errrr", "Failed to update Product");
                }
            
            });
            
            
            
            
        }
        
        public void deleteSelected() {
            Product selected = inventoryTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("No Selection" ,"Select a Product to Delete Again");
                return;
            }
            
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Delete \"" + selected.getName() + "\"? This Cannot be Undone",
                    ButtonType.YES, ButtonType.NO);
            
            confirm.setTitle("Confirm Delete");
            confirm.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.YES) {
                    if (DatabaseManager.deleteProduct(selected.getProductID())) {
                        refreshTable();
                    } else {
                        showAlert("Error", "Failed to Delete Product");
                    }
                }
            });
        
        
        }
        
        private void showAlert(String title, String message){
            Alert alert = new Alert (Alert.AlertType.INFORMATION, message, ButtonType.OK);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        
        
        
        
        
        
        
        
       
    
}
