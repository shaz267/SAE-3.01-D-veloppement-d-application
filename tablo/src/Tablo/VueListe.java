package Tablo;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
public class VueListe extends VBox implements Observateur {

    /**
     * Attribut qui permet de reconnaitre une liste
     */
    private int numListe;
    private Label labelTitre;

    /**
     * Constructeur de la classe VueListe
     * @param numListe
     * @param titre
     */
    public VueListe(int numListe, String titre) {

        super();
        this.numListe = numListe;
        this.setSpacing(10);
        this.setMinWidth(200);

        // Affichage du titre de la liste
        this.labelTitre = new Label(titre);
        this.labelTitre.setStyle("-fx-font-weight: bold;");

        this.getChildren().add(this.labelTitre);

        // Couleur de fond de la liste
        this.setStyle("-fx-background-color: #e6e6e6;");

        this.setMinHeight(700);
        this.setPadding(new Insets(10));


        Modele.nombresListes++;
        this.numListe = Modele.nombresListes;

        this.setOnMouseEntered(event -> setStyle("-fx-background-color: #d9d9d9;"));
        this.setOnMouseExited(event -> setStyle("-fx-background-color: #e6e6e6;"));

    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {
    //TODO : actualiser la vue liste avec les nouvelles tâches

    }

    public int getNumListe() {
        return numListe;
    }
}
