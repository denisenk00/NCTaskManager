package ua.edu.sumdu.j2se.denysenko.tasks.view;

import ua.edu.sumdu.j2se.denysenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ConsoleView {

    public static void clearScreen() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public void printTaskList(AbstractTaskList list){
        if(list != null) {
            Iterator<Task> it = list.iterator();
            int i = 2;
            while (it.hasNext()) {
                Task temp = it.next();
                if (temp.isActive() && temp.nextTimeAfter(LocalDateTime.now()) != null) {
                    System.out.println(i + " " + temp.getTitle() + "  Date:" + temp.nextTimeAfter(LocalDateTime.now()).toLocalDate()
                            + "  Time:" + temp.nextTimeAfter(LocalDateTime.now()).toLocalTime() + "  Active:" + temp.isActive());
                } else {
                    if (!temp.isActive()) {
                        System.out.println(i + " " + temp.getTitle() + " Active: " + temp.isActive());
                    } else {
                        System.out.println(i + " " + temp.getTitle() + " Time is over");
                    }
                }
                i++;
            }
        }
        else{
            System.out.println("No tasks found");
        }
    }
    public void printTaskInfo(Task task){
        System.out.println(task.toString());
    }
    public void printCalendar(SortedMap<LocalDateTime, Set<Task>> map){
        clearScreen();
        System.out.println("Calendar for week");
        LocalDate oldKey = LocalDate.of(2020, 01, 10);
        for (Map.Entry<LocalDateTime, Set<Task>> entry : map.entrySet()) {
            if(!oldKey.equals(entry.getKey().toLocalDate())) {
                System.out.println("\nDate" + " " + entry.getKey().toLocalDate().toString() + ":");
            }
            entry.getValue().stream().forEach(a -> {
                        System.out.println("\t" + a.getTitle() + "  Time:" + a.nextTimeAfter(LocalDateTime.now()).toLocalTime());
            });
            oldKey = entry.getKey().toLocalDate();
        }
        System.out.println("\nIf you want to go back, press any key...");
        getUserInput();
    }
    public void printTasksOption(boolean active){
        clearScreen();
        System.out.println("0 edit");
        if(active) System.out.println("1 deactivate");
        else System.out.println("1 activate");
        System.out.println("2 delete");
        System.out.println("3 go back");
    }
    public void printMainMenu(){
        clearScreen();
        System.out.println("0 add task\n1 calendar for week\n2 task list\n3 exit");
    }
    public void printListMenu(){
        clearScreen();
        System.out.println("All tasks:\n0 go back\n1 filter tasks");
    }
    public int getUserInput(){
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()){
            scanner.next();
        }
        return scanner.nextInt();
    }
    public String getTitle(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new title without spaces: ");
        String s = scanner.nextLine();
        return s;
    }

    public String inputTask(){
        clearScreen();
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title without spaces: ");
        String title = scanner.nextLine();
        System.out.println("The task is repeated? 1 if so, otherwise 0: ");
        int repeated = scanner.nextInt();
        builder.append(repeated + " " + title + " ");
        scanner.nextLine();
        String pattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}";
        String pattern2 = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
        if (repeated == 1) {
            System.out.println("Enter start time in format 2007-12-03T10:15:30: ");
            String s = scanner.nextLine();
            while (!s.matches(pattern) && !s.matches(pattern2)) {
                s = scanner.nextLine();
            }
            builder.append(s + " ");
            System.out.println("Enter end time in format 2007-12-03T10:15:30: ");
            s = scanner.nextLine();
            while (!s.matches(pattern) && !s.matches(pattern2)) {
                s = scanner.nextLine();
            }
            builder.append(s + " ");
            System.out.println("Enter interval in seconds: ");
            while (!scanner.hasNextInt()) {
                scanner.next();
            }
            builder.append(scanner.nextInt() + " ");
            scanner.nextLine();
        } else {
            System.out.println("Enter time in format 2007-12-03T10:15:30: ");
            String s = scanner.nextLine();
            while (!s.matches(pattern) && !s.matches(pattern2)) {
                s = scanner.nextLine();
            }
            builder.append(s + " ");
        }
        System.out.println("Do you want to activate the task? 1 if so, otherwise 0: ");
        builder.append(scanner.nextInt());
        return builder.toString();
    }
    public String getInterval(){
        clearScreen();
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        String pattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}";
        String pattern2 = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
        System.out.println("Enter start time in format 2007-12-03T10:15:30: ");
        String s = scanner.nextLine();
        while(!s.matches(pattern) && !s.matches(pattern2)) {
            s = scanner.nextLine();
        }
        builder.append(s + " ");
        System.out.println("Enter end time in format 2007-12-03T10:15:30: ");
        s = scanner.nextLine();
        while(!s.matches(pattern) && !s.matches(pattern2)) {
            s = scanner.nextLine();
        }
        builder.append(s);
        return builder.toString();
    }
    public String inputTime(boolean repeated){
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        String pattern = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}";
        String pattern2 = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
        if(repeated) {
            System.out.println("Enter start time in format 2007-12-03T10:15:30: ");
            String s = scanner.nextLine();
            while(!s.matches(pattern) && !s.matches(pattern2)) {
                s = scanner.nextLine();
            }
            builder.append(s + " ");
            System.out.println("Enter end time in format 2007-12-03T10:15:30: ");
            s = scanner.nextLine();
            while(!s.matches(pattern) && !s.matches(pattern2)) {
                s = scanner.nextLine();
            }
            builder.append(s + " ");
            System.out.println("Enter interval in seconds: ");
            while (!scanner.hasNextInt()){
                scanner.next();
            }
            builder.append(scanner.nextInt() + " ");
            scanner.nextLine();
        }
        else{
            System.out.println("Enter time in format 2007-12-03T10:15:30: ");
            String s = scanner.nextLine();
            while(!s.matches(pattern) && !s.matches(pattern2)) {
                s = scanner.nextLine();
            }
            builder.append(s + " ");
        }
        return builder.toString();
    }

    public void editField(boolean repeated, String info){
        clearScreen();
        System.out.println(info);
        System.out.println("What do you want to change?\n0 Title");
        if(repeated){
            System.out.println("1 Start time, End time,  Interval\n2 go back");
        }
        else{
            System.out.println("1 Time\n2 go back");
        }
    }
}
