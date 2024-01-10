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
        else

        System.out.println("DragEvent : " + dragEvent.getEventType());

    }

    private void listeDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() != dragEvent.getTarget() && dragEvent.getTarget() instanceof VueListe) {
            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            Modele.setListeCourante(this.vueListeDestination.getNumListe());
            System.out.println("Drag Over - Source: " + Modele.getListeCourante());
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }


    // On drag dropped on source
    private void listeDragDropped(DragEvent dragEvent) {
        System.out.println("Drag Dropped - Source: " + dragEvent.getGestureSource() + ", Target: " + dragEvent.getTarget());        // On récupère la liste de destination
        int numListe = Modele.getListeCourante();

            System.out.println("numListe : " + Modele.getListeCourante());

            System.out.println("deplacement de la tache");
            //System.out.println("numTache : " + Modele.getTacheCourante());
            setTacheADeplacer();


            modele.deplacerTache(this.tacheADeplacer, numListe);



        dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }


    private void listeDragExited(DragEvent dragEvent) {

            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            System.out.println("Drag Exited - Source: " + dragEvent.getGestureSource() + ", Target: " + dragEvent.getTarget());        // On récupère la liste de destination
            int numListe = this.vueListeDestination.getNumListe();
            System.out.println("numListe : " + numListe);


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
