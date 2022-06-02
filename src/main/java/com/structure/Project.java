package com.structure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class Project extends Unit {

	public Project(LocalDate deadline,LocalDate start, Manager manager, String description, StatusC.stat s, int id) {
		super(deadline, start, manager, description, s, id);
		// TODO Auto-generated constructor stub
	}
	
	private List<Module> moduleSet = new ArrayList<Module>();
	
	public void addModule(Module m)
	{
		moduleSet.add(m);
	}
	
	public void modyfiModule(Module m, int mId)
	{
		int index = 0;
		for(Module i : moduleSet)
		{
			if(i.getID() == mId)
				{
					moduleSet.set(index, m);
					return;
				}
			
			index++;
				
		}
	}
	
	

	@Override
	public String getRaport() {
		// TODO Auto-generated method stub
		return "Projekt [deadline=" + deadline + ", manager=" + manager + ", description=" + description + "]";
	}

}
