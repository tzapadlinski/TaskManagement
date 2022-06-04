package com.example.taskmanagement;
import com.structure.Employee;

import com.structure.Position;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginTest extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginBox.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.getIcons().add(new Image("")); TODO weź ktoś dodaj ten obrazek bo mnie pojebie zaraz z tą ścieżką
        stage.setTitle("Log in to our service!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}