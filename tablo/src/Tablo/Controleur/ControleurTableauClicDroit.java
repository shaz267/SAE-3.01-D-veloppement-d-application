package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Classe ControleurTableauClicDroit qui implémente l'interface EventHandler et qui permet de gérer le clic droit sur un tableau
 */
public class ControleurTableauClicDroit implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurTableauClicDroit
     *
     * @param m Modele de l'application
     */
    public ControleurTableauClicDroit(Modele m) {
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer le clic droit sur un tableau
     *
     * @param mouseEvent Evènement qui permet de gérer le clic droit sur un tableau
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //Si on a cliqué avec le bouton droit de la souris on affiche la boîte de dialogue
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {

            //On récupère le bouton sur lequel on a cliqué
            Button b = (Button) mouseEvent.getSource();

            //On récupère le titre du tableau
            String TitreTableauChercher = b.getText();

            //On récupère l'id du tableau
            int numTableau = this.modele.getNumTableau(TitreTableauChercher);

            //On créé la boîte de dialogue
            ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
            dialog.setTitle("Modifier Tableau");
            dialog.setHeaderText("Modifier un Tableau");

            // On crée une VBox qui va contenir les choix de l'utilisateur
            VBox conteneur = new VBox();

            // On crée le TextArea qui va contenir le nouveau titre de la liste
            TextField champ_saisie = new TextField();

            // On crée le bouton supprimer pour supprimer la liste
            Button supprimer = new Button("Supprimer");

            //On ajoute un évènement au bouton supprimer
            supprimer.setOnMouseClicked(new ControleurSupprimerTableau(this.modele, numTableau));

            // On ajoute les éléments à la VBox
            conteneur.getChildren().addAll(champ_saisie, supprimer);

            // On ajoute la VBox à la boîte de dialogue
            dialog.getDialogPane().setContent(conteneur);

            // On montre la boîte de dialogue et on attend la réponse de l'utilisateur
            dialog.showAndWait();

            //On vérifie que le titre n'est pas déjà utilisé
            for (Tableau t : this.modele.getTableaux()) {

                // Si le titre est déjà utilisé, on affiche une erreur et on quitte la méthode
                if (t.getTitre().equals(champ_saisie.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur");
                    alert.setContentText("Le titre du tableau est déjà utilisé");
                    alert.showAndWait();
                    return;
                }
            }

            //Si le champ de saisie n'est pas vide et s'il ne dépasse pas 15 caractères on modifie le titre du tableau
            if (!champ_saisie.getText().isEmpty()) {
                if (champ_saisie.getText().length() > 15) {

                    //On affiche une erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur");
                    alert.setContentText("Le titre du tableau ne doit pas dépasser 15 caractères");
                    alert.showAndWait();

                } else {
                    //On récupère le nouveau titre du tableau
                    String titreTableau = champ_saisie.getText();

                    //On change le titre du tableau
                    this.modele.changerTitreTableau(titreTableau);
                }
            }

        }

    }
}
