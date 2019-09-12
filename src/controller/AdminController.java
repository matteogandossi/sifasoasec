package controller;

import java.util.ArrayList;

import base.Exam;
import base.Iscrizione;
import base.Student;
import crypt.rsa.RSAKeyConverter;
import crypt.rsa.RSAKeyGenerator;
import model.ModelSifa;
import view.AdminView;

public class AdminController {
	
	private AdminController() {}
	
	public static void addNewStudent() {
		
		Student student = AdminView.getNewStudent();
		
		RSAKeyGenerator rSAKeyGenerator = new RSAKeyGenerator();
		String publicKeyConverted = RSAKeyConverter.keyToString(rSAKeyGenerator.getPublicKey());
		String privateKeyConverted = RSAKeyConverter.keyToString(rSAKeyGenerator.getPrivateKey());
		
		boolean result = ModelSifa.insertNewStudent(student, privateKeyConverted, publicKeyConverted);	
		AdminView.showResult(result);
	}

	public static void addNewExam() {
		
		Exam exam = AdminView.getNewExam();
		
		boolean result = ModelSifa.insertNewExam(exam);
		AdminView.showResult(result);
	}

	public static void showAllStudents() {
		
		ArrayList<Student> list = ModelSifa.getAllStudents();		
		AdminView.showAllStudents(list);
		
	}
	
	public static void showAllExams() {
		
		ArrayList<Exam> list = ModelSifa.getAllExams();		
		AdminView.showAllExam(list);
		
	}
	
	public static void showAllIscrizioni() {
		
		ArrayList<Iscrizione> list = ModelSifa.getAllIscrizioni();		
		AdminView.showAllIscrizioni(list);
		
	}

}
