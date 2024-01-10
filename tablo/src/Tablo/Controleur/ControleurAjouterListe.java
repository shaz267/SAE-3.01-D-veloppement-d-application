package Tablo.Controleur;

import Tablo.Modele.Liste;
import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

/**
 * Classe ControleurAjouterListe qui permet de gérer l'ajout d'une liste
 */
public class ControleurAjouterListe implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurAjouterListe
     *
     * @param m Modele de l'application
     */
    public ControleurAjouterListe(Modele m) {
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer l'ajout d'une liste
     *
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        // Création d'une boîte de dialogue pour demander le titre de la liste
        TextInputDialog dialog = new TextInputDialog("Nouvelle liste");
        dialog.setTitle("Ajouter une liste");
        dialog.setHeaderText("Ajouter une liste");
        dialog.setContentText("Veuillez entrer le nom de la liste :");

        // Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
        dialog.showAndWait().ifPresent(titre -> {

            //On vérifie que le titre de la liste est valide
            if (titre.length() > 25 || titre.isEmpty()) {

                //On affiche une alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur");
                alert.setContentText("Le titre de la liste ne doit pas être vide et ne doit pas dépasser 25 caractères");
                alert.showAndWait();

            }else{
                //On ajoute la liste au modele
                modele.ajouterListe(titre);
            }

        });
    }
}
