package com.example.taskmanagement;

import com.structure.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private Button button;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Label label;

    public void log(ActionEvent e) throws IOException, SQLException {
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
            changeScene("hello-view_k.fxml");
        }
        else if (user.getClass().equals(Worker.class)){
            Position position = ((Worker) user).getPosition();
            switch(position){
                case TESTER:
                    changeScene("hello-view_m.fxml");
                    break;
                default:
                    changeScene("hello-view_m.fxml");
                    break;
            }
        }

    }

    public void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage window = (Stage) button.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
