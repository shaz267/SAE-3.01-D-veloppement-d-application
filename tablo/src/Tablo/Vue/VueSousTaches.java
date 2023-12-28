package Tablo.Vue;

import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Modele.TacheMere;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class VueSousTaches extends ScrollPane {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Conteneur des sous taches
     */
    private VBox sousTachesContainer;

    /**
     * Constructeur de la classe VueSousTaches
     *
     * @param modele
     */
    public VueSousTaches(Modele modele) {
        super();

        this.modele = modele;

        this.sousTachesContainer = new VBox();

        List<Tache> sousTaches = modele.getSousTaches();

        //Si la liste de sous taches n'est pas vide
        if (sousTaches != null) {
            //Pour chaques sous taches de la tache courante
            for (Tache t : sousTaches) {

                //On ajoute la vue de la sous tache
                sousTachesContainer.getChildren().add(new Text(t.getTitre()));
            }
        }

        this.setContent(sousTachesContainer);

    }
}
