/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;
import java.util.List;
import java.util.Optional;

public class UsermanagementController {

    @FXML private TableView<String[]> usersTable;
    @FXML private TableColumn<String[], String> colId;
    @FXML private TableColumn<String[], String> colUsername;
    @FXML private TableColumn<String[], String> colRole;
    @FXML private TextField newUsernameField;
    @FXML private PasswordField newPasswordField;
    @FXML private ComboBox<String> newRoleCombo;
    @FXML private Label messageLabel;

    private ObservableList<String[]> userList = FXCollections.observableArrayList();

    public void initialize() {
        colId.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));
        colUsername.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));
        colRole.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue()[2]));

        newRoleCombo.setItems(FXCollections.observableArrayList("owner", "manager"));
        newRoleCombo.setValue("manager");

        loadUsers();
    }

    private void loadUsers() {
        userList.clear();
        userList.addAll(DatabaseManager.getAllUsers());
        usersTable.setItems(userList);
    }

    @FXML
    private void handleAddUser() {
        String username = newUsernameField.getText().trim();
        String password = newPasswordField.getText().trim();
        String role = newRoleCombo.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username and password cannot be empty!");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        boolean success = DatabaseManager.addUser(username, password, role);
        if (success) {
            messageLabel.setText("User added successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
            newUsernameField.clear();
            newPasswordField.clear();
            loadUsers();
        } else {
            messageLabel.setText("Failed to add user. Username may already exist.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleDeleteUser() {
        String[] selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Please select a user to delete.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        int id = Integer.parseInt(selected[0]);
        String username = selected[1];

        // prevent deleting yourself - assuming owner id is 1
        if (selected[2].equals("owner") && DatabaseManager.getAllUsers().stream()
                .filter(u -> u[2].equals("owner")).count() <= 1) {
            messageLabel.setText("Cannot delete the only owner account!");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete User");
        confirm.setHeaderText("Delete " + username + "?");
        confirm.setContentText("This cannot be undone.");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            DatabaseManager.deleteUser(id);
            messageLabel.setText("User deleted.");
            messageLabel.setStyle("-fx-text-fill: green;");
            loadUsers();
        }
    }

    @FXML
    private void handleChangePassword() {
        String[] selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("Please select a user to change password.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Change password for: " + selected[1]);
        dialog.setContentText("New password:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !result.get().trim().isEmpty()) {
            DatabaseManager.updatePassword(Integer.parseInt(selected[0]), result.get().trim());
            messageLabel.setText("Password updated successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
        }
    }
}
