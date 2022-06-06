package com.example.taskmanagement;

import java.time.LocalDate;
import java.util.List;

import com.structure.Project;
import com.structure.StaticContainer;
import com.structure.StatusC;
import com.structure.Task;
import com.structure.Tester;
import com.structure.Unit;
import com.structure.Worker;
import com.structure.Manager;
import com.structure.Module;
import com.structure.Position;

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
    @FXML
    private Pane logoutPane;
    @FXML
    private ListView unitView;
    @FXML
    private ListView employeeView;
    @FXML
    private Button actionButton;
    @FXML
    private Button setTaskButton;
    @FXML
    private Button setTaskStatusButton;
    @FXML
    private Button syncButton;
    
    private enum activeView {project, module, task,taskFocused};
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
        setTaskStatusButton.setVisible(false);
        syncButton.setVisible(false);
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
            		     StaticContainer.setModuleList(mList);
            		     setTaskStatusButton.setVisible(false);
            		     ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
            		     employeeView.setItems(itemsS);
            		}else if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1)
            		{
            			System.out.println("selected " + unitView.getSelectionModel().getSelectedItem());
            			
            			Project p = (Project) unitView.getSelectionModel().getSelectedItem();
            			StatusC.stat s = p.getS();
            			if((s==StatusC.stat.anulowane)||(s==StatusC.stat.ukończone))
            				return;
            			
            			ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
           			 	itemsS.add(StatusC.stat.anulowane);
           			 	itemsS.add(StatusC.stat.ukończone);
           			 	
           			 	employeeView.setItems(itemsS);
           			 	setTaskStatusButton.setVisible(true);
            		}
            	}else if(activ == activeView.module)
            	{
            		if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            			System.out.println("clicked on " + unitView.getSelectionModel().getSelectedItem());
            			
            			 ObservableList<Task> itemsT = FXCollections.observableArrayList ();
            			 Module m = (Module) unitView.getSelectionModel().getSelectedItem();
            			 List<Task> tList = m.getTasksSet();
            		     for(Task i : tList)
            		     {
            		      	itemsT.add(i);
            		       	//System.out.println(i);
            		     }
            		     unitView.setItems(itemsT);
            		     activ = activeView.task;
            		     StaticContainer.setTaskList(tList);
            		     setTaskStatusButton.setVisible(false);
            		     ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
            		     employeeView.setItems(itemsS);
            		}else if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1)
            		{
            			System.out.println("selected " + unitView.getSelectionModel().getSelectedItem());
            			
            			Module m = (Module) unitView.getSelectionModel().getSelectedItem();
            			StatusC.stat s = m.getS();
            			if((s==StatusC.stat.anulowane)||(s==StatusC.stat.ukończone))
            				return;
            			
            			ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
           			 	itemsS.add(StatusC.stat.anulowane);
           			 	itemsS.add(StatusC.stat.ukończone);
           			 	
           			 	employeeView.setItems(itemsS);
           			 	setTaskStatusButton.setVisible(true);
            		}
            	}else if(activ == activeView.task)
            	{
            		if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            			System.out.println("clicked on " + unitView.getSelectionModel().getSelectedItem());
            			
            			 ObservableList<String> itemsS = FXCollections.observableArrayList ();
            			 Task t = (Task) unitView.getSelectionModel().getSelectedItem();
            			 
            			 LocalDate deadline = t.getDeadline();
            			 itemsS.add("Deadline: "+ deadline.toString());
            			 Manager manager = t.getManager();
            			 itemsS.add(manager.toString());
            			 String description = t.getDescription();
            			 itemsS.add("Opis: "+description);
            			 StatusC.stat s = t.getS();
            			 itemsS.add("Status: "+s.toString());
            			 int id = t.getID();
            			 itemsS.add(String.valueOf("id: " +id));
            			 LocalDate startDate = t.getStartDate();
            			 itemsS.add("Data poczatkowa: "+startDate.toString());
            			 String name = t.getName();
            			 itemsS.add("Nazwa: "+name);
            			 int moduleId = t.getModuleID();
            			 itemsS.add(String.valueOf("ID modulu: "+moduleId));
            		     
            		     unitView.setItems(itemsS);
            		     activ = activeView.taskFocused;
            		     setEmployee(s);
            		     
            		     setTaskStatusButton.setVisible(false);
            		     
            		     
            		}else if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1)
            		{
            			System.out.println("selected " + unitView.getSelectionModel().getSelectedItem());
            			
            			Task t = (Task) unitView.getSelectionModel().getSelectedItem();
            			StatusC.stat s = t.getS();
            			if((s==StatusC.stat.anulowane)||(s==StatusC.stat.ukończone))
            				return;
            			
            			ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
           			 	itemsS.add(StatusC.stat.anulowane);
           			 	itemsS.add(StatusC.stat.ukończone);
           			 	
           			 	employeeView.setItems(itemsS);
           			 	setTaskStatusButton.setVisible(true);
            		}
            	}
                
            }
        });
    }
    
    public void goBack(ActionEvent event)
    {
    	setTaskStatusButton.setVisible(false);
	     ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
	     employeeView.setItems(itemsS);
	     
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
    	}else if(activ == activeView.task)
    	{
    		ObservableList<Module> itemsM = FXCollections.observableArrayList ();
            for(Module i : StaticContainer.moduleList)
            {
            	itemsM.add(i);
            	//System.out.println(i);
            }
            unitView.setItems(itemsM);
            activ = activeView.module;
    	}
    	else if(activ == activeView.taskFocused)
    	{
    		ObservableList<Task> itemsT = FXCollections.observableArrayList ();
            for(Task i : StaticContainer.taskList)
            {
            	itemsT.add(i);
            	//System.out.println(i);
            }
            unitView.setItems(itemsT);
            activ = activeView.task;
            actionButton.setVisible(false);
    	}
    }
    
    private void setEmployee(StatusC.stat status)
    {
    	String buttonText = "";
    	ObservableList<String> items = FXCollections.observableArrayList ();
    	switch (status)
    	{
    		
    		case nowy:
    	        for(Worker i : StaticContainer.workerList)
    	        {
    	        	if(i.getPosition() != Position.TESTER)
    	        	{
    	        		items.add(i.getEmployeeData());
    	        		System.out.println(i);
    	        	}
    	        }
    	        buttonText = "Przydziel pracownika";
    	        break;
    		case doPoprawy:
    			
    	        for(Worker i : StaticContainer.workerList)
    	        {
    	        	if(i.getPosition() != Position.TESTER)
    	        	{
    	        		items.add(i.getEmployeeData());
    	        		System.out.println(i);
    	        	}
    	        }
    	        buttonText = "Przydziel pracownika";
    	        break;
    		case doTestowania:
    			
    	        for(Worker i : StaticContainer.workerList)
    	        {
    	        	if(i.getPosition() == Position.TESTER)
    	        	{
    	        		items.add(i.getEmployeeData());
    	        		System.out.println(i);
    	        	}
    	        	
    	        }
    	        buttonText = "Przydziel testera";
    	        break;
    	     default:
    	    	 
    	    	 items.add("Nie mozna przydzielic, status zadania: "+status);
    	    	 break;
    	};
    	
    	employeeView.setItems(items);
    	if(!buttonText.equals(""))
    	{
    		actionButton.setText(buttonText);
    		actionButton.setVisible(true);
    	}
    }
    
    
    public void setTaskStatus(ActionEvent event)
    {
    	Unit u = (Unit) unitView.getSelectionModel().getSelectedItem();
    	StatusC.stat s = (StatusC.stat) employeeView.getSelectionModel().getSelectedItem();
    	u.setS(s);
    	sync();
    }
    
    
    public void sync()
    {
    	if(activ == activeView.project)
    	{
    		ObservableList<Project> items = FXCollections.observableArrayList ();
            for(Project i : StaticContainer.projectList)
            {
            	items.add(i);
            	//System.out.println(i);
            }
            unitView.setItems(items);
    	}
    	else if(activ == activeView.module)
    	{
    		
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
    		     StaticContainer.setModuleList(mList);
    		     setTaskStatusButton.setVisible(false);
    		     ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
    		     employeeView.setItems(itemsS);
    		
    	}else if(activ == activeView.task)
    	{
    		
    			System.out.println("clicked on " + unitView.getSelectionModel().getSelectedItem());
    			
    			 ObservableList<Task> itemsT = FXCollections.observableArrayList ();
    			 Module m = (Module) unitView.getSelectionModel().getSelectedItem();
    			 List<Task> tList = m.getTasksSet();
    		     for(Task i : tList)
    		     {
    		      	itemsT.add(i);
    		       	//System.out.println(i);
    		     }
    		     unitView.setItems(itemsT);
    		     activ = activeView.task;
    		     StaticContainer.setTaskList(tList);
    		     setTaskStatusButton.setVisible(false);
    		     ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
    		     employeeView.setItems(itemsS);
    		
    	}
    }
    
    
    


}
