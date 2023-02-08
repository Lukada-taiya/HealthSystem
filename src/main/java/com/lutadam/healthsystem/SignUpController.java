package com.lutadam.healthsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfRetypePassword;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnLogIn;

    public void initialize() {
        btnLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "mainview.fxml", "Log In", null);
            }
        });

        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = tfName.getText().trim();
                String password = tfPassword.getText().trim();
                String retypePassword = tfRetypePassword.getText().trim();
                DBUtils.signUpUser(event, name, password, retypePassword);
            }
        });
    }
}
