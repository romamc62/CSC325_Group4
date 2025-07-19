/*package com.bloodpressuremonitor.group4.csc325_group4.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BloodPressureReading {

    private int systolic;
    private int diastolic;
    private String readingTimestamp;

    public BloodPressureReading(int systolic, int diastolic) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.readingTimestamp = dateToString();
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public String getReadingTimestamp() {
        return readingTimestamp;
    }

    public void setReadingTimestamp(String readingTimestamp) {
        this.readingTimestamp = readingTimestamp;
    }

    //gets current date and time, formats as string and returns string
    public static String dateToString(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String bpReading = now.format(formatter);
        System.out.println("Adding BP Reading: " + bpReading);
        return bpReading;
    }
}
*/

package com.bloodpressuremonitor.group4.csc325_group4.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BloodPressureReading {

    private final IntegerProperty systolic = new SimpleIntegerProperty();
    private final IntegerProperty diastolic = new SimpleIntegerProperty();
    private final StringProperty readingTimestamp = new SimpleStringProperty();

    public BloodPressureReading(int systolic, int diastolic) {
        this.systolic.set(systolic);
        this.diastolic.set(diastolic);
        this.readingTimestamp.set(dateToString());
    }

    public BloodPressureReading(int systolic, int diastolic, String timestamp) {
        this.systolic.set(systolic);
        this.diastolic.set(diastolic);
        this.readingTimestamp.set(timestamp);
    }

    // Standard Getters/Setters
    public int getSystolic() { return systolic.get(); }
    public void setSystolic(int val) { systolic.set(val); }

    public int getDiastolic() { return diastolic.get(); }
    public void setDiastolic(int val) { diastolic.set(val); }

    public String getReadingTimestamp() { return readingTimestamp.get(); }
    public void setReadingTimestamp(String val) { readingTimestamp.set(val); }

    // Property Getters (used by TableView)
    public IntegerProperty systolicProperty() { return systolic; }
    public IntegerProperty diastolicProperty() { return diastolic; }
    public StringProperty timestampProperty() { return readingTimestamp; }

    public static String dateToString() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        return now.format(formatter);
    }

}
