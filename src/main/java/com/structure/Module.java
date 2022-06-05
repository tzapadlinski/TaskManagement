package com.structure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Module extends Unit {
	
	private int projectID;

	

	public Module(LocalDate deadline,LocalDate start, String description, StatusC.stat s, int id, String name, Project proj) throws Exception {
		super(deadline, start,  proj.getManager(), description, s, id, name);
		// TODO Auto-generated constructor stub
		if(deadline.compareTo(proj.getDeadline())<0)
			throw new Exception("blad daty");
		
		projectID = proj.getID();
	}
	
	private List<Task> tasksSet = new ArrayList<Task>();
	
	public int getIdProj()
	{
		return projectID; 
	}
	
	public void addTask(Task t)
	{
		tasksSet.add(t);
	}
	
	public void modyfitask(Task t, int tId)
	{
		int index = 0;
		for(Task i : tasksSet)
		{
			if(i.getID() == tId)
				{
					tasksSet.set(index, t);
					return;
				}
			
			index++;
				
		}
	}

	@Override
	public String getRaport() {
		// TODO Auto-generated method stub
		return "Modul [deadline=" + deadline + ", manager=" + manager + ", description=" + description + "]";
	}
	
	@Override
	public String toString() {
		return "Modul "+ name +" status: "+ s;
	}

	public List<Task> getTasksSet() {
		return tasksSet;
	}
	
	

}
