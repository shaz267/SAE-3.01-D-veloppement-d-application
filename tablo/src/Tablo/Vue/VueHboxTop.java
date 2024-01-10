package Tablo.Vue;

import Tablo.Controleur.ControleurAjoutTemplates;
import Tablo.Modele.Modele;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Classe VueHboxTop qui hérite de HBox et qui implémente l'interface Observateur
 */
public class VueHboxTop extends HBox implements Observateur {

    /**
     * Constructeur de la classe VueHboxTop
     * @param modele Modele de l'application
     */
    public VueHboxTop(Modele modele){

        // On crée les composantes graphiques pour la zone 'top'
        Image logo = new Image("Logo_Tablo.png");
        ImageView view = new ImageView(logo);

        //On redimensionne l'image
        view.setFitHeight(40);
        view.setFitWidth(40);

        //Templates comboBox
        ComboBox<String> templates = new ComboBox<>();
        //On redimensionne le comboBox
        templates.setMinWidth(110);
        templates.setMinHeight(40);

        //On met le titre du comboBox en "Espace de travail"
        templates.setPromptText("Templates");

        //On met en gras le titre du comboBox
        templates.setStyle("-fx-font-weight: bold;");

        //On ajoute les templates
        templates.getItems().add("Conduite de projet");
        templates.getItems().add("Modèle Kanban");
        templates.getItems().add("Réunion hebdomadaire");
        templates.getItems().add("Tableau Agile");

        //On ajoute un évènement au comboBox
        templates.setOnAction(new ControleurAjoutTemplates(modele));

        //Titre du tableau
        VueTitreTableau titreTableau = new VueTitreTableau();
        modele.enregistrerObservateur(titreTableau);

        //On met le titre du tableau au centre
        titreTableau.setTranslateX(300);

        //On ajoute les composantes graphiques à la racine
        this.getChildren().addAll(view, templates, titreTableau);
        templates.setTranslateX(30);
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        // On récupère le modele
        Modele m = (Modele) s;

        // On change le style de la vue en fonction du mode sombre
        if (Modele.getModeSombre()){
            // On change le fond de la vue en sombre si le mode sombre est activé
            this.setStyle("-fx-background-color: #2b2b2b;");
        } else{
            // On change le fond de la vue en clair si le mode sombre est désactivé
            this.setStyle("-fx-background-color: #ffffff;");
        }

    }
}
