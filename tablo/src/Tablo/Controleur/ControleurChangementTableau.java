package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueDifferentTableaux;
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
     *
     * @param m Modele de l'application
     */
    public ControleurChangementTableau(Modele m) {
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer le changement de tableau
     *
     * @param mouseEvent Evenement qui va déclencher le changement de tableau
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère le bouton sur lequel on a cliqué
        Button b = (Button) mouseEvent.getSource();

        //On récupère le titre du bouton
        String titre = b.getText();

        int numTableau = -1;

        //On récupère le numéro du tableau en fonction du titre du bouton
        for (int i = 0; i < this.modele.getTableaux().size(); i++) {

            //Si le titre du tableau correspond au titre du bouton on récupère le numéro du tableau
            if (this.modele.getTableaux().get(i).getTitre().equals(titre)) {
                numTableau = this.modele.getTableaux().get(i).getNumTableau();
            }
        }

        //Si le numéro du tableau est différent de -1 (ce qui veut dire qu'on a trouvé le tableau)
        if (numTableau != -1) {

            //On change le tableau courant
            this.modele.changerTableauCourant(numTableau);
        }
    }
}
