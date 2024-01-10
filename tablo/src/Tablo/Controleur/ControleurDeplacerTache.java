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

public class ControleurDeplacerTache implements EventHandler<MouseEvent> {
    private Modele modele;
    private  static VueTache vueTacheADeplacer;
    private Dragboard db;

    public ControleurDeplacerTache(Modele m) {
        this.modele = m;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
        this.vueTacheADeplacer = (VueTache) mouseEvent.getSource();
        Modele.setTacheCourante(this.vueTacheADeplacer.getNumTache());
        this.modele.getTacheADeplacer();
        db = this.vueTacheADeplacer.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(this.vueTacheADeplacer.snapshot(new SnapshotParameters(), null));
        db.setContent(content);
        System.out.println("liste tache modele" + this.modele.getTaches());
        mouseEvent.consume();
    }


}
