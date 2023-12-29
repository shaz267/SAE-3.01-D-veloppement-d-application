package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Vue.VueTableau;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

/**
 * Classe ControleurAjouterTableau qui implémente l'interface EventHandler et qui permet d'ajouter un tableau
 */
public class ControleurAjouterTableau implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurAjouterTableau
     *
     * @param m Modele de l'application
     */
    public ControleurAjouterTableau(Modele m) {
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer l'ajout d'un tableau
     *
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        // On créé la boîte de dialogue
        TextInputDialog dialog = new TextInputDialog("Nouveau tableau");
        dialog.setTitle("Ajouter un tableau");
        dialog.setHeaderText("Ajouter un tableau");
        dialog.setContentText("Veuillez entrer le nom du tableau :");

        // On affiche la boîte de dialogue et on récupère le titre du tableau
        dialog.showAndWait().ifPresent(titre -> {

            //On vérifie que le titre n'est pas déjà utilisé
            for (Tableau t : this.modele.getTableaux()) {

                // Si le titre est déjà utilisé, on affiche une erreur et on quitte la méthode
                if (t.getTitre().equals(titre)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur");
                    alert.setContentText("Le titre du tableau est déjà utilisé");
                    alert.showAndWait();
                    return;
                }
                Loggeur.enregistrer("Ajout du tableau : " + titre + " dans l'application");
            }

            // Si le titre ne dépasse pas 15 caractères, on affiche une erreur et on quitte la méthode
            if (titre.length() > 15) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur");
                alert.setContentText("Le titre du tableau ne doit pas dépasser 15 caractères");
                alert.showAndWait();
                return;
            } else {
                // Création du tableau
                Tableau t = new Tableau(titre);

                //On ajoute la liste au modele
                this.modele.ajouterTableau(t);

                // On ajoute la vue du tableau au modele
                this.modele.enregistrerObservateur(new VueTableau());

                // On notifie les observateurs
                this.modele.notifierObservateurs();
            }
        });
    }
}
