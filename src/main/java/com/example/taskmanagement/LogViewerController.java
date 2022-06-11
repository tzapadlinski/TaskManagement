package com.example.taskmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LogViewerController {
    @FXML
    private Label tittle;

    @FXML
    private Label text;

    public void display(String title_, String text_){
        tittle.setText(title_);
        text.setText(text_);
    }
}
