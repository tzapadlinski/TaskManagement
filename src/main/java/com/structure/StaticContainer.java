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
		
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 1", StatusC.stat.nowy, 10, "Nazwa 1"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 2", StatusC.stat.nowy, 10, "Nazwa 2"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 3", StatusC.stat.nowy, 10, "Nazwa 3"));
		projectList.add(new Project(LocalDate.parse("2020-01-01"),LocalDate.parse("2019-01-01"), null, "Opis 4", StatusC.stat.nowy, 10, "Nazwa 4"));

		System.out.println("koniec");
	}

}