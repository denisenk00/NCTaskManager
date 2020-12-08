package ua.edu.sumdu.j2se.denysenko.tasks;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList {
    private class ListElement{
        Task task;
        ListElement next;
    }
    private ListElement head;
    private ListElement tail;
    private int size = 0;


    public void add(Task task){
        ListElement x = new ListElement();
        x.task = task;
        if(head == null){
            head = x;
            tail = x;
        }
        else{
            tail.next = x;
            tail = x;
        }
        size++;
    }

    public boolean remove(Task task){
        if(head == null) return false;
        if(head.task == task){
            if(head == tail){
                head = null;
                tail = null;
                size = 0;
                return true;
            }
            else{
                head = head.next;
                size--;
                return true;
            }
        }
        else {
            ListElement x = head;
            while (x.next != null) {
                if (x.next.task == task) {
                    if (x.next == tail) {
                        tail = x;
                    }
                    else{
                        x.next = x.next.next;
                    }
                    size--;
                    return true;
                }
                x = x.next;
            }
            return false;
        }
    }

    public int size(){
        return size;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException{
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException("The maximum index of an element in an array = "+ (size-1) + ", your index = " + index);
        }
        int i = 0;
        ListElement x = head;
        while(i < index){
            x = x.next;
            i++;
        }
        return x.task;
    }

    public LinkedTaskList incoming(int from, int to){
        LinkedTaskList linkedTaskList = new LinkedTaskList();
        ListElement x = head;
        while(x.next != null){
            int time = x.task.nextTimeAfter(from);
            if(time <= to && time != -1)  linkedTaskList.add(x.task);
            x = x.next;
        }
        return linkedTaskList;
    }

    @Override
    public Iterator<Task> iterator() {
        return new LinkedTaskListIterator();
    }

    private class LinkedTaskListIterator implements Iterator<Task> {
        private ListElement lastReturned = null;
        private ListElement nextElement = head;
        private int lastIndex = -1;
        @Override
        public boolean hasNext( ) {
            return (lastIndex < size-1);
        }
        @Override
        public Task next() {
            if(hasNext()) {
                lastReturned = nextElement;
                nextElement = nextElement.next;
                lastIndex++;
                return lastReturned.task;
            }
            else return null;
        }

        @Override
        public void remove() throws IllegalStateException{
            if(lastIndex >= 0 && lastIndex < size) {
                LinkedTaskList.this.remove(lastReturned.task);
                lastReturned = null;
                lastIndex--;
            }
            else{
                throw new IllegalStateException("Method remove() should be called after next()");
            }
        }
    }

    @Override
    public boolean equals(Object a) {
        if(a == this) return true;
        if (a == null || getClass() != a.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) a;
        if(size == that.size){
            int ok = 0;
            for(int i = 0; i < size; i++){
                if( that.getTask(i).equals(getTask(i))) ok++;
                else return false;
            }
            if(ok == size) return true;
            else return false;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        ListElement a = head;
        for(int i = 0; i<size; i++){
            result = 31 * result + a.task.hashCode();
            a = a.next;
        }
        return result;
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList linkedTaskList = new LinkedTaskList();
        for(int i = 0; i < size; i++){
            linkedTaskList.add(getTask(i));
        }
        return linkedTaskList;
    }

    @Override
    public String toString() {
        String result = "";
        ListElement listElement = head;
        for(int i = 0; i < size; i++){
            result += listElement.task.toString()+"\n";
            listElement = listElement.next;
        }
        return result;
    }
}
