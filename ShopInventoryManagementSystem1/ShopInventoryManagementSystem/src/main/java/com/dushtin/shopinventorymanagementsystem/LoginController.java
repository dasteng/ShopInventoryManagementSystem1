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
            String user = usernameField.getText();
            String pass = passwordField.getText();

            try {
                if (user.equals("admin") && pass.equals("1234")) {
                    App.setRoot("AdminDashboard"); // switch to admin
                } else if (user.equals("manager") && pass.equals("1234")) {
                    App.setRoot("ManagerDashboard"); // switch to manager
                } else {
                    messageLabel.setText("Incorrect username or password");
                    messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}