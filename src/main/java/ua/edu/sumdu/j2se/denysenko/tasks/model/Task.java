package ua.edu.sumdu.j2se.denysenko.tasks.model;


import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable{
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;
    public Task(){}

    public Task(String title, LocalDateTime time) throws IllegalArgumentException{
        if(title == null || time == null){
            throw new IllegalArgumentException("Time can not be negative, your time = " + time);//!!!
        }
        this.title = title;
        this.time = time;
        start = time;
        end = time;
        interval = 0;
    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException{
        if(interval <= 0 || title == null || start == null || end == null){
            throw new IllegalArgumentException("The interval must be greater than zero, your interval = " + interval);
        }
        if(start.isAfter(end)){
            throw new IllegalArgumentException("The beginning must be earlier than the end");
        }
        time = cloneLocalDateTime(start);
        this.title = title;
        this.start = cloneLocalDateTime(start);
        this.end = cloneLocalDateTime(end);
        this.interval = interval;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public boolean isActive(){
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public LocalDateTime getTime(){
        return time;
    }

    public void setTime(LocalDateTime time){
        this.time = cloneLocalDateTime(time);
        start = cloneLocalDateTime(time);
        end = cloneLocalDateTime(time);
    }

    public LocalDateTime getStartTime(){
        return start;
    }

    public LocalDateTime getEndTime(){
        return end;
    }

    public int getRepeatInterval(){
        if(isRepeated()) return interval;
        else return 0;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval){
        time = cloneLocalDateTime(start);
        this.start = cloneLocalDateTime(start);
        this.end = cloneLocalDateTime(end);
        this.interval = interval;
    }

    public boolean isRepeated(){
        if(start.isEqual(end) || interval == 0) return false;
        else return true;
    }

    public LocalDateTime nextTimeAfter(LocalDateTime current){
        if(active && current != null){
            if(isRepeated()){
                if(start.isAfter(current)) return start;
                else if(!end.isBefore(current)){
                    LocalDateTime theNextTime = cloneLocalDateTime(start);
                    while(theNextTime.isBefore(current) || theNextTime.isEqual(current)){
                        theNextTime = theNextTime.plusSeconds(interval);
                    }
                    if(!theNextTime.isAfter(end)) return theNextTime;
                    else return null;
                }
                else return null;
            }
            else{
                if(time.isAfter(current)) return time;
                else return null;
            }
        }
        else return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return interval == task.interval &&
                active == task.active &&
                title.equals(task.title) &&
                time.equals(task.time) &&
                start.equals(task.start) &&
                end.equals(task.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active);
    }

    @Override
    public String toString() {
        return "Title: " + title +
                "\nTime: " + time +
                "\nStart: " + start +
                "\nEnd: " + end +
                "\nInterval: " + interval +
                "\nActive: " + active;
    }

    @Override
    public Task clone() {
        Task task = new Task();
        task.start = cloneLocalDateTime(this.start);
        task.active = this.active;
        task.end = cloneLocalDateTime(this.end);
        task.title = this.title;
        task.time = cloneLocalDateTime(this.time);
        task.interval = this.interval;
        return task;
    }

    private LocalDateTime cloneLocalDateTime(LocalDateTime time){
        return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond(), time.getNano());
    }

}
