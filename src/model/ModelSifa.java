package model;

import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import base.Exam;
import base.Iscrizione;
import base.Student;
import database.DatabasePrivate;
import database.DatabaseSifa;
import exception.ExamNotFoundException;
import exception.StudentNotFoundException;
import rsa.KeyConverter;

public class ModelSifa {
	
	private ModelSifa() {}
	
	/**
	 * 	
	 * SELECT
	 */
	public static ArrayList<Student> getAllStudents(){
		
		ArrayList<Student> list = new ArrayList<>();
		String matricola, nome, cognome, dataDiNascita, email;
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabaseSifa.selectStudents(st);
		
		try {
			
			while(rs.next()) {
				
				matricola = rs.getString("matricola");
				nome = rs.getString("nome");
				cognome = rs.getString("cognome");
				dataDiNascita = rs.getString("dob");
				email = rs.getString("email");
				
				list.add(new Student(matricola, nome, cognome, dataDiNascita, email));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Problem retrieving all students");
		}
		
		DatabaseSifa.closeConnection(st);
		
		return list;
		
	}
	
	public static Student getStudentByMatricola(String matricola) throws StudentNotFoundException {
		
		ArrayList<Student> list = getAllStudents();
		
		for (Student student : list) 
			if(student.getMatricola().equals(matricola))
				return student;
		throw new StudentNotFoundException();
		
	}
	
	public static ArrayList<Exam> getAllExams(){
		
		ArrayList<Exam> list = new ArrayList<>();
		String codice, titolo, docente;
		int cfu;
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabaseSifa.selectExams(st);
		
		try {
			
			while(rs.next()) {
				
				codice = rs.getString("codice");
				titolo = rs.getString("titolo");
				docente = rs.getString("docente");
				cfu = rs.getInt("cfu");
				
				list.add(new Exam(codice, titolo, docente, cfu));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Problem retrieving all exams");
		}
		
		DatabaseSifa.closeConnection(st);
		
		return list;
		
	}
	
	public static Key getPrivKeySifa() {
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabasePrivate.selectPrivateKeySifa(st);
		
		try {
			rs.next();
			String result = rs.getString("privateKey");
			DatabaseSifa.closeConnection(st);
			return KeyConverter.getPrivateKeyFromString(result);
		} catch (SQLException e) {
			System.out.println("Error priv Key");
			return null;
		}
		
	}
	
	public static Key getPrivKeyMail() {
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabasePrivate.selectPrivateKeyMail(st);
		
		try {
			rs.next();
			return KeyConverter.getPrivateKeyFromString(rs.getString("privateKey"));
		} catch (SQLException e) {
			System.out.println("Error priv Key");
			return null;
		}
		
	}
	
	public static Exam getExamByCodice(String codice) throws ExamNotFoundException {
		
		ArrayList<Exam> list = getAllExams();
		
		for (Exam exam : list) 
			if(exam.getCodice().equals(codice))
				return exam;
		throw new ExamNotFoundException();
		
	}
	
	public static ArrayList<Iscrizione> getAllIscrizioni(){
		
		ArrayList<Iscrizione> list = new ArrayList<>();
		String codice, matricola;
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabaseSifa.selectIscrizioni(st);
		
		try {
			
			while(rs.next()) {
				
				matricola = rs.getString("matricola");
				codice = rs.getString("codice");
				
				list.add(new Iscrizione(matricola, codice));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Problem retrieving all iscrizioni");
		}
		
		DatabaseSifa.closeConnection(st);
		
		return list;
		
	}
	
	public static ArrayList<Exam> getAllEsamiIscrivibili(String matricola){
		
		ArrayList<Exam> list = new ArrayList<>();
		String codice, titolo, docente;
		int cfu;
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabaseSifa.selectEsamiIscrivibili(st, matricola);
		
		try {
			
			while(rs.next()) {
				
				codice = rs.getString("codice");
				titolo = rs.getString("titolo");
				docente = rs.getString("docente");
				cfu = rs.getInt("cfu");
				
				list.add(new Exam(codice, titolo, docente, cfu));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Problem retrieving all exams iscrivibili");
		}
		
		DatabaseSifa.closeConnection(st);
		
		return list;
		
	}
	
	public static ArrayList<Exam> getAllEsamiIscritti(String matricola){
		
		ArrayList<Exam> list = new ArrayList<>();
		String codice, titolo, docente;
		int cfu;
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabaseSifa.selectEsamiIscitti(st, matricola);
		
		try {
			
			while(rs.next()) {
				
				codice = rs.getString("codice");
				titolo = rs.getString("titolo");
				docente = rs.getString("docente");
				cfu = rs.getInt("cfu");
				
				list.add(new Exam(codice, titolo, docente, cfu));
			}
			
		} catch (SQLException e) {
			
			System.out.println("Problem retrieving all exams iscrivibili");
		}
		
		DatabaseSifa.closeConnection(st);
		
		return list;
		
	}
	
	/**
	 * 
	 * INSERT
	 */
	public static boolean insertNewStudent(Student student, String privateKey, String publicKey) {
		
		Statement st = DatabaseSifa.connect();
		
		boolean result = DatabaseSifa.insertNewStudent(st, student, privateKey, publicKey);
		
		DatabaseSifa.closeConnection(st);
		
		return result;
	}
	
	public static boolean insertNewExam(Exam exam) {
		
		Statement st = DatabaseSifa.connect();
		
		boolean result = DatabaseSifa.insertNewExam(st, exam);
		
		DatabaseSifa.closeConnection(st);
		
		return result;
	}
	
	public static boolean insertNewIscrizione(String matricola, String codice) {
		
		Statement st = DatabaseSifa.connect();
		
		boolean result = DatabaseSifa.insertNewIscrizione(st, matricola, codice);
		
		DatabaseSifa.closeConnection(st);
		
		return result;
	}
	
	public static boolean deleteIscrizione(String matricola, String codice) {
		
		Statement st = DatabaseSifa.connect();
		
		boolean result = DatabaseSifa.deleteIscrizione(st, matricola, codice);
		
		DatabaseSifa.closeConnection(st);
		
		return result;
	}

}
