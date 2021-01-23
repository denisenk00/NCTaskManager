package ua.edu.sumdu.j2se.denysenko.tasks.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;


public class ArrayTaskList extends AbstractTaskList {
    private Task arrayList[] = new Task[5];
    private int size = 0;
    private int maxSize = 5;

    public void add(Task task){
        if(size == maxSize){
            Task newArrayList[] = new Task[2 * maxSize];
            for(int i = 0; i < size; i++){
                newArrayList[i] = arrayList[i];
            }
            arrayList = newArrayList;
            arrayList[size] = task;
            maxSize *= 2;
            size++;
        }
        else{
            arrayList[size] = task;
            size++;
        }
    }

    public boolean remove(Task task){
        int i = 0;
        while(i < size && arrayList[i] != task) i++;
        if(i == size) return false;
        else{
            for(int j = i + 1; j < size; i++, j++){
                arrayList[i] = arrayList[j];
            }
            size--;
            return true;
        }
    }

    public int size(){
        return size;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException{
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("The maximum index of an element in an array = "+ (size-1) + ", your index = " + index);
        }
        return arrayList[index];
    }

    @Override
    public Iterator <Task> iterator(){
        return new ArrayTaskListIterator();
    }

    private class ArrayTaskListIterator implements Iterator<Task> {
        int lastIndex = -1;
        @Override
        public boolean hasNext(){
            return (lastIndex + 1 < size);
        }
        @Override
        public Task next(){
            if(hasNext()) {
                Task a = arrayList[lastIndex + 1];
                lastIndex++;
                return a;
            }
            else return null;
        }
        @Override
        public void remove() throws IllegalStateException{
            if(lastIndex >= 0 && lastIndex < size) {
                ArrayTaskList.this.remove(arrayList[lastIndex]);
                lastIndex--;
            }
            else{
                throw new IllegalStateException("Method remove() should be called after next()");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        return size == that.size &&
                maxSize == that.maxSize &&
                Arrays.equals(arrayList, that.arrayList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size, maxSize);
        result = 31 * result + Arrays.hashCode(arrayList);
        return result;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "arrayList=" + Arrays.toString(arrayList) +
                ", size=" + size +
                ", maxSize=" + maxSize +
                '}';
    }

    @Override
    public ArrayTaskList clone() {
        ArrayTaskList arrayTaskList = new ArrayTaskList();
        for (Task a: this.arrayList) {
            arrayTaskList.add(a);
        };
        arrayTaskList.size = this.size;
        arrayTaskList.maxSize = this.maxSize;
        return arrayTaskList;
    }
    @Override
    public ListTypes.types getType(){
        return ListTypes.types.ARRAY;
    }

}
