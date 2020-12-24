package ua.edu.sumdu.j2se.denysenko.tasks;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract ListTypes.types getType();
    public abstract int size();


    public Stream<Task> getStream(){
        LinkedList<Task> list = new LinkedList<>();
        Iterator<Task> it = iterator();
        while(it.hasNext()){
            list.add(it.next());
        }
        return list.stream();
    }

}
