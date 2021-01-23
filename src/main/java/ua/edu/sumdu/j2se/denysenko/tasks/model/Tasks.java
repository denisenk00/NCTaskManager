package ua.edu.sumdu.j2se.denysenko.tasks.model;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {
    public static Iterable <Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        Iterator <Task> it = tasks.iterator();
        LinkedTaskList list = new LinkedTaskList();
        while(it.hasNext()){
            Task temp = it.next();
            if(temp.nextTimeAfter(start) != null && !temp.nextTimeAfter(start).isAfter(end)){
                list.add(temp);
            }
        }
        return list;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar (Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        SortedMap<LocalDateTime, Set<Task>> sortedMap = new TreeMap<>();
        Iterable<Task> task = incoming(tasks, start, end);
        Iterator<Task> it = task.iterator();
        while(it.hasNext()){
            Task a = it.next();
            if(a.isRepeated()){
                LocalDateTime myDate = a.nextTimeAfter(start);
                while(!myDate.isAfter(a.getEndTime()) && !myDate.isAfter(end)){
                    if(sortedMap.containsKey(myDate)){
                        sortedMap.get(myDate).add(a);
                    }
                    else{
                        sortedMap.put(myDate, new HashSet<Task>());
                        sortedMap.get(myDate).add(a);
                    }
                    myDate = myDate.plusSeconds(a.getRepeatInterval());
                }
            }
            else{
                if(sortedMap.containsKey(a.getTime())){
                    sortedMap.get(a.getTime()).add(a);
                }
                else{
                    sortedMap.put(a.getTime(), new HashSet<Task>());
                    sortedMap.get(a.getTime()).add(a);
                }
            }
        }
        return sortedMap;
    }
}
