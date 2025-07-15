package com.bloodpressuremonitor.group4.csc325_group4.model;

import com.bloodpressuremonitor.group4.csc325_group4.view.App;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.scene.control.Alert;

public class SignUpModel {

    /*public boolean registerUser(String email, String password) {
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

    }*/
    public void registerUser(String email, String pass, String confirm) {
        String API_KEY = loadApiKey();
        if (!pass.equals(confirm)) {
                showAlert("Password Mismatch", "Passwords do not match.");
                return;
            }
            try {
                URL url = new URL(
                  "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" 
                  + API_KEY
                );
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String jsonInput = String.format(
                  "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                  email, pass
                );
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                InputStream in = 
                  conn.getResponseCode() == 200 
                    ? conn.getInputStream() 
                    : conn.getErrorStream();
                String response = new BufferedReader(new InputStreamReader(in))
                    .lines().reduce("", String::concat);
                JSONObject json = new JSONObject(response);

                if (conn.getResponseCode() == 200) {
                    showAlert("Success", "Please login.");
                    goToLogin();
                } else {
                    String errorMsg = json
                      .getJSONObject("error")
                      .getString("message");
                    showAlert("Sign-Up Failed", errorMsg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "An unexpected error occurred.");
            }
        }
    
    private String loadApiKey() {
        try (InputStream in = getClass().getResourceAsStream("/files/FirebaseAPI.json")) {
            if (in == null) throw new RuntimeException("Cannot find FirebaseAPI.json");
            String json = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                            .lines().reduce("", (a,b)->a+b);
            return new JSONObject(json).getString("apiKey");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load API key", e);
        }
    }
}
