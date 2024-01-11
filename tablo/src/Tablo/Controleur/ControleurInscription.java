package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Modele.Utilisateur;
import Tablo.MyApplication;
import Tablo.SecuriteFormulaire;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe ControleurInscription qui implémente l'interface EventHandler et qui permet de gérer l'inscription d'un utilisateur
 */
public class ControleurInscription implements EventHandler<MouseEvent> {

    /**
     * Stage de l'application
     */
    private final Stage stage;

    /**
     * Constructeur de la classe ControleurInscription
     * @param stage Stage de l'application
     */
    public ControleurInscription(Stage stage) {

        this.stage = stage;
    }

    /**
     * Méthode handle qui permet de gérer l'inscription d'un utilisateur
     * @param mouseEvent Evènement de la souris
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        //On crée la boite de dialogue pour l'inscription
        Dialog<VBox> dialog = new Dialog<>();
        dialog.setTitle("Inscription");
        dialog.setHeaderText("Inscription");

        // On permet de fermer la boite de dialogue
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);


        //On crée la VBox pour l'inscription
        VBox rootInscription = new VBox();

        //On crée le texte field pour le nom d'utilisateur
        TextField nomUtilisateur = new TextField();
        nomUtilisateur.setPromptText("Nom d'utilisateur");
        nomUtilisateur.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

        //On crée le texte field pour l'email
        TextField email = new TextField();
        email.setPromptText("Email");
        email.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

        //On crée le texte field pour le mot de passe
        PasswordField mdp = new PasswordField();
        mdp.setPromptText("Mot de passe");
        mdp.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

        //On crée le texte field pour la confirmation du mot de passe
        PasswordField confirmationMdp = new PasswordField();
        confirmationMdp.setPromptText("Confirmation du mot de passe");
        confirmationMdp.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

        //On crée le bouton valider
        Button valider = new Button("Valider");
        valider.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';-fx-font-size: 15px;"); // Couleur du bouton
        valider.setOnMouseEntered(e -> valider.setStyle("-fx-background-color: #808080;-fx-font-family: 'Roboto Light';-fx-font-size: 15px;")); // Changement de couleur au survol
        valider.setOnMouseExited(e -> valider.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';-fx-font-size: 15px;"));  // Changement de couleur à la sortie du survol
        valider.setOnMousePressed(e -> valider.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';-fx-font-size: 15px;")); // Changement de couleur au clic

        valider.setOnMouseClicked(event1 -> {

            //On récupère les champs de saisie
            String nomUtilisateurRecup = nomUtilisateur.getText();
            String emailRecup = email.getText();
            String mdpRecup = mdp.getText();
            String confirmationMdpRecup = confirmationMdp.getText();

            //On vérifie que les champs de saisie ne sont pas vides
            if (nomUtilisateurRecup.isEmpty() || emailRecup.isEmpty() || mdpRecup.isEmpty() || confirmationMdpRecup.isEmpty()) {

                //On affiche une erreur si un ou plusieurs des champs de saisie sont vides
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();

                //On vérifie que le pseudo, l'email et le mot de passe sont valides
            } else if (SecuriteFormulaire.pseudoVerif(nomUtilisateurRecup) && SecuriteFormulaire.emailVerif(emailRecup) && SecuriteFormulaire.mdpVerif(mdpRecup)) {

                //On vérifie que le mot de passe et la confirmation du mot de passe sont identiques
                if (mdpRecup.equals(confirmationMdpRecup)) {
                    // On insère l'utilisateur avec le mdp, mail et pseudo récupérés en sauvegardant un objet Utilisateur
                    Modele.setUser(new Utilisateur(nomUtilisateurRecup, emailRecup, mdpRecup));
                    try {
                        // On insère l'utilisateur dans la base de données
                        Modele.user.save();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    //lancer l'application
                    try {
                        // Appel de startApplication avec le nouveau stage
                        MyApplication.startApplication(this.stage);
                        // On ferme la boite de dialogue
                        dialog.getDialogPane().getScene().getWindow().hide();
                    } catch (IOException e) {
                        // On relance une exception en cas d'erreur
                        throw new RuntimeException(e);
                    }
                } else {
                    //On affiche une erreur si le mot de passe et la confirmation du mot de passe ne sont pas identiques
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur");
                    alert.setContentText("Le mot de passe et la confirmation du mot de passe ne sont pas identiques");
                    alert.showAndWait();
                }
            }

        });

        valider.setMinSize(80, 35);

        //On ajoute les composantes graphiques à la VBox rootInscription
        rootInscription.getChildren().addAll(nomUtilisateur, email, mdp, confirmationMdp, valider);
        rootInscription.setAlignment(javafx.geometry.Pos.CENTER);
        rootInscription.setSpacing(20);

        //On ajoute la VBox rootInscription à la boite de dialogue
        dialog.getDialogPane().setContent(rootInscription);

        //On montre la boite de dialogue et on attend la réponse de l'utilisateur
        dialog.showAndWait();
    }
}
