package rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyConverter {
	
	private KeyConverter() {}
	
	public static String keyToString(Key key) {
		
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	private static byte[] stringToByte(String encodedKey) {
		
		return Base64.getDecoder().decode(encodedKey);
	}
	
	public static Key getPublicKeyFromString(String keyFromDB) {
		
		byte[] encodedKey = stringToByte(keyFromDB);
		Key publicKey = null;
		
		KeyFactory keyFactory;
		try {
			
			keyFactory = KeyFactory.getInstance(Constants.ALGORITHM);
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
			publicKey = keyFactory.generatePublic(publicKeySpec);
			 
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.out.println("Error converting public key");
		}
		
		return publicKey;		
		
	}
	
	public static Key getPrivateKeyFromString(String keyFromDB) {
		
		byte[] encodedKey = stringToByte(keyFromDB);
		Key privateKey = null;
		
		KeyFactory keyFactory;
		try {
			
			keyFactory = KeyFactory.getInstance(Constants.ALGORITHM);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
			privateKey = keyFactory.generatePrivate(privateKeySpec);
			 
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.out.println("Error converting private key");
		}
		
		return privateKey;		
		
	}

}
