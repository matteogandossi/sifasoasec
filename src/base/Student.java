package base;

import java.io.Serializable;

public class Student implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4435141978473332541L;
	private String matricola;
	private String nome, cognome;
	private String dataDiNascita;
	private String email;
	
	public Student(String matricola, String nome, String cognome, String dataDiNascita, String email) {
		
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.email = email;
	}
	
	
	public String getMatricola() {
		return matricola;
	}
	
	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}
	
	public String getDataDiNascita() {
		return dataDiNascita;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("--------------\n");
		sb.append("Matricola: " + matricola + "\n");
		sb.append("Nome " + nome + " " + cognome + "\n");
		sb.append("Data di nascita: " + dataDiNascita + "\n");
		sb.append("Email " + email  + "\n");
		sb.append("--------------\n");
		
		return sb.toString();
		
	}	

}
