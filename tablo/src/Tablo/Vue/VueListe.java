package Tablo.Vue;

import Tablo.Controleur.ControleurCreationTache;
import Tablo.Controleur.ControleurListeCliquee;
import Tablo.Controleur.ControleurDeplacerTacheListe;
import Tablo.Controleur.ControleurTacheCliquee;
import Tablo.Modele.Liste;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

/**
 * Classe VueListe qui hérite de VBox et qui permet de gérer l'affichage d'une liste
 */
public class VueListe extends VBox {

    /**
     * Attribut qui permet de reconnaitre une liste
     */
    private int numListe;

    /**
     * Attribut qui permet d'afficher le titre de la liste
     */
    private Label labelTitre;

    /**
     * Constructeur de la classe VueListe
     *
     * @param titre
     */
    public VueListe(String titre, int numListe, Modele modele) {

        // On appelle le constructeur de la classe mère
        super();
        // On définit les propriétés de la VBox
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

        // On associe un évènement pour gérer le survol de la liste
        this.setOnMouseEntered(event -> setStyle("-fx-background-color: #d9d9d9;"));
        this.setOnMouseExited(event -> setStyle("-fx-background-color: #e6e6e6;"));

        // On initialise l'attribut numListe
        this.numListe = numListe;

        // On ajoute un bouton pour ajouter une tâche
        Button btnAjouterTache = new Button("Ajouter une tache");

        btnAjouterTache.setStyle("-fx-font-weight: bold;");
        btnAjouterTache.setStyle("-fx-font-size: 20px;");
        btnAjouterTache.setPrefSize(200, 50);

        //On met le bouton en VERT
        btnAjouterTache.setStyle("-fx-background-color: #b6f5b6;");

        //On récupère la liste courante
        Liste l = modele.getListes().get(this.numListe - 1);

        //On initialise le numéro de la tache
        int numTache = 1;
        //On parcourt les taches de la liste
        for (Tache tache : l.getTaches()) {

            //Si la tache n'est pas archivée, on l'affiche
            if (!tache.isArchivee()) {
                VueTache vtache = new VueTache(numTache, tache.getTitre(), modele);
                vtache.setOnMouseClicked(new ControleurTacheCliquee(modele));
                this.getChildren().add(vtache);
            }
            //On incrémente le numéro de la tache quand meme, car on veut que le numéro de la tache soit le meme que l'index de la tache dans la liste
            numTache++;
        }

        //On ajoute le bouton à la liste
        this.getChildren().add(btnAjouterTache);
        btnAjouterTache.setOnAction(new ControleurCreationTache(modele));
        // On associe un évènement pour gérer le clic droit sur une liste
        this.setOnMousePressed(new ControleurListeCliquee(modele));

        // On associe un évènement pour gérer le déplacement de tâches
        this.setOnDragOver(new ControleurDeplacerTacheListe(modele));
        this.setOnDragExited(new ControleurDeplacerTacheListe(modele));
        this.setOnDragDropped(new ControleurDeplacerTacheListe(modele));

    }

    /**
     * Méthode qui permet de récupérer le numéro de la liste
     *
     * @return le numéro de la liste
     */
    public int getNumListe() {
        return numListe;
    }
}
