package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueListe;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.scene.input.*;

public class ControleurDeplacerTacheListe implements EventHandler<DragEvent> {

    private Modele modele;
    private VueListe vueListeDestination;

    public ControleurDeplacerTacheListe(Modele m) {
        this.modele = m;
    }

    @Override
    public void handle(DragEvent dragEvent)
    {

        if (dragEvent.getEventType() == DragEvent.DRAG_OVER)
        {
            this.listeDragOver(dragEvent);
        }
        else if (dragEvent.getEventType() == DragEvent.DRAG_ENTERED)
        {
            this.ListeDragEntered(dragEvent);
        }
        else if (dragEvent.getEventType() == DragEvent.DRAG_EXITED)
        {
            this.ListeDragExited(dragEvent);
        }
        else if (dragEvent.getEventType() == DragEvent.DRAG_DROPPED)
        {
            this.ListeDragDropped(dragEvent);
        }
        else if (dragEvent.getEventType() == DragEvent.DRAG_DONE)
        {
           // this.ListeDragDone(dragEvent);
        }

    }

    private void listeDragOver(DragEvent dragEvent)
    {
        if (dragEvent.getGestureSource() != dragEvent.getTarget() && dragEvent.getDragboard().hasString())
        {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    private void ListeDragEntered(DragEvent dragEvent)
    {
        if (dragEvent.getGestureSource() != dragEvent.getTarget() && dragEvent.getDragboard().hasString())
        {
           this.vueListeDestination = (VueListe) dragEvent.getTarget();
            this.vueListeDestination.setStyle("-fx-background-color: #f9e9a9;");
        }
        dragEvent.consume();
    }

    private void ListeDragExited(DragEvent dragEvent)
    {
        if (!dragEvent.isAccepted())
        {
            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            this.vueListeDestination.setStyle("-fx-background-color: #f4a8f6;");
        }
        dragEvent.consume();
    }

    private void ListeDragDropped(DragEvent dragEvent)
    {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString())
        {
            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            this.vueListeDestination.setStyle("-fx-background-color: #f4a8f6;");
            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }


}
