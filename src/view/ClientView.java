package view;

import java.util.ArrayList;
import java.util.Scanner;

import base.Exam;
import messages.S2CMessage;
import messages.constants.Answer;

public class ClientView {
	
	
	private static Scanner scanner = new Scanner(System.in);
	
	private ClientView() {}

	public static int showMenu(boolean logged) {
		
		if(logged)
			return showLoggedMenu();
		return showNotLoggedMenu();
		
		
	}
	
	private static int showNotLoggedMenu() {
		
		System.out.println("Seleziona l'operazione da effettuare:");
		System.out.println("1) Log in");
		System.out.println("0) Esci");
		System.out.println();
		System.out.print("Scelta: ");
		
		int scelta = scanner.nextInt();
		scanner.nextLine();
		
		return scelta;
	}
	
	private static int showLoggedMenu() {
		
		System.out.println("Seleziona l'operazione da effettuare:");
		System.out.println("1) Iscriviti a un esame");
		System.out.println("2) Cancella iscrizione");
		System.out.println("3) Visualizza esami iscrivibili");
		System.out.println("4) Visualizza iscrizioni confermate");		
		System.out.println("5) Mostra informazioni studente");
		System.out.println("0) Logout");
		System.out.println();
		System.out.print("Scelta: ");
		
		int scelta = scanner.nextInt();
		scanner.nextLine();
		
		return scelta;
	}
	
	public static void showListaEsami(ArrayList<Exam> list) {
		
		if(list.size() > 0)			
			for(Exam exam: list)
				System.out.println(exam);
		else
			System.out.println("Nessun esame presente.");			
		
	}
	
	public static void pressEnterToContinue() {
		
		System.out.print("Premi INVIO per continuare...");
		scanner.nextLine();
	}

	public static String getMatricola() {
		
		System.out.print("Inserisci la matricola: ");
		return scanner.nextLine();
	}
	
	public static String getCodiceEsame() {
		
		System.out.print("Inserisci il codice dell'esame: ");
		return scanner.nextLine();
	}
	
	public static String getMessageOutcome(S2CMessage message) {
		
		switch (message.getType()) {
		
			case Answer.INIT:
				return "Login accettato.";
			
			case Answer.ESAMI_DISPONIBILI:
				return "Lista esami disponibili.";
				
			case Answer.ESAMI_CANCELLABILI:
				return "Lista esami iscritti.";
			
			case Answer.OK:
				return "Operazione completata con successo.";
			
			case Answer.FAIL:
				return "Operazione non effettuata.\nMotivo: " + message.getMessage();
	
			default:
				return "Errore codice Messaggio.";
		}
	}

}
