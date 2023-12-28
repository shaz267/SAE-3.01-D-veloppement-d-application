package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import Tablo.Vue.VueListe;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControleurListeCliquee implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurListeCliquee
     * @param m Modele de l'application
     */
    public ControleurListeCliquee(Modele m){
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer la modification d'une liste
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère la liste qui a été cliquée
        VueListe vl = (VueListe) mouseEvent.getSource();

        vl.setStyle("-fx-background-color: #666666;");

        //On récupère le numéro de la liste
        int numListe = vl.getNumListe();

        //On change la liste courante
        Modele.setListeCourante(numListe);

        if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){

            //On créé la boîte de dialogue
            ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
            dialog.setTitle("Modifier Liste");
            dialog.setHeaderText("Modifier une liste");

            // On crée une VBox qui va contenir les choix de l'utilisateur
            VBox conteneur = new VBox();

            // On crée le TextArea qui va contenir le nouveau titre de la liste
            TextField champ_saisie = new TextField();

            // On crée le bouton supprimer pour supprimer la liste
            Button supprimer = new Button("Supprimer");

            // On ajoute le controleur pour supprimer la liste
            supprimer.setOnMouseClicked(new ControleurSupprimerListe(this.modele));

            // On ajoute les composantes graphiques à la VBox
            conteneur.getChildren().addAll(champ_saisie, supprimer);

            // On ajoute la VBox à la boîte de dialogue
            dialog.getDialogPane().setContent(conteneur);

            // Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
            dialog.showAndWait();

            // Si le champ de saisie n'est pas vide
            if(!champ_saisie.getText().isEmpty()) {
                // On récupère le nouveau titre de la liste
                String titre = champ_saisie.getText();
                this.modele.changerTitreListe(titre);

                // On notifie les observateurs
                this.modele.notifierObservateurs();
                // On enregistre l'action dans les logs
                Loggeur.enregistrer("Modification de titre d'une liste");
            }
        }
    }
}
