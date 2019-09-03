package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePublic {
	
	private static String url = "jdbc:mysql://localhost:3306/soasec_public_key?serverTimezone=UTC";
	private static String user = "root";
	private static String password = "";
	
	private DatabasePublic() {}
	
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
			
			System.out.println("Opening Connection Error!");
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
	
	protected static boolean insertPublicKey(Statement st, String matricola, String publicKey) {
		
		int result = 0;
		
		String query = "INSERT INTO chiave"
					+ "VALUES ('" + matricola + "','" + publicKey +  "')";
		
		try {
			result = st.executeUpdate(query);			
		} catch (SQLException e) {
			System.out.println("Error Insert PubKey.");
		}
		
		return (result > 0);
		
	}
	
	public static ResultSet selectPubKey(Statement st, String matricola) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT publicKey FROM chiave WHERE matricola = '" + matricola + "'");
			
		} catch (SQLException e) {
			System.out.println("Error select iscrizioni");
		}
		
		return result;
	}

}
