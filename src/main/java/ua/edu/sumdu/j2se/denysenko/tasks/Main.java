package ua.edu.sumdu.j2se.denysenko.tasks;


import ua.edu.sumdu.j2se.denysenko.tasks.controller.Controller;
import ua.edu.sumdu.j2se.denysenko.tasks.controller.NotificationController;
import ua.edu.sumdu.j2se.denysenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.denysenko.tasks.view.ConsoleView;
import ua.edu.sumdu.j2se.denysenko.tasks.view.NotificationView;

import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String []args) {
		LinkedTaskList model = new LinkedTaskList();
		ConsoleView view = new ConsoleView();
		NotificationView notificationView = new NotificationView();
		File data = new File("B://Lessons//NC//Main_Project//NCTaskManager//src//main//java//ua//edu//sumdu//j2se//denysenko//tasks", "data.txt");
		TaskIO.readBinary(model, data);
		Controller controller = new Controller(model, view);
		NotificationController notificationController = new NotificationController(model, notificationView);
		notificationController.startNotify();
		controller.mainMenu();
		data.delete();
		try {
			data.createNewFile();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		TaskIO.writeBinary(model, data);
	}
}
