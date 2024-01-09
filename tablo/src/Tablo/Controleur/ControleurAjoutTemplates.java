package Tablo.Controleur;

import Tablo.Modele.*;
import Tablo.Vue.VueTableau;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

/**
 * Classe ControleurAjoutTemplates qui implémente l'interface EventHandler et qui permet d'ajouter un tableau par défaut à partir de modèle connu
 */
public class ControleurAjoutTemplates implements EventHandler<ActionEvent> {

    /**
     * Modele de l'application
     */
    private final Modele modele;

    /**
     * Constructeur de la classe ControleurAjoutTemplates
     * @param modele Modele de l'application
     */
    public ControleurAjoutTemplates(Modele modele) {
        this.modele = modele;
    }

    /**
     * Méthode qui permet de gérer l'ajout d'un tableau par défaut à partir de modèle connu
     * @param actionEvent Evènement qui permet de gérer l'ajout d'un tableau par défaut à partir de modèle connu
     */
    @Override
    public void handle(ActionEvent actionEvent) {

        //On récupère la ComboBox
        ComboBox comboBox = (ComboBox) actionEvent.getSource();

        //On récupère le titre du template
        String titre = (String) comboBox.getValue();

        //On fait un switch sur le titre du template
        switch (titre) {
            case "Conduite de projet":
                this.modele.templateConduiteProjet(titre);
                break;

            case "Modèle Kanban":
                //On ajoute le template 2
                this.modele.templateModeleKanban(titre);
                break;
            case "Réunion hebdomadaire":
                //On ajoute le template 3
                this.modele.templateReuHebdo(titre);
                break;
            case "Tableau Agile":
                //On ajoute le template 4
                this.modele.templateTableauAgile(titre);
                break;
        }
    }

}
