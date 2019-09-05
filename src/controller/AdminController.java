package controller;

import java.util.ArrayList;

import base.Exam;
import base.Iscrizione;
import base.Student;
import model.ModelSifa;
import rsa.KeyConverter;
import rsa.KeyGenerator;
import view.AdminView;

public class AdminController {
	
	private AdminController() {}
	
	public static void addNewStudent() {
		
		Student student = AdminView.getNewStudent();
		
		KeyGenerator keyGenerator = new KeyGenerator();
		String publicKeyConverted = KeyConverter.keyToString(keyGenerator.getPublicKey());
		String privateKeyConverted = KeyConverter.keyToString(keyGenerator.getPrivateKey());
		
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
