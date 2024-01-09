package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Controleur permettant de supprimer totalement une tache une fois qu'elle est archivée
 */
public class ControleurSupprimerArchive implements EventHandler<MouseEvent> {

    /**
     * Modèle de l'application
     */
    private Modele modele;

    public ControleurSupprimerArchive(Modele m) {

        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //TODO : Supprimer la tache
    }
}
