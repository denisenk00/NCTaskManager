package ua.edu.sumdu.j2se.denysenko.tasks.controller;

import ua.edu.sumdu.j2se.denysenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Task;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Tasks;
import ua.edu.sumdu.j2se.denysenko.tasks.view.NotificationView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class NotificationController {
    LinkedTaskList model;
    NotificationView view;

    public NotificationController(LinkedTaskList model, NotificationView view) {
        this.model = model;
        this.view = view;
    }

    public void startNotify(){
        Set set = Tasks.calendar(model, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1)).entrySet();
        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Notification");
                timer.cancel();
            }
        };
        Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.YEAR, setTas.)
        //timer.schedule();
    }
}
