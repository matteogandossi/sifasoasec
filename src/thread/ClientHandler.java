package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.Key;
import java.util.ArrayList;

import javax.crypto.SealedObject;

import base.Exam;
import base.Student;
import base.StudentComplete;
import exception.MissingKeyException;
import exception.StudentNotFoundException;
import messages.C2SMessage;
import messages.Question;
import messages.S2CMessage;
import model.ModelSifa;
import model.Utils;
import rsa.EncryptDecrypt;

public class ClientHandler extends Thread {
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private boolean logged;
	private Key clientKey;
	private String matricola;

	public ClientHandler(Socket socket) {
		logged = false;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error stream");
		}
	}
	
	@Override
	public void run() {
		
		SealedObject inputSO, outputSO;
		C2SMessage question;
		S2CMessage answer;
		
		while(true) {
			
			if(!logged) { //messaggi in chiaro
				
				try {
					question = (C2SMessage) ois.readObject();
					answer = notLoggedMessage(question);
					oos.writeObject(answer);
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Error reading obj");
				}				
				
			}
			else { //messaggi criptati
				
				try {
					inputSO =  (SealedObject) ois.readObject();
					question = (C2SMessage) EncryptDecrypt.decryptObject(inputSO, null); /// DA INSERIRE
					answer = loggedMessage(question);
					outputSO = EncryptDecrypt.encryptObject(answer, clientKey);
					oos.writeObject(outputSO);
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Error reading obj");
				}	
			}
			
			
			
		}
		
	}
	
	private S2CMessage notLoggedMessage(C2SMessage question) {
		
		if(question.getType() == Question.LOGIN) {
			
			try {
				StudentComplete student = Utils.getStudentByMatricola(question.getMatricola());
				logged = true;
				clientKey = Utils.getPublicKey(student.getMatricola());
				matricola = student.getMatricola();
				return S2CMessage.createInitMessage(student);
				
			} catch (StudentNotFoundException | MissingKeyException e) {
				return S2CMessage.createFailMessage("Matricola inesistente.");
			}
		}
		
		return S2CMessage.createFailMessage("Devi loggarti prima di effettuare qualsiasi operazione.");
		
	}
	
	private S2CMessage loggedMessage(C2SMessage question) {
		
		ArrayList<Exam> list;
		boolean result;
		
		if(!question.getMatricola().equals(matricola))
			return S2CMessage.createFailMessage("Matricola non conforme.");
		
		switch (question.getType()) {
		
			case Question.LOGIN:
				return S2CMessage.createFailMessage("Sei già loggato.");
			
			case Question.LISTA_ISCRIZIONE:
				list = ModelSifa.getAllEsamiIscrivibili(question.getMatricola());
				return S2CMessage.createEsamiDisponibiliMessage(list);
			
			case Question.LISTA_CANCELLAZIONE:
				list = ModelSifa.getAllEsamiIscritti(question.getMatricola());
				return S2CMessage.createEsamiCancellabiliMessage(list);
			
			case Question.ISCRIZIONE:
				result = ModelSifa.insertNewIscrizione(question.getMatricola(), question.getCodice());
				if(result)
					return S2CMessage.createOkMessage();
				return S2CMessage.createFailMessage("Iscrizione non effettuata.");
			
			case Question.CANCELLAZIONE:
				result = ModelSifa.deleteIscrizione(question.getMatricola(), question.getCodice());
				if(result)
					return S2CMessage.createOkMessage();
				return S2CMessage.createFailMessage("Cancellazione non effettuata.");
			
			case Question.LOGOUT:
				logged= false;
				return S2CMessage.createOkMessage();				
				
			default:
				return S2CMessage.createFailMessage("Operazione sconosciuta.");	
			
		}
	}	
	

}
