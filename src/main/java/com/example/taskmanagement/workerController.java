package com.example.taskmanagement;

import com.structure.StaticContainer;
import com.structure.Task;
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
import java.util.ResourceBundle;

public class workerController implements Initializable {
    @FXML
    private Button logoutButton;

    @FXML
    private Button enterTaskButton;

    @FXML
    private Pane logoutPane;

    @FXML
    private ListView<String> taskListView = new ListView<>();

    private String currentTaskString;
    private Task currentTask;


    private Stage stage;
    private Scene scene;
    private Parent root;
    //fasasfasf

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        StaticContainer inicjalizacja = new StaticContainer();
        //actionButton.setVisible(false);
        ObservableList<String> items = FXCollections.observableArrayList ();
        for(Task i : StaticContainer.WorkerList.get(0).getTasksList())
        {
            items.add(i.getShortcut());
            System.out.println(i);
        }
        taskListView.setItems(items);

        taskListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentTaskString = taskListView.getSelectionModel().getSelectedItem();

            }
        });
    }

    public void logout(ActionEvent event) {
        //dodanie nowego okna z potwierdzeniem
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wylogowywanie");
        alert.setHeaderText("Zostaniesz wylogowany!");
        alert.setContentText("Na pewno chcesz zostać wylogowany?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) logoutPane.getScene().getWindow();
            System.out.println("Wylogowano!");
            stage.close();
        }
    }

    /*
    @FXML
    public void initialize() {
        StaticContainer inicjalizacja = new StaticContainer();
        //actionButton.setVisible(false);
        ObservableList<String> items = FXCollections.observableArrayList ();
        for(Task i : StaticContainer.WorkerList.get(0).getTasksList())
        {
            items.add(i.getShortcut());
            System.out.println(i);
        }
        taskListView.setItems(items);
    }
     */




    public void switchToTaskScene(ActionEvent event) throws IOException {
        if(currentTaskString != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("task_scene.fxml"));
            root = loader.load();

            taskController taskController = loader.getController();
            taskController.setName(currentTaskString);
            taskController.setWorkerAndTask(StaticContainer.WorkerList.get(0), StaticContainer.WorkerList.get(0).getTasksList().get(0));

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Okno zadania");
            stage.show();
        }
    }

}
