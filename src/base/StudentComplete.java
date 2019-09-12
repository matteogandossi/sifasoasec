package base;

public class StudentComplete extends Student {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6891390479730202053L;
	private String privateKey;
	
	public StudentComplete(String matricola, String nome, String cognome, String dataDiNascita, String email, String privateKey) {
		super(matricola, nome, cognome, dataDiNascita, email);
		this.privateKey = privateKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}
	
}
