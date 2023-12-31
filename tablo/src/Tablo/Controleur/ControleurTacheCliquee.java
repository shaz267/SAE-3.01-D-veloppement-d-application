package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Vue.VueListe;
import Tablo.Vue.VueSousTaches;
import Tablo.Vue.VueTache;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe ControleurTacheCliquee qui permet de gérer la modification d'une tache
 */
public class ControleurTacheCliquee implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurTacheCliquee
     *
     * @param m Modele de l'application
     */
    public ControleurTacheCliquee(Modele m) {

        this.modele = m;
    }

    /**
     * Méthode de la classe EventHandler qui permet de gérer la modification d'une tache
     *
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On change la tache courante
        //On récupère la tache qui a été cliquée
        VueTache vT = (VueTache) mouseEvent.getSource();

        //On récupère l'id de la liste
        int numTache = vT.getNumTache();

        //On change la tache courante
        Modele.setTacheCourante(numTache);

        //On récupère la liste qui a été cliquée c'est à dire le parent de la source
        VueListe vl = (VueListe) vT.getParent();
        //On récupère le numéro de la liste
        int numListe = vl.getNumListe();
        //On change la liste courante
        Modele.setListeCourante(numListe);

        //Si c'est un click gauche on change la couleur de la tache
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

            //Si la tache courante est selectionnee on la déselectionne
            if (this.modele.isSelectionnee()) {

                this.modele.setTacheCouranteSelectionnee(false);

                //On met la tache à sa couleur d'origine
                vT.setStyle("-fx-background-color: #f8b9a7");
            }
            else {//Sinon on la selectionne

                this.modele.setTacheCouranteSelectionnee(true);

                //On rend la tache grise
                vT.setStyle("-fx-background-color: rgba(167,181,248,0.2)");
            }
        }



        //Si c'est un click droit on affiche la boîte de dialogue
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){

            //On créé la boîte de dialogue
            ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
            dialog.setTitle("Modifier Tâche");
            dialog.setHeaderText("Modifier une tâche");

            // On crée une VBox qui va contenir les choix de l'utilisateur
            VBox conteneur = new VBox();

            Label labeltitre = new Label("Titre");
            // On crée le TextField qui va contenir le nouveau titre de la tâche
            TextField titreField = new TextField();
            titreField.setText(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getTitre());

            Label labelcontenu = new Label("Contenu");
            // On crée le TextArea qui va contenir la description de la tâche
            TextArea contenuArea = new TextArea();
            contenuArea.setText(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getContenu());

            Label labelDateDeb = new Label("Date début");
            // On crée le DatePicker pour gérer la date de début
            DatePicker dateDeb = new DatePicker();
            dateDeb.setShowWeekNumbers(false);
            dateDeb.setValue(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getDateDebut());

            Label labelDateFin = new Label("Date fin");
            // On crée le DatePicker pour gérer la date de fin
            DatePicker dateFin = new DatePicker();
            dateFin.setShowWeekNumbers(false);
            dateFin.setValue(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getDateLimite());

            Label labelsoustache = new Label("Choisir une sous tâche");
            // On crée la comboBox qui contiendra la liste des tâches qu'on pourra ajouter en sous tâche
            ComboBox<String> listeSousTache = getStringComboBox(numTache);

            // On crée le bouton supprimer pour supprimer la liste
            Button archButton = new Button("Archiver la tâche");

            // On ajoute le controleur pour supprimer la liste
            archButton.setOnMouseClicked(new ControleurArchiverTache(this.modele));

            VueSousTaches vueSousTaches = new VueSousTaches(this.modele);

            // On ajoute les composantes graphiques à la VBox
            conteneur.getChildren().addAll(labeltitre, titreField, labelcontenu, contenuArea, labelDateDeb, dateDeb, labelDateFin, dateFin, labelsoustache, listeSousTache, vueSousTaches, archButton);

            // On ajoute la VBox à la boîte de dialogue
            dialog.getDialogPane().setContent(conteneur);

            // Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
            dialog.showAndWait();

            // si le titre n'est pas vide et le titre n'est pas le meme que celui enregistré
            if (!titreField.getText().isEmpty() && !titreField.getText().equals(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getTitre())) {

                // On récupère le nouveau titre de la tâche
                String titre = titreField.getText();
                this.modele.changerTitreTache(titre);
            }
            // Si le contenu n'est pas vide ou que le contenu n'est pas le même que celui enregistré
            if (!contenuArea.getText().isEmpty() && !contenuArea.getText().equals(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getContenu())) {

                // On récupère le nouveau contenu
                String contenu = contenuArea.getText();
                this.modele.changerContenuTache(contenu);
            }
            // Si la date de début n'est pas vide et que la date n'est pas la même que celle enregistrée et que la date choisie est bien avant la date de fin
            if ((dateDeb.getValue() != null && !dateDeb.getValue().equals(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getDateDebut())) && dateDeb.getValue().isBefore(dateFin.getValue())) {

                // On récupère la date du datePicker
                LocalDate date = dateDeb.getValue();
                this.modele.modifierDateDebut(date);
            }
            // Si la date de fin n'est pas vide et que la date n'est pas la même que celle enregistrée et que la date choisie est bien après la date de début
            if ((dateFin.getValue() != null && !dateFin.getValue().equals(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getDateLimite())) && !dateFin.getValue().isBefore(dateDeb.getValue())) {

                // On récupère la date du datePicker
                LocalDate date = dateFin.getValue();
                this.modele.modifierDateLimite(date);
            }
            //Si la date de début n'est pas antérieure à la date de fin On affiche une erreur
            if (!dateDeb.getValue().isBefore(dateFin.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de date");
                alert.setContentText("La date de début doit être antérieure à la date de fin.");
                alert.showAndWait();
            }

            //Si la comboBox n'est pas vide
            if (listeSousTache.getValue() != null) {

                // On récupère le titre de la tâche choisie
                String titre = listeSousTache.getValue();

                // On récupère la tâche choisie
                Tache tacheFille = this.modele.getTache(titre);

                // On ajoute la tâche fille à la tâche mère. Si le résultat est false on change la tache courante en TacheMere
                if (!this.modele.ajouterSousTache(tacheFille)) {

                    // On change la tâche courante en tâche mère
                    this.modele.tacheCouranteEnMere();

                    // On ajoute la tâche fille à la tâche mère
                    this.modele.ajouterSousTache(tacheFille);
                }
            }
        }



    }

    private ComboBox<String> getStringComboBox(int numTache) {
        ComboBox<String> listeSousTache = new ComboBox<String>();
        listeSousTache.setPromptText("Liste des sous-tâches");

        // On crée l'objet qui sera contenu dans notre ComboBox, une ObservableList
        ObservableList<String> observableList = FXCollections.observableList(new ArrayList<>());

        // Pour éviter de créer des méthodes dans les classes Listes, Tableau, Modele et Tache pour un usage quasi unique
        // alors je récupère ici le titre des tâches pour la liste des tâches existances et je l'ajoute dans
        // mon ObservableList
        for (Tache tache : this.modele.getTaches()) {

            // booléen qui vérifie si la tâche est deja une sous tâche
            boolean ajouterTacheQuiEstSousTache = false;

            if (modele.getSousTaches() != null) {

                ajouterTacheQuiEstSousTache = modele.getSousTaches().contains(tache);
            }

            // booléen qui vérifie si la tâche est une sous tâche de la tâche courante
            boolean ajouterTacheQuiEstSousTacheDeLaTacheCourante = false;

            if (tache.getSousTaches() != null) {

                ajouterTacheQuiEstSousTacheDeLaTacheCourante = tache.getSousTaches().contains(this.modele.getTaches().get(numTache - 1));
            }


            // On vérifie qu'on ajoute pas la tâche courante pour ne pas pouvoir se définir elle-même comme sous tâches. On vérifie aussi qu'on ajoute pas une tâche qui est déjà une sous tâche. Enfin on vérifie que la tâche qu'on ajoute au sous taches n'est pas une sous tâche de la tâche courante. Et la tache n'est pas archivée
            if (tache != this.modele.getTaches().get(numTache - 1) && !ajouterTacheQuiEstSousTache && !ajouterTacheQuiEstSousTacheDeLaTacheCourante && !tache.isArchivee()) {
                observableList.add(tache.getTitre());
            }
        }
        // On ajoute l'observableList à la ComboBox
        listeSousTache.setItems(observableList);
        return listeSousTache;
    }
}
