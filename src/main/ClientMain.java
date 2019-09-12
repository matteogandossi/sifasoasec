package main;

import controller.ClientController;
import view.ClientView;

public class ClientMain {

	public static void main(String[] args) {
		
		int scelta;
		boolean logged = false;
		String matricola, codice;
		ClientController clientController = new ClientController();
		
		do {
			
			scelta = ClientView.showMenu(logged);
			
			if(!logged) {
				
				if(scelta == 1) {
					
					matricola = ClientView.getMatricola();
					logged = clientController.login(matricola);
					
				}				
				
			}
			else {
				
				switch (scelta) {
				
				case 1:
					codice = ClientView.getCodiceEsame();
					clientController.iscrizione(codice);					
					break;
					
				case 2:
					codice = ClientView.getCodiceEsame();
					clientController.cancellazione(codice);
					break;
				
				case 3:
					clientController.listaIscrivibili();
					break;
				
				case 4:
					clientController.listaIscrizioni();
					break;
				
				case 5:
					clientController.showInfoStudent();
					break;
				
				case 0:
					logged = !clientController.logout();
					scelta = -1;
					break;

				default:
					break;
				}
				
			}
			
			
		}while(! (scelta == 0 && !logged) );
		
		clientController.close();
		

	}

}
