package com.example.taskmanagement;

import com.structure.*;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class taskController {

    @FXML
    private ListView dataListView;
    @FXML
    Label taskLabel;
    @FXML
    private Button endTaskButton;


    private Worker worker;
    private Task task;


    public void setWorkerAndTask(Worker worker, Task task) {
        this.worker = worker;
        this.task = task;

        this.setName(task.getShortcut());
        this.loadList();
    }

    public void loadList() {
        //StaticContainer inicjalizacja = new StaticContainer();
        ObservableList<String> items = FXCollections.observableArrayList ();

        LocalDate deadline = task.getDeadline();
        items.add("Deadline: "+ deadline.toString());
        Manager manager = task.getManager();
        items.add(manager.toString());
        String description = task.getDescription();
        items.add("Opis: "+description);
        StatusC.stat s = task.getS();
        items.add("Status: "+s.toString());
        int id = task.getID();
        items.add(String.valueOf("id: " +id));
        LocalDate startDate = task.getStartDate();
        items.add("Data poczatkowa: "+startDate.toString());
        String name = task.getName();
        items.add("Nazwa: "+name);
        int moduleId = task.getModuleID();
        items.add(String.valueOf("ID modulu: "+moduleId));
        long pozostaloCzasu = DAYS.between(LocalDate.now(), deadline);
        items.add(String.valueOf("Pozostało : "+pozostaloCzasu + " dni"));

        dataListView.setItems(items);
    }

    public void setName(String name) {
        taskLabel.setText(name);
    }

    public void setTask(Task task ) {
        this.task = task;
    }

    public void endTaskAction(ActionEvent event) throws IOException{
        worker.endTask(task);
        //odswiezanie
        this.setName(task.getShortcut());
        this.loadList();
    }

    public void inProgress(ActionEvent event) throws IOException{
        worker.taskInProgress(task);
        //odswiezanie
        this.setName(task.getShortcut());
        this.loadList();
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
