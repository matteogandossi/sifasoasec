package base;

import java.io.Serializable;

public class Iscrizione implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2294178409369390095L;
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
