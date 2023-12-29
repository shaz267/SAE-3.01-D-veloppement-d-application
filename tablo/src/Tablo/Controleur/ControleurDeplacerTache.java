package Tablo.Controleur;

import Tablo.Modele.Liste;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.scene.input.*;

import java.util.List;

public class ControleurDeplacerTache implements EventHandler<MouseEvent> {

    private Modele modele;
    private VueTache vueTacheDragged;

    public ControleurDeplacerTache(Modele m) {
        this.modele = m;
    }

    /**
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent)
    {
        if (mouseEvent.getEventType() == MouseEvent.DRAG_DETECTED) {
            handleDragDetected(mouseEvent);
        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            handleMouseDragged(mouseEvent);
        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
            handleMouseReleased(mouseEvent);
        }
    }



    private void handleDragDetected(MouseEvent event) {
        if (event.getSource() instanceof VueTache) vueTacheDragged = (VueTache) event.getSource();
        System.out.println("Mouse pressed on " + vueTacheDragged.getNumTache());
            Dragboard dragboard = vueTacheDragged.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString("test");
            dragboard.setContent(content);

            event.consume();

    }

    private void handleMouseDragged(MouseEvent event) {
        // update location of the draggable node
        vueTacheDragged.setLayoutX(event.getX() + vueTacheDragged.getLayoutX());
    }

    private void handleMouseReleased(MouseEvent event) {
        int taskNumber = vueTacheDragged.getNumTache() - 1;

        if (taskNumber >= 0 && taskNumber < modele.getTaches().size()) {
            Tache tache = modele.getTaches().get(taskNumber);
            Liste listeDestination = determinerListeDestination();
            modele.deplacerTache(tache, listeDestination.getNumListe());
        }
    }

    private Liste determinerListeDestination() {
        Liste listeDestination = null;

        for (Liste liste : modele.getListes()) {
            if (liste.getTaches().contains(vueTacheDragged.getNumTache())) {
                listeDestination = liste;
            }
        }

        return listeDestination;
    }

}
