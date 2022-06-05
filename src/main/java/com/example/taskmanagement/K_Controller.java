package com.example.taskmanagement;

import java.util.List;

import com.structure.Project;
import com.structure.StaticContainer;
import com.structure.Module;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    
    private enum activeView {project, module, task};
    private activeView activ;

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
        activ = activeView.project;
        ObservableList<Project> items = FXCollections.observableArrayList ();
        for(Project i : StaticContainer.projectList)
        {
        	items.add(i);
        	//System.out.println(i);
        }
        unitView.setItems(items);
        
        unitView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	
            	if(activ == activeView.project)
            	{
            		if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            			System.out.println("clicked on " + unitView.getSelectionModel().getSelectedItem());
            			
            			 ObservableList<Module> itemsM = FXCollections.observableArrayList ();
            			 Project p = (Project) unitView.getSelectionModel().getSelectedItem();
            			 List<Module> mList = p.getModuleSet();
            		     for(Module i : mList)
            		     {
            		      	itemsM.add(i);
            		       	//System.out.println(i);
            		     }
            		     unitView.setItems(itemsM);
            		     activ = activeView.module;
            		}
            	}
                
            }
        });
    }
    
    public void goBack(ActionEvent event)
    {
    	if(activ == activeView.module)
    	{
    		ObservableList<Project> itemsP = FXCollections.observableArrayList ();
            for(Project i : StaticContainer.projectList)
            {
            	itemsP.add(i);
            	//System.out.println(i);
            }
            unitView.setItems(itemsP);
            activ = activeView.project;
    	}
    }
    
    
    


}
