package Tablo.Vue;

import Tablo.Controleur.ControleurArchiveCliquee;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Classe VueTachesArchivees qui hérite de ScrollPane et qui permet de gérer l'affichage des taches archivées
 */
public class VueTachesArchivees extends ScrollPane {

    /**
     * Constructeur de la classe VueTachesArchivees
     * @param modele
     */
    public VueTachesArchivees(Modele modele) {
        super();
        this.setPrefSize(200, 200);

        VBox tachesContainer = new VBox();

        tachesContainer.setSpacing(10);

        //Si la liste de taches archivées n'est pas vide
        if (modele.getTachesArchivees() != null) {

            //Pour chaques taches archivées
            for (Tache t : modele.getTachesArchivees()) {

                VueTache vt = new VueTache(t.getNumTache(), t.getTitre(), modele);

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
