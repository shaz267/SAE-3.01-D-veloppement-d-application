package Tablo;

import javafx.scene.layout.VBox;

public class VueListe extends VBox implements Observateur {

    /**
     * Attribut qui permet de reconnaitre une liste
     */
    private int numListe;

    /**
     * Constructeur de la classe VueListe
     * @param numListe
     * @param titre
     */
    public VueListe(int numListe, String titre) {

        super();
        this.numListe = numListe;
        this.setSpacing(10);

        //On leur donne une taille fixe
        this.setMinWidth(200);
        this.setMaxHeight(300);

        //On leur donne une couleur de fond
        this.setStyle("-fx-background-color: #e6e6e6;");
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {


    }
}
