package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

/**
 * Classe ControleurSupprimerTableau qui implémente l'interface EventHandler et qui permet de supprimer un tableau
 */
public class ControleurSupprimerTableau implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Numéro du tableau à supprimer
     */
    private int numTableau;

    /**
     * Constructeur de la classe ControleurSupprimerTableau
     * @param modele Modele de l'application
     */
    public ControleurSupprimerTableau(Modele modele, int numTableau) {
        this.modele = modele;
        this.numTableau = numTableau;
    }

    /**
     * Méthode qui permet de gérer la suppression d'un tableau
     * @param mouseEvent Evènement qui permet de gérer la suppression d'un tableau
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //Si on supprime le tableau courant on change le tableau courant
        if (Modele.getTableauCourant() == this.numTableau) {
            this.modele.changerTableauCourant(this.modele.getTableaux().get(0).getNumTableauMax());
        }

        //Si on essaye de supprimer le dernier tableau on affiche une erreur
        if (this.modele.getTableaux().size() == 1){

            //On affiche une erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");
            alert.setContentText("Vous ne pouvez pas supprimer le dernier tableau");
            alert.showAndWait();

            //sinon on supprime le tableau
        }else {
            //On retire le tableau
            this.modele.retirerTableau(this.numTableau);

            //On notifie les observateurs
            this.modele.notifierObservateurs();

            //On informe le logger
            Loggeur.enregistrer("Suppression du tableau " + this.numTableau);
        }

    }
}
