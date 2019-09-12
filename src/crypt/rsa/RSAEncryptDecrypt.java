package crypt.rsa;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class RSAEncryptDecrypt {
	
	private RSAEncryptDecrypt() {}
	
	public static SealedObject encryptObject(Serializable obj, Key publicKey) {
		
		Cipher cipher;
		SealedObject sealedObject = null;
		try {
			
			cipher = Cipher.getInstance(RSAConstants.ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			sealedObject = new SealedObject(obj, cipher);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | IOException e) {
			System.out.println("Error encrypting obj");
		}
		
		return sealedObject;
		
	}
	
	public static Serializable decryptObject(SealedObject sealedObject, Key privateKey) {
		
		Cipher cipher;
		Serializable result = null;
		
		try {
			
			cipher = Cipher.getInstance(RSAConstants.ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			result = (Serializable) sealedObject.getObject(cipher);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | IOException | ClassNotFoundException | BadPaddingException e) {
			System.out.println("Error decrypting obj");
		}
		
		return result;
		
	}

}
