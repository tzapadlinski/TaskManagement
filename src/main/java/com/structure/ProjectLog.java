package com.structure;

import java.io.*;
import java.time.LocalDate;
import java.util.NoSuchElementException;

public class ProjectLog {
    private boolean succeeded;
    private String fileName;
    private Project project;


    ProjectLog(String fileName){
        this.fileName = fileName;
    }

    //TODO
    public static ProjectLog loadProject(String fileName){
        ProjectLog log = new ProjectLog(fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\resources\\com\\example\\taskmanagement\\projectLogs\\"
                    + fileName + ".bin"));
            ObjectInputStream o = new ObjectInputStream(fileInputStream);
            log.project = (Project) o.readObject();
            o.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return log;
    }


    public void saveProject(){
        try {
            if (project == null)
                throw new NoSuchFieldException();
            FileOutputStream fileOutputStream = new FileOutputStream(new File("src\\main\\resources\\com\\example\\taskmanagement\\projectLogs\\"
                    + fileName + ".bin"));
            ObjectOutputStream o = new ObjectOutputStream(fileOutputStream);
            o.writeObject(project);
            o.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e){
            System.out.println("Należy wczytać projekt do klasy.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Project getProject() {
        return project;
    }
}
