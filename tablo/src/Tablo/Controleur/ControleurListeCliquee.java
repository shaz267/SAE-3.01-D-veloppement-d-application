package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueListe;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControleurListeCliquee implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurListeCliquee
     * @param m Modele de l'application
     */
    public ControleurListeCliquee(Modele m){
        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère la liste qui a été cliquée
        VueListe vl = (VueListe) mouseEvent.getSource();

        vl.setStyle("-fx-background-color: #666666;");

        //On récupère l'id de la liste
        int numListe = vl.getNumListe();

        //On change la liste courante
        modele.changerListeCourante(numListe);
    }
}
