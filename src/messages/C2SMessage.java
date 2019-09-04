package messages;

public class C2SMessage {
	
	private short type;
	private String matricola, codice;
	
	private C2SMessage(short type, String matricola, String codice) {
		this.type = type;
		this.matricola = matricola;
		this.codice = codice;
	}
	
	public static C2SMessage createLoginMessage(String matricola) {
		return new C2SMessage(Question.LOGIN, matricola, null);
	}
	
	public static C2SMessage createListaIscrizioneMessage(String matricola) {
		return new C2SMessage(Question.LISTA_ISCRIZIONE, matricola, null);
	}
	
	public static C2SMessage createListaCancellazioneMessage(String matricola) {
		return new C2SMessage(Question.LISTA_CANCELLAZIONE, matricola, null);
	}
	
	public static C2SMessage createIscrizioneMessage(String matricola, String codice) {
		return new C2SMessage(Question.ISCRIZIONE, matricola, codice);
	}
	
	public static C2SMessage createCancellazioneMessage(String matricola, String codice) {
		return new C2SMessage(Question.CANCELLAZIONE, matricola, codice);
	}
	
	public static C2SMessage createLogoutMessage(String matricola) {
		return new C2SMessage(Question.LOGOUT, matricola, null);
	}
	
	public short getType() {
		return type;
	}
	public String getCodice() {
		return codice;
	}

	public String getMatricola() {
		return matricola;
	}

}
