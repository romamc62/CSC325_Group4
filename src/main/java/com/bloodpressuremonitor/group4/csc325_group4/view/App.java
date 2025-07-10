package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.google.cloud.firestore.Firestore;


import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static Firestore fstore;
    public static FirebaseAuth fauth;
    public static Scene scene;
    private final com.bloodpressuremonitor.group4.csc325_group4.model.FirestoreContext contxtFirebase = new com.bloodpressuremonitor.group4.csc325_group4.model.FirestoreContext();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();

    }
}
