package Tablo.Controleur;

import Tablo.Modele.Liste;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Vue.VueTache;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurEnvoyerArchive implements EventHandler<ActionEvent> {

    /**
     * Modèle de l'application
     */
    private final Modele modele;

    /**
     * Vue de la tache
     */
    private final VueTache vT;

    /**
     * Constructeur de la classe ControleurEnvoyerArchive
     * @param m
     */
    public ControleurEnvoyerArchive(Modele m , VueTache vT) {

        this.modele = m;
        this.vT = vT;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        //On récupère la liste et la tache courante
        Liste l = modele.getListes().get(vT.getNumListe() - 1);
        Tache t = l.getTaches().get(vT.getNumTache() - 1);

        //On archive la tache
        t.archiver(false);

        //On notifie les observateurs
        modele.notifierObservateurs();

        //On ferme la fenêtre
        ((Button)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
