package com.example.librarymanagmentsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private Button LoginButton;

    @FXML
    private Button cancelButton;
    @FXML
    private TextField loginUsernameTextField;
    @FXML
    private PasswordField loginPasswordPasswordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        LoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    DBUtils.logInUser(actionEvent,loginUsernameTextField.getText(),loginPasswordPasswordField.getText());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void handleCancelButtonAction() {
        // Handle cancel button action
    }

    @FXML
    private void handleLoginButtonAction() {
        // Handle login button action
        String username = loginUsernameTextField.getText();
        String password = loginPasswordPasswordField.getText();

        // Perform login logic here
    }
}
