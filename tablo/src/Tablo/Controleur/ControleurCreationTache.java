package Tablo.Controleur;


import Tablo.Modele.Liste;
import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Modele.TacheMere;
import Tablo.Vue.VueListe;
import Tablo.Vue.VueTache;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class ControleurCreationTache implements EventHandler<ActionEvent> {

    private Modele modele;

    public ControleurCreationTache(Modele m){
        this.modele = m;
    }


    @Override
    public void handle(ActionEvent actionEvent) {

        int numTache;

        if (this.modele.getTaches() == null) {
            numTache = 1;
        }
        else {
            numTache = this.modele.getTaches().size() + 1;
        }

        //On change la liste courante

        //On récupère la liste qui a été cliquée c'est à dire le parent de la source
        VueListe vl = (VueListe) ((Button) actionEvent.getSource()).getParent();


        //On récupère le numéro de la liste
        int numListe = vl.getNumListe();

        //On change la liste courante
        modele.changerListeCourante(numListe);

        //String titre = "Nouvelle tâche";
        //String contenu = "Contenu de la tâche";

        // Création d'une nouvelle liste
        TextInputDialog dialog = new TextInputDialog("Nouvelle Tache");
        dialog.setTitle("Ajouter une tache");
        dialog.setHeaderText("Ajouter une tache");
        dialog.setContentText("Veuillez entrer le nom de la tache :");

        // Affichage de la boîte de dialogue et attente de la réponse de l'utilisateur
        dialog.showAndWait().ifPresent(titre -> {

            // Création de la liste
            Tache tache = new TacheMere(numTache, titre);

            //On ajoute la liste au modele
            modele.ajouterTache(tache);

        });
    }
}
