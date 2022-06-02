package com.structure;

import java.time.LocalDate;

public class Manager extends Employee{


    //TODO - zmiana wyjatku
    public void extendProjectTime(Project project, LocalDate newProjectDeadline) //throws Wyjatek zrobiony przez Kube
    {
        LocalDate oldProjectDeadline = project.getDeadline();
        //nowa data jest przed stara lub jest jej rowna
        if(newProjectDeadline.isBefore(oldProjectDeadline) || newProjectDeadline.isEqual(oldProjectDeadline)) {
            //throw Exception
        }
        else {
            project.deadline = newProjectDeadline;
        }
    }

    public void assignTask(Worker worker, Task taskToAssign) {
        worker.tasksList.add(taskToAssign);
    }

}
