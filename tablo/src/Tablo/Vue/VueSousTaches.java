package Tablo.Vue;

import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Classe VueSousTaches qui hérite de ScrollPane et qui permet de gérer l'affichage des sous taches
 */
public class VueSousTaches extends ScrollPane {

    /**
     * Constructeur de la classe VueSousTaches
     *
     * @param modele
     */
    public VueSousTaches(Modele modele) {
        super();

        //On initialise le conteneur des sous taches
        VBox sousTachesContainer = new VBox();

        List<Tache> sousTaches = modele.getSousTaches();

        //Si la liste de sous taches n'est pas vide
        if (sousTaches != null) {
            //Pour chaques sous taches de la tache courante
            for (Tache t : sousTaches) {

                //On ajoute la vue de la sous tache
                sousTachesContainer.getChildren().add(new Text(t.getTitre()));
            }
        }

        //On ajoute le conteneur des sous taches dans le ScrollPane
        this.setContent(sousTachesContainer);
    }
}
