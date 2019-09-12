package thread;


import java.io.IOException;
import java.net.ServerSocket;

import controller.AdminController;
import view.AdminView;

public class AdminThread extends Thread {
	
	private ServerSocket serverSocket;
	
	public AdminThread(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	@Override
	public void run() {
		
		int scelta;
		
		do {
			scelta = AdminView.mainMenu();
			
			switch (scelta) {
			
				case 1:
					AdminController.addNewStudent();					
					break;
					
				case 2:
					AdminController.addNewExam();
					break;
					
				case 3:
					AdminController.showAllStudents();
					break;
				
				case 4:
					AdminController.showAllExams();
					break;
				
				case 5:
					AdminController.showAllIscrizioni();
					break;
	
				default:
					break;
					
			}
			
			
		}while(scelta > 0);
		
		try {
			serverSocket.close();
		} catch (IOException e) {
		}
		
	}

}
