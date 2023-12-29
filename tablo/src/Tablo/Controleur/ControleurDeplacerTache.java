package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.scene.input.*;

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
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
            handleMousePressed(mouseEvent);
        } else if (mouseEvent.getEventType() == MouseEvent.DRAG_DETECTED) {
            handleDragDetected(mouseEvent);
        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            handleMouseDragged(mouseEvent);
        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
            handleMouseReleased(mouseEvent);
        }
    }

    private void handleMousePressed(MouseEvent event) {
        vueTacheDragged = (VueTache) event.getSource();
    }

    private void handleDragDetected(MouseEvent event) {
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
        Tache tache = modele.getTaches().get(vueTacheDragged.getNumTache() - 1);
        modele.deplacerTache(tache);
    }
}
