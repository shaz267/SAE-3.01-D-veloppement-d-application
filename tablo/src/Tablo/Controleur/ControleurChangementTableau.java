package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurChangementTableau implements EventHandler<MouseEvent> {

    private Modele modele;

    public ControleurChangementTableau(Modele m){
        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère le bouton sur lequel on a cliqué
        Button b = (Button) mouseEvent.getSource();

        //On récupère le titre du bouton
        String titre = b.getText();

        //On récupère l'id du tableau
        int numTableau = this.modele.getNumTableaux(titre);

        //On change le tableau courant
        if (numTableau != -1){
            Modele.setTableauCourant(numTableau);
//            this.modele.changerListeCourante(0);
//            this.modele.changerTacheCourante(0);
        }

        //On gère l'exception ConcurrentModificationException en notifiant les observateurs
        this.modele.notifierObservateurs();

    }
}
