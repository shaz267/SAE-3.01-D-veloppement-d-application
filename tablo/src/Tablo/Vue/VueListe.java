package Tablo.Vue;

import Tablo.Controleur.ControleurCreationTache;
import Tablo.Controleur.ControleurListeCliquee;
import Tablo.Controleur.ControleurTacheCliquee;
import Tablo.Modele.Liste;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class VueListe extends VBox {

    /**
     * Attribut qui permet de reconnaitre une liste
     */
    private int numListe;
    private Label labelTitre;

    /**
     * Constructeur de la classe VueListe
     * @param titre
     */
    public VueListe(String titre, int numListe, Modele modele) {

        super();
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

        this.setOnMouseEntered(event -> setStyle("-fx-background-color: #d9d9d9;"));
        this.setOnMouseExited(event -> setStyle("-fx-background-color: #e6e6e6;"));

        this.numListe = numListe;

        Button b = new Button("Ajouter une tache");

        b.setStyle("-fx-font-weight: bold;");
        b.setStyle("-fx-font-size: 20px;");
        b.setPrefSize(200, 50);

        //On met le bouton en VERT
        b.setStyle("-fx-background-color: #b6f5b6;");

        //On récupère la liste courante
        Liste l = modele.getListes().get(this.numListe - 1);

        int numTache = 1;
        for (Tache tache : l.getTaches()) {

            VueTache text = new VueTache(numTache, tache.getTitre(),modele);
            text.setOnMouseClicked(new ControleurTacheCliquee(modele));
            this.getChildren().add(text);
            numTache++;
        }

        this.getChildren().add(b);
        b.setOnAction(new ControleurCreationTache(modele));
        // On associe un évènement pour gérer le clic droit sur une liste
        this.setOnMousePressed(new ControleurListeCliquee(modele));
    }

    public int getNumListe() {
        return numListe;
    }
}