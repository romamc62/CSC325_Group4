package com.bloodpressuremonitor.group4.csc325_group4.viewmodel;

import com.bloodpressuremonitor.group4.csc325_group4.view.App;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.scene.control.Alert;

public class SignUpViewModel {

    //private String email;
    //private String password;
    //private String confirmPassword;

    public SignUpViewModel() {
        //this.email = email;
        //this.password = password;
        //this.confirmPassword = confirmPassword;
    }

    public boolean registerUser(String email, String password) {
        UserRecord.CreateRequest request = new  UserRecord.CreateRequest()
                .setEmail(email)
                .setEmailVerified(false)
                .setPassword(password)
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully registered user: "  + userRecord.getUid());
            return true;
        } catch (FirebaseAuthException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
            return false;
        }


    }


}
