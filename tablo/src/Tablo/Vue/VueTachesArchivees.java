package Tablo.Vue;

import Tablo.Controleur.ControleurArchiveCliquee;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class VueTachesArchivees extends ScrollPane {


    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Conteneur tacges
     */
    private VBox tachesContainer;

    /**
     * Constructeur de la classe VueTachesArchivees
     * @param modele
     */
    public VueTachesArchivees(Modele modele) {
        super();
        this.setPrefSize(200, 200);

        this.modele = modele;

        this.tachesContainer = new VBox();

        this.tachesContainer.setSpacing(10);

        //Si la liste de taches archivées n'est pas vide
        if (modele.getTachesArchivees() != null) {

            //Pour chaques taches archivées
            for (Tache t : modele.getTachesArchivees()) {

                VueTache vt = new VueTache(t.getNumTache(), t.getTitre());

                //On diminue la taille de la tache
                vt.setPrefSize(200, 50);

                //On ajoute le controleur
                vt.setOnMouseClicked(new ControleurArchiveCliquee(modele));

                //On ajoute la vue de la tache
                tachesContainer.getChildren().add(vt);
            }
        }


        this.setContent(tachesContainer);
    }
}