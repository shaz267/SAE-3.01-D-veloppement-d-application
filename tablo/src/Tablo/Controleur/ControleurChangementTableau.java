package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Classe ControleurChangementTableau qui implémente l'interface EventHandler et qui permet de changer de tableau
 */
public class ControleurChangementTableau implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurChangementTableau
     * @param m Modele de l'application
     */
    public ControleurChangementTableau(Modele m){
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer le changement de tableau
     * @param mouseEvent Evenement qui va déclencher le changement de tableau
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère le bouton sur lequel on a cliqué
        Button b = (Button) mouseEvent.getSource();

        //On récupère le titre du bouton
        String titre = b.getText();

        //On récupère l'id du tableau
        int idTableau = this.modele.getNumTableaux(titre);

        //On change le tableau courant
        if (idTableau != -1){
            this.modele.changerTableauCourant(idTableau);
        }

        //On notifie les observateurs
        this.modele.notifierObservateurs();

    }
}
