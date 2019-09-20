package base;

import java.io.Serializable;

public class Exam implements Serializable{
	
	
	private String codice;
	private String titolo;
	private String docente;
	private int cfu;
	
	public Exam(String codice, String titolo, String docente, int cfu) {
		
		this.codice = codice;
		this.titolo = titolo;
		this.docente = docente;
		this.cfu = cfu;
	}
	
	public String getCodice() {
		return codice;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public String getDocente() {
		return docente;
	}
	
	public int getCfu() {
		return cfu;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("--------------\n");
		sb.append("Codice esame: " + codice + "\n");
		sb.append("Esame di " + titolo + "\n");
		sb.append("Docente: " + docente + "\n");
		sb.append("Crediti formativi: " + cfu  + "\n");
		sb.append("--------------\n");
		
		return sb.toString();
		
	}	

}
