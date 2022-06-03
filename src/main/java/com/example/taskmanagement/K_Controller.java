package com.example.taskmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class K_Controller {
    @FXML
    private Button logoutButton;
    private Pane logoutPane;
    private ListView unitView;
    private ListView employeeView;
    @FXML
    private Button actionButton;
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
        actionButton.setVisible(false);
    }
    
    
    


}
