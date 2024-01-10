package Tablo.Controleur;

import Tablo.Modele.Liste;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Vue.VueListe;
import javafx.event.EventHandler;
import javafx.scene.input.*;

public class ControleurDeplacerTacheListe implements EventHandler<DragEvent> {

    private Modele modele;
    private VueListe vueListeDestination;
    private Tache tacheADeplacer;

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
            this.listeDragExited(dragEvent);
        }
        else if (dragEvent.getEventType() == DragEvent.DRAG_DROPPED)
        {
            this.listeDragDropped(dragEvent);
        }

    }

    private void listeDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != dragEvent.getTarget() && dragEvent.getTarget() instanceof VueListe) {
            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            Modele.setListeCourante(this.vueListeDestination.getNumListe());
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }


    // On drag dropped on source
    private void listeDragDropped(DragEvent dragEvent) {
        int numListe = Modele.getListeCourante();
            setTacheADeplacer();


            modele.deplacerTache(this.tacheADeplacer, numListe);



        dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }


    private void listeDragExited(DragEvent dragEvent) {

            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            int numListe = this.vueListeDestination.getNumListe();

        //dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }

    /**
     * Méthode qui retourne la tache à déplacer
     * Cette méthode cherche la tache dont l'attribut selectionnee est à true
     */
    private void setTacheADeplacer() {
        for (Liste l : this.modele.getListes()) {
            for (Tache t : l.getTaches()) {
                if (t.isEstSelectionnee()) {
                    this.tacheADeplacer = t;
                }
            }
        }}
}
