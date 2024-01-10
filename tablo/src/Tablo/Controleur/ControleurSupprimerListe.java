package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Controleur permettant de supprimer une liste
 */
public class ControleurSupprimerListe implements EventHandler<MouseEvent> {

    /**
     * Modèle de l'application
     */
    private Modele modele;

    public ControleurSupprimerListe(Modele m) {
        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //On retire la liste courante, si on a pu la retirer on désactive le bouton
        if (modele.retirerListe()) {

            // On récupère le bouton supprimer
            Button supprimer = (Button) mouseEvent.getSource();

            // On ferme la fenêtre
            supprimer.getScene().getWindow().hide();
        }
    }
}
