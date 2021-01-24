package ua.edu.sumdu.j2se.denysenko.tasks.view;

import ua.edu.sumdu.j2se.denysenko.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public interface View {
    void printTaskList(Iterable<Task> list);
    void printCalendar(SortedMap<LocalDateTime, Set<Task>> map);
    String inputTask();
    void printMainMenu();
    void printListMenu();
    void printEditMenu(boolean repeated, String info);
}
