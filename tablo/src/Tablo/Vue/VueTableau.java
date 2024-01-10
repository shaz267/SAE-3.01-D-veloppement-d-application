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
     * conteneur des listes et du bouton ajouter liste
     */
    private HBox container = new HBox();


    /**
     * Constructeur de la classe VueTableau
     */
    public VueTableau() {

        this.setPannable(true);
        this.setStyle("-fx-border-color: #b3b3b3; -fx-border-width: 1px; -fx-background-color: transparent;");

        //On initialise le bouton et on lui donne un style
        ajouterListe = new Button("Ajouter une liste");
        ajouterListe.setStyle("-fx-font-size: 15px;-fx-background-color: rgba(70,117,67,0.71);-fx-text-fill: white;-fx-font-family: 'Roboto Light'; ");
        ajouterListe.setOnMouseEntered(e -> ajouterListe.setStyle("-fx-font-size: 15px;-fx-background-color: rgba(50,80,46,0.71);-fx-text-fill: white;-fx-font-family: 'Roboto Light';")); // Changement de couleur au survol
        ajouterListe.setOnMouseExited(e -> ajouterListe.setStyle("-fx-font-size: 15px;-fx-background-color: rgba(70,117,67,0.71);-fx-text-fill: white;-fx-font-family: 'Roboto Light';"));  // Changement de couleur à la sortie du survol
        ajouterListe.setOnMousePressed(e -> ajouterListe.setStyle("-fx-border-width: 3px; -fx-border-color: rgba(70,117,67,0.71); -fx-font-size: 15px;-fx-background-color: rgba(70,117,67,0.71);-fx-text-fill: white;-fx-font-family: 'Roboto Light';")); // Changement de couleur au clic

        ajouterListe.setPrefSize(200, 50);

        //On masque le bouton
        ajouterListe.setVisible(false);

        //On initialise le conteneur des listes
        listeContainer = new HBox();
        listeContainer.setSpacing(10);
        listeContainer.setFillHeight(true);

        container.getChildren().addAll(listeContainer, ajouterListe);
        container.setSpacing(10);

        this.setContent(container);
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     *
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        // On change le style de la vue en fonction du mode sombre
        if (Modele.getModeSombre()) {

            // On change le fond de la vue en sombre si le mode sombre est activé
            this.setStyle("-fx-border-color: #b3b3b3; -fx-border-width: 1px; -fx-background-color: #2b2b2b;");

            // On change le fond du conteneur des listes en sombre si le mode sombre est activé
            listeContainer.setStyle("-fx-background-color: #2b2b2b;");

            // On change le fond du conteneur des listes et du bouton ajouter liste en sombre si le mode sombre est activé
            container.setStyle("-fx-background-color: #2b2b2b;");
        } else {

            // On change le fond de la vue en clair si le mode sombre est désactivé
            this.setStyle("-fx-border-color: #b3b3b3; -fx-border-width: 1px; -fx-background-color: transparent;");

            // On change le fond du conteneur des listes en clair si le mode sombre est désactivé
            listeContainer.setStyle("-fx-background-color: transparent;");

            // On change le fond du conteneur des listes et du bouton ajouter liste en clair si le mode sombre est désactivé
            container.setStyle("-fx-background-color: transparent;");
        }

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

        //On initialise le tableau
        Tableau t = null;

        //On récupère le tableau courant
        for (int i = 0; i < m.getTableaux().size(); i++) {

            //Si le numéro du tableau est le même que le tableau courant
            if (m.getTableaux().get(i).getNumTableau() == Modele.getTableauCourant()) {

                //On récupère le tableau
                t = m.getTableaux().get(i);
            }
        }

        int numListe = 1;
        //On parcourt les listes du tableau si le tableau n'est pas null
        if (t != null) {
            for (Liste liste : t.getListes()) {

                VueListe vueListe = new VueListe(liste.getTitre(), numListe, m);
                vueListe.setOnMouseClicked(new ControleurListeCliquee(m));
                listeContainer.getChildren().add(vueListe);
                numListe++;
            }
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
