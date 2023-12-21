package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Vue.VueDifferentTableaux;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControleurTableauClicDroit implements EventHandler<MouseEvent> {

    private Modele modele;

    public ControleurTableauClicDroit(Modele m) {
        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //Si on a cliqué avec le bouton droit de la souris on affiche la boîte de dialogue
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {

            //On récupère le bouton sur lequel on a cliqué
            Button b = (Button) mouseEvent.getSource();

            //On récupère le titre du tableau
            String TitreTableauChercher = b.getText();

            //On récupère l'id du tableau
            int numTableau = this.modele.getNumTableaux(TitreTableauChercher);

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

            //Si le champ de saisie n'est pas vide on modifie le titre du tableau
            if (!champ_saisie.getText().isEmpty()) {

                //On récupère le nouveau titre du tableau
                String titreTableau = champ_saisie.getText();

                //On change le titre du tableau
                this.modele.getTableaux().get(numTableau).changerTitre(titreTableau);

                //On informe le loggeur
                Loggeur.enregistrer("Modification du titre du tableau : " + titreTableau + "Numéro du tableau : " + numTableau);

                //On met à jour la vue
                this.modele.notifierObservateurs();

                //sinon on affiche une erreur et on quitte la méthode
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur");
                alert.setContentText("Le nouveau titre du tableau ne peut pas être vide");
                alert.showAndWait();
            }

        }

    }
}
