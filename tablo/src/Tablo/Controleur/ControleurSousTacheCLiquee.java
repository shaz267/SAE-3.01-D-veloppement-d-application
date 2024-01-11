package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueTache;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControleurSousTacheCLiquee  implements EventHandler<MouseEvent> {

    /**
     * Modèle de l'application
     */
    private final Modele modele;

    public ControleurSousTacheCLiquee(Modele m) {

        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        //On change la tache courante
        //On récupère la tache qui a été cliquée
        VueTache vT = (VueTache) mouseEvent.getSource();

        //On créé la boîte de dialogue
        ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
        dialog.setTitle("Supprimer Sous-Tâche");
        dialog.setHeaderText("Cliquez sur le bouton pour supprimer la sous-tâche");

        // On crée une VBox qui va contenir les choix de l'utilisateur
        VBox conteneur = new VBox();

        //On crée les boutons
        Button supr = new Button("Supprimer la sous-tâche");
        supr.setOnMouseClicked(new ControleurSupprimerSousTache(modele, vT.getNumTache()));

        //On ajoute les boutons à la VBox
        conteneur.getChildren().add(supr);

        //On ajoute la VBox à la boîte de dialogue
        dialog.getDialogPane().setContent(conteneur);

        //On affiche la boîte de dialogue
        dialog.showAndWait();

        //On ferme la fenêtre
        vT.getScene().getWindow().hide();
    }
}
