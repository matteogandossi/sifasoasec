package view;

import java.util.ArrayList;
import java.util.Scanner;

import base.Exam;
import base.Iscrizione;
import base.Student;

public class AdminView {
	
	private static Scanner scanner = new Scanner(System.in);
	
	private AdminView() {}
	
	public static int mainMenu() {
		
		System.out.println("Seleziona l'operazione da effettuare;");
		System.out.println("1) Inserisci Studente");
		System.out.println("2) Inserisci Esame");
		System.out.println("3) Visualizza tutti gli studenti");
		System.out.println("4) Visualizza tutti gli esami");
		System.out.println("5) Visualizza tutte le iscrizioni");
		System.out.println();
		System.out.println("0) Esci");
		System.out.println();
		System.out.print("Scelta: ");
		
		int scelta = scanner.nextInt();
		scanner.nextLine();
		
		return scelta;
		
	}

	public static Student getNewStudent() {
		
		String matricola, nome, cognome, dataDiNascita, email;
		
		System.out.print("Inserisci la matricola dello studente: ");
		matricola = scanner.nextLine();
		System.out.print("Inserisci il nome dello studente: ");
		nome = scanner.nextLine();
		System.out.print("Inserisci il cognome dello studente: ");
		cognome = scanner.nextLine();
		System.out.print("Inserisci la data di nascita (YYYY-MM-DD) dello studente: ");
		dataDiNascita = scanner.nextLine();
		System.out.print("Inserisci l'email dello studente: ");
		email = scanner.nextLine();
		
		return new Student(matricola, nome, cognome, dataDiNascita, email);
		
	}

	public static Exam getNewExam() {
		
		String codice, titolo, docente;
		int cfu;
		
		System.out.print("Inserisci il codice esame: ");
		codice = scanner.nextLine();
		System.out.print("Inserisci il titolo dell'esame: ");
		titolo = scanner.nextLine();
		System.out.print("Inserisci il docente dell'esame: ");
		docente = scanner.nextLine();
		System.out.print("Inserisci i cfu dell'esame: ");
		cfu = scanner.nextInt();		
		scanner.nextLine();
		
		return new Exam(codice, titolo, docente, cfu);
		
		
	}

	public static void showAllStudents(ArrayList<Student> list) {
		
		System.out.println("Visualizzazione di tutti gli studenti...");
		
		if(list.isEmpty())
			System.out.println("Nessuno studente presente.");
		else
			for (Student student : list) 
				System.out.println(student);
			
		
	}
	
	public static void showAllExam(ArrayList<Exam> list) {
		
		System.out.println("Visualizzazione di tutti gli esami...");
		
		if(list.isEmpty())
			System.out.println("Nessun esame presente.");
		else
			for (Exam exam : list) 
				System.out.println(exam);
			
		
	}

	public static void showAllIscrizioni(ArrayList<Iscrizione> list) {
	
		System.out.println("Visualizzazione di tutte le iscrizioni...");
		
		if(list.isEmpty())
			System.out.println("Nessuna iscrizione presente.");
		else
			for (Iscrizione iscrizione : list) 
				System.out.println(iscrizione);
			
		
	
	}

	public static void showResult(boolean result) {
		
		if(result)
			System.out.println("Operazione eseguita correttamente.");
		else
			System.out.println("Operazione NON eseguita.");
		
	}

}
