package com.bloodpressuremonitor.group4.csc325_group4.model;

import java.util.Date;

public class BloodPressureReading {

    private int systolic;
    private int diastolic;
    private Date readingTimestamp;

    public BloodPressureReading(int systolic, int diastolic) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.readingTimestamp = new Date();
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

    public Date getReadingTimestamp() {
        return readingTimestamp;
    }

    public void setReadingTimestamp(Date readingTimestamp) {
        this.readingTimestamp = readingTimestamp;
    }
}
