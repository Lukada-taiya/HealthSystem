package com.lutadam.healthsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
        if(username != null) {
            try {
                root = fxmlLoader.load();
                DashboardController dashboardController = fxmlLoader.getController();
                dashboardController.setUserInformation(username);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    private static void initializeErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
    }


    public static void signUpUser(ActionEvent event, String username, String password, String retypePassword) {
        Connection connection = null;
        PreparedStatement psCheckIfUserExists = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        if(username.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
            initializeErrorAlert("All fields required");
        }else if(!password.equals(retypePassword)) {
            initializeErrorAlert("Please enter matching passwords");
        }else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_java", "root", "");
                psCheckIfUserExists = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
                psCheckIfUserExists.setString(1, username);
                resultSet = psCheckIfUserExists.executeQuery();
                if(resultSet.isBeforeFirst()) {
                    initializeErrorAlert("Please enter a valid username");
                }else {
                    String hashedPass = Encryptor.encrypt(password);
                    psInsert = connection.prepareStatement("INSERT INTO users(name,password) VALUES(?,?)");
                    psInsert.setString(1, username);
                    psInsert.setString(2, hashedPass);
                    psInsert.executeUpdate();
                    changeScene(event, "dashboard.fxml", "Dashboard", username);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(resultSet != null) {
                    try {
                        resultSet.close();
                    }catch(SQLException e) {
                        e.printStackTrace();
                    }

                }
                if(psCheckIfUserExists != null) {
                    try {
                        psCheckIfUserExists.close();
                    }catch(SQLException e) {
                        e.printStackTrace();
                    }

                }
                if(psInsert != null) {
                    try {
                        psInsert.close();
                    }catch(SQLException e) {
                        e.printStackTrace();
                    }

                }
                if(connection != null) {
                    try {
                        connection.close();
                    }catch(SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement psUserExists = null;
        ResultSet resultSet = null;

        if(username.isEmpty() || password.isEmpty()) {
            initializeErrorAlert("All fields are required");
        }else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_java", "root", "");
                psUserExists = connection.prepareStatement("SELECT password FROM users WHERE name = ?");
                psUserExists.setString(1, username);
                resultSet = psUserExists.executeQuery();
                if(!resultSet.isBeforeFirst()) {
                    initializeErrorAlert("Invalid name or password");
                }else {
                    String dbPass = null;
                    String hashedPass = Encryptor.encrypt(password);
                    while (resultSet.next()) {
                        dbPass = resultSet.getString("password");
                    }
                    if(!dbPass.equals(hashedPass)) {
                        initializeErrorAlert("Invalid name or password");
                    }else {
                        changeScene(event, "dashboard.fxml", "Dashboard", username);
                    }

                }
            }catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(resultSet != null) {
                    try {
                        resultSet.close();
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(psUserExists != null) {
                    try {
                        psUserExists.close();
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(connection != null) {
                    try {
                        connection.close();
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
