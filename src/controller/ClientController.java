package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Key;

import javax.crypto.SealedObject;

import base.StudentComplete;
import crypt.aes.AESEncryptDecrypt;
import crypt.rsa.RSAEncryptDecrypt;
import crypt.rsa.RSAKeyConverter;
import exception.MissingKeyException;
import messages.C2SMessage;
import messages.S2CMessage;
import messages.constants.Answer;
import model.Utils;
import server.SifaPath;
import view.ClientView;

public class ClientController {
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket socket;
	private Key publicKeySifa, privateKeyClient;
	private StudentComplete student;
	
	public ClientController() {
		
		try {
			publicKeySifa = Utils.getPublicKey("sifa");
		} catch (MissingKeyException e1) {
			System.out.println("Cannot find sifa key");
		}		
		
		try {
			socket = new Socket(SifaPath.ADDRESS, SifaPath.PORT);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Error socket client.");
		}
		
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private S2CMessage sendAndReceivePlain(C2SMessage message) {
		
		
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			System.out.println("Error sending C2S message");
			return null;
		}
		
		try {
			
			S2CMessage response =  (S2CMessage) ois.readObject();
			return response;
			
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Error receiving C2S message (sealed)");
			return null;
		}
		
	}
	
	private S2CMessage sendAndReceiveCrypted(C2SMessage message) {
		
		AESEncryptDecrypt aesConverter;
		Key decryptedAESKey;
		SealedObject outputSealedAESKey, outputSealedQuestion;
		SealedObject inputSealedAESKey, inputSealedAnswer;
		
		//create new AES object
		aesConverter = new AESEncryptDecrypt();
		
		//seal AES key (with RSA) and send to SIFA
		outputSealedAESKey = RSAEncryptDecrypt.encryptObject(aesConverter.getAesKey(), publicKeySifa);
		try {
			oos.writeObject(outputSealedAESKey);
				
			//seal answer (with AES) and send to client
			outputSealedQuestion = aesConverter.encryptObject(message);
			oos.writeObject(outputSealedQuestion);
			
		} catch (IOException e) {
			System.out.println("Error writing message to server (sealed)");
		}
		
		//read sealed (by RSA) AES key and convert					
		try {
			inputSealedAESKey = (SealedObject) ois.readObject();
			decryptedAESKey = (Key) RSAEncryptDecrypt.decryptObject(inputSealedAESKey, privateKeyClient);
			
			//create AES object					
			aesConverter = new AESEncryptDecrypt(decryptedAESKey);					
			
			//read sealed (by AES) message and convert
			inputSealedAnswer = (SealedObject) ois.readObject();
			S2CMessage answer = (S2CMessage) aesConverter.decryptObject(inputSealedAnswer);
			
			return answer;
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
	}

	public boolean login(String matricola) {
		
		S2CMessage response = sendAndReceivePlain(C2SMessage.createLoginMessage(matricola));
		boolean outcome;
		
		if(response.getType() == Answer.INIT) {
			student = response.getStudentComplete();
			privateKeyClient = RSAKeyConverter.getPrivateKeyFromString(student.getPrivateKey());
			outcome = true;
		}
		else
			outcome = false;
		
		System.out.println(ClientView.getMessageOutcome(response));
		
		return outcome;
		
	}

	public void iscrizione(String codice) {
		
		S2CMessage response = sendAndReceiveCrypted(C2SMessage.createIscrizioneMessage(student.getMatricola(), codice));
		
		System.out.println(ClientView.getMessageOutcome(response));		
		
	}

	public void cancellazione(String codice) {

		S2CMessage response = sendAndReceiveCrypted(C2SMessage.createCancellazioneMessage(student.getMatricola(), codice));
		
		System.out.println(ClientView.getMessageOutcome(response));	
		
	}

	public void listaIscrivibili() {

		S2CMessage response = sendAndReceiveCrypted(C2SMessage.createListaIscrizioneMessage(student.getMatricola()));
		
		if(response.getType() == Answer.ESAMI_DISPONIBILI) {
			ClientView.showListaEsami(response.getList());
			ClientView.pressEnterToContinue();
		}
		else
			System.out.println(ClientView.getMessageOutcome(response));
		
	}

	public void listaIscrizioni() {
		
		S2CMessage response = sendAndReceiveCrypted(C2SMessage.createListaCancellazioneMessage(student.getMatricola()));
		
		if(response.getType() == Answer.ESAMI_CANCELLABILI) {
			ClientView.showListaEsami(response.getList());
			ClientView.pressEnterToContinue();
		}
		else
			System.out.println(ClientView.getMessageOutcome(response));
		
	}

	public void showInfoStudent() {
		
		System.out.println("You are logged as...");
		System.out.println(student);
		ClientView.pressEnterToContinue();
		
	}
	
	public boolean logout() {
		
		S2CMessage response = sendAndReceiveCrypted(C2SMessage.createLogoutMessage(student.getMatricola()));
		boolean outcome;
		
		if(response.getType() == Answer.OK) {
			student = null;
			privateKeyClient = null;
			outcome = true;
		}
		else
			outcome = false;
		
		System.out.println(ClientView.getMessageOutcome(response));
		
		return outcome;
	}

	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {}
	}
	
	

}
