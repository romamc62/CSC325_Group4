package com.bloodpressuremonitor.group4.csc325_group4.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView {

    @FXML
    private TextField emailField = new TextField();
    @FXML
    private PasswordField passwordField = new PasswordField();
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;

    @FXML
    private void handleLoginButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TODO");
        alert.setHeaderText("TODO: Login");
        alert.showAndWait();
    }

    @FXML
    private void handleSignUpButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TODO");
        alert.setHeaderText("TODO: Sign Up");
        alert.showAndWait();
    }


}
