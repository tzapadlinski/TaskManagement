

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Module extends Unit {
	
	private int projectID;

	

	public Module(LocalDate deadline,LocalDate start, Manager manager, String description, StatusC.stat s, int id, Project proj) throws Exception {
		super(deadline, start,  manager, description, s, id);
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
	
	public void addModule(Task t)
	{
		tasksSet.add(t);
	}
	
	public void modyfiModule(Task t, int tId)
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

}
