package com.example.taskmanagement;

import com.structure.StaticContainer;
import com.structure.Task;
import com.structure.Worker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class taskController{
    private Worker worker;
    private Task task;
    @FXML
    Label taskLabel;
    //dsadasdhjg

    @FXML
    private Button endTaskButton;

    public void setWorkerAndTask(Worker worker, Task task) {
        this.worker = worker;
        this.task = task;
    }

    public void setName(String name) {
        taskLabel.setText(name);
    }

    public void endTaskAction(ActionEvent event) throws IOException{
        worker.endTask(task);
    }

    public void switchToWorkerScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view_m.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Okno pracownika");
        stage.show();
    }
}
