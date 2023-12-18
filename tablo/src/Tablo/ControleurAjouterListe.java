package Tablo;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

public class ControleurAjouterListe implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurAjouterListe
     * @param m Modele de l'application
     */
    public ControleurAjouterListe(Modele m){
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer l'ajout d'une liste
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        int numListe = this.modele.getListes().size() + 1;

        // Création d'une nouvelle liste
        TextInputDialog dialog = new TextInputDialog("Nouvelle liste");
        dialog.setTitle("Ajouter une liste");
        dialog.setHeaderText("Ajouter une liste");
        dialog.setContentText("Veuillez entrer le nom de la liste :");

        // Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
        dialog.showAndWait().ifPresent(titre -> {
                    // Création de la liste
                    Liste l = new Liste(numListe, titre);

                    //On ajoute la liste au modele
                    modele.ajouterListe(l);

                });

        Loggeur.enregistrer("Ajout d'une liste");
    }
}
