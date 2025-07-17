package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.bloodpressuremonitor.group4.csc325_group4.viewmodel.LoginViewModel;
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

    private LoginViewModel loginViewModel = new LoginViewModel();

    @FXML
    public void initialize() {
        emailField.textProperty().bindBidirectional(loginViewModel.emailProperty());
        passwordField.textProperty().bindBidirectional(loginViewModel.passwordProperty());
    }

    @FXML
    private void handleLoginButton(ActionEvent event) {
        try {
            //String email = emailField.getText().trim();
            //String password = passwordField.getText().trim();
            loginViewModel.login();
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter your email and password");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleSignUpButton(ActionEvent event) throws IOException {
        App.setRoot("/files/SignUpView.fxml");
    }


}
