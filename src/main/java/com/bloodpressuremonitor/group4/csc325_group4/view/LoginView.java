package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

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
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        try {
            if (email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter your email and password");
                alert.showAndWait();
            } else {
                UserRecord user = App.fauth.getUserByEmail(email);
                if (user == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Email");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Simulated Login Successful");
                    alert.showAndWait();
                    App.setRoot("/files/DashboardView.fxml");
                }
            }
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("TODO");
        //alert.setHeaderText("TODO: Login User");
        //alert.showAndWait();
    }

    @FXML
    private void handleSignUpButton(ActionEvent event) throws IOException {
        App.setRoot("/files/SignUpView.fxml");
    }


}
