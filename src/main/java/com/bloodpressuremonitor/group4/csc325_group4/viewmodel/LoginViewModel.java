package com.bloodpressuremonitor.group4.csc325_group4.viewmodel;

import com.bloodpressuremonitor.group4.csc325_group4.model.LoginModel;
import com.google.firebase.auth.FirebaseAuthException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

public class LoginViewModel {

    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password =  new SimpleStringProperty();
    private final LoginModel loginModel = new LoginModel();

    public StringProperty emailProperty() { return email; }
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }

    public StringProperty passwordProperty() { return password; }
    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }

    public void login() throws IOException, FirebaseAuthException {
        String email = getEmail();
        String password = getPassword();
        loginModel.login(email, password);
    }
}
