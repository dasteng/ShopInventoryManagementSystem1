/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dushtin.shopinventorymanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController {
// same shit, it
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label messageLabel;

    public void initialize() {
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            
            String role = DatabaseManager.login(username, password);

            try {
                if("admin".equals(role)) {
                    App.setRoot("AdminDashboard");
                } else if("manager".equals(role)) {
                    App.setRoot("ManagerDashboard");
                } else {
                    messageLabel.setText("Incorrect Username or Password!");
                    messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        });
    }
}