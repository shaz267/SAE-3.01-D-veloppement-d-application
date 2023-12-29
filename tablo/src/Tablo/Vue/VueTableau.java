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

/**
 * Classe VueTableau qui hérite de ScrollPane et qui implémente l'interface Observateur
 */
public class VueTableau extends ScrollPane implements Observateur {

    /**
     * Bouton qui permet d'ajouter une liste
     */
    private Button ajouterListe;

    /**
     * Conteneur des listes
     */
    private HBox listeContainer;

    /**
     * Constructeur de la classe VueTableau
     */
    public VueTableau() {
        // On appelle le constructeur de la classe mère
        super();

        //On initialise le bouton et on lui donne un style
        ajouterListe = new Button("Ajouter une liste");
        ajouterListe.setStyle("-fx-font-weight: bold;");
        ajouterListe.setStyle("-fx-font-size: 20px;");
        ajouterListe.setStyle("-fx-background-color: #b6f5b6;");
        ajouterListe.setPrefSize(200, 50);

        //On masque le bouton
        ajouterListe.setVisible(false);

        //On initialise le conteneur des listes
        listeContainer = new HBox();
        listeContainer.setSpacing(10);
        listeContainer.setFillHeight(true);

        HBox container = new HBox();
        container.getChildren().addAll(listeContainer, ajouterListe);

        this.setContent(container);
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     *
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        //On efface tout
        listeContainer.getChildren().clear();

        //On récupère le modele
        Modele m = (Modele) s;

        //Si il y a au moins un tableau
        if (!m.getTableaux().isEmpty()) {
            //On affiche le bouton
            ajouterListe.setVisible(true);
        } else {
            //On masque le bouton
            ajouterListe.setVisible(false);
        }

        //On récupère le tableau courant
        Tableau t = m.getTableaux().get(Modele.getTableauCourant());

        int numListe = 1;
        //On parcourt les listes du tableau
        for (Liste liste : t.getListes()) {

            VueListe vueListe = new VueListe(liste.getTitre(), numListe, m);
            vueListe.setOnMouseClicked(new ControleurListeCliquee(m));
            listeContainer.getChildren().add(vueListe);
            numListe++;
        }
    }

    /**
     * Getter de l'attribut AjouterListe
     *
     * @return ajouterListe
     */
    public Button getAjouterListe() {
        return ajouterListe;
    }
}
