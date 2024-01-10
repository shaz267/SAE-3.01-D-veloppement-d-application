package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Classe qui gère le clic sur une tâche archivée
 */
public class ControleurArchiveCliquee implements EventHandler<MouseEvent> {

    /**
     * Modèle de l'application
     */
    private Modele modele;

    public ControleurArchiveCliquee(Modele m) {

        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //On change la tache courante
        //On récupère la tache qui a été cliquée
        VueTache vT = (VueTache) mouseEvent.getSource();

        vT.setStyle("-fx-background-color: rgba(167,181,248,0.2); -fx-font-family: 'Roboto Light'; -fx-font-size: 15px; -fx-border-width: 1px; -fx-border-color: #000000; -fx-border-radius: 5px;");

        //On récupère le numéro de la liste
        int numTache = vT.getNumTache();

        //On change la tache courante
        Modele.setTacheCourante(numTache);

        //On créé la boîte de dialogue
        ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
        dialog.setTitle("Modifier Tâche");
        dialog.setHeaderText("Modifier une tâche");

        // On crée une VBox qui va contenir les choix de l'utilisateur
        VBox conteneur = new VBox();

        Button envoyerArchive = new Button("Envoyer sur le tableau");
        envoyerArchive.setOnAction(new ControleurEnvoyerArchive(modele, vT));

        //On ajoute les boutons à la VBox
        conteneur.getChildren().addAll(envoyerArchive);

        //On ajoute la VBox à la boîte de dialogue
        dialog.getDialogPane().setContent(conteneur);

        //On affiche la boîte de dialogue
        dialog.showAndWait();

        //On ferme la boite de dialogue
        vT.getScene().getWindow().hide();
    }
}
