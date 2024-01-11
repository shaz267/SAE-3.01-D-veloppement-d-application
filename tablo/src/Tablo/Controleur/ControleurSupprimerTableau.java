package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Classe ControleurSupprimerTableau qui implémente l'interface EventHandler et qui permet de supprimer un tableau
 */
public class ControleurSupprimerTableau implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private final Modele modele;

    /**
     * Constructeur de la classe ControleurSupprimerTableau
     *
     * @param modele Modele de l'application
     */
    public ControleurSupprimerTableau(Modele modele) {
        this.modele = modele;
    }

    /**
     * Méthode qui permet de gérer la suppression d'un tableau
     *
     * @param mouseEvent Evènement qui permet de gérer la suppression d'un tableau
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère le bouton supprimer
        Button supprimer = (Button) mouseEvent.getSource();

        if (this.modele.retirerTableau()) {
            //On ferme la fenêtre
            supprimer.getScene().getWindow().hide();
        }
    }
}
