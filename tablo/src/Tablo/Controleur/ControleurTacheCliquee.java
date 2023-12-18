package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControleurTacheCliquee implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurListeCliquee
     * @param m Modele de l'application
     */
    public ControleurTacheCliquee(Modele m){
        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère la liste qui a été cliquée
        VueTache vT = (VueTache) mouseEvent.getSource();

        vT.setStyle("-fx-background-color: #666666;");

        //On récupère l'id de la liste
        int numTache = vT.getNumTache();

        //On change la liste courante
        modele.changerTacheCourante(numTache);
    }
}
