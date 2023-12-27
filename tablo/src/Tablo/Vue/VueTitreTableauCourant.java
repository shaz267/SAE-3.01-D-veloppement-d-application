package Tablo.Vue;

import Tablo.Modele.Modele;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.text.Text;

/**
 * Classe VueTitreTableauCourant qui hérite de Text et qui implémente l'interface Observateur
 */
public class VueTitreTableauCourant extends Text implements Observateur {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe VueTitreTableauCourant
     * @param titre Titre du tableau courant
     */
    public VueTitreTableauCourant(String titre) {
        super(titre);

        //On change  le style du titre
        this.setStyle("-fx-font-weight: bold;");
        this.setStyle("-fx-font-size: 20px;");
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {
        this.modele = (Modele) s;

        this.setText(modele.getTableaux().get(Modele.getTableauCourant()).getTitre());
    }
}
