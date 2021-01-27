package ua.edu.sumdu.j2se.denysenko.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.denysenko.tasks.Main;
import ua.edu.sumdu.j2se.denysenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Task;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Tasks;
import ua.edu.sumdu.j2se.denysenko.tasks.view.ConsoleView;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Iterator;

public class Controller {
    private AbstractTaskList model;
    private ConsoleView view;
    private final static Logger logger = Logger.getLogger(Controller.class);

    public Controller(AbstractTaskList model, ConsoleView view) {
        this.model = model;
        this.view = view;
    }

    private LinkedTaskList filter(String s){
        try {
            String interval[] = s.split(" ", 2);
            LocalDateTime from = LocalDateTime.parse(interval[0]);
            LocalDateTime to = LocalDateTime.parse(interval[1]);
            if(from.isAfter(to)){
                logger.warn("Time period entered incorrectly");
            }
            else {
                Iterator<Task> it = Tasks.incoming(model, from, to).iterator();
                LinkedTaskList list = new LinkedTaskList();
                while (it.hasNext()) {
                    list.add(it.next());
                }
                logger.info("Task list has been filtered");
                return list;
            }
        }
        catch (Exception e){
            logger.error("Entered data is incorrect");
        }
        return null;
    }

    private void addTask(String s){
        try {
            if (s.startsWith("1")) {
                String parameters[] = s.split(" ", 6);
                String title = parameters[1];
                LocalDateTime start = LocalDateTime.parse(parameters[2]);
                LocalDateTime end = LocalDateTime.parse(parameters[3]);
                int interval = Integer.parseInt(parameters[4]);
                Task task = new Task(title, start, end, interval);
                if (s.endsWith("1")) task.setActive(true);
                if(end.isBefore(LocalDateTime.now())){
                    view.printTaskMessage();
                }
                else{
                    model.add(task);
                }
            } else {
                String parameters[] = s.split(" ", 4);
                String title = parameters[1];
                LocalDateTime time = LocalDateTime.parse(parameters[2]);
                Task task = new Task(title, time);
                if (s.endsWith("1")) task.setActive(true);
                if(time.isBefore(LocalDateTime.now())){
                    view.printTaskMessage();
                }
                else{
                    model.add(task);
                }
            }
            logger.info("Added new task");
        }
        catch (Exception e){
            logger.error("Entered data is incorrect");
        }
    }

    private void editTask(Task task){
        boolean repeated = task.isRepeated();
        int input = 0;
        while(input != 2){
            view.printEditMenu(repeated, task.toString());
            input = view.getUserInput();
            logger.info("Getting user input. Result: " + input);
            switch (input){
                case 0:
                    task.setTitle(view.getTitle());
                    logger.info("Title was changed");
                    break;
                case 1:
                    String param[] = view.inputTime(repeated).split(" ", 3);
                    try {
                        if (repeated) {
                            LocalDateTime start = LocalDateTime.parse(param[0]);
                            LocalDateTime end = LocalDateTime.parse(param[1]);
                            int interval = Integer.parseInt(param[2]);
                            task.setTime(start, end, interval);
                        } else {
                            LocalDateTime time = LocalDateTime.parse(param[0]);
                            task.setTime(time);
                        }
                        logger.info("Time was changed");
                    }
                    catch (DateTimeParseException e){
                        logger.error("String was not parsed to LocalDateTime");
                    }
                    break;
            }
        }
    }

    public void mainMenu(){
        int input = 0;
        while(input != 3){
            view.printMainMenu();
            input = view.getUserInput();
            logger.info("Getting user input. Result: " + input);
            switch (input){
                case 0:
                    String s = view.inputTask();
                    logger.info("Got info about new task");
                    addTask(s);
                    Main.startNotify();
                    break;
                case 1:
                    logger.info("Go to calendar for week");
                    calendarMenu();
                    break;
                case 2:
                    logger.info("Go to list all tasks");
                    listMenu(model);
                    break;
                case 3:
                    Main.saveChanges(model);
                    logger.info("Terminating the program ============");
                    System.exit(0);
                    break;
            }
        }
    }

    private void listMenu(AbstractTaskList list){
        int input = 1;
        while(input != 0){
            view.printListMenu();
            view.printTaskList(list);
            input = view.getUserInput();
            logger.info("Getting user input. Result: " + input);
            if(input == 1){
                logger.info("Go to filter tasks");
                String interval = view.getInterval();
                if(filter(interval) != null) {
                    listMenu(filter(interval));
                }
            }
            else if(input > 1 && input < list.size() + 2) {
                logger.info("Selected a task");
                taskMenu(list.getTask(input - 2));
            }
        }
    }

    private void taskMenu(Task inputTask){
        Task task = model.getTask(model.getTaskIndex(inputTask));
        view.printTaskInfo(inputTask);
        view.printTasksOption(task.isActive());
        int input = view.getUserInput();
        logger.info("Getting user input. Result: " + input);
        switch (input){
            case 0:
                logger.info("Go to edit task");
                editTask(task);
                Main.startNotify();
                break;
            case 1:

                if(task.isActive()){
                    task.setActive(false);
                    logger.info("Task has been deactivated");
                }
                else{
                    task.setActive(true);
                    logger.info("Task has been activated");
                }
                Main.startNotify();
                break;
            case 2:
                model.remove(task);
                logger.info("Task has been deleted");
                Main.startNotify();
                break;
        }
    }

    private void calendarMenu(){
        int input = 0;
        while(input != 1){
            view.printCalendar(Tasks.calendar(model, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1)));
            Iterable<Task> iterable = Tasks.incoming(model,LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));
            Iterator<Task> it = iterable.iterator();
            System.out.println("\n1 go back");
            view.printTaskList(iterable);
            input = view.getUserInput();
            logger.info("Getting user input. Result: " + input);
            if(input > 1) {
                Task a;
                int index = 0;
                while (it.hasNext()){
                    a = it.next();
                    if(index == input-2){
                        logger.info("Selected a task");
                        taskMenu(model.getTask(model.getTaskIndex(a)));
                    }
                    index++;
                }
            }
        }
    }

}
