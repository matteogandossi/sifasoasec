package view;

import base.Exam;
import base.Student;
import messages.constants.Mail;

public class MailServerView {
	
	private MailServerView() {}
	
	public static void forgeMail(short type, Student student, Exam exam) {
		
		System.out.println("--------------------------------");
		
		switch (type) {
		
			case Mail.ISCRIZIONE_ESAME:
				forgeIscrizioneMail(student, exam);
				break;
			
			case Mail.CANCELLAZIONE_ESAME:
				forgeCancellazioneMail(student, exam);
				break;
	
			default:
				break;
		}
		
		System.out.println("--------------------------------");
		
	}
	
	private static void forgeIscrizioneMail(Student student, Exam exam) {
		
		System.out.println("Mittente: MailServer Unimi <no-reply@unimi.it>");
		System.out.println("Destinatario: " + student.getNome() + " " + student.getCognome() + " <" + student.getEmail() + ">");
		System.out.println("Oggetto: Iscrizione Esame " + exam.getTitolo());
		System.out.println("Corpo:");
		System.out.println("Conferma l'avvenuta iscrizione dello studente " + student.getNome() + " " + student.getCognome() + 
				" (matr. " + student.getMatricola() + ") al seguente esame: ");
		System.out.println(exam);
		
		System.out.println("Si prega di non rispondere alla seguente mail.");
		System.out.println("Cordiali saluti,");
		System.out.println("Università degli studi di Milano");
		
		
	}

	private static void forgeCancellazioneMail(Student student, Exam exam) {
		
		System.out.println("Mittente: MailServer Unimi <no-reply@unimi.it>");
		System.out.println("Destinatario: " + student.getNome() + " " + student.getCognome() + " <" + student.getEmail() + ">");
		System.out.println("Oggetto: Cancellazione Esame " + exam.getTitolo());
		System.out.println("Corpo:");
		System.out.println("Conferma l'avvenuta cancellazione dello studente " + student.getNome() + " " + student.getCognome() + 
				" (matr. " + student.getMatricola() + ") al seguente esame: ");
		System.out.println(exam);
		
		System.out.println("Si prega di non rispondere alla seguente mail.");
		System.out.println("Cordiali saluti,");
		System.out.println("Università degli studi di Milano");
		
	}

}
