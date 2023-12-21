package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Classe ControleurSupprimerTableau qui implémente l'interface EventHandler et qui permet de supprimer un tableau
 */
public class ControleurSupprimerTableau implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Numéro du tableau à supprimer
     */
    private int numTableau;

    /**
     * Constructeur de la classe ControleurSupprimerTableau
     * @param modele Modele de l'application
     */
    public ControleurSupprimerTableau(Modele modele, int numTableau) {
        this.modele = modele;
        this.numTableau = numTableau;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if (Modele.getTableauCourant() == this.numTableau) {
            this.modele.changerTableauCourant(this.modele.getTableaux().get(0).getNumTableauMax());
        }
        //On retire le tableau
        this.modele.retirerTableau(this.numTableau);

        //On notifie les observateurs
        this.modele.notifierObservateurs();

        //On informe le logger
        Loggeur.enregistrer("Suppression du tableau " + this.numTableau);

    }
}
