package com.bloodpressuremonitor.group4.csc325_group4.model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;

/**
 * TODO : Generate and share a Firestore Key, add to .gitignore
 *
 */
public class FirestoreContext {

    public Firestore firebase() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("/files/key.json")))
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase is initialized");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return FirestoreClient.getFirestore();
    }


}

