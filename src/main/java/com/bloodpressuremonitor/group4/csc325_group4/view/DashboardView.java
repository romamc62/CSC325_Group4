/*package com.bloodpressuremonitor.group4.csc325_group4.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardView {

    @FXML
    private Button hamburgerMenuButton;
    @FXML
    private Button logoutButton;
    @FXML
    private VBox hamburgerMenuBox;

    private boolean isMenuOpen = false;



    @FXML
    private void handleHamburgerMenuButton(ActionEvent event){
        if(!isMenuOpen){
            hamburgerMenuBox.setTranslateX(0);
            isMenuOpen = true;
        } else{
            hamburgerMenuBox.setTranslateX(-100);
            isMenuOpen = false;
        }
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) throws IOException {
        App.setRoot("/files/LoginView.fxml");
    }

}
*/
package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.bloodpressuremonitor.group4.csc325_group4.model.BloodPressureReading;
import com.bloodpressuremonitor.group4.csc325_group4.session.Session;
import com.bloodpressuremonitor.group4.csc325_group4.session.SessionManager;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardView {

    @FXML private Button hamburgerMenuButton;
    @FXML private Button logoutButton;
    @FXML private VBox hamburgerMenuBox;

    @FXML private TableView<BloodPressureReading> tableView;
    @FXML private TableColumn<BloodPressureReading, Integer> systolicCol;
    @FXML private TableColumn<BloodPressureReading, Integer> diastolicCol;
    @FXML private TableColumn<BloodPressureReading, String> timestampCol;

    private boolean isMenuOpen = false;
    private Session session;

    @FXML
    public void initialize() {
        session = SessionManager.getSession();

        // Map columns to basic POJO getters
        systolicCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getSystolic()).asObject());
        diastolicCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getDiastolic()).asObject());
        timestampCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getReadingTimestamp()));

        loadBloodPressureHistory(session.getUser().getUid());
    }
    private void loadBloodPressureHistory(String userId) {
        try {
            DocumentReference docRef = App.fstore.collection("userData").document(userId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot doc = future.get();

            if (doc.exists()) {
                List<Map<String, Object>> bpHistory = (List<Map<String, Object>>) doc.get("bphistory"); // <- FIXED
                System.out.println("bpHistory size: " + (bpHistory != null ? bpHistory.size() : "null"));

                if (bpHistory != null) {
                    List<BloodPressureReading> readings = new ArrayList<>();
                    for (Map<String, Object> map : bpHistory) {
                        int systolic = ((Number) map.get("systolic")).intValue();
                        int diastolic = ((Number) map.get("diastolic")).intValue();
                        String timestamp = (String) map.get("readingTimestamp");
                        System.out.println("Loaded BP Reading: " + systolic + "/" + diastolic + " at " + timestamp);
                        readings.add(new BloodPressureReading(systolic, diastolic, timestamp));
                    }

                    tableView.setItems(FXCollections.observableArrayList(readings));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleHamburgerMenuButton(ActionEvent event) {
        if (!isMenuOpen) {
            hamburgerMenuBox.setTranslateX(0);
            isMenuOpen = true;
        } else {
            hamburgerMenuBox.setTranslateX(-100);
            isMenuOpen = false;
        }
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) throws IOException {
        App.setRoot("/files/LoginView.fxml");
    }
}
