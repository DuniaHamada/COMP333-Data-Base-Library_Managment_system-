package com.example.librarymanagmentsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField signUpPasswordField;
    @FXML
    public Button loginButton;
    @FXML
    private TextField signUpUsernameTextField;

    @FXML
    private Button signUpButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"logIn.fxml");
            }

         });

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.signUpUser(actionEvent,signUpUsernameTextField.getText(),emailTextField.getText(),signUpPasswordField.getText());

            }
        });
    }



}
