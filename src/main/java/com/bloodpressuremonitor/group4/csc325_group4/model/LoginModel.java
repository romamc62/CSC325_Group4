package com.bloodpressuremonitor.group4.csc325_group4.model;

import com.bloodpressuremonitor.group4.csc325_group4.view.App;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class LoginModel {

    public UserRecord currUser;

    public boolean login(String email, String password) throws FirebaseAuthException, IOException {

        if (!pAuth(email, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Email or Password");
            alert.showAndWait();
            return false;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Login Successful");
            alert.showAndWait();
            System.out.println("Login Successful User ID: " + currUser.getUid());

            // TODO : load user from FireStore Database

            App.setRoot("/files/DashboardView.fxml");
            return true;
        }
    }

    public boolean pAuth(String email, String password) {

        try {
            // Load API key from FirebaseAPI.json
            InputStream jsonIn = getClass().getResourceAsStream("/files/FirebaseAPI.json");
            if (jsonIn == null) {
                System.out.println("!! FirebaseAPI.json not found in /files/");
                return false;
            }

            JsonObject config;
            String apiKey;
            try (InputStreamReader reader = new InputStreamReader(jsonIn, StandardCharsets.UTF_8)) {
                config = new Gson().fromJson(reader, JsonObject.class);
                apiKey = config.get("apiKey").getAsString();
            }

            // Send request to Firebase Auth REST API
            String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey;
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // Send JSON payload
            String jsonInput = String.format("""
            {
             "email": "%s",
             "password": "%s",
             "returnSecureToken": true
            }
            """, email, password);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Debug HTTP response code
            int responseCode = conn.getResponseCode();
            System.out.println("Firebase response code: " + responseCode);

            if (responseCode == 200) {
                try {
                    currUser = App.fauth.getUserByEmail(email);
                    System.out.println("Firebase login successful for: " + email);
                    return true;
                } catch (FirebaseAuthException e) {
                    System.out.println("getUserByEmail failed: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            } else {
                // Print error response from Firebase
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String error = br.lines().collect(Collectors.joining("\n"));
                System.out.println("Login failed. HTTP " + responseCode);
                System.out.println("Firebase API Error Response: " + error);
            }

        } catch (Exception e) {
            System.out.println("Exception during login: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

}
