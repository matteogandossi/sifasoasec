package sifathread;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Key;
import java.util.ArrayList;

import javax.crypto.SealedObject;

import base.Exam;
import base.StudentComplete;
import crypt.aes.AESEncryptDecrypt;
import crypt.rsa.RSAEncryptDecrypt;
import exception.MissingKeyException;
import exception.StudentNotFoundException;
import messages.C2SMessage;
import messages.Question;
import messages.S2CMessage;
import model.ModelSifa;
import model.Utils;

public class ClientHandler extends Thread {
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private boolean logged;
	private Key clientKey, privateKeySifa;
	private String matricola;

	public ClientHandler(Socket socket, Key privateKeySifa) {
		logged = false;
		this.privateKeySifa = privateKeySifa;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error stream");
		}
	}
	
	@Override
	public void run() {
		
		SealedObject inputSealedAESKey, inputSealedQuestion;
		SealedObject outputSealedAESKey, outputSealedAnswer;
		Key decryptedAESKey;
		AESEncryptDecrypt aesConverter;
		
		C2SMessage question;
		S2CMessage answer;
		
		while(true) {
			
			if(!logged) { //messaggi in chiaro
				
				try {
					question = (C2SMessage) ois.readObject();
					answer = notLoggedMessage(question);
					oos.writeObject(answer);
				} catch (EOFException e) {
					return; //closed socket
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Error reading obj (chiaro clienthandler)");
					return;
				}
				
			}
			else { //messaggi criptati
				
				try {
					
					//read sealed (by RSA) AES key and convert					
					inputSealedAESKey = (SealedObject) ois.readObject();
					decryptedAESKey = (Key) RSAEncryptDecrypt.decryptObject(inputSealedAESKey, privateKeySifa);
					
					//create AES object					
					aesConverter = new AESEncryptDecrypt(decryptedAESKey);					
					
					//read sealed (by AES) message and convert
					inputSealedQuestion = (SealedObject) ois.readObject();							
					question = (C2SMessage) aesConverter.decryptObject(inputSealedQuestion);
					
					//forge answer
					answer = loggedMessage(question);
					
					//create new AES object
					aesConverter.generateNewKey();
					
					//seal AES key (with RSA) and send to client
					outputSealedAESKey = RSAEncryptDecrypt.encryptObject(aesConverter.getAesKey(), clientKey);
					oos.writeObject(outputSealedAESKey);
					
					//seal answer (with AES) and send to client
					outputSealedAnswer = aesConverter.encryptObject(answer);
					oos.writeObject(outputSealedAnswer);
					
				} catch (EOFException e) {
					return; //closed socket
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Error reading obj(cripto clienthandler)");
					e.printStackTrace();
					return;
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
		
		//Controllo se l'utente richiedente è conforme al contenuto del messaggio
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
