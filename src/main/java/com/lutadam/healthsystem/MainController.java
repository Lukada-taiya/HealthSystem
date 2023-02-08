package com.lutadam.healthsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    private Button btnLogin;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnSignUp;

    public void initialize() {
        btnLogin.setOnAction(event -> {
            String username = tfName.getText().trim();
            String password = tfPassword.getText().trim();
            DBUtils.logInUser(event, username,password);

        });

        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "signup.fxml", "Sign Up", null);
            }
        });


    }
}