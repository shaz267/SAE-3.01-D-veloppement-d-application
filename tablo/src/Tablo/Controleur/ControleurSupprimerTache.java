package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurSupprimerTache implements EventHandler<MouseEvent> {

	/**
	 * Modèle de l'application
	 */
	private Modele modele;

	public ControleurSupprimerTache(Modele m){ this.modele = m; }

	@Override
	public void handle(MouseEvent mouseEvent) {

		// On récupère le bouton supprimer
		Button supprimer = (Button) mouseEvent.getSource();
		// On le désactive
		supprimer.setDisable(true);

		//On récupère le numéro de la liste qui a été cliquée
		int numTache = Modele.getTacheCourante();

		//On retire la liste courante
		modele.retirerListe(modele.getListes().get(numTache - 1));
		modele.changerTacheCourante(numTache-1);
	}
}
