package com.example.taskmanagement;

import com.structure.Project;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class workerController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Button enterTaskButton;
    @FXML
    private Pane logoutPane;
    @FXML
    private ListView taskListView;
    @FXML
    private Label workerNameLabel;

    private Task currentTask;
    private Worker currentWorker;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //StaticContainer inicjalizacja = new StaticContainer();

        setCurrentWorker(StaticContainer.workerList.get(0));

        ObservableList<Task> items = FXCollections.observableArrayList ();
        for(Task i : currentWorker.getTasksList())
        {
            items.add(i);
        }
        taskListView.setItems(items);
    }

    public void logout(ActionEvent event) throws IOException {
        //dodanie nowego okna z potwierdzeniem
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wylogowywanie");
        alert.setHeaderText("Zostaniesz wylogowany!");
        alert.setContentText("Na pewno chcesz zostać wylogowany?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) logoutPane.getScene().getWindow();
            System.out.println("Wylogowano!");
            stage.close();

            switchToLogout(event);
        }
    }

    public void switchToLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loginBox.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Okno logowania");
        stage.show();
    }


    public void switchToTaskScene(ActionEvent event) throws IOException {
        try {
            currentTask = (Task) taskListView.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("task_scene.fxml"));
            root = loader.load();

            taskController taskController = loader.getController();
            taskController.setWorkerAndTask(currentWorker, currentTask);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Okno zadania");
            stage.show();
        } catch (Exception e) {
            return;
        }
    }

    public void setCurrentWorker(Worker worker) {
        this.currentWorker = worker;
        this.workerNameLabel.setText(worker.getName());
    }

    public void updateList() {
        //StaticContainer inicjalizacja = new StaticContainer();
        ObservableList<Task> items = FXCollections.observableArrayList ();
        for(Task i : currentWorker.getTasksList())
        {
            items.add(i);
        }
        taskListView.setItems(items);
    }

}
