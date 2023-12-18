package Tablo;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurSupprimerListe implements EventHandler<MouseEvent> {

	/**
	 * Modèle de l'application
	 */
	private Modele modele;

	public ControleurSupprimerListe(Modele m){ this.modele = m; }

	@Override
	public void handle(MouseEvent mouseEvent) {

		//On récupère la liste qui a été cliquée
		VueListe vl = (VueListe) mouseEvent.getSource();

		//On récupère le numero de la liste
		int numListe = vl.getNumListe()-1;

		//On change la liste courante
		modele.retirerListe(modele.getListes().get(numListe));
	}
}
