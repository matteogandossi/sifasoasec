package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabasePublic;
import exception.MissingKeyException;

public class ModelPublic {
	
	private ModelPublic() {}
	
	public static String getPublicKey(String matricola) throws MissingKeyException {
		
		Statement st = DatabasePublic.connect();
		
		ResultSet rs = DatabasePublic.selectPubKey(st, matricola);
		
		try {
			
			if(rs.next())
				return rs.getString("publicKey");
			
		} catch (SQLException e) {
			System.out.println("Error retrieving pubKey");
		}
		
		DatabasePublic.closeConnection(st);
		
		throw new MissingKeyException();
				
	}

}
