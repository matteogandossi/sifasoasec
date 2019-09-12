package crypt.rsa;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSAKeyGenerator {
	
	private KeyPair keyPair;
	
	public RSAKeyGenerator() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(RSAConstants.ALGORITHM);
			generator.initialize(RSAConstants.ALGORITHM_BITS);
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
