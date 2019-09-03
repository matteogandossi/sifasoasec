package base;

public class Student {
	
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
	

}
