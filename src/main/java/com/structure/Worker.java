package com.structure;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Worker extends Employee{
    protected Position position;
    ArrayList<Task> tasksList;
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/accountbase","root","");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Worker(int employeeID, String firstName, String secondName, Position position) {
        super(employeeID, firstName, secondName);
        tasksList = new ArrayList<>();
        this.position = position;

        /*
        try {
            loadTasksOnList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */
    }

    public void loadTasksOnList() throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM task JOIN employee_task ON task.taskID = employee_task.taskID WHERE employee_task.employeeID ="
                            + this.getEmployeeID() + "\n ");

        while (resultSet.next()) {
            StatusC.stat stat = StatusC.stat.ukończone;

            switch (resultSet.getString("status")){
                case "wRealizacji":
                    stat = StatusC.stat.wRealizacji;
                    break;
                case "anulowane":
                    stat = StatusC.stat.anulowane;
                    break;
                case "doPoprawy":
                    stat = StatusC.stat.doPoprawy;
                    break;
                case "doTestowania":
                    stat = StatusC.stat.doTestowania;
                    break;
                case "nowy":
                    stat = StatusC.stat.nowy;
                    break;
                case "poprawiane":
                    stat = StatusC.stat.poprawiane;
                    break;
                case "przydzielone":
                    stat = StatusC.stat.przydzielone;
                    break;
                case "ukończone":
                    stat = StatusC.stat.ukończone;
                    break;
                default:
                    stat = StatusC.stat.err;
                    break;
            }

            try {
                Task task = new Task(resultSet.getDate("deadline").toLocalDate(), resultSet.getDate("startdate").toLocalDate(), resultSet.getString("description"),
                        stat, resultSet.getInt("taskID"), resultSet.getString("nazwa"));
                this.tasksList.add(task);
                System.out.println(task);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void endTask(Task task) throws MyException{
        int taskID = findTaskID(task);

        if(taskID < 0 || taskID >= tasksList.size()) {
            throw new MyException("Nie ma takiego zadania na liscie pracownika!");
        }
        else {
            try {
                if(this.position == Position.TESTER) {
                    changeTaskStatus(task, StatusC.stat.ukończone);
                }
                else {
                    changeTaskStatus(task, StatusC.stat.doTestowania);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void taskInProgress(Task task) throws MyException{
        int taskID = findTaskID(task);

        if(taskID < 0 || taskID >= tasksList.size()) {
            throw new MyException("Nie ma takiego zadania na liscie pracownika!");
        }
        else {
            try {
                changeTaskStatus(task, StatusC.stat.wRealizacji);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public void changeTaskStatus(Task task, StatusC.stat newStatus) throws MyException, SQLException {


        int taskID = findTaskID(task);
        if(taskID < 0 || taskID >= tasksList.size()) {
            throw new MyException("Nie ma takiego zadania na liscie pracownika!");
        }
        else {
            //zmiana na liscie pracownika
            tasksList.get(taskID).s = newStatus;

            //zmiana w bazie danych
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE task SET status = \"" + newStatus + "\" WHERE task.taskID = " + this.employeeID + "\n ");
        }
    }

    private int findTaskID(Task task) {
        for(int i=0; i<tasksList.size(); i++) {
            if(tasksList.get(i) == task)
                return  i;
        }
        return -1;
    }


    public void setPosition(Position position) {
        this.position = position;
    }

    public void addTask(Task task) {
        this.tasksList.add(task);
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    public Position getPosition() {
        return position;
    }

	@Override
	public String toString() {
		return String.valueOf(lastName+" "+firstName);
	}

    public void sortTasks() {
        Collections.sort(this.tasksList, new tasksComparatorByStatus());
        Collections.sort(this.tasksList, new taskComparatorByStatusAndDate());

    }


    /**
     * Komparator sluzacy do sortowania listy z zdaniami koljeno od wRealizacji do ukończonych
     */
    class tasksComparatorByStatus implements Comparator<Task> {

        // override the compare() method
        @Override
        public int compare(Task t1, Task t2)
        {
            if (t1.s == t2.s)
                return 0;
            else if (t1.s == StatusC.stat.ukończone && t2.s != StatusC.stat.ukończone)
                return 1;
            else if(t1.s != StatusC.stat.wRealizacji && t2.s == StatusC.stat.wRealizacji)
                return 1;
            else
                return -1;
        }
    }

    class taskComparatorByDate implements Comparator<Task> {

        // override the compare() method
        @Override
        public int compare(Task t1, Task t2)
        {
            if (t1.deadline.isEqual(t2.deadline))
                return 0;
            else if (t1.deadline.isAfter(t2.deadline))
                return 1;
            else
                return -1;
        }
    }

    class taskComparatorByStatusAndDate implements Comparator<Task> {

        // override the compare() method
        @Override
        public int compare(Task t1, Task t2)
        {
            if(t1.s == t2.s) {
                if (t1.deadline.isEqual(t2.deadline))
                    return 0;
                else if (t1.deadline.isAfter(t2.deadline))
                    return 1;
                else
                    return -1;
            }
            else return 0;
        }
    }
}
