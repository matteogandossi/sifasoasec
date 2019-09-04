package base;

public class Iscrizione {
	
	private String matricola, codice;
	
	public Iscrizione(String matricola, String codice) {
		this.matricola = matricola;
		this.codice = codice;
	}

	public String getCodice() {
		return codice;
	}

	public String getMatricola() {
		return matricola;
	}
	
	@Override
	public String toString() {
		
		return matricola + " - " + codice;
	}

}
