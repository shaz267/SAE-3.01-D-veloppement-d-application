package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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

			Label labelcontenu = new Label("Contenu");
			// On crée le TextArea qui va contenir la description de la tâche
			TextArea contenuArea = new TextArea();

			Label labelDateDeb = new Label("Date début");
			// On crée le DatePicker pour gérer la date de début
			DatePicker dateDeb = new DatePicker();
			dateDeb.setShowWeekNumbers(false);

			Label labelDateFin = new Label("Date fin");
			// On crée le DatePicker pour gérer la date de fin
			DatePicker dateFin = new DatePicker();
			dateDeb.setShowWeekNumbers(false);

			// On crée la comboBox qui contiendra la liste des tâches qu'on pourra

			// On crée le bouton supprimer pour supprimer la liste
			Button supprButton = new Button("Supprimer");

			// On ajoute le controleur pour supprimer la liste
			supprButton.setOnMouseClicked(new ControleurSupprimerTache(this.modele));

			// On ajoute les composantes graphiques à la VBox
			conteneur.getChildren().addAll(labeltitre,titreField, labelcontenu, contenuArea, labelDateDeb, dateDeb, labelDateFin, dateFin, supprButton);

			// On ajoute la VBox à la boîte de dialogue
			dialog.getDialogPane().setContent(conteneur);

			// Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
			dialog.showAndWait();

			// Si le titre n'est pas vide
			if(titreField.getText() != ""){
				// On récupère le nouveau titre de la tâche
				String titre = titreField.getText();
				this.modele.getTaches().get(Modele.getTacheCourante() - 1).changerTitre(titre);

				// On notifie les observateurs
				this.modele.notifierObservateurs();
				// On enregistre l'action dans les logs
				Loggeur.enregistrer("Modification de titre d'une tâche");
			}
			// Si le contenu n'est pas vide
			//if()
		}
	}
}
