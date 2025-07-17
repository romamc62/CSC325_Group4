package com.bloodpressuremonitor.group4.csc325_group4.viewmodel;

import com.bloodpressuremonitor.group4.csc325_group4.model.SignUpModel;
import com.bloodpressuremonitor.group4.csc325_group4.view.App;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public class SignUpViewModel {

    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password =  new SimpleStringProperty();
    private final StringProperty confirmPassword = new SimpleStringProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final StringProperty dateOfBirth = new SimpleStringProperty();
    private final SignUpModel signUpModel = new SignUpModel();


    public StringProperty emailProperty() { return email; }
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }

    public StringProperty passwordProperty() { return password; }
    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }

    public StringProperty confirmPasswordProperty() { return confirmPassword; }
    public String getConfirmPassword() { return confirmPassword.get(); }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword.set(confirmPassword); }

    public StringProperty firstNameProperty() { return firstName; }
    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String firstName) { this.firstName.set(firstName); }

    public StringProperty lastNameProperty() { return lastName; }
    public String getLastName() { return lastName.get(); }
    public void setLastName(String lastName) { this.lastName.set(lastName); }

    public StringProperty dateOfBirthProperty() { return dateOfBirth; }
    public String getDateOfBirth() { return dateOfBirth.get(); }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth.set(dateOfBirth); }

    public SignUpViewModel() {

    }

    public void registerUser(){
        String email = getEmail();
        String password = getPassword();
        String firstName = getFirstName();
        String lastName = getLastName();
        String dateOfBirth = getDateOfBirth();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate dob = LocalDate.parse(dateOfBirth, format);
        LocalDate currDate = LocalDate.now();

        Period age = Period.between(dob, currDate);

        signUpModel.registerUser(email, password, firstName, lastName, dob, age.getYears());
    }

}
