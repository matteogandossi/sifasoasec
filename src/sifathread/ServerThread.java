package sifathread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.Key;

import crypt.rsa.RSAKeyConverter;
import exception.MissingKeyException;
import model.ModelPublic;
import model.ModelSifa;
import server.SifaPath;

public class ServerThread extends Thread {
	
	
	private ServerSocket serverSocket;
	private Key privateKeySifa, publicKeyMail;
	
	public ServerThread() {
		try {
			serverSocket = new ServerSocket(SifaPath.PORT);
		} catch (IOException e) {
			System.out.println("Error server thread socket.");
		}
		privateKeySifa = ModelSifa.getPrivKeySifa();
		try {
			publicKeyMail = RSAKeyConverter.getPublicKeyFromString(ModelPublic.getPublicKey("mail"));
		} catch (MissingKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ServerSocket getSocket() {
		return serverSocket;
	}
	
	public void run() {
		
		while(true) {
			
			try {
				Socket socket = serverSocket.accept();
				ClientHandler ch = new ClientHandler(socket, privateKeySifa, publicKeyMail);
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
