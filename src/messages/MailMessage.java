package messages;

import base.Iscrizione;
import messages.constants.Mail;

public class MailMessage {
	
	private short type;
	private Iscrizione iscrizione;
	
	private MailMessage(short type, String matricola, String codice) {
		this.type = type;
		iscrizione = new Iscrizione(matricola, codice);
	}
	
	public MailMessage createIscrizioneMail(String matricola, String codice) {
		return new MailMessage(Mail.ISCRIZIONE_ESAME, matricola, codice);
	}
	
	public MailMessage createCancellazioneMail(String matricola, String codice) {
		return new MailMessage(Mail.CANCELLAZIONE_ESAME, matricola, codice);
	}
	
	public short getType() {
		return type;
	}

	public Iscrizione getIscrizione() {
		return iscrizione;
	}

}
