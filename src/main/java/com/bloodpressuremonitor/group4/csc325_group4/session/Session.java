package com.bloodpressuremonitor.group4.csc325_group4.session;


import com.bloodpressuremonitor.group4.csc325_group4.model.User;
import com.bloodpressuremonitor.group4.csc325_group4.view.App;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.concurrent.ExecutionException;

public class Session {

    private String userID;
    private String email;
    private User user;
    private boolean isActiveSession = false;

    public Session(String userID, String email) {
        this.userID = userID;
        this.email = email;
        isActiveSession = true;
        this.user = loadUser(userID);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActiveSession() {
        return isActiveSession;
    }

    public void setActiveSession(boolean activeSession) {
        isActiveSession = activeSession;
    }

    public Session getSession(){
        return this;
    }

    //gets user class data from firestore database
    public User loadUser(String uid){
        DocumentReference ref = App.fstore.collection("userData").document(uid);
        ApiFuture<DocumentSnapshot> future = ref.get();

        try {
            DocumentSnapshot document = future.get();
            if(document.exists()){
                return (User) document.toObject(User.class);
            } else {
                System.out.println("User not found");
                return null;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
