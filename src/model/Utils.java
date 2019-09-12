package model;

import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import base.Exam;
import base.StudentComplete;
import crypt.rsa.RSAKeyConverter;
import database.DatabasePublic;
import database.DatabaseSifa;
import exception.ExamNotFoundException;
import exception.MissingKeyException;
import exception.StudentNotFoundException;

public class Utils {
	
	private Utils() {}
	
	public static StudentComplete getStudentByMatricola(String matricola) throws StudentNotFoundException {
		
		Statement st = DatabaseSifa.connect();
		
		ResultSet rs = DatabaseSifa.selectStudentByMatricola(st, matricola);
		
		try {
			
			if(rs.next()) {
				
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String dataDiNascita = rs.getString("dob");
				String email = rs.getString("email");
				String privateKey = rs.getString("privateKey");
				
				return new StudentComplete(matricola, nome, cognome, dataDiNascita, email, privateKey);				
			}
		
		} catch (SQLException e) {}
		
		DatabaseSifa.closeConnection(st);
		
		throw new StudentNotFoundException();
		
	}
	
	public static Exam getExamByCodice(String codice) throws ExamNotFoundException {
		
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
	
	public static Key getPublicKey(String matricola) throws MissingKeyException {
		
		Statement st = DatabasePublic.connect();
		
		ResultSet rs = DatabasePublic.selectPubKey(st, matricola);
		
		try {
			
			if(rs.next()) {
				
				String publicKey = rs.getString("publicKey");
				return RSAKeyConverter.getPublicKeyFromString(publicKey);				
			}
			
		} catch (SQLException e) {}
		
		throw new MissingKeyException();
		
	}

}
