package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePrivate {
	
	private DatabasePrivate() {}
	
	
	public static ResultSet selectPrivateKeySifa(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM server WHERE matricola = 'sifa'");
			
		} catch (SQLException e) {
			System.out.println("Error select priv key sifa");
		}
		
		return result;
		
	}
	
	public static ResultSet selectPrivateKeyMail(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM server WHERE matricola = 'mail'");
			
		} catch (SQLException e) {
			System.out.println("Error select priv key mail");
		}
		
		return result;
		
	}

}
