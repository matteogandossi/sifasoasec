package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import base.Exam;
import base.Student;
import database.DatabaseSifa;
import exception.ExamNotFoundException;
import exception.StudentNotFoundException;

public class UtilsController {
	
	private UtilsController() {}
	
	protected static Student getStudentByMatricola(String matricola) throws StudentNotFoundException {
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabaseSifa.selectStudentByMatricola(st, matricola);
		
		try {
			
			if(rs.next()) {
				
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String dataDiNascita = rs.getString("dob");
				String email = rs.getString("email");
				
				return new Student(matricola, nome, cognome, dataDiNascita, email);				
			}
		
		} catch (SQLException e) {}
		
		DatabaseSifa.closeConnection(st);
		
		throw new StudentNotFoundException();
		
	}
	
	protected static Exam getExamByCodice(String codice) throws ExamNotFoundException {
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabaseSifa.selectExamByCodice(st, codice);
		
		try {
			
			if(rs.next()) {
				
				String titolo = rs.getString("titolo");
				String docente = rs.getString("docente");
				int cfu = rs.getInt("cfu");
				
				return new Exam(codice, titolo, docente, cfu);				
			}
		
		} catch (SQLException e) {}
		
		DatabaseSifa.closeConnection(st);
		
		throw new ExamNotFoundException();
		
	}

}
