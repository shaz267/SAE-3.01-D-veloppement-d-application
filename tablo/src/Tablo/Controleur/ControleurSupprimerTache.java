package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControleurSupprimerTache implements EventHandler<MouseEvent> {

    /**
     * Modèle de l'application
     */
    private Modele modele;

    public ControleurSupprimerTache(Modele m){

        this.modele = m;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On supprime la tache
        //modele.supprimerTache();
    }
}
