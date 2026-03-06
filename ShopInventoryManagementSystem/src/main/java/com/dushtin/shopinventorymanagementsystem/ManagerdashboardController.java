package com.dushtin.shopinventorymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class ManagerdashboardController {

    @FXML
    private StackPane centerPane;

    @FXML
    private Button inventoryButton, salesButton, ordersButton, profileButton, logoutButton;

    @FXML
    public void initialize() {
        // Sidebar buttons
        inventoryButton.setOnAction(e -> showInventory());
        salesButton.setOnAction(e -> showSales());
        ordersButton.setOnAction(e -> showOrders());
        profileButton.setOnAction(e -> showProfile());

        // Logout button
        logoutButton.setOnAction(e -> {
            try {
                App.setRoot("login"); // your login scene
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // Show Inventory Table
    private void showInventory() {
        centerPane.getChildren().clear();

        TableView<String> table = new TableView<>();
        TableColumn<String, String> nameCol = new TableColumn<>("Product Name");
        TableColumn<String, String> qtyCol = new TableColumn<>("Quantity");

        table.getColumns().addAll(nameCol, qtyCol);

        // TODO: Load actual product data from database here

        centerPane.getChildren().add(table);
    }

    private void showSales() {
        centerPane.getChildren().clear();
        Label label = new Label("Sales Report Page");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        centerPane.getChildren().add(label);
    }

    private void showOrders() {
        centerPane.getChildren().clear();
        Label label = new Label("Orders Page");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        centerPane.getChildren().add(label);
    }

    private void showProfile() {
        centerPane.getChildren().clear();
        Label label = new Label("Profile Page");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        centerPane.getChildren().add(label);
    }
}