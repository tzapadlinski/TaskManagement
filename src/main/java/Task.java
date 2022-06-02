

import java.time.LocalDate;

public class Task extends Unit {
	
	private int moduleID;

	public Task(LocalDate deadline, LocalDate start, Manager manager, String description, StatusC.stat s, int id, Module mod) throws Exception{
		super(deadline, start, manager, description, s, id);
		// TODO Auto-generated constructor stub
		if(deadline.compareTo(mod.getDeadline())<0)
			throw new Exception("blad daty");
		
		moduleID = mod.getID();
		
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

}
