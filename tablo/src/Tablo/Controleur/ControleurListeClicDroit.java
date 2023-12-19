package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ControleurListeClicDroit implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurAjouterListe
     * @param m Modele de l'application
     */
    public ControleurListeClicDroit(Modele m){
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer l'ajout d'une liste
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
            // On crée une boîte de dialogue dans laquelle l'utilisateur pourra entrer le nouveau nom de la liste
            TextInputDialog dialog = new TextInputDialog("Modifier la liste");
            dialog.setTitle("Modifier la liste");
            dialog.setHeaderText("Modifier la liste");
            dialog.setContentText("Veuillez entrer le nouveau nom de la liste :");

            // Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
            dialog.showAndWait().ifPresent(titre -> {
                // On modifie donc la liste
                this.modele.getListes().get(Modele.getListeCourante()).changerTitre(titre);
            });

            // On notifie les observateurs
            this.modele.notifierObservateurs();
            // On enregistre l'action dans les logs
            Loggeur.enregistrer("Modification de titre d'une liste");
        }
    }
}
