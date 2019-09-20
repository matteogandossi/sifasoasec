package main;

import sifathread.AdminThread;
import sifathread.ServerThread;

public class ServerMain {

	public static void main(String[] args) {
		
		ServerThread serverThread = new ServerThread();
		AdminThread adm = new AdminThread(serverThread.getSocket());
		adm.start();
		serverThread.start();
	}

}
