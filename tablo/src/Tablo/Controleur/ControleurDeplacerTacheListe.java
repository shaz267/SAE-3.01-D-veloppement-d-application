package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Vue.VueListe;
import Tablo.Controleur.ControleurDeplacerTache;
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
        else if (dragEvent.getEventType() == DragEvent.DRAG_EXITED)
        {
            this.ListeDragExited(dragEvent);
        }
        else if (dragEvent.getEventType() == DragEvent.DRAG_DROPPED)
        {
            this.ListeDragDropped(dragEvent);
        }
        System.out.println("DragEvent : " + dragEvent.getEventType());

    }

    private void listeDragOver(DragEvent dragEvent)
    {
        if (dragEvent.getGestureSource() != dragEvent.getTarget() && dragEvent.getDragboard().hasString())
        {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            Modele.setListeDestination(this.vueListeDestination.getNumListe());
        }
        dragEvent.consume();
    }

    private void ListeDragExited(DragEvent dragEvent)
    {
        if (!dragEvent.isAccepted())
        {
            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            //this.vueListeDestination.setStyle("-fx-background-color: #f4a8f6;");
        }
        dragEvent.consume();
    }

    private void ListeDragDropped(DragEvent dragEvent)
    {
        boolean success = false;
        int numTache = ControleurDeplacerTache.getVueTacheADeplacer().getNumTache();
            Tache tache = this.modele.getTaches().get(numTache);
            this.modele.retirerTache(tache);
            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            success = true;

        dragEvent.setDropCompleted(success);
        dragEvent.consume();
        System.out.println("DragEvent : " + dragEvent.getEventType());
    }


}
