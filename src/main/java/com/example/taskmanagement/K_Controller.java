package com.example.taskmanagement;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import com.structure.*;

import com.structure.Module;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	@FXML
	private Button raporty;
	@FXML
	private Button makeLogButton;
    
    private enum activeView {project, module, task,taskFocused};
    private activeView activ;
    private Project pSync;
	private Project pSync2;
    private Module mSync;
    private Task tSync;
    private int index;
   

    Stage stage;

    public void logout(ActionEvent event) throws  IOException{
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

	public void sync2(ActionEvent event)
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
			setTaskStatusButton.setVisible(false);
			ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
			employeeView.setItems(itemsS);
		}
		else if(activ == activeView.module)
		{

			System.out.println("clicked on " + unitView.getSelectionModel().getSelectedItem());

			ObservableList<Module> itemsM = FXCollections.observableArrayList ();
			Project p = pSync;
			List<Module> mList = p.getModuleSet();
			for(Module i : mList)
			{
				itemsM.add(i);
				//System.out.println(i);
			}
			unitView.setItems(itemsM);

			StaticContainer.setModuleList(mList);
			setTaskStatusButton.setVisible(false);
			ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
			employeeView.setItems(itemsS);

		}else if(activ == activeView.task)
		{

			System.out.println("clicked on " + unitView.getSelectionModel().getSelectedItem());

			ObservableList<Task> itemsT = FXCollections.observableArrayList ();
			Module m = mSync;
			List<Task> tList = m.getTasksSet();
			for(Task i : tList)
			{
				itemsT.add(i);
				//System.out.println(i);
			}
			unitView.setItems(itemsT);

			StaticContainer.setTaskList(tList);
			setTaskStatusButton.setVisible(false);
			ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
			employeeView.setItems(itemsS);

		}else
		{
			ObservableList<String> itemsS = FXCollections.observableArrayList ();
			Task t = tSync;

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

			setEmployee(s);

			setTaskStatusButton.setVisible(false);

		}
	}
    
    @FXML
    public void initialize() {
    	//StaticContainer inicjalizacja = new StaticContainer();
        actionButton.setVisible(false);
        setTaskStatusButton.setVisible(false);
        //syncButton.setVisible(false);
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
            		     pSync = p;
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

           			 	
           			 	pSync2 = p;
           			 	
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
            		     mSync = m;
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
            			 index = unitView.getSelectionModel().getSelectedIndex();
            			 
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
            			 
            			 if((showWorker(t.getID()) != null))
            			 {
            				 itemsS.add(showWorker(t.getID()));
            			 }
            			 if(showTester(t.getID()) != null)
            			 {
            				 itemsS.add(showTester(t.getID()));
            			 }
            		     
            		     unitView.setItems(itemsS);
            		     activ = activeView.taskFocused;
            		     setEmployee(s);
            		     
            		     setTaskStatusButton.setVisible(false);
            		     tSync = t;
            		     
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
    	ObservableList items = FXCollections.observableArrayList ();
    	switch (status)
    	{
    		
    		case nowy:
    	        for(Worker i : StaticContainer.workerList)
    	        {
    	        	if(i.getPosition() != Position.TESTER)
    	        	{
    	        		items.add(i);
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
    	        		items.add(i);
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
    	        		items.add(i);
    	        		System.out.println(i);
    	        	}
    	        	
    	        }
    	        buttonText = "Przydziel testera";
    	        break;
    	     default:
    	    	 
    	    	 items.add("Nie mozna przydzielic");
    	    	 items.add("Status zadania: "+status);
    	    	 actionButton.setVisible(false);
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
    	System.out.println("set task");
    	if(u instanceof Project)
    		{
    			//System.out.println("adada");
    			List<Module> mList = ((Project) u).getModuleSet();
    			
    			try {
   			 		Statement statement = ServiceAccess.connection.createStatement();
					statement.executeUpdate("UPDATE project set status = \""
					    + s + "\" where projectID = " + u.getID());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			for(Module m : mList)
    			{
    				List<Task> tList = m.getTasksSet();
    				
    				for(Task t : tList)
    				{
    					if((t.getS()!=StatusC.stat.anulowane)&&(t.getS()!=StatusC.stat.ukończone))
    					{
    						t.setS(s);
    						try {
            			 		Statement statement = ServiceAccess.connection.createStatement();
    							statement.executeUpdate("UPDATE task set status = \""
    							    + s + "\" where taskID = " + t.getID());
    						} catch (SQLException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    						
    					}
    				}
    				
    				if((m.getS()!=StatusC.stat.anulowane)&&(m.getS()!=StatusC.stat.ukończone))
					{
						m.setS(s);
						 try {
	        			 		Statement statement = ServiceAccess.connection.createStatement();
								statement.executeUpdate("UPDATE module set status = \""
								    + s + "\" where moduleID = " + m.getID());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
    			}
    			
    		}else if(u instanceof Module)
    		{
    			List<Task> tList = ((Module) u).getTasksSet();
    			
    			 try {
 			 		Statement statement = ServiceAccess.connection.createStatement();
						statement.executeUpdate("UPDATE module set status = \""
						    + s + "\" where moduleID = " + u.getID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			
    			for(Task t : tList)
    			{ 				
    				if((t.getS()!=StatusC.stat.anulowane)&&(t.getS()!=StatusC.stat.ukończone))
					{
						t.setS(s);
						try {
        			 		Statement statement = ServiceAccess.connection.createStatement();
							statement.executeUpdate("UPDATE task set status = \""
							    + s + "\" where taskID = " + t.getID());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
    			}
    		}else
    		{
    			try {
			 		Statement statement = ServiceAccess.connection.createStatement();
					statement.executeUpdate("UPDATE task set status = \""
					    + s + "\" where taskID = " + u.getID());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
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
            setTaskStatusButton.setVisible(false);
		    ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
		    employeeView.setItems(itemsS);
    	}
    	else if(activ == activeView.module)
    	{
    		
    			 System.out.println("clicked on " + unitView.getSelectionModel().getSelectedItem());
    			
    			 ObservableList<Module> itemsM = FXCollections.observableArrayList ();
    			 Project p = pSync;
    			 List<Module> mList = p.getModuleSet();
    		     for(Module i : mList)
    		     {
    		      	itemsM.add(i);
    		       	//System.out.println(i);
    		     }
    		     unitView.setItems(itemsM);
    		     
    		     StaticContainer.setModuleList(mList);
    		     setTaskStatusButton.setVisible(false);
    		     ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
    		     employeeView.setItems(itemsS);
    		
    	}else if(activ == activeView.task)
    	{
    		
    			 System.out.println("clicked on " + unitView.getSelectionModel().getSelectedItem());
    			
    			 ObservableList<Task> itemsT = FXCollections.observableArrayList ();
    			 Module m = mSync;
    			 List<Task> tList = m.getTasksSet();
    		     for(Task i : tList)
    		     {
    		      	itemsT.add(i);
    		       	//System.out.println(i);
    		     }
    		     unitView.setItems(itemsT);
    		     
    		     StaticContainer.setTaskList(tList);
    		     setTaskStatusButton.setVisible(false);
    		     ObservableList<StatusC.stat> itemsS = FXCollections.observableArrayList ();
    		     employeeView.setItems(itemsS);
    		
    	}else
    	{
    		 ObservableList<String> itemsS = FXCollections.observableArrayList ();
			 Task t = tSync;
			 
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
			 
			 if(showWorker(t.getID()) != null)
			 {
				 itemsS.add(showWorker(t.getID()));
			 }
			 if(showTester(t.getID()) != null)
			 {
				 itemsS.add(showTester(t.getID()));
			 }
		     
		     unitView.setItems(itemsS);
		    
		     setEmployee(s);
		     
		     setTaskStatusButton.setVisible(false);
		   
    	}
    }
    
    
    public void setEmployeeOnClick(ActionEvent event)
    {
    	try {
    		System.out.println("esfdvc");
    		Worker w = (Worker) employeeView.getSelectionModel().getSelectedItem();
    		w.addTask(tSync);
    		try {
		 		Statement statement = ServiceAccess.connection.createStatement();
				statement.executeUpdate("INSERT INTO employee_task values(" + w.getEmployeeID() + ","
				    + tSync.getID() + ")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("esfdvc");
    		for(Task t : w.getTasksList())
    		{
    			System.out.println(t);
    		}
    		StatusC.stat var;
    		switch(tSync.getS())
    		{
    		case nowy: var = StatusC.stat.wRealizacji; break;
    		case doPoprawy: var = StatusC.stat.poprawiane; break;
    		case doTestowania: var = StatusC.stat.testowane; break;
    		default : var = StatusC.stat.err;
    		}
    		tSync.setS(var);
    		
    		try {
		 		Statement statement = ServiceAccess.connection.createStatement();
				statement.executeUpdate("UPDATE task set status = \""
				    + var + "\" where taskID = " + tSync.getID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		StaticContainer.taskList.set(index, tSync);
    		sync();
    		
    		System.out.println("esfdvevfsdcc");
    	}catch(Exception e)
    	{
    		//e.printStackTrace();
    	}
    }
    
    public String showWorker(int id)
    {
    	
		try {
			Statement statement = ServiceAccess.connection.createStatement();
			ResultSet r = statement.executeQuery("SELECT * FROM accounts where (position = \"other\" OR position = \"programmer\") AND employeeID in (SELECT "
					+ " employeeID from employee_task where taskID = "+ id +")");
			if(r.next())
			{
				return String.valueOf("Pracownik odpowiedzialny za zadanie: "+r.getString("firstName") + " " +
				r.getString("lastName"));
			}else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    public String showTester(int id)
    {
    	
		try {
			Statement statement = ServiceAccess.connection.createStatement();
			ResultSet r = statement.executeQuery("SELECT * FROM accounts where (position = \"tester\" ) AND employeeID in (SELECT "
					+ " employeeID from employee_task where taskID = "+ id +")");
			if(r.next())
			{
				return String.valueOf("Tester odpowiedzialny za zadanie: "+r.getString("firstName") + " " +
				r.getString("lastName"));
			}else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    
    //Czesc, tu Tomek
	public void setRaporty(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("logScene.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Raport");
		stage.setScene(new Scene(root));
		stage.show();
	}
    
	public void createLog(ActionEvent e) throws IOException {
		if(pSync2 == null)
			return;
		ProjectLog log = new ProjectLog(pSync2.getName());
		log.parseProject(pSync2);
		log.saveProject();
		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/accountbase", "root", "");
			Statement statement = connection.createStatement();
			statement.executeUpdate(
					"CALL addLog(\""+ pSync2.getName() + "\")");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
}

