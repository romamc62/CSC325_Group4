package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.google.cloud.firestore.Firestore;


import com.google.firebase.auth.FirebaseAuth;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        Parent newRoot = loadFXML(fxml);

        // Optional: fade out current root
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), scene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Set new root after fade-out
        fadeOut.setOnFinished(e -> {
            scene.setRoot(newRoot);

            // Fade in new scene
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }


    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
