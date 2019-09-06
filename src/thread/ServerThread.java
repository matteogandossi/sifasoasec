package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread extends Thread {
	
	public static final int PORT = 4000;
	public static final String ADDRESS = "localhost";
	
	private ServerSocket serverSocket;
	
	public ServerThread() {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Error server thread socket.");
		}
	}
	
	public ServerSocket getSocket() {
		return serverSocket;
	}
	
	public void run() {
		
		while(true) {
			
			try {
				Socket socket = serverSocket.accept();
				ClientHandler ch = new ClientHandler(socket);
				ch.start();
			} catch (SocketException e) {
				return;
			} catch (IOException e) {
				System.out.println("Problem with accept of ServerThread");
				System.exit(0);
			}
		}
	}

}
