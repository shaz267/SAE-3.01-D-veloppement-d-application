package Tablo.Vue;

import Tablo.Modele.Modele;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.text.Text;

/**
 * Classe VueTitreTableau qui hérite de Text et qui implémente l'interface Observateur
 */
public class VueTitreTableau extends Text implements Observateur {

    public VueTitreTableau() {
        // On appelle le constructeur de la classe mère avec un texte vide
        super("");

        //On change le style du titre du tableau
        this.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 35px; -fx-fill: rgba(70,117,67,0.71); -fx-underline: true;");


    }

    /**
     * Méthode qui permet de mettre à jour la vue du titre du tableau
     *
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        //On récupère le modele
        Modele m = (Modele) s;

        //On change le texte du titre du tableau
        this.setText(m.getTitreTableau());

    }
}