package com.bloodpressuremonitor.group4.csc325_group4.model;

import com.bloodpressuremonitor.group4.csc325_group4.model.BloodPressureReading;
import java.util.List;

public class HealthInsightsService {

    public static double getAverageSystolic(List<BloodPressureReading> readings) {
        return readings.stream().mapToInt(BloodPressureReading::getSystolic).average().orElse(0);
    }

    public static double getAverageDiastolic(List<BloodPressureReading> readings) {
        return readings.stream().mapToInt(BloodPressureReading::getDiastolic).average().orElse(0);
    }

    public static boolean hasConsistentHypertension(List<BloodPressureReading> readings) {
        return readings.stream()
                .filter(r -> r.getSystolic() >= 140 || r.getDiastolic() >= 90)
                .count() >= 3;  // 3 high readings = concern
    }

    public static String generateHealthReport(List<BloodPressureReading> readings) {
        StringBuilder sb = new StringBuilder();
        sb.append("BP Report Summary:\n");
        sb.append("Avg Systolic: ").append(getAverageSystolic(readings)).append("\n");
        sb.append("Avg Diastolic: ").append(getAverageDiastolic(readings)).append("\n");
        if (hasConsistentHypertension(readings)) {
            sb.append("⚠ Warning: Repeated high blood pressure detected.\n");
        } else {
            sb.append("Good! No consistent signs of hypertension.\n");
        }
        return sb.toString();
    }
}

// String report = HealthInsightsService.generateHealthReport(readings);
