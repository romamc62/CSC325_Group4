package com.bloodpressuremonitor.group4.csc325_group4.view;

import com.bloodpressuremonitor.group4.csc325_group4.session.Session;
import com.bloodpressuremonitor.group4.csc325_group4.session.SessionManager;
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

    private Session session;

    private boolean isMenuOpen = false;


    public void initialize() {
        //gets session from SessionManager
        session = SessionManager.getSession();
        System.out.println(session.getUser().toString());
    }

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
        //closes session
        SessionManager.closeSession();
        session = null;
        App.setRoot("/files/LoginView.fxml");
    }

}
