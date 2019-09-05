package rsa;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyGenerator {
	
	private KeyPair keyPair;
	
	public KeyGenerator() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(Constants.ALGORITHM);
			generator.initialize(Constants.ALGORITHM_BITS);
			keyPair = generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error generator");
		}
	}
	
	public Key getPublicKey() {
		return keyPair.getPublic();
	}
	
	public Key getPrivateKey() {
		return keyPair.getPrivate();
	}

}
