package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.Key;

import model.ModelSifa;

public class ServerThread extends Thread {
	
	public static final int PORT = 4000;
	public static final String ADDRESS = "localhost";
	
	private ServerSocket serverSocket;
	private Key privateKeySifa;
	
	public ServerThread() {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Error server thread socket.");
		}
		privateKeySifa = ModelSifa.getPrivKeySifa();
	}
	
	public ServerSocket getSocket() {
		return serverSocket;
	}
	
	public void run() {
		
		while(true) {
			
			try {
				Socket socket = serverSocket.accept();
				ClientHandler ch = new ClientHandler(socket, privateKeySifa);
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
