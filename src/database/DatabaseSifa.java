package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import base.Exam;
import base.Student;

public class DatabaseSifa {
	
	private static String url = "jdbc:mysql://localhost:3306/soasec_sifa?serverTimezone=UTC";
	private static String user = "root";
	private static String password = "";
	
	private DatabaseSifa() {}
	
	/**
	 *	Connection Methods 
	 * 
	 */	
	public static Statement connect() {
		
		Connection connect;
		
		try {
			
			connect = DriverManager.getConnection(url, user, password);
			return connect.createStatement();
			
		} catch (SQLException e) {
			
			System.out.println("Opening Connection to Sifa Error!");
			System.exit(0);
			return null;
		}
		
	}
	
	public static void closeConnection(Statement st) {
		
		try {
			
			st.close();
			
		} catch (SQLException e) {
			
			System.out.println("Closing Connection Error!");
			System.exit(0);
		}
		
	}
	
	/**
	 * 
	 * SELECT
	 */
	public static ResultSet selectStudents(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT matricola, nome, cognome, dob, email FROM studente");
			
		} catch (SQLException e) {
			System.out.println("Error select students");
		}
		
		return result;
	}
	
	public static ResultSet selectStudentByMatricola(Statement st, String matricola) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM studente WHERE matricola = '" + matricola + "'");
			
		} catch (SQLException e) {
			System.out.println("Error select student by matricola");
		}
		
		return result;		
	}
	
	public static ResultSet selectExams(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM esame");
			
		} catch (SQLException e) {
			System.out.println("Error select esame by codice");
		}
		
		return result;
	}
	
	public static ResultSet selectExamByCodice(Statement st, String codice) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM esame WHERE codice = '" + codice + "'");
			
		} catch (SQLException e) {
			System.out.println("Error select esame");
		}
		
		return result;
	}
	
	public static ResultSet selectIscrizioni(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM iscrizione");
			
		} catch (SQLException e) {
			System.out.println("Error select iscrizioni");
		}
		
		return result;
	}
	
	public static ResultSet selectEsamiIscitti(Statement st, String matricola) {
		
		ResultSet result = null;
		String query = "SELECT esame.codice AS codice, titolo, docente, cfu "
					+ "FROM  esame INNER JOIN iscrizione WHERE matricola = '" + matricola + "'";
		
		try {
			result = st.executeQuery(query);
			
		} catch (SQLException e) {
			System.out.println("Error select esami iscritti for matricola " + matricola);
		}
		
		return result;
	}
	
	public static ResultSet selectEsamiIscrivibili(Statement st, String matricola) {
		
		ResultSet result = null;
		String query = "SELECT * FROM esame WHERE codice NOT IN"
				+ "(SELECT codice FROM iscrizione WHERE matricola = '" + matricola + "')";
		
		try {
			result = st.executeQuery(query);
			
		} catch (SQLException e) {
			System.out.println("Error select esami iscrivibili for matricola  " + matricola);
		}
		
		return result;
	}
	
	/**
	 * 
	 * INSERT
	 */
	public static boolean insertNewStudent(Statement st, Student stud, String privateKey, String publicKey) {
		
		int result = 0;
		boolean pubResult = false;
		
		String query = "INSERT INTO studente "
					+ "VALUES ('" + stud.getMatricola() + "','"
					+ stud.getNome() + "','"
					+ stud.getCognome() + "','"
					+ stud.getDataDiNascita() + "','"
					+ stud.getEmail() + "','"
					+ privateKey + "')";
		
		//insert publickey as well		
		Statement publicSt = DatabasePublic.connect();
		
		try {
			result = st.executeUpdate(query);
			pubResult = DatabasePublic.insertPublicKey(publicSt, stud.getMatricola(), publicKey);
		} catch (SQLException e) {
			System.out.println("Error Insert Student.");
			e.printStackTrace();
		}
		
		DatabasePublic.closeConnection(publicSt);
		
		return (result > 0) && pubResult;
		
	}
	
	public static boolean insertNewExam(Statement st, Exam exam) {
		
		int result = 0;
		
		String query = "INSERT INTO esame "
					+ "VALUES ('" + exam.getCodice() + "','"
					+ exam.getTitolo() + "','"
					+ exam.getDocente() + "',"
					+ exam.getCfu() + ")";
		
		try {
			result = st.executeUpdate(query);			
		} catch (SQLException e) {
			System.out.println("Error Insert Exam.");
		}
		
		return (result > 0);
		
	}
	
	public static boolean insertNewIscrizione(Statement st, String matricola, String codice) {
		
		int result = 0;
		
		String query = "INSERT INTO iscrizione (matricola, codice) "
					+ "VALUES ('" + matricola + "','" + codice + "')";
		
		try {
			result = st.executeUpdate(query);			
		} catch (SQLException e) {
			System.out.println("Error Insert Iscrizione.");
		}
		
		return (result > 0);
		
	}
	
	/**
	 * DELETE
	 */
	public static boolean deleteIscrizione(Statement st, String matricola, String codice) {
		
		int result = 0;
		
		String query = "DELETE FROM iscrizione WHERE matricola = '" + matricola + "' AND codice = '" + codice + "'";
		
		try {
			
			result = st.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.println("Error Delete Exam");
		}
		
		return (result > 0);
	}
	
	

}
