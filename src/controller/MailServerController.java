package controller;

import base.Exam;
import base.Student;
import exception.ExamNotFoundException;
import exception.StudentNotFoundException;
import model.Utils;
import view.MailServerView;

public class MailServerController {	
			
	public MailServerController() {}
	
	public void sendMail(short type, String matricola, String codice) {
		
		try {
			Student student = Utils.getStudentByMatricola(matricola);
			Exam exam = Utils.getExamByCodice(codice);
			MailServerView.forgeMail(type, student, exam);
			
		} catch (StudentNotFoundException | ExamNotFoundException e) {
			
		}
		
	}
	
	

}
