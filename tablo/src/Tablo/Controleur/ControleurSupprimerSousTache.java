package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurSupprimerSousTache implements EventHandler<MouseEvent> {

    /**
     * Modèle de l'application
     */
    private Modele modele;

    /**
     * Numéro de la tâche
     */
    private int numTache;

    public ControleurSupprimerSousTache(Modele m, int numTache) {

        this.modele = m;
        this.numTache = numTache;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère le bouton supprimer
        Button supprimer = (Button) mouseEvent.getSource();

        if (this.modele.supprimerSousTache(this.numTache)){

            supprimer.getScene().getWindow().hide();
        }
    }
}
