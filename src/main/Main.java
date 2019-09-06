package main;

import thread.AdminThread;

public class Main {

	public static void main(String[] args) {
		
		AdminThread adm = new AdminThread();
		adm.start();

	}

}
