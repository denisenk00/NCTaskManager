package ua.edu.sumdu.j2se.denysenko.tasks.controller;

import ua.edu.sumdu.j2se.denysenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Task;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Tasks;
import ua.edu.sumdu.j2se.denysenko.tasks.view.ConsoleView;

import java.time.LocalDateTime;
import java.util.Iterator;

public class Controller {
    private LinkedTaskList model;
    private ConsoleView view;

    public Controller(LinkedTaskList model, ConsoleView view) {
        this.model = model;
        this.view = view;
    }

    private LinkedTaskList filter(String s){
        try {
            String interval[] = s.split(" ", 2);
            Iterator<Task> it = Tasks.incoming(model, LocalDateTime.parse(interval[0]),
                    LocalDateTime.parse(interval[1])).iterator();
            LinkedTaskList list = new LinkedTaskList();
            while (it.hasNext()) {
                list.add(it.next());
            }
            return list;
        }
        catch (Exception e){
            System.err.println("Entered data is incorrect");
        }
        return null;
    }

    private void addTask(String s){
        try {
            if (s.startsWith("1")) {
                String parameters[] = s.split(" ", 6);
                Task task = new Task(parameters[1], LocalDateTime.parse(parameters[2]), LocalDateTime.parse(parameters[3]),
                        Integer.parseInt(parameters[4]));
                if (s.endsWith("1")) task.setActive(true);
                model.add(task);
            } else {
                String parameters[] = s.split(" ", 4);
                Task task = new Task(parameters[1], LocalDateTime.parse(parameters[2]));
                if (s.endsWith("1")) task.setActive(true);
                model.add(task);
            }
        }
        catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }
        catch (Exception e){
            System.err.println("Entered data is incorrect");
        }
    }

    private void editTask(Task task){
        boolean repeated = task.isRepeated();
        int back = 0;
        while(back != 2){
            view.editField(repeated, task.toString());
            int input = view.getUserInput();
            switch (input){
                case 0:
                    task.setTitle(view.getTitle());
                    break;
                case 1:
                    String param[] = view.inputTime(repeated).split(" ", 3);
                    if(repeated){
                        task.setTime(LocalDateTime.parse(param[0]), LocalDateTime.parse(param[1]), Integer.parseInt(param[2]));
                    }
                    else{
                        task.setTime(LocalDateTime.parse(param[0]));
                    }
                    break;
                case 2:
                    back = 2;
                    break;
            }
        }
    }

    public void mainMenu(){
        int exit = 0;
        while(exit != 3){
            view.printMainMenu();
            int input = view.getUserInput();
            switch (input){
                case 0:
                    String s = view.inputTask();
                    addTask(s);
                    break;
                case 1:
                    view.printCalendar(Tasks.calendar(model, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1)));
                    break;
                case 2:
                    listMenu(model);
                    break;
                case 3:
                    exit = 3;
                    break;
            }
        }
    }

    private void listMenu(LinkedTaskList list){
        int exit = 1;
        while(exit != 0){
            view.printListMenu();
            view.printTaskList(list);
            int input = view.getUserInput();
            switch (input){
                case 0:
                    exit = 0;
                    break;
                case 1:
                    listMenu(filter(view.getInterval()));
                    break;
            }
            if(input > 1 && input < list.size() + 2) {
                taskMenu(list.getTask(input - 2));
            }
        }
    }

    private void taskMenu(Task inputTask){
        Task task = model.getTask(model.getTaskIndex(inputTask));
        view.printTaskInfo(inputTask);
        view.printTasksOption(task.isActive());
        int input = view.getUserInput();
        switch (input){
            case 0:
                editTask(task);
                break;
            case 1:
                if(task.isActive()) task.setActive(false);
                else task.setActive(true);
                break;
            case 2:
                model.remove(task);
                break;
        }
    }

}
