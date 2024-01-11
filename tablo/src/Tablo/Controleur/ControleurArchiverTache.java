package Tablo.Controleur;

import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Controleur permettant d'archiver une tache
 */
public class ControleurArchiverTache implements EventHandler<MouseEvent> {

    /**
     * Modèle de l'application
     */
    private final Modele modele;

    /**
     * Constructeur de la classe ControleurArchiverTache
     * @param m Modèle de l'application
     */
    public ControleurArchiverTache(Modele m) {

        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        // On récupère le bouton supprimer
        Button supprimer = (Button) mouseEvent.getSource();

        //On archive la tache courante. Si la tache est archivée, on ferme la fenêtre
        if (modele.archiverTache()) {
            // On notifie les observateurs
            modele.notifierObservateurs();

            // On ferme la fenêtre
            supprimer.getScene().getWindow().hide();
        }
    }
}
