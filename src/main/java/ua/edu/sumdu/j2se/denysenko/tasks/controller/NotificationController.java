package ua.edu.sumdu.j2se.denysenko.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.denysenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Task;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Tasks;
import ua.edu.sumdu.j2se.denysenko.tasks.model.TimerTask;

import java.time.LocalDateTime;
import java.util.*;

public class NotificationController {
    private LinkedTaskList model;
    private List<Timer> timerList = new LinkedList<>();
    private final static Logger logger = Logger.getLogger(NotificationController.class);

    public NotificationController(LinkedTaskList model) {
        this.model = model;
    }

    public void startNotify(){
        clearTimer();
        logger.info("Timers were canceled");
        SortedMap<LocalDateTime, Set<Task>> map = Tasks.calendar(model, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));
        List<TimerTask> timerTaskList = new LinkedList<>();
        int i = 0;
        for(Map.Entry<LocalDateTime, Set<Task>> entry : map.entrySet()){
            Calendar calendar = Calendar.getInstance();
            calendar.set(entry.getKey().getYear(), entry.getKey().getMonthValue()-1, entry.getKey().getDayOfMonth(),
                    entry.getKey().getHour(), entry.getKey().getMinute(), entry.getKey().getSecond());
            timerTaskList.add(new TimerTask(entry.getValue()));
            Timer timer = new Timer();
            timer.schedule(timerTaskList.get(i), calendar.getTime());
            timerList.add(timer);
            i++;
        }
        logger.info("Created new timers");
    }

    public void clearTimer(){
        Iterator<Timer> it = timerList.listIterator();
        while (it.hasNext()){
            it.next().cancel();
        }
    }
}
