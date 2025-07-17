package com.bloodpressuremonitor.group4.csc325_group4.model;

import com.bloodpressuremonitor.group4.csc325_group4.view.App;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class SignUpModel {

    private User newUser;

    public boolean registerUser(String email, String password, String firstName, String lastName, LocalDate dateOfBirth, int age) {
        UserRecord.CreateRequest request = new  UserRecord.CreateRequest()
                .setEmail(email)
                .setEmailVerified(false)
                .setPassword(password)
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            newUser = new User(firstName, lastName, email, age, dateOfBirth, userRecord.getUid());
            System.out.println("Successfully registered user: \n"  + newUser.toString());

            //TODO : initialize new user in FireStore database

            App.fstore.collection("userData").document(userRecord.getUid()).set(newUser);

            App.setRoot("/files/loginView.fxml");
            return true;
        } catch (FirebaseAuthException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
