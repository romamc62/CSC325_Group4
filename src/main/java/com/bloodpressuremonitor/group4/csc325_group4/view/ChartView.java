package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.bloodpressuremonitor.group4.csc325_group4.model.BloodPressureReading;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class ChartView {

    @FXML private LineChart<String, Number> bpLineChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Button backButton;

    public static List<BloodPressureReading> readings;

    @FXML
    public void initialize() {
        if (readings == null || readings.isEmpty()) {
            System.out.println("No readings available to plot.");
            return;
        }

        XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
        systolicSeries.setName("Systolic");

        XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
        diastolicSeries.setName("Diastolic");

        for (BloodPressureReading r : readings) {
            systolicSeries.getData().add(new XYChart.Data<>(r.getReadingTimestamp(), r.getSystolic()));
            diastolicSeries.getData().add(new XYChart.Data<>(r.getReadingTimestamp(), r.getDiastolic()));
        }

        bpLineChart.getData().addAll(systolicSeries, diastolicSeries);
    }

    @FXML
    private void handleBackButton() {
        try {
            App.setRoot("/files/DashboardView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
