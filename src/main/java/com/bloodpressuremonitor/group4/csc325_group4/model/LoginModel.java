package com.bloodpressuremonitor.group4.csc325_group4.model;

import com.bloodpressuremonitor.group4.csc325_group4.session.Session;
import com.bloodpressuremonitor.group4.csc325_group4.session.SessionManager;
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
            SessionManager.setSession(new Session(currUser));

            // TODO : load user from FireStore Database

            App.setRoot("/files/DashboardView.fxml");
            return true;
        }
    }

    public boolean pAuth(String email, String password) {
        try{
            InputStream jsonIn = getClass().getResourceAsStream("/files/FirebaseAPI.json");
            JsonObject config;
            String apiKey;
            try (InputStreamReader reader = new InputStreamReader(jsonIn, StandardCharsets.UTF_8)) {
                config = new Gson().fromJson(reader, JsonObject.class);
                apiKey = config.get("apiKey").getAsString();
            }

            String endpoint = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey;

            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String jsonInput = String.format("""
                    {
                     "email": "%s",
                     "password": "%s",
                     "returnSecureToken": true
                    }
                    """, email, password);

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if(conn.getResponseCode() == 200){
                currUser = App.fauth.getUserByEmail(email);
                return true;
            } else{
                System.out.println(conn.getResponseCode() + " " + conn.getResponseMessage());
            }

        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        } catch (FirebaseAuthException e) {
            System.out.println("Firebase Auth Error: " + e.getMessage());
        }
        return false;
    }
}
