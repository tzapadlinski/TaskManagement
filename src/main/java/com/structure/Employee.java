package com.structure;

public class Employee {
    protected int employeeID;
    protected String firstName;
    protected String secondName;

    public Employee() {}

    public Employee(int employeeID, String firstName, String secondName) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public int getEmployeeID() {
        return employeeID;
    }
    
    public String getEmployeeData()
    {
    	return String.valueOf(secondName+" "+firstName);
    }


}
