package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.bloodpressuremonitor.group4.csc325_group4.viewmodel.SignUpViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private SignUpViewModel signUpViewModel =  new SignUpViewModel();

    @FXML
    public void initialize() {
        emailField.textProperty().bindBidirectional(signUpViewModel.emailProperty());
        passwordField.textProperty().bindBidirectional(signUpViewModel.passwordProperty());
    }

    @FXML
    private void handleBackToLoginButton(ActionEvent event) throws IOException {
        App.setRoot("/files/LoginView.fxml");
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) throws IOException {
        try {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            if(hasAllFields(email, password,confirmPassword)){
                if(isValidEmail(email)){
                    if(passwordMatches(password,confirmPassword)){
                        signUpViewModel.registerUser();
                        App.setRoot("/files/loginView.fxml");
                    }
                }
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
        //Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("TODO");
        //alert.setHeaderText("TODO: Register User");
        //alert.showAndWait();
    }

    private static boolean hasAllFields(String email, String password, String confirmPassword) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Enter All Fields");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email");
            alert.showAndWait();
            return false;
        }
    }

    private static boolean passwordMatches(String password, String confirmPassword) {
        if(password.equals(confirmPassword)){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Passwords do not match");
            alert.showAndWait();
            return false;
        }
    }

}
