package com.structure;

import java.time.LocalDate;

public class ProjectLog {
    private boolean succeeded;
    private String filename;
    //private com.structure.Manager projectLeader;
    //private []com.structure.Employee employees;
    private LocalDate startTimestamp;
    private LocalDate deadline;
    private String details;

    //TODO
    ProjectLog(){

    }

    //TODO
    public static ProjectLog loadProject(String fileName){
        return null;
    }

    //TODO
    public void saveProject(String fileName){

    }

    //TODO
    @Override
    public String toString() {
        return "com.structure.ProjectLog{" +
                "details='" + details + '\'' +
                '}';
    }
}
