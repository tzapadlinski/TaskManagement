import java.util.ArrayList;

public class Worker extends Employee{
    ArrayList<Task> tasksList;


    public void changeTaskStatus(Task task, StatusC.stat newStatus) throws IndexOutOfBoundsException{
        int taskID = findTaskID(task);
        if(taskID < 0 || taskID >= tasksList.size()) {
            throw new IndexOutOfBoundsException("Nie ma takiego zadania na liscie pracownika!");
        }
        else {
            tasksList.get(taskID).s = newStatus;

            //jesli ukonczone lub anulowane to usun z listy zadan konkretnego pracownika
            if(newStatus == StatusC.stat.anulowane || newStatus == StatusC.stat.anulowane)
                tasksList.remove(taskID);
        }
    }

    private int findTaskID(Task task) {
        for(int i=0; i<tasksList.size(); i++) {
            if(tasksList.get(i) == task)
                return  i;
        }
        return -1;
    }
}
