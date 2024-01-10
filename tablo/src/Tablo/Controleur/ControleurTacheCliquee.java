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
                vT.setStyle("-fx-background-color: rgba(187,108,87,0.71); -fx-font-family: 'Roboto Light'; -fx-text-fill: white; -fx-font-size: 15px;");
            }
            else {//Sinon on la selectionne

                this.modele.setTacheCouranteSelectionnee(true);

                //Si le mode sombre est activé on change la couleur de la tache quand elle est selectionnée
                if (Modele.getModeSombre()){
                    //On rend la tache grise quand elle est selectionnee
                    vT.setStyle("-fx-background-color: rgba(203,203,203,0.71); -fx-font-family: 'Roboto Light'; -fx-font-size: 15px; -fx-border-width: 1px; -fx-border-color: #000000; -fx-border-radius: 3px;");
                } else {
                    //On rend la tache grise quand elle est selectionnee
                    vT.setStyle("-fx-background-color: rgba(169,169,169,0.71); -fx-font-family: 'Roboto Light'; -fx-font-size: 15px; -fx-border-width: 1px; -fx-border-color: #000000; -fx-border-radius: 3px;");

                }
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
            labeltitre.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 13px");
            // On crée le TextField qui va contenir le nouveau titre de la tâche
            TextField titreField = new TextField();
            titreField.setText(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getTitre());

            Separator separator = new Separator();
            separator.setMinHeight(10);

            Label labelcontenu = new Label("Contenu");
            labelcontenu.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 13px");
            // On crée le TextArea qui va contenir la description de la tâche
            TextArea contenuArea = new TextArea();
            contenuArea.setText(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getContenu());
            contenuArea.setPrefHeight(100);

            Separator separator1 = new Separator();
            separator1.setMinHeight(10);

            Label labelDateDeb = new Label("Date début");
            labelDateDeb.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 13px");
            // On crée le DatePicker pour gérer la date de début
            DatePicker dateDeb = new DatePicker();
            dateDeb.setShowWeekNumbers(false);
            dateDeb.setValue(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getDateDebut());

            Separator separator2 = new Separator();
            separator2.setMinHeight(10);

            Label labelDateFin = new Label("Date fin");
            labelDateFin.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 13px");
            // On crée le DatePicker pour gérer la date de fin
            DatePicker dateFin = new DatePicker();
            dateFin.setShowWeekNumbers(false);
            dateFin.setValue(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getDateLimite());

            Separator separator3 = new Separator();
            separator3.setMinHeight(10);

            Label labelsoustache = new Label("Choisir une sous tâche");
            labelsoustache.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 13px");
            // On crée la comboBox qui contiendra la liste des tâches qu'on pourra ajouter en sous tâche
            ComboBox<String> listeSousTache = getStringComboBox(numTache);

            Separator separator4 = new Separator();
            separator4.setMinHeight(10);

            // On crée le bouton supprimer pour supprimer la liste
            Button archButton = new Button("Archiver la tâche");
            archButton.setStyle("-fx-background-color: #C0C0C0;"); // Couleur du bouton
            archButton.setOnMouseEntered(e -> archButton.setStyle("-fx-background-color: #808080;")); // Changement de couleur au survol
            archButton.setOnMouseExited(e -> archButton.setStyle("-fx-background-color: #C0C0C0;"));  // Changement de couleur à la sortie du survol
            archButton.setOnMousePressed(e -> archButton.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;")); // Changement de couleur au clic


            // On ajoute le controleur pour supprimer la liste
            archButton.setOnMouseClicked(new ControleurArchiverTache(this.modele));

            VueSousTaches vueSousTaches = new VueSousTaches(this.modele);

            // On ajoute les composantes graphiques à la VBox
            conteneur.getChildren().addAll(labeltitre, titreField, separator, labelcontenu, contenuArea, separator1, labelDateDeb, dateDeb, separator2, labelDateFin, dateFin, separator3, labelsoustache, listeSousTache, vueSousTaches, separator4, archButton);
            conteneur.setSpacing(5);

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

            boolean ajoutTacheDontLaTacheCouranteDescend = false;

            if (tache.getSousTaches() != null) {

                ajoutTacheDontLaTacheCouranteDescend = tache.getSousTachesReccursif().contains(this.modele.getTaches().get(numTache - 1));
            }


            // On vérifie qu'on ajoute pas la tâche courante pour ne pas pouvoir se définir elle-même comme sous tâches. On vérifie aussi qu'on ajoute pas une tâche qui est déjà une sous tâche. Enfin on vérifie que la tâche qu'on ajoute au sous taches n'est pas une sous tâche de la tâche courante. Et la tache n'est pas archivée
            if (tache != this.modele.getTaches().get(numTache - 1) && !ajouterTacheQuiEstSousTache && !ajouterTacheQuiEstSousTacheDeLaTacheCourante && !tache.isArchivee() && !ajoutTacheDontLaTacheCouranteDescend) {
                observableList.add(tache.getTitre());
            }
        }
        // On ajoute l'observableList à la ComboBox
        listeSousTache.setItems(observableList);
        return listeSousTache;
    }
}
