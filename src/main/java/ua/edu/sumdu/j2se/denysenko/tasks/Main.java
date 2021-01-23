package ua.edu.sumdu.j2se.denysenko.tasks;


import ua.edu.sumdu.j2se.denysenko.tasks.controller.Controller;
import ua.edu.sumdu.j2se.denysenko.tasks.controller.NotificationController;
import ua.edu.sumdu.j2se.denysenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.denysenko.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.denysenko.tasks.view.ConsoleView;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;


public class Main {
	private static File data;
	private static NotificationController notificationController;
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String []args) {
	    logger.info("Start of the program ===================");
		LinkedTaskList model = new LinkedTaskList();
		ConsoleView view = new ConsoleView();
		data = new File("B://Lessons//NC//Main_Project//NCTaskManager//src//main//resources", "data.txt");
		TaskIO.readBinary(model, data);
		logger.info("Data from the file data.txt has been read");
		Controller controller = new Controller(model, view);
		notificationController = new NotificationController(model);
		startNotify();
		logger.info("Go to main menu");
		controller.mainMenu();
	}

	public static void saveChanges(AbstractTaskList model){
		data.delete();
		logger.info("File data.txt has been deleted");
		try {
			data.createNewFile();
			logger.info("New file data.txt created");
		}
		catch (IOException e) {
			logger.error("IOException, while creating new file");
			System.err.println("IOException, while creating new file");
		}
		TaskIO.writeBinary(model, data);
		logger.info("Data has been written to a file data.txt");
	}

	public static void startNotify(){
		notificationController.startNotify();
		logger.info("Restarted the notification subsystem");
	}

}
