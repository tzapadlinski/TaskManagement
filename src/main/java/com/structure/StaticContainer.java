package com.structure;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaticContainer {

	public static Employee loggedUser;

	public static List<String> raportList = new ArrayList<>();
	public static List<Project> projectList = new ArrayList<>();	//x
	public static List<Module> moduleList = new ArrayList<>();		//x
	public static List<Task> taskList = new ArrayList<>();			//x
	public static List<Worker> workerList = new ArrayList<>();
	public static List<Manager> managerList = new ArrayList<>();
	public static List<Worker> testerList = new ArrayList<>(); //jak bedzie tester
	public static ProjectLog  activeLog;
	
	{
		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/accountbase","root","");
			Statement statement = connection.createStatement();

			//loading managers
			ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE accounts.position = \"manager\"");
			while(resultSet.next()) {
				int id = resultSet.getInt("employeeID");
				String name = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String position = resultSet.getString("position");
				Manager m = new Manager(id,name,lastName);

				managerList.add(m);
			}

			//loading projects
			resultSet = statement.executeQuery("SELECT * FROM project");
			while(resultSet.next()){
				StatusC.stat stat;
				switch (resultSet.getString("status")){
					case "wRealizacji":
						stat = StatusC.stat.wRealizacji;
						break;
					case "anulowane":
						stat = StatusC.stat.anulowane;
						break;
					case "doPoprawy":
						stat = StatusC.stat.doPoprawy;
						break;
					case "doTestowania":
						stat = StatusC.stat.doTestowania;
						break;
					case "nowy":
						stat = StatusC.stat.nowy;
						break;
					case "poprawiane":
						stat = StatusC.stat.poprawiane;
						break;
					case "przydzielone":
						stat = StatusC.stat.przydzielone;
						break;
					case "ukończone":
						stat = StatusC.stat.ukończone;
						break;
					default:
						stat = StatusC.stat.err;
						break;

				}
				//TODO - przypisany jest pierwszy na liscie manager, zmienic na losowy albo ktory ma mniej
				projectList.add(new Project(resultSet.getDate("deadline").toLocalDate(),
						resultSet.getDate("startdate").toLocalDate(), managerList.get(0),
						resultSet.getString("description"), stat,
						resultSet.getInt("projectID"), resultSet.getString("nazwa")));
			}

			//loading modules
			resultSet = statement.executeQuery("SELECT * FROM module");
			while(resultSet.next()){
				StatusC.stat stat;
				switch (resultSet.getString("status")){
					case "wRealizacji":
						stat = StatusC.stat.wRealizacji;
						break;
					case "anulowane":
						stat = StatusC.stat.anulowane;
						break;
					case "doPoprawy":
						stat = StatusC.stat.doPoprawy;
						break;
					case "doTestowania":
						stat = StatusC.stat.doTestowania;
						break;
					case "nowy":
						stat = StatusC.stat.nowy;
						break;
					case "poprawiane":
						stat = StatusC.stat.poprawiane;
						break;
					case "przydzielone":
						stat = StatusC.stat.przydzielone;
						break;
					case "ukończone":
						stat = StatusC.stat.ukończone;
						break;
					default:
						stat = StatusC.stat.err;
						break;
				}
				int projectID = resultSet.getInt("projectID");
				Project project = null;
				for (Project saved:projectList) {
					if (saved.getID() == projectID) {
						project = saved;
						break;
					}
				}
				Module new_ = new Module(resultSet.getDate("deadline").toLocalDate(),
						resultSet.getDate("startdate").toLocalDate(),
						resultSet.getString("description"), stat,
						resultSet.getInt("moduleID"), resultSet.getString("nazwa"),
						project);
				project.addModule(new_);
				moduleList.add(new_);
			}

			//loading tasks
			resultSet = statement.executeQuery("SELECT * FROM task");
			while(resultSet.next()){
				StatusC.stat stat;
				switch (resultSet.getString("status")){
					case "wRealizacji":
						stat = StatusC.stat.wRealizacji;
						break;
					case "anulowane":
						stat = StatusC.stat.anulowane;
						break;
					case "doPoprawy":
						stat = StatusC.stat.doPoprawy;
						break;
					case "doTestowania":
						stat = StatusC.stat.doTestowania;
						break;
					case "nowy":
						stat = StatusC.stat.nowy;
						break;
					case "poprawiane":
						stat = StatusC.stat.poprawiane;
						break;
					case "przydzielone":
						stat = StatusC.stat.przydzielone;
						break;
					case "ukończone":
						stat = StatusC.stat.ukończone;
						break;
					default:
						stat = StatusC.stat.err;
						break;
				}
				int moduleId = resultSet.getInt("moduleID");
				Module module = null;
				for (Module saved:moduleList) {
					if (saved.getID() == moduleId) {
						module = saved;
						break;
					}
				}
				Task new_ = new Task(resultSet.getDate("deadline").toLocalDate(),
						resultSet.getDate("startdate").toLocalDate(),
						resultSet.getString("description"), stat,
						resultSet.getInt("taskID"), resultSet.getString("nazwa"),
						module);
				taskList.add(new_);
				module.addTask(new_);
			}

			//loading tasks
			resultSet = statement.executeQuery("SELECT * FROM accounts");
			while(resultSet.next()) {
				int id = resultSet.getInt("employeeID");
				String name = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String position = resultSet.getString("position");
				if (position.equals("manager"))
					continue;
				Worker worker = null;
				switch (position) {
					case "programmer":
						worker = new Worker(id, name, lastName, Position.PROGRAMISTA);
						break;
					case "tester":
						worker = new Worker(id, name, lastName, Position.TESTER);
						break;
					case "other":
						worker = new Worker(id, name, lastName, Position.ANALITYK);
						break;
				}
				
				Statement statement1 = connection.createStatement();
				ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM employee_task WHERE employeeID = " + worker.getEmployeeID());
				while(resultSet1.next()){
					int taskId = resultSet1.getInt("taskID");
					for (Task task:StaticContainer.taskList) {
						if (task.getID() == taskId){
							worker.addTask(task);
							break;
						}
					}
				}

				workerList.add(worker);
			}



		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*do testowania
		raportList.add("svcd");
		raportList.add("sgqevcd");
		raportList.add("svegqrcd");
		raportList.add("svcdgrdsv");
		Manager m = new Manager(1,"jan","kowalski");
		Worker w1 = new Worker(1, "Adam", "Nowak", Position.PROGRAMISTA);
		Worker w2 = new Worker(1, "Adam", "NowakTester", Position.TESTER);
		
		Project p = new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), m, "Opis 5", StatusC.stat.nowy, 5, "Nazwa 5");
		Project p2 = new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), m, "Opis 6", StatusC.stat.nowy, 6, "Nazwa 6");
		
		try {
			Module m1 = new Module(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Opis 1 M", StatusC.stat.nowy, 1, "Nazwa 1 M",p);
			Module m2 = new Module(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Opis 2 M", StatusC.stat.nowy, 2, "Nazwa 2 M",p);
			p2.addModule(m2);
			p.addModule(m1);
			Task t1 = new Task(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Opis 1 T", StatusC.stat.nowy, 1, "Nazwa 1 T",m1);
			Task t2 = new Task(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Opis 2 T", StatusC.stat.doTestowania, 2, "Nazwa 2 T",m1);
			m1.addTask(t1);
			m2.addTask(t2);
			w1.addTask(t1);
			w2.addTask(t2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 1", StatusC.stat.nowy, 1, "Nazwa 1"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 2", StatusC.stat.nowy, 2, "Nazwa 2"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 3", StatusC.stat.nowy, 3, "Nazwa 3"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 4", StatusC.stat.nowy, 4, "Nazwa 4"));
		projectList.add(p);
		projectList.add(p2);
		
		System.out.println("koniec");

		//testy listy pracownika
		
		//w1.dodajPrzykladowyTask(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Dwie funkcje", StatusC.stat.nowy, 1, "Pierwszy task");
		
		workerList.add(w1);
		workerList.add(w2);
		*/

	}

	public static void setModuleList(List<Module> moduleList) {
		StaticContainer.moduleList = moduleList;
	}

	public static void setTaskList(List<Task> taskList) {
		StaticContainer.taskList = taskList;
	}
	
	public static void setCurrenLoggedUser(Employee loggedUser) {
		StaticContainer.loggedUser = loggedUser;
	}

}
