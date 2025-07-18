package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.google.cloud.firestore.Firestore;


import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static Firestore fstore;
    public static FirebaseAuth fauth;
    public static Scene scene;
    private final com.bloodpressuremonitor.group4.csc325_group4.model.FirestoreContext contxtFirebase = new com.bloodpressuremonitor.group4.csc325_group4.model.FirestoreContext();



    @Override
    public void start(Stage primaryStage) throws IOException {
        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();
        scene = new Scene(loadFXML("/files/LoginView.fxml"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blood Pressure Monitoring Application");
        primaryStage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
