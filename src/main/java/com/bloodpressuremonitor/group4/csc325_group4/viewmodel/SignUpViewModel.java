package com.bloodpressuremonitor.group4.csc325_group4.viewmodel;

import com.bloodpressuremonitor.group4.csc325_group4.model.SignUpModel;
import com.bloodpressuremonitor.group4.csc325_group4.view.App;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

public class SignUpViewModel {

    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password =  new SimpleStringProperty();
    private final SignUpModel signUpModel = new SignUpModel();
    //private String confirmPassword;

    public StringProperty emailProperty() { return email; }
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }

    public StringProperty passwordProperty() { return password; }
    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }

    public SignUpViewModel() {

    }

    public void registerUser(){
        String email = getEmail();
        String password = getPassword();
        signUpModel.registerUser(email,password);
    }
/*          --Method moved to SignUpModel--
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

 */

}
