package base;

public class StudentComplete extends Student {

	private String privateKey;
	
	public StudentComplete(String matricola, String nome, String cognome, String dataDiNascita, String email, String privateKey) {
		super(matricola, nome, cognome, dataDiNascita, email);
		this.privateKey = privateKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}
	
}
