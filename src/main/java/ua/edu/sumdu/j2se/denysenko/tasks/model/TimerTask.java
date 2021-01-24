package ua.edu.sumdu.j2se.denysenko.tasks.model;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Set;

public class TimerTask extends java.util.TimerTask {
    private Set<Task> taskSet;
    private final static Logger logger = Logger.getLogger(TimerTask.class);

    public TimerTask(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

    @Override
    public void run() {
        printMessage(taskSet);
        logger.info("Notification was been printed");
    }

    private void printMessage(Set<Task> set){
        System.out.println("\nTime to execute such tasks:");
        Iterator<Task> it = set.iterator();
        while (it.hasNext()){
            System.out.println("\t" + it.next().getTitle());
        }
    }

}
