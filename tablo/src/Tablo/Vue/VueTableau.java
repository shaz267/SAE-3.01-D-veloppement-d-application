package Tablo.Vue;

import Tablo.Controleur.*;
import Tablo.Modele.Liste;
import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class VueTableau extends ScrollPane implements Observateur {

    /**
     * Attribut qui permet de reconnaitre un tableau
     */
    private int numTableau;
    private Button ajouterListe;
    private HBox listeContainer;

    /**
     * Constructeur de la classe VueTableau
     * @param titre
     */
    public VueTableau(int numTableau, String titre) {
        super();

        ajouterListe = new Button("Ajouter une liste");
        ajouterListe.setStyle("-fx-font-weight: bold;");
        ajouterListe.setStyle("-fx-font-size: 20px;");
        ajouterListe.setStyle("-fx-background-color: #f8b9a7;");
        ajouterListe.setPrefSize(200, 50);

        listeContainer = new HBox();
        listeContainer.setSpacing(10);
        listeContainer.setFillHeight(true);

        HBox container = new HBox();
        container.getChildren().addAll(listeContainer, ajouterListe);

        this.setContent(container);
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        //On efface tout
        listeContainer.getChildren().clear();

        Modele m = (Modele) s;

        //On récupère le tableau courant
        Tableau t = m.getTableaux().get(m.getTableaux().size() - 1);

        listeContainer.getChildren().clear();

        int numListe = 1;
        //On parcourt les listes du tableau
        for (Liste liste : t.getListes()) {

            VueListe vueListe = new VueListe(liste.getTitre(), numListe, m);
            vueListe.setOnMouseClicked(new ControleurListeCliquee(m));
            vueListe.setOnMouseClicked(new ControleurSupprimerListe(m));
            listeContainer.getChildren().add(vueListe);
            numListe++;
        }
    }

    public Button getAjouterListe() {
        return ajouterListe;
    }
}
