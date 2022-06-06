package com.structure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Worker extends Employee{
    protected Position position;
    ArrayList<Task> tasksList;

    /**
     * Komparator sluzacy do sortowania listy z zdaniami koljeno od wRealizacji do ukończonych
     */
    class tasksComparator implements Comparator<Task> {

        // override the compare() method
        @Override
        public int compare(Task t1, Task t2)
        {
            if (t1.s == t2.s)
                return 0;
            else if (t1.s == StatusC.stat.ukończone && t2.s != StatusC.stat.ukończone)
                return 1;
            else if(t1.s != StatusC.stat.wRrealizacji && t2.s == StatusC.stat.wRrealizacji)
                return 1;
            else
                return -1;
        }
    }

    public Worker(int employeeID, String firstName, String secondName, Position position) {
        super(employeeID, firstName, secondName);
        tasksList = new ArrayList<>();
        this.position = position;
    }

    public void endTask(Task task) throws IndexOutOfBoundsException{
        int taskID = findTaskID(task);

        if(taskID < 0 || taskID >= tasksList.size()) {
            throw new IndexOutOfBoundsException("Nie ma takiego zadania na liscie pracownika!");
        }
        else {
            tasksList.get(taskID).s = StatusC.stat.ukończone;
        }

    }

    public void taskInProgress(Task task) throws IndexOutOfBoundsException{
        int taskID = findTaskID(task);

        if(taskID < 0 || taskID >= tasksList.size()) {
            throw new IndexOutOfBoundsException("Nie ma takiego zadania na liscie pracownika!");
        }
        else {
            tasksList.get(taskID).s = StatusC.stat.wRrealizacji;
        }

    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public void changeTaskStatus(Task task, StatusC.stat newStatus) throws IndexOutOfBoundsException{
        int taskID = findTaskID(task);
        if(taskID < 0 || taskID >= tasksList.size()) {
            throw new IndexOutOfBoundsException("Nie ma takiego zadania na liscie pracownika!");
        }
        else {
            tasksList.get(taskID).s = newStatus;

            //jesli ukonczone lub anulowane to usun z listy zadan konkretnego pracownika
            //if(newStatus == StatusC.stat.anulowane || newStatus == StatusC.stat.anulowane)
            //    tasksList.remove(taskID);
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

    public void refreshTasks() {
        Collections.sort(this.tasksList, new tasksComparator());
    }





}
