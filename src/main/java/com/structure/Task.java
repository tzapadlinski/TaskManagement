package com.structure;

import java.time.LocalDate;

public class Task extends Unit {
	
	private int moduleID;

	public Task(LocalDate deadline, LocalDate start, String description, StatusC.stat s, int id,String name, Module mod) throws Exception{
		super(deadline, start, null, description, s, id, name);
		// TODO Auto-generated constructor stub
		if(deadline.compareTo(mod.getDeadline())<0)
			throw new Exception("blad daty");
		
		moduleID = mod.getID();
		
	}

	//TYLKO DO TESTOW
	public Task(LocalDate deadline, LocalDate start, String description, StatusC.stat s, int id,String name) throws Exception{
		super(deadline, start, null, description, s, id, name);
		// TODO Auto-generated constructor stub
		//if(deadline.compareTo(mod.getDeadline())<0)
		//	throw new Exception("blad daty");

		//moduleID = mod.getID();

	}
	
	public int getModId()
	{
		return moduleID;
	}

	@Override
	public String getRaport() {
		// TODO Auto-generated method stub
		return "Zadanie [deadline=" + deadline + ", manager=" + manager + ", description=" + description + "]";
	}

	public String getShortcut()
	{
		return "Task - "+ name +", status: "+ s;
	}

}
