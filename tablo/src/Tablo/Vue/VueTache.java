package Tablo.Vue;

import Tablo.Controleur.ControleurDeplacerTache;
import Tablo.Controleur.ControleurDeplacerTacheListe;
import Tablo.Modele.Modele;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class VueTache extends Button implements Observateur {

    /**
     * Attribut qui permet de reconnaitre une tache
     */
    private int numTache;

    /**
     * Constructeur de la classe VueTache
     * @param titre
     */
    public VueTache(int numTache, String titre, Modele modele) {
        super(titre);

        this.numTache = numTache;

        //On change  le style de la tache
        this.setStyle("-fx-font-weight: bold;");
        this.setStyle("-fx-font-size: 40px;");
        this.setStyle("-fx-background-color: #f8b9a7;");
        this.setPrefSize(200, 50);

        //Le texte en gras
        this.setFont(javafx.scene.text.Font.font("Verdana", 15));

        // Deplacement de taches
        ControleurDeplacerTache cdt = new ControleurDeplacerTache(modele);
        this.setOnDragDetected(cdt);

    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {


    }

    public int getNumTache() {
        return numTache;
    }
}
