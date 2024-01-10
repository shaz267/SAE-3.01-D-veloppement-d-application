package Tablo.Vue;

import Tablo.Controleur.ControleurSousTacheCLiquee;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Classe VueSousTaches qui hérite de ScrollPane et qui permet de gérer l'affichage des sous taches
 */
public class VueSousTaches extends ScrollPane {

    /**
     * Constructeur de la classe VueSousTaches
     *
     * @param modele
    l     */
    public VueSousTaches(Modele modele) {
        super();
        this.setPannable(true);

        //On initialise le conteneur des sous taches
        GridPane sousTachesContainer = new GridPane();

        List<Tache> sousTaches = modele.getSousTaches();

        //Si la liste de sous taches n'est pas vide
        if (sousTaches != null) {
            //Pour chaques sous taches de la tache courante

            int i = 0;
            for (Tache t : sousTaches) {

                VueTache vt = new VueTache(t.getNumTache(), t.getTitre(), modele);

                //On diminue la taille de la tache
                vt.setPrefSize(100, 25);

                //On ajoute le controleur
                vt.setOnMouseClicked(new ControleurSousTacheCLiquee(modele));

                //On ajoute la vue de la tache avec un max de 4 taches par ligne
                sousTachesContainer.add(vt, i % 4, i / 4);

                i++;
            }
        }

        //On ajoute le conteneur des sous taches dans le ScrollPane
        this.setContent(sousTachesContainer);
    }
}
