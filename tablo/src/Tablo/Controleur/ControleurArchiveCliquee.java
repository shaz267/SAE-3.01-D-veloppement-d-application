package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueListe;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControleurArchiveCliquee implements EventHandler<MouseEvent> {

    /**
     * Modèle de l'application
     */
    private Modele modele;

    public ControleurArchiveCliquee(Modele m){

        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //On change la tache courante
        //On récupère la tache qui a été cliquée
        VueTache vT = (VueTache) mouseEvent.getSource();

        vT.setStyle("-fx-background-color: #666666;");

        //On récupère l'id de la liste
        int numTache = vT.getNumTache();

        //On change la tache courante
        Modele.setTacheCourante(numTache);

        //On créé la boîte de dialogue
        ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
        dialog.setTitle("Modifier Tâche");
        dialog.setHeaderText("Modifier une tâche");

        // On crée une VBox qui va contenir les choix de l'utilisateur
        VBox conteneur = new VBox();

        Button supr = new Button("Supprimer la tâche");
        supr.setOnMouseClicked(new ControleurSupprimerTache(modele));

        Button envoyerArchive = new Button("Envoyer sur le tableau");
        envoyerArchive.setOnMouseClicked(new ControleurEnvoyerArchive(modele));

        //On ajoute les boutons à la VBox
        conteneur.getChildren().addAll(supr, envoyerArchive);

        //On ajoute la VBox à la boîte de dialogue
        dialog.getDialogPane().setContent(conteneur);

        //On affiche la boîte de dialogue
        dialog.showAndWait();
    }
}
