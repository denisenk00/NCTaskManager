package ua.edu.sumdu.j2se.denysenko.tasks;

public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    public Task(){}
    public Task(String title, int time) throws IllegalArgumentException{
        if(time<0){
            throw new IllegalArgumentException("Time can not be negative, your time = " + time);
        }
        this.title=title;
        this.time=time;
        start=time;
        end=time;
        interval=0;
    }
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException{
        if(interval<=0){
            throw new IllegalArgumentException("The interval must be greater than zero, your interval = " + interval);
        }
        time=start;
        this.title=title;
        this.start=start;
        this.end=end;
        this.interval=interval;
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
        this.time=time;
        start=time;
        end=time;
    }
    public int getStartTime(){
        return start;
    }
    public int getEndTime(){
        return end;
    }
    public int getRepeatInterval(){
        if(start>=end) return 0;
        else return interval;
    }
    public void setTime(int start, int end, int interval){
        time=start;
        this.start=start;
        this.end=end;
        this.interval=interval;
    }
    public boolean isRepeated(){
        if(start<end && interval>0) return true;
        else return false;
    }
    public int nextTimeAfter(int current){
        if(current>=0 && active){
            if(isRepeated()){
                if(start>current) return start;
                else if(start<=current && end>=current){
                    int theNextTime=start;
                    while(theNextTime<=current){
                        theNextTime+=interval;
                    }
                    if(theNextTime<=end) return theNextTime;
                    else return -1;
                }
                else return -1;
            }
            else{
                if(time>current) return time;
                else return -1;
            }
        }
        else return -1;
    }
}
