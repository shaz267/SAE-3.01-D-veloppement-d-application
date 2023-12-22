package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
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

public class ControleurTacheClicDroit implements EventHandler<MouseEvent> {

	/**
	 * Modele de l'application
	 */
	private Modele modele;

	/**
	 * Constructeur de la classe ControleurTacheClicDroit
	 * @param m Modele de l'application
	 */
	public ControleurTacheClicDroit(Modele m){ this.modele = m; }

	/**
	 * Méthode de la classe EventHandler qui permet de gérer la modification d'une tache
	 * @param mouseEvent
	 */
	@Override
	public void handle(MouseEvent mouseEvent) {

		// Si le clique effectué est un clique droit alors
		if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){

			// On récupère la tâche sur laquelle on a cliqué
			VueTache vt = (VueTache) mouseEvent.getSource();

			// On récupère le numéro de la tâche
			int numTache = vt.getNumTache();

			// On change la tâche courante
			modele.changerTacheCourante(numTache);

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
			// On crée la comboBox qui contiendra la liste des tâches qu'on pourra
			ComboBox listeSousTache = new ComboBox();
			listeSousTache.setPromptText("Liste des sous-tâches");

			// On crée l'objet qui sera contenu dans notre ComboBox, une ObservableList
			ObservableList<String> observableList = FXCollections.observableList(new ArrayList<>());

			// Pour éviter de créer des méthodes dans les classes Listes, Tableau, Modele et Tache pour un usage quasi unique
			// alors je récupère ici le titre des tâches pour la liste des tâches existances et je l'ajoute dans
			// mon ObservableList
			for(Tache tache : this.modele.getTaches()){
				// On vérifie qu'on ajoute pas la tâche courante pour ne pas pouvoir se définir elle-même comme sous tâches
				if(tache != this.modele.getTaches().get(numTache-1)){
					observableList.add(tache.getTitre());
				}
			}
			// On ajoute l'observableList à la ComboBox
			listeSousTache.setItems(observableList);


			// On crée le bouton supprimer pour supprimer la liste
			Button supprButton = new Button("Supprimer");

			// On ajoute le controleur pour supprimer la liste
			supprButton.setOnMouseClicked(new ControleurSupprimerTache(this.modele));

			// On ajoute les composantes graphiques à la VBox
			conteneur.getChildren().addAll(labeltitre,titreField, labelcontenu, contenuArea, labelDateDeb, dateDeb, labelDateFin, dateFin, labelsoustache, listeSousTache, supprButton);

			// On ajoute la VBox à la boîte de dialogue
			dialog.getDialogPane().setContent(conteneur);

			// Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
			dialog.showAndWait();

			// Si le titre n'est pas vide
			if(titreField.getText() != "" || titreField.getText().equals(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getTitre())){
				// On récupère le nouveau titre de la tâche
				String titre = titreField.getText();
				this.modele.getTaches().get(Modele.getTacheCourante() - 1).changerTitre(titre);

				// On notifie les observateurs
				this.modele.notifierObservateurs();
				// On enregistre l'action dans les logs
				Loggeur.enregistrer("Modification du titre de la tâche "+this.modele.getTaches().get(Modele.getTacheCourante() - 1).getTitre());
			}
			// Si le contenu n'est pas vide
			if(contenuArea.getText() != "" || contenuArea.getText().equals(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getContenu())){
				// On récupère le nouveau contenu
				String contenu = contenuArea.getText();
				this.modele.getTaches().get(Modele.getTacheCourante() - 1).changerContenu(contenu);

				// On notifie les observateurs
				this.modele.notifierObservateurs();
				// On enregistre l'action dans les logs
				Loggeur.enregistrer("Modification du contenu de la tâche "+this.modele.getTaches().get(Modele.getTacheCourante() - 1).getTitre());
			}
			// Si la date de début n'est pas vide ou que la date n'est pas la même que celle enregistrée et que la date choisie est bien avant la date de fin
			if((dateDeb.getValue() != null || dateDeb.getValue().equals(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getDateDebut())) && dateDeb.getValue().isBefore(dateFin.getValue())){
				// On récupère la date du datePicker
				LocalDate date = dateDeb.getValue();
				this.modele.getTaches().get(Modele.getTacheCourante() - 1).modifierDateDebut(date);

				// On notifie les observateurs
				this.modele.notifierObservateurs();
				// On enregistre l'action dans les logs
				Loggeur.enregistrer("Modification de la date de début de la tâche "+this.modele.getTaches().get(Modele.getTacheCourante() - 1).getTitre());
			}
			// Si la date de fin n'est pas vide ou que la date n'est pas la même que celle enregistrée et que la date choisie est bien après la date de début
			if((dateFin.getValue() != null || dateFin.getValue().equals(this.modele.getTaches().get(Modele.getTacheCourante() - 1).getDateLimite())) && !dateFin.getValue().isBefore(dateDeb.getValue())){
				// On récupère la date du datePicker
				LocalDate date = dateFin.getValue();
				this.modele.getTaches().get(Modele.getTacheCourante() - 1).modifierDateLimite(date);

				// On notifie les observateurs
				this.modele.notifierObservateurs();
				// On enregistre l'action dans les logs
				Loggeur.enregistrer("Modification de la date de fin de la tâche "+this.modele.getTaches().get(Modele.getTacheCourante() - 1).getTitre());
			}
			if(!dateDeb.getValue().isBefore(dateFin.getValue())){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Erreur de date");
				alert.setContentText("La date de début doit être antérieure à la date de fin.");
				alert.showAndWait();
			}
		}
	}
}
