package Tablo.Vue;

import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Modele.TacheMere;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

public class VueSousTaches extends ScrollPane implements Observateur {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe VueSousTaches
     * @param modele
     */
    public VueSousTaches(Modele modele) {
        super();

        //Pour chaques sous taches de la tache courante
        for (Tache t : modele.getSousTaches()) {

            System.out.println(t.getTitre());
            //On ajoute la vue de la sous tache
            this.getChildren().add(new Text(t.getTitre()));
        }
    }

    @Override
    public void actualiser(Sujet s) {


    }
}
