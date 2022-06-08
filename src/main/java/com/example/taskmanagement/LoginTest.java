package com.example.taskmanagement;
import com.structure.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginTest extends Application {

    private Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("loginBox.fxml"));
        stage.getIcons().add(new Image("file:src\\main\\resources" +
                "\\com\\example\\taskmanagement\\images\\logo.png")); //!here's path to images!
        stage.setTitle("Jira 2.0");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void main(String[] args) {
        StaticContainer inicjalizacja = new StaticContainer();
        launch();
    }
}