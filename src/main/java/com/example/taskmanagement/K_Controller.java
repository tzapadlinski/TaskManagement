package com.example.taskmanagement;

import com.structure.Project;
import com.structure.StaticContainer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class K_Controller {
    @FXML
    private Button logoutButton;
    private Pane logoutPane;
    @FXML
    private ListView unitView;
    @FXML
    private ListView employeeView;
    @FXML
    private Button actionButton;
    @FXML
    private Button setTaskButton;

    Stage stage;

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
    
    
    
    @FXML
    public void initialize() {
    	StaticContainer inicjalizacja = new StaticContainer();
        actionButton.setVisible(false);
        ObservableList<String> items = FXCollections.observableArrayList ();
        for(Project i : StaticContainer.projectList)
        {
        	items.add(i.getShortcut());
        	System.out.println(i);
        }
        unitView.setItems(items);
    }
    
    
    


}
