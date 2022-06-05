package com.structure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StaticContainer {
	
	public static List<String> raportList = new ArrayList<>();
	public static List<Project> projectList = new ArrayList<>();
	public static List<Module> moduleList = new ArrayList<>();
	public static List<Task> taskList = new ArrayList<>();
	public static List<Worker> WorkerList = new ArrayList<>();
	public static List<Manager> ManagerList = new ArrayList<>();
	public static List<String> TesterList = new ArrayList<>(); //jak bedzie tester
	
	{
		//do testowania
		raportList.add("svcd");
		raportList.add("sgqevcd");
		raportList.add("svegqrcd");
		raportList.add("svcdgrdsv");
		Manager m = new Manager(1,"jan","kowalski");
		
		Project p = new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), m, "Opis 5", StatusC.stat.nowy, 10, "Nazwa 5");
		Project p2 = new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), m, "Opis 6", StatusC.stat.nowy, 10, "Nazwa 6");
		
		try {
			Module m1 = new Module(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Opis 1 M", StatusC.stat.nowy, 10, "Nazwa 1 M",p);
			Module m2 = new Module(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Opis 2 M", StatusC.stat.nowy, 10, "Nazwa 2 M",p);
			p2.addModule(m2);
			p.addModule(m1);
			Task t1 = new Task(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Opis 1 T", StatusC.stat.nowy, 10, "Nazwa 1 T",m1);
			Task t2 = new Task(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Opis 2 T", StatusC.stat.nowy, 10, "Nazwa 2 T",m1);
			m1.addTask(t1);
			m2.addTask(t2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 1", StatusC.stat.nowy, 10, "Nazwa 1"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 2", StatusC.stat.nowy, 10, "Nazwa 2"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 3", StatusC.stat.nowy, 10, "Nazwa 3"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 4", StatusC.stat.nowy, 10, "Nazwa 4"));
		projectList.add(p);
		projectList.add(p2);
		
		System.out.println("koniec");

		//testy listy pracownika
		Worker w1 = new Worker(1, "Adam", "Nowak", Position.PROGRAMISTA);
		w1.dodajPrzykladowyTask(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), "Dwie funkcje", StatusC.stat.nowy, 1, "Pierwszy task");
		WorkerList.add(w1);

	}

	public static void setModuleList(List<Module> moduleList) {
		StaticContainer.moduleList = moduleList;
	}

	public static void setTaskList(List<Task> taskList) {
		StaticContainer.taskList = taskList;
	}
	
	
	
	

}
