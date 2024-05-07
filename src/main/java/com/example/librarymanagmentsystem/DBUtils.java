package com.example.librarymanagmentsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile) {
                    Parent root = null;

                    try {
                        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                        root = loader.load();
                        //the main screen controller

                    } catch (IOException e) {
                        e.printStackTrace();

        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String Email, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckAdminExists = null;
        ResultSet resultSet = null;

        try {
            DBConn dbConn = new DBConn("127.0.0.1", "3306", "LibraryManagementSystem", "root", "1182002Amani");
            connection = dbConn.connectDB();

            psCheckAdminExists = connection.prepareStatement("select * from admin where username = ?");
            psCheckAdminExists.setString(1, username);
            resultSet = psCheckAdminExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("insert into admin (username, Email, password) values (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, Email);
                psInsert.setString(3, password);
                psInsert.executeUpdate();

                // Here we will put the main screen change action
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (psCheckAdminExists != null) psCheckAdminExists.close();
                if (psInsert != null) psInsert.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) throws ClassNotFoundException {




        try {
            Connection con = Connector.a.connectDB();

            PreparedStatement  preparedStatement = con.prepareStatement("SELECT password, username FROM admin WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet  resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        changeScene(event,"mainScreen.fxml");
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        }
    }
