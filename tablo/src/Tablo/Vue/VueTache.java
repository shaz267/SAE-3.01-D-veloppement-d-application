package Tablo.Vue;

import Tablo.Modele.Modele;
import javafx.scene.control.Button;
import Tablo.Controleur.ControleurDeplacerTache;

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
    public VueTache(int numTache, String titre, Modele modele) {
        super(titre);

        // On initialise l'attribut numTache
        this.numTache = numTache;

        //On change  le style de la tache
        this.setStyle("-fx-background-color: rgba(187,108,87,0.71); -fx-font-family: 'Roboto Light'; -fx-text-fill: white; -fx-font-size: 15px;");
        this.setPrefSize(200, 50);
        this.setOnDragDetected(new ControleurDeplacerTache(modele));
    }

    /**
     * Méthode qui permet de récupérer le numéro de la tache
     * @return numTache
     */
    public int getNumTache() {
        return numTache;
    }
}
