module com.bloodpressuremonitor.group4.csc325_group4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires jdk.jsobject;
    requires java.logging;
    requires java.xml;

    requires google.cloud.firestore;
    requires com.google.common;
    requires com.google.auth.oauth2;
    requires com.google.api.apicommon;
    requires firebase.admin;
    requires google.cloud.core;
    requires com.google.auth;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;

    opens com.bloodpressuremonitor.group4.csc325_group4.model; // to javafx.fxml;
    exports  com.bloodpressuremonitor.group4.csc325_group4.model;
    opens com.bloodpressuremonitor.group4.csc325_group4.view;
    exports com.bloodpressuremonitor.group4.csc325_group4.view;
    opens com.bloodpressuremonitor.group4.csc325_group4.viewmodel;
    exports com.bloodpressuremonitor.group4.csc325_group4.viewmodel;

}