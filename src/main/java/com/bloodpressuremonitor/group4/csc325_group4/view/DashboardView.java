package com.bloodpressuremonitor.group4.csc325_group4.view;

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
