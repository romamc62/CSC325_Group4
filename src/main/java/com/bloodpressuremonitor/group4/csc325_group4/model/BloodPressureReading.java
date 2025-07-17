package com.bloodpressuremonitor.group4.csc325_group4.model;

import java.time.LocalDate;
import java.util.Date;

public class BloodPressureReading {

    private int systolic;
    private int diastolic;
    private LocalDate readingTimestamp;

    public BloodPressureReading(int systolic, int diastolic) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.readingTimestamp = LocalDate.now();
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

    public LocalDate getReadingTimestamp() {
        return readingTimestamp;
    }

    public void setReadingTimestamp(LocalDate readingTimestamp) {
        this.readingTimestamp = readingTimestamp;
    }
}
