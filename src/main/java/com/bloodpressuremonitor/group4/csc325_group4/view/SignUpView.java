package com.bloodpressuremonitor.group4.csc325_group4.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpView {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button backToLoginButton;

    @FXML
    private void handleBackToLoginButton(ActionEvent event) throws IOException {
        App.setRoot("/files/LoginView.fxml");
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TODO");
        alert.setHeaderText("TODO: Register User");
        alert.showAndWait();
    }

}
