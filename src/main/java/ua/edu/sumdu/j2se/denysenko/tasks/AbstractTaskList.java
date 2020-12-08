package ua.edu.sumdu.j2se.denysenko.tasks;

import java.util.Iterator;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract AbstractTaskList incoming(int from, int to);
    public abstract int size();

}
