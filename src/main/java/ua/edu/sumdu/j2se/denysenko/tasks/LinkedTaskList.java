package ua.edu.sumdu.j2se.denysenko.tasks;

public class LinkedTaskList {
    private class ListElement{
        Task task;
        ListElement next;
    }
    private ListElement head;
    private ListElement tail;
    private int size=0;
    public void add(Task task){
        ListElement x = new ListElement();
        x.task = task;
        if(head==null){
            head=x;
            tail=x;
        }
        else{
            tail.next=x;
            tail=x;
        }
        size++;
    }
    public boolean remove(Task task){
        if(head==null) return false;
        if(head.task==task){
            if(head==tail){
                head=null;
                tail=null;
                size=0;
                return true;
            }
            else{
                head=head.next;
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
        int i=0;
        ListElement x=head;
        while(i<index){
            x=x.next;
            i++;
        }
        return x.task;
    }
    public LinkedTaskList incoming(int from, int to){
        LinkedTaskList linkedTaskList=new LinkedTaskList();
        ListElement x=head;
        while(x.next!=null){
            int time = x.task.nextTimeAfter(from);
            if(time<=to && time!=-1)  linkedTaskList.add(x.task);
            x=x.next;
        }
        return linkedTaskList;
    }
}
