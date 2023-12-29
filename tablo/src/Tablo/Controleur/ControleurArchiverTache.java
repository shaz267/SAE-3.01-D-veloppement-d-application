package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurArchiverTache implements EventHandler<MouseEvent> {

	/**
	 * Modèle de l'application
	 */
	private Modele modele;

	public ControleurArchiverTache(Modele m){

		this.modele = m;
	}

	@Override
	public void handle(MouseEvent mouseEvent) {

		// On récupère le bouton supprimer
		Button supprimer = (Button) mouseEvent.getSource();


		//On récupère le numéro de la liste qui a été cliquée
		int numTache = Modele.getTacheCourante();

		//On archive la tache courante. Si la tache est archivée, on désactive le bouton supprimer
		if (modele.archiverTache()){

			modele.notifierObservateurs();
			// On désactive le bouton supprimer
			supprimer.getScene().getWindow().hide();
		}
		//Modele.setTacheCourante(numTache - 1);
	}
}
