package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * Classe ControleurDeplacerTache qui implémente EventHandler et qui permet de gérer le déplacement d'une tache
 */
public class ControleurDeplacerTache implements EventHandler<MouseEvent> {

    /**
     * Attribut qui permet de gérer le modèle
     */
    private Modele modele;

    /**
     * Attribut qui permet de reconnaitre une tache
     */
    private  static VueTache vueTacheADeplacer;

    /**
     * Attribut qui permet de gérer le déplacement d'une tache
     */
    private Dragboard db;

    /**
     * Constructeur de la classe ControleurDeplacerTache
     * @param m Modele
     */
    public ControleurDeplacerTache(Modele m) {
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer le déplacement d'une tache
     * @param mouseEvent Evenement qui permet de gérer le déplacement d'une tache
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        this.vueTacheADeplacer = (VueTache) mouseEvent.getSource();
        Modele.setTacheCourante(this.vueTacheADeplacer.getNumTache());
        this.modele.getTacheADeplacer();
        db = this.vueTacheADeplacer.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(this.vueTacheADeplacer.snapshot(new SnapshotParameters(), null));
        db.setContent(content);
        mouseEvent.consume();
    }


}
