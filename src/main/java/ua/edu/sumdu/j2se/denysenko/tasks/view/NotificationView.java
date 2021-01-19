package ua.edu.sumdu.j2se.denysenko.tasks.view;

import ua.edu.sumdu.j2se.denysenko.tasks.model.Task;

import java.util.Iterator;
import java.util.Set;

public class NotificationView {
    public void printMessage(Set<Task> set){
        System.out.println("Time to perform such tasks:");
        Iterator <Task> it = set.iterator();
        while (it.hasNext()){
            System.out.println(it.next().getTitle());
        }
    }
}
