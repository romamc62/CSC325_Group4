package com.bloodpressuremonitor.group4.csc325_group4.model;

import com.bloodpressuremonitor.group4.csc325_group4.view.App;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.scene.control.Alert;

import java.io.IOException;

public class LoginModel {

    public boolean login(String email, String password) throws FirebaseAuthException, IOException {
        UserRecord user = App.fauth.getUserByEmail(email);
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email");
            alert.showAndWait();
            return false;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Simulated Login Successful");
            alert.showAndWait();
            System.out.println("Login Successful User ID: " + user.getUid());
            App.setRoot("/files/DashboardView.fxml");
            return true;
        }
    }
}
