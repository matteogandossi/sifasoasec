package main;

import controller.ClientController;
import view.ClientView;

public class ClientMain {

	public static void main(String[] args) {
		
		int scelta;
		boolean logged = false, listNotEmpty;
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
					listNotEmpty = clientController.listaIscrivibili();
					if(listNotEmpty) {
						codice = ClientView.getCodiceEsame();
						clientController.iscrizione(codice);
					}
					else
						System.out.println("Nessuna iscrizione possibile.");
										
					break;
					
				case 2:
					listNotEmpty = clientController.listaIscrizioni();
					if(listNotEmpty) {
						codice = ClientView.getCodiceEsame();
						clientController.cancellazione(codice);
					}
					else
						System.out.println("Nessuna cancellazione possibile.");
					
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
