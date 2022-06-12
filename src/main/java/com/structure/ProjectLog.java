package com.structure;

import java.io.*;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProjectLog implements Serializable{
    private String fileName;
    private String content;


    public ProjectLog(String fileName){
        this.fileName = fileName;
    }


    public static ProjectLog loadProject(String fileName) throws Exception{
        ProjectLog log = new ProjectLog(fileName);
        log.content = new Scanner(new File(
                "src\\main\\resources\\com\\example\\taskmanagement\\projectLogs\\"
                + fileName + ".txt")).useDelimiter("\\Z").next();
        return log;
    }


    public void saveProject() throws IOException {
        FileWriter fw = new FileWriter(
                "src\\main\\resources\\com\\example\\taskmanagement\\projectLogs\\"
                        + fileName + ".txt");
        PrintWriter pw = new PrintWriter(fw);
        if(content != null){
            pw.write(content);
        }
        pw.close();
        fw.close();
    }

    public String getFileName() {
        return fileName;
    }


    public void parseProject(Project project) {
        if (project == null) {
            content = null;
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("ID projektu: " + project.getID() + "\n");
        builder.append("Prowadzacy: " + project.getManager().toString() + "\n");
        builder.append("Opis: " + project.getDescription() + "\n");
        builder.append("Czas rozpoczecia: " + project.getStartDate() + "\n");
        builder.append("Czas zakonczenia: " + project.getDeadline() + "\n");
        content = builder.toString();
    }

    public String getContent() {
        return content;
    }
}
