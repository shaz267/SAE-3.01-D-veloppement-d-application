package Tablo;

import javafx.scene.text.Text;

public class VueTache extends Text implements Observateur {

    /**
     * Attribut qui permet de reconnaitre une tache
     */
    private int numTache;

    /**
     * Constructeur de la classe VueTache
     * @param titre
     */
    public VueTache(String titre, Modele modele) {

        super(titre);

        this.setStyle("-fx-background-color: #e6e6e6;");
        this.setWrappingWidth(200);
        this.setLineSpacing(10);


        this.numTache = modele.getTaches().size();
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
