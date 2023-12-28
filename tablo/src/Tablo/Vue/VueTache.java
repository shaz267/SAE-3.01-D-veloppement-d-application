package Tablo.Vue;

import Tablo.Controleur.ControleurTacheCliquee;
import Tablo.Modele.Modele;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.Button;

public class VueTache extends Button{

    /**
     * Attribut qui permet de reconnaitre une tache
     */
    private int numTache;

    /**
     * Constructeur de la classe VueTache
     *
     * @param titre
     */
    public VueTache(int numTache, String titre) {
        super(titre);

        this.numTache = numTache;

        //On change  le style de la tache
        this.setStyle("-fx-font-weight: bold;");
        this.setStyle("-fx-font-size: 40px;");
        this.setStyle("-fx-background-color: #f8b9a7;");
        this.setPrefSize(200, 50);
        this.setFont(javafx.scene.text.Font.font("Verdana", 15));
    }

    public int getNumTache() {
        return numTache;
    }
}
