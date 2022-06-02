

import java.time.LocalDate;

public abstract class Unit {
	
	protected int id;
	protected LocalDate deadline;
	protected LocalDate startDate;
	protected Manager manager; 
	protected String description;
	protected StatusC.stat s;
	
	public Unit(LocalDate deadline, LocalDate start, Manager manager, String description, StatusC.stat s, int id) {
		this.deadline = deadline;
		this.manager = manager;
		this.description = description;
		this.s = s;
		this.id = id;
		startDate = start;
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

	
	
	
	

}
