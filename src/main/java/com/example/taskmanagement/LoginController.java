package com.example.taskmanagement;

import com.structure.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button button;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Label label;

    public void log(ActionEvent e) throws IOException {
        LoginTest main = new LoginTest();
        String login = userName.getText().toString();
        String pass = password.getText().toString();
        if (login.isEmpty() || pass.isEmpty()) {
            label.setText("Aby zalogować się, wpisz poprawne dane.");
            password.clear();
            return;
        }
        Account usersAccount = new Account(login, pass);
        Employee user = ServiceAccess.accountInBase(usersAccount);
        if (user == null){
            label.setText("Nieprawidłowe dane.");
            password.clear();
            return;
        }
        if (user.getClass().equals(Manager.class)){
            main.changeScene("hello-view_m.fxml");
        }
        else if (user.getClass().equals(Worker.class)){
            Position position = ((Worker) user).getPosition();
            switch(position){
                case TESTER:
                    main.changeScene("hello-view_k.fxml");
                    break;
                default:
                    main.changeScene("Manager_fx.fxml");
                    break;
            }
        }

    }
}
