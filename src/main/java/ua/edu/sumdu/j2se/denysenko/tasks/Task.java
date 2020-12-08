package ua.edu.sumdu.j2se.denysenko.tasks;

import java.util.Objects;

public class Task implements Cloneable{
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    public Task(){}
    public Task(String title, int time) throws IllegalArgumentException{
        if(time < 0){
            throw new IllegalArgumentException("Time can not be negative, your time = " + time);
        }
        this.title = title;
        this.time = time;
        start = time;
        end = time;
        interval = 0;
    }
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException{
        if(interval <= 0){
            throw new IllegalArgumentException("The interval must be greater than zero, your interval = " + interval);
        }
        time = start;
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active=active;
    }
    public int getTime(){
        return time;
    }
    public void setTime(int time){
        this.time = time;
        start = time;
        end = time;
    }
    public int getStartTime(){
        return start;
    }
    public int getEndTime(){
        return end;
    }
    public int getRepeatInterval(){
        if(start >= end) return 0;
        else return interval;
    }
    public void setTime(int start, int end, int interval){
        time = start;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }
    public boolean isRepeated(){
        if(start < end && interval > 0) return true;
        else return false;
    }
    public int nextTimeAfter(int current){
        if(current >= 0 && active){
            if(isRepeated()){
                if(start > current) return start;
                else if(start <= current && end >= current){
                    int theNextTime = start;
                    while(theNextTime <= current){
                        theNextTime += interval;
                    }
                    if(theNextTime <= end) return theNextTime;
                    else return -1;
                }
                else return -1;
            }
            else{
                if(time > current) return time;
                else return -1;
            }
        }
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time &&
                start == task.start &&
                end == task.end &&
                interval == task.interval &&
                active == task.active &&
                title.equals(task.title);
    }

    @Override
    public int hashCode() {
        int res = 1;
        res = 31 * res + time;
        res = 31 * res + start;
        res = 31 * res + end;
        res = 31 * res + interval;
        res = 31 * res + title.hashCode();
        if(active){
            res = 31 * res + 1;
        }
        else{
            res = 31 * res;
        }
        return res;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task task = new Task();
        task.start = this.start;
        task.active = this.active;
        task.end = this.end;
        task.title = this.title;
        task.time = this.time;
        task.interval = this.interval;
        return task;
    }
}
