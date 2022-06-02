import java.util.ArrayList;

public class Employee {
    protected int employeeID;
    protected String firstName;
    protected String secondName;
    protected Position position;

    public Employee() {}

    public Employee(int employeeID, String firstName, String secondName, Position position) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.position = position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getEmployeeID() {
        return employeeID;
    }



}
