package ua.edu.sumdu.j2se.denysenko.tasks.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable, Cloneable, Serializable {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract int size();
    public abstract ListTypes.types getType();

    public Stream<Task> getStream(){
        LinkedList<Task> list = new LinkedList<>();
        Iterator<Task> it = iterator();
        while(it.hasNext()){
            list.add(it.next());
        }
        return list.stream();
    }

    public int getTaskIndex(Task task){
        Iterator<Task> it = iterator();
        int i = 0;
        while(it.hasNext()){
            if(it.next().equals(task)) return i;
            i++;
        }
        return -1;
    }
}
