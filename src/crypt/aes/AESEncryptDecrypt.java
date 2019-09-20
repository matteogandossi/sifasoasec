package crypt.aes;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

public class AESEncryptDecrypt {
	

	private Key aesKey;
	
	public AESEncryptDecrypt() {
		
		generateNewKey();
		
	}
	
	public AESEncryptDecrypt(Key aesKey) {
		this.aesKey = aesKey;
	}

	public Key getAesKey() {
		return aesKey;
	}
	
	public SealedObject encryptObject(Serializable obj) {
		
		Cipher cipher;
		SealedObject sealedObject = null;
		
		try {
			
			cipher = Cipher.getInstance(AESConstants.ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			
			sealedObject = new SealedObject(obj, cipher);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | IOException e) {
			System.out.println("Error encrypting obj AES");
			e.printStackTrace();
		}
		
		return sealedObject;
		
	}
	
	public Serializable decryptObject(SealedObject sealedObject) {
		
		Cipher cipher;
		Serializable result = null;
		
		try {
			
			cipher = Cipher.getInstance(AESConstants.ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			
			result = (Serializable) sealedObject.getObject(cipher);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | IOException | ClassNotFoundException | BadPaddingException e) {
			System.out.println("Error decrypting obj AES");
		}
		
		return result;
		
	}

	public void generateNewKey() {
		
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance(AESConstants.ALGORITHM);
			keyGen.init(AESConstants.ALGORITHM_BITS);
			aesKey = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error AES generator");
		}
		
	}
	

}
