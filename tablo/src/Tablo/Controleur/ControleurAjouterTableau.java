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

public class ControleurAjouterTableau implements EventHandler<MouseEvent> {

    private Modele modele;

    public ControleurAjouterTableau(Modele m){
        this.modele = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

            TextInputDialog dialog = new TextInputDialog("Nouveau tableau");
            dialog.setTitle("Ajouter un tableau");
            dialog.setHeaderText("Ajouter un tableau");
            dialog.setContentText("Veuillez entrer le nom du tableau :");

            dialog.showAndWait().ifPresent(titre -> {

                //On vérifie que le titre n'est pas déjà utilisé
                for (Tableau t : this.modele.getTableaux()) {
                    if (t.getTitre().equals(titre)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur");
                        alert.setContentText("Le titre du tableau est déjà utilisé");
                        alert.showAndWait();
                        return;
                    }
                }

                // Création du tableau
                Tableau t = new Tableau(titre);

                //On ajoute la liste au modele
                modele.ajouterTableau(t);

                this.modele.enregistrerObservateur(new VueTableau(t.getNumTableauMax(), titre));

                this.modele.notifierObservateurs();

            });


            Loggeur.enregistrer("Ajout d'un tableau");
    }
}
