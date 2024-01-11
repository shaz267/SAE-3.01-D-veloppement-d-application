package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueListe;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.*;

/**
 * Classe ControleurDeplacerListe qui implémente EventHandler et qui permet de gérer le déplacement d'une liste
 */
public class ControleurDeplacerListe implements EventHandler<MouseEvent> {

    /**
     * Attribut qui permet de gérer le modèle
     */
    private Modele modele;

    /**
     * Attribut qui permet de reconnaitre une liste
     */
    private static VueListe vueListeDestination;

    /**
     * Attribut qui permet de gérer le déplacement d'une liste
     */
    private Dragboard db;

    /**
     * Constructeur de la classe ControleurDeplacerListe
     * @param m Modele
     */
    public ControleurDeplacerListe(Modele m) {
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer le déplacement d'une liste
     * @param mouseEvent Evenement qui permet de gérer le déplacement d'une liste
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        this.vueListeDestination = (VueListe) mouseEvent.getSource();
        Modele.setListeCourante(this.vueListeDestination.getNumListe());
        this.modele.getListeADeplacer();
        db = this.vueListeDestination.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(this.vueListeDestination.snapshot(new SnapshotParameters(), null));
        db.setContent(content);

        mouseEvent.consume();
    }
}
