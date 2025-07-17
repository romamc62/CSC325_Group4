package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.bloodpressuremonitor.group4.csc325_group4.viewmodel.SignUpViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.threeten.bp.format.DateTimeParseException;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.function.UnaryOperator;
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
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField dateOfBirthField;
    @FXML
    private Button registerButton;
    @FXML
    private Button backToLoginButton;

    private SignUpViewModel signUpViewModel =  new SignUpViewModel();


    @FXML
    public void initialize() {
        emailField.textProperty().bindBidirectional(signUpViewModel.emailProperty());
        passwordField.textProperty().bindBidirectional(signUpViewModel.passwordProperty());
        confirmPasswordField.textProperty().bindBidirectional(signUpViewModel.confirmPasswordProperty());
        firstNameField.textProperty().bindBidirectional(signUpViewModel.firstNameProperty());
        lastNameField.textProperty().bindBidirectional(signUpViewModel.lastNameProperty());
        dateOfBirthField.textProperty().bindBidirectional(signUpViewModel.dateOfBirthProperty());


        //date of birth text field formatter
        dateOfBirthField.setTextFormatter(new TextFormatter<String>(change -> {
            if(change.isContentChange()){
                String oldText = change.getControlText();
                String newText = change.getControlNewText().replaceAll("[^\\d]", "");

                if(newText.length() > 8){
                    newText = newText.substring(0, 8);
                }

                StringBuilder format = new StringBuilder();
                for(int i = 0; i < newText.length(); i++){
                    if(i == 2 || i == 4){
                        format.append("/");
                    }
                    format.append(newText.charAt(i));
                }

                int newCaretPosition = format.length();

                change.setText(format.toString());
                change.setRange(0, oldText.length());

                change.selectRange(newCaretPosition, newCaretPosition);

            }

            return change;
        }));


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
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String dateOfBirth = dateOfBirthField.getText().trim();
            if(hasAllFields(email, password,confirmPassword, firstName, lastName, dateOfBirth)){
                if(isValidEmail(email)){
                    if(passwordMatches(password,confirmPassword)){
                        if(isValidDate(dateOfBirth)) {
                            signUpViewModel.registerUser();
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Enter All Fields");
            alert.showAndWait();
        }

    }

    //checks if all fields have values
    private static boolean hasAllFields(String email, String password, String confirmPassword, String firstName, String lastName, String dateOfBirth) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Enter All Fields");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    //checks if email is proper format
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

    //checks if date is valid
    private static boolean isValidDate(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(dateOfBirth, formatter);
            return true;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Date");
            alert.showAndWait();
            return false;
        }
    }

    //checks if password and confirmPassword match
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
