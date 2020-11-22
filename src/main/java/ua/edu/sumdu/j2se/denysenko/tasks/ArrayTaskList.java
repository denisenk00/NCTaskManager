package ua.edu.sumdu.j2se.denysenko.tasks;

public class ArrayTaskList {
    private Task arrayList[]=new Task[5];
    private int size=0;
    private int maxSize=5;
    public void add(Task task){
        if(size==maxSize){
            Task newArrayList[]=new Task[2*maxSize];
            for(int i=0; i<size; i++){
                newArrayList[i]=arrayList[i];
            }
            arrayList=newArrayList;
            arrayList[size]=task;
            size++;
        }
        else{
            arrayList[size]=task;
            size++;
        }
    }
    public boolean remove(Task task){
        int i=0;
        while(i<size && arrayList[i]!=task) i++;
        if(i==size) return false;
        else{
            for(int j=i+1; j<size; i++, j++){
                arrayList[i]=arrayList[j];
            }
            size--;
            return true;
        }
    }
    public int size(){
        return size;
    }
    public Task getTask(int index) throws IndexOutOfBoundsException{
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException("The maximum index of an element in an array = "+ (size-1) + ", your index = " + index);
        }
        return arrayList[index];
    }
    public ArrayTaskList incoming(int from, int to){
        ArrayTaskList arrayTaskList=new ArrayTaskList();
        for(int i=0; i<size; i++){
            int time=arrayList[i].nextTimeAfter(from);
            if(time<=to && time!=-1)  arrayTaskList.add(arrayList[i]);
        }
        return arrayTaskList;
    }
}
