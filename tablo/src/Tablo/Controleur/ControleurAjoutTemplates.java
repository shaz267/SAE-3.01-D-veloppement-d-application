package Tablo.Controleur;

import Tablo.Modele.*;
import Tablo.Modele.Templates.TemplateConduiteProjet;
import Tablo.Modele.Templates.TemplateKanban;
import Tablo.Modele.Templates.TemplateReuHebdo;
import Tablo.Modele.Templates.TemplateTableauAgile;
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
        ComboBox<String> comboBox = (ComboBox<String>)actionEvent.getSource();

        //On récupère le titre du template
        String titre = comboBox.getValue();

        //On fait un switch sur le titre du template
        switch (titre) {
            case "Conduite de projet":
                //On modifie le template du modele en utilisant le patron singleton
                this.modele.setTemplate(TemplateConduiteProjet.getInstance(this.modele));
                break;
            case "Modèle Kanban":
                //On modifie le template du modele en utilisant le patron singleton
                this.modele.setTemplate(TemplateKanban.getInstance(this.modele));
                break;
            case "Réunion hebdomadaire":
                //On modifie le template du modele
                this.modele.setTemplate(TemplateReuHebdo.getInstance(this.modele));
                break;
            case "Tableau Agile":
                //On modifie le template du modele
                this.modele.setTemplate(TemplateTableauAgile.getInstance(this.modele));
                break;
        }
        //On crée le tableau avec le template choisi
        this.modele.creerTableau();
    }

}
