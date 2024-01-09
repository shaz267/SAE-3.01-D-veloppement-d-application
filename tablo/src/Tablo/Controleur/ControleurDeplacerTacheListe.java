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

    private boolean listeDragOver(DragEvent dragEvent) {
        boolean accept = false;
        if (dragEvent.getGestureSource() != dragEvent.getTarget() && dragEvent.getDragboard().hasString()) {
            System.out.println("Drag Over - Source: " + dragEvent.getGestureSource() + ", Target: " + dragEvent.getTarget());
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            this.vueListeDestination = (VueListe) dragEvent.getTarget();
            accept = true;
        }
        dragEvent.consume();
        return accept;
    }

    // On drag dropped on source
    private void listeDragDropped(DragEvent dragEvent){
        this.vueListeDestination = (VueListe) dragEvent.getTarget();
        System.out.println("Drag Dropped - Source: " + dragEvent.getGestureSource() + ", Target: " + dragEvent.getTarget());        // On récupère la liste de destination
        int numListe = this.vueListeDestination.getNumListe();
        System.out.println("numListe : " + numListe);

        // On vérifie que le numéro de la liste est valide
        if (numListe >= 0 && numListe < modele.getListes().size()) {
            System.out.println("deplacement de la tache");
            this.tacheADeplacer = getTacheADeplacer();
            int numTache = Modele.getTacheCourante();
            System.out.println("numTache : " + Modele.getTacheCourante());
            // On vérifie que le numéro de la tache est valide
                System.out.println("tache valide");
                Tache tache = modele.getTaches().get(Modele.getTacheCourante());
                modele.deplacerTache(tache, numListe);
                System.out.println("Tache déplacée " + tache.getTitre() + " dans la liste " + numListe);
                System.out.println(this.modele.getListes().get(numListe).getTaches().get(numTache).getTitre());

        }

        dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }

    private void listeDragExited(DragEvent dragEvent) {
        this.vueListeDestination = (VueListe) dragEvent.getTarget();
        System.out.println("Drag Dropped - Source: " + dragEvent.getGestureSource() + ", Target: " + dragEvent.getTarget());        // On récupère la liste de destination


















        //dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }

    public Tache getTacheADeplacer() {
        Tache tache = null;
        for (int i = 0; i < modele.getTaches().size(); i++) {
            if (modele.getTaches().get(i).getNumTache() == Modele.getTacheCourante()) {
                tache = modele.getTaches().get(i);
            }
        }
        return tache;
    }


}
