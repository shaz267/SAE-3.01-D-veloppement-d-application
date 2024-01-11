package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import Tablo.Vue.VueListe;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Classe ControleurListeCliquee qui permet de gérer la modification d'une liste
 */
public class ControleurListeCliquee implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private final Modele modele;

    /**
     * Constructeur de la classe ControleurListeCliquee
     *
     * @param m Modele de l'application
     */
    public ControleurListeCliquee(Modele m) {
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer la modification d'une liste
     *
     * @param mouseEvent Evènement de la souris
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère la liste qui a été cliquée
        VueListe vl = (VueListe) mouseEvent.getSource();

        //On récupère le numéro de la liste
        int numListe = vl.getNumListe();

        //On change la liste courante
        Modele.setListeCourante(numListe);

        //Si l'utilisateur a cliqué sur le bouton droit de la souris
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {

            //On créé la boîte de dialogue
            ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
            dialog.setTitle("Modifier Liste");
            dialog.setHeaderText("Modifier une liste");

            // On crée une VBox qui va contenir les choix de l'utilisateur
            VBox conteneur = new VBox();

            // On crée le TextArea qui va contenir le nouveau titre de la liste
            TextField champ_saisie = new TextField();
            champ_saisie.setPromptText("Modifier le titre ici");

            Separator separateur = new Separator();
            separateur.setMinHeight(10);

            // On crée le bouton supprimer pour supprimer la liste
            Button supprimer = new Button("Supprimer");
            supprimer.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';"); // Couleur du bouton
            supprimer.setOnMouseEntered(e -> supprimer.setStyle("-fx-background-color: #808080;-fx-font-family: 'Roboto Light';")); // Changement de couleur au survol
            supprimer.setOnMouseExited(e -> supprimer.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';"));  // Changement de couleur à la sortie du survol
            supprimer.setOnMousePressed(e -> supprimer.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';")); // Changement de couleur au clic


            // On ajoute le controleur pour supprimer la liste
            supprimer.setOnMouseClicked(new ControleurSupprimerListe(this.modele));

            // On ajoute les composantes graphiques à la VBox
            conteneur.getChildren().addAll(champ_saisie, separateur, supprimer);
            conteneur.setSpacing(10);
            conteneur.setAlignment(javafx.geometry.Pos.CENTER);

            // On ajoute la VBox à la boîte de dialogue
            dialog.getDialogPane().setContent(conteneur);

            // Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
            dialog.showAndWait();

            // Si le champ de saisie n'est pas vide
            if (!champ_saisie.getText().isEmpty()) {

                // Si le titre de la liste dépasse 25 caractères on affiche une alerte
                if (champ_saisie.getText().length() > 25){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur");
                    alert.setContentText("Le titre de la liste ne doit pas dépasser 25 caractères");
                    alert.showAndWait();

                    //Sinon on change le titre de la liste
                }else {
                    // On récupère le nouveau titre de la liste
                    String titre = champ_saisie.getText();
                    this.modele.changerTitreListe(titre);
                }
            }
        }
    }
}
