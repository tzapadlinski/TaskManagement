package com.example.taskmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Button button;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    public void log(ActionEvent e){
        String user = new String();
        String pass = new String();
        password.clear();
    }
}
