package Tablo;

import javafx.scene.control.Button;
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
     * Modele de l'application
     */
    private Modele modele;

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


        this.getChildren().add(b);

        b.setOnAction(new ControleurCreationTache(modele));
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        System.out.println("Actualisation de la liste");

        //On efface tout
        this.getChildren().clear();

        Modele m = (Modele) s;

        System.out.println(m.getListes().get(this.numListe - 1).getTaches());

        //On récupère la liste courante
        Liste l = m.getListes().get(this.numListe - 1);

        for (Tache tache : l.getTaches()) {

            VueTache vueTache = new VueTache(tache.getTitre(), m);
            vueTache.setOnMouseClicked(new ControleurTacheCliquee(m));
            this.getChildren().add(vueTache);
        }

        Button b = new Button("Ajouter une tache");


        this.getChildren().add(b);
    }

    public int getNumListe() {
        return numListe;
    }
}
