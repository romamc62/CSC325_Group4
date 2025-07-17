package com.bloodpressuremonitor.group4.csc325_group4.model;

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
