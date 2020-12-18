package ua.edu.sumdu.j2se.denysenko.tasks;



public class Main {
	public static void main(String []args){
		ArrayTaskList a = new ArrayTaskList();
		a.add(new Task("FSa", 25));
		a.add(new Task("gtfhf", 50));
		a.add(new Task("sffs", 80));
		System.out.println(a.incoming(10, 60).toString());
	}
}
