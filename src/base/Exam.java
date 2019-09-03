package base;

public class Exam {
	
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
	

}
