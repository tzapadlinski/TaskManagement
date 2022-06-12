package com.example.taskmanagement;

import com.structure.StaticContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LogViewerController implements Initializable {
    @FXML
    private Label titttle;

    @FXML
    private Label texttt;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titttle.setText(StaticContainer.activeLog.getFileName());
        texttt.setText(StaticContainer.activeLog.getContent());
    }
}
