package com.lutadam.healthsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Button btnLogout;
    @FXML
    private Label lblWelcome;

    public void initialize() {
        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "mainview.fxml", "Log In", null);
            }
        });
    }

    public void setUserInformation(String username) {
        lblWelcome.setText("Welcome " + username + "!");
    }
}
