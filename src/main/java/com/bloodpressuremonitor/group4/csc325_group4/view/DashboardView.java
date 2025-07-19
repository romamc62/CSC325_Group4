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
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashboardView {

    @FXML private Button hamburgerMenuButton;
    @FXML private Button logoutButton;
    @FXML private Button deleteReadingButton;
    @FXML private VBox hamburgerMenuBox;

    @FXML private TableView<BloodPressureReading> tableView;
    @FXML private TableColumn<BloodPressureReading, Integer> systolicCol;
    @FXML private TableColumn<BloodPressureReading, Integer> diastolicCol;
    @FXML private TableColumn<BloodPressureReading, String> timestampCol;

    @FXML private Label loginPopup;
    @FXML private Label bpAlertLabel;


    @FXML private TextField systolicField;
    @FXML private TextField diastolicField;

    private boolean isMenuOpen = false;
    private Session session;

    @FXML
    public void initialize() {
        session = SessionManager.getSession();
        showLoginPopup(); // show reminder after login
        scheduleDailyReminder(); // schedule reminder

        VBox.setVgrow(tableView, Priority.ALWAYS);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        systolicCol.setCellValueFactory(data -> data.getValue().systolicProperty().asObject());
        diastolicCol.setCellValueFactory(data -> data.getValue().diastolicProperty().asObject());
        timestampCol.setCellValueFactory(data -> data.getValue().timestampProperty());

        // Highlight high systolic
        systolicCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(value.toString());
                    setStyle(value > 140 ? "-fx-background-color: #ffcccc;" : "");
                }
            }
        });

        // Highlight low diastolic
        diastolicCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(value.toString());
                    setStyle(value < 60 ? "-fx-background-color: #ffcccc;" : "");
                }
            }
        });

        loadBloodPressureHistory(session.getUser().getUid());
    }

    private void loadBloodPressureHistory(String userId) {
        try {
            DocumentReference docRef = App.fstore.collection("userData").document(userId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot doc = future.get();

            if (doc.exists()) {
                List<Map<String, Object>> bpHistory = (List<Map<String, Object>>) doc.get("bphistory");
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

    private void showLoginPopup() {
        loginPopup.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.seconds(6), loginPopup);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);
        fade.setOnFinished(e -> loginPopup.setVisible(false));
        fade.play();
    }

    @FXML
    private void handleAddReading() {
        try {
            int systolic = Integer.parseInt(systolicField.getText());
            int diastolic = Integer.parseInt(diastolicField.getText());

            checkBPAlert(systolic, diastolic);
            if (systolic >= 140 || diastolic >= 90 || systolic <= 90 || diastolic <= 60) {
                showBPAlert("High BP reading – consult your physician.");
            }

            // conditional block to show alert
            if (systolic >= 140 || diastolic >= 90) {
                bpAlertLabel.setText("High BP reading – consult your physician.");
                bpAlertLabel.setVisible(true);
            } else if (systolic <= 90 || diastolic <= 60) {
                bpAlertLabel.setText("Low BP reading – consult your physician.");
                bpAlertLabel.setVisible(true);
            } else {
                bpAlertLabel.setVisible(false);
            }

            if (systolic < 70 || systolic > 250 || diastolic < 40 || diastolic > 150) {
                showAlert("Invalid Input", "Please enter realistic blood pressure values:\n• Systolic: 70–250\n• Diastolic: 40–150");
                return;
            }

            String timestamp = BloodPressureReading.dateToString();
            BloodPressureReading reading = new BloodPressureReading(systolic, diastolic, timestamp);

            // Add to table view
            tableView.getItems().add(0, reading);
            tableView.scrollTo(reading);
            systolicField.clear();
            diastolicField.clear();

            // SAVE TO FIREBASE
            saveReadingToFirestore(reading);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid **numbers only** for both systolic and diastolic values.");
        }
    }


    @FXML
    private void handleDeleteReading() {
        BloodPressureReading selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tableView.getItems().remove(selected);
            updateFirestoreAfterDelete();
        } else {
            System.out.println("No row selected");
        }
    }

    // Sync to firestore
    private void updateFirestoreAfterDelete() {
        try {
            List<BloodPressureReading> updatedList = new ArrayList<>(tableView.getItems());
            List<Map<String, Object>> mappedList = new ArrayList<>();
            for (BloodPressureReading reading : updatedList) {
                Map<String, Object> entry = Map.of(
                        "systolic", reading.getSystolic(),
                        "diastolic", reading.getDiastolic(),
                        "readingTimestamp", reading.getReadingTimestamp()
                );
                mappedList.add(entry);
            }

            DocumentReference docRef = App.fstore.collection("userData").document(session.getUser().getUid());
            docRef.update("bphistory", mappedList);
            System.out.println("Firestore updated after deletion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkBPAlert(int systolic, int diastolic) {
        if (systolic >= 140 || diastolic >= 90) {
            bpAlertLabel.setText("High BP reading on " + getCurrentDate() + " – consult your physician.");
            bpAlertLabel.setVisible(true);
        } else {
            bpAlertLabel.setVisible(false);
        }
    }

    private void showBPAlert(String message) {
        bpAlertLabel.setText(message);
        bpAlertLabel.setVisible(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(event -> bpAlertLabel.setVisible(false));
        delay.play();
    }


    private String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d"));
    }


    @FXML
    private void handleReminderButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reminders");
        alert.setHeaderText("Set Daily Reminder");
        alert.setContentText("This will remind you each day to log your blood pressure.");
        alert.showAndWait();
    }

    private void scheduleDailyReminder() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Reminder");
                    alert.setHeaderText("Don't forget!");
                    alert.setContentText("Please take and log your blood pressure today.");
                    alert.showAndWait();
                });
            }
        }, 100_000); // Show after 100 seconds
    }



    @FXML
    private void handleExportData() {
        System.out.println("Readings to export: " + tableView.getItems().size());  // DEBUG

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Blood Pressure History");
        fileChooser.setInitialFileName("bp_readings.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("Systolic,Diastolic,Timestamp");
                for (BloodPressureReading reading : tableView.getItems()) {
                    writer.printf("%d,%d,%s%n",
                            reading.getSystolic(),
                            reading.getDiastolic(),
                            reading.getReadingTimestamp()
                    );
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText(null);
                alert.setContentText("Data exported to CSV successfully!");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Failed");
                alert.setHeaderText("Error exporting data");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void handleChartsButton() {
        try {
            ChartView.readings = new ArrayList<>(tableView.getItems());
            App.setRoot("/files/ChartView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // alerts:
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    private void saveReadingToFirestore(BloodPressureReading reading) {
        try {
            String userId = session.getUser().getUid();
            DocumentReference docRef = App.fstore.collection("userData").document(userId);

            Map<String, Object> newEntry = Map.of(
                    "systolic", reading.getSystolic(),
                    "diastolic", reading.getDiastolic(),
                    "readingTimestamp", reading.getReadingTimestamp()
            );

            // Append new reading to array field "bphistory"
            docRef.update("bphistory", com.google.cloud.firestore.FieldValue.arrayUnion(newEntry))
                    .addListener(() -> System.out.println("Reading saved."), Runnable::run);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Save Failed", "There was an error saving the reading to the cloud.");
        }
    }

}
