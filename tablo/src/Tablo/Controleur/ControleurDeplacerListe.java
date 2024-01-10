package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueListe;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.*;

public class ControleurDeplacerListe implements EventHandler<MouseEvent> {
    private Modele modele;
    private static VueListe vueListeDestination;
    private Dragboard db;

    public ControleurDeplacerListe(Modele m) {
        this.modele = m;
    }

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
