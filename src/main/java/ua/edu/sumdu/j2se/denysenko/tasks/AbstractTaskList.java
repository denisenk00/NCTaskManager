package ua.edu.sumdu.j2se.denysenko.tasks;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract ListTypes.types getType();
    public abstract int size();

    final public AbstractTaskList incoming(int from, int to){
        AbstractTaskList taskList = TaskListFactory.createTaskList(this.getType());
        Stream <Task> stream = this.getStream();
        stream.filter(task -> task.nextTimeAfter(from) != -1 && task.nextTimeAfter(from) <= to).forEach(taskList::add);
        return taskList;
    }

    public Stream<Task> getStream(){
        LinkedList<Task> list = new LinkedList<>();
        Iterator<Task> it = iterator();
        while(it.hasNext()){
            list.add(it.next());
        }
        return list.stream();
    }

}
