package com.bloodpressuremonitor.group4.csc325_group4.model;

import java.time.LocalDateTime;

public class BloodPressureReading {

    private int systolic;
    private int diastolic;
    private LocalDateTime timeStamp;

    public BloodPressureReading(int systolic, int diastolic) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.timeStamp = LocalDateTime.now();
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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
