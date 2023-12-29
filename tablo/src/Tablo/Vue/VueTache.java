package Tablo.Vue;

import javafx.scene.control.Button;

/**
 * Classe VueTache qui hérite de Button et permet de gérer l'affichage d'une tache
 */
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

        // On initialise l'attribut numTache
        this.numTache = numTache;

        //On change  le style de la tache
        this.setStyle("-fx-font-weight: bold;");
        this.setStyle("-fx-font-size: 40px;");
        this.setStyle("-fx-background-color: #f8b9a7;");
        this.setPrefSize(200, 50);
        this.setFont(javafx.scene.text.Font.font("Verdana", 15));
    }

    /**
     * Méthode qui permet de récupérer le numéro de la tache
     * @return numTache
     */
    public int getNumTache() {
        return numTache;
    }
}
