package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.security.Key;

import javax.crypto.SealedObject;

import base.Exam;
import base.Student;
import crypt.aes.AESEncryptDecrypt;
import crypt.rsa.RSAEncryptDecrypt;
import exception.ExamNotFoundException;
import exception.StudentNotFoundException;
import messages.MailMessage;
import model.ModelSifa;
import server.MailPath;
import view.MailServerView;

public class MailServerMain {
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket;
		ObjectInputStream ois;
		
		Key privateKeyMail;
		
		System.out.println("Starting mail server...");
		
		try {
			serverSocket = new ServerSocket(MailPath.PORT);
		} catch (IOException e) {
			System.out.println("Error creating mail server socket.");
			return;
		}
		
		privateKeyMail = ModelSifa.getPrivKeyMail();
		
		while(true) {
			
			//read sealed (by RSA) AES key and convert					
			SealedObject inputSealedAESKey;
			
			System.out.println("Waiting for request...");
			
			try {
				ois = new ObjectInputStream(serverSocket.accept().getInputStream());
			} catch (IOException e) {
				System.out.println("Error acepting socket sifa.");
				return;
			}
			System.out.println("Request accepted.");
			try {
				
				inputSealedAESKey = (SealedObject) ois.readObject();
				Key decryptedAESKey = (Key) RSAEncryptDecrypt.decryptObject(inputSealedAESKey, privateKeyMail);
				
				//create AES object					
				AESEncryptDecrypt aesConverter = new AESEncryptDecrypt(decryptedAESKey);					
				
				//read sealed (by AES) message and convert
				SealedObject inputSealedQuestion = (SealedObject) ois.readObject();							
				MailMessage message = (MailMessage) aesConverter.decryptObject(inputSealedQuestion);
				
				ois.close();
				
				try {
					sendMail(message);
				} catch (StudentNotFoundException | ExamNotFoundException e) {
					System.out.println("Cannot send mail, because the student and/or the exam cannot be found on the database.");
				}
				System.out.println("Request completed.");
				
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
		}
		
	}

	private static void sendMail(MailMessage message) throws StudentNotFoundException, ExamNotFoundException {
		
		Exam exam = ModelSifa.getExamByCodice(message.getIscrizione().getCodice());
		Student student = ModelSifa.getStudentByMatricola(message.getIscrizione().getMatricola());
		
		MailServerView.forgeMail(message.getType(), student, exam);
		
		
	}

}
