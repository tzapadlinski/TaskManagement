package com.structure;

import java.time.LocalDate;

public abstract class Unit {
	
	protected int id;
	protected LocalDate deadline;
	protected LocalDate startDate;
	protected Manager manager; 
	protected String description;
	protected StatusC.stat s;
	protected String name;
	
	public Unit(LocalDate deadline, LocalDate start, Manager manager, String description, StatusC.stat s, int id, String name) {
		this.deadline = deadline;
		this.manager = manager;
		this.description = description;
		this.s = s;
		this.id = id;
		startDate = start;
		this.name = name;
	}
	
	public int getID()
	{
		return id;
	}
	
	public LocalDate getDeadline()
	{
		return deadline;
	}
	
	public abstract String getRaport();
	//		return "Jednostka [deadline=" + deadline + ", manager=" + manager + ", description=" + description + "]";

	public LocalDate getStartDate() {
		return startDate;
	}

	public Manager getManager() {
		return manager;
	}

	public String getDescription() {
		return description;
	}

	public StatusC.stat getS() {
		return s;
	}

	public String getName() {
		return name;
	}

	public void setS(StatusC.stat s) {
		this.s = s;
	}

	
	
	
	

}
