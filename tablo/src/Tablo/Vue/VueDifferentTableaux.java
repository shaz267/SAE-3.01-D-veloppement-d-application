package Tablo.Vue;

import Tablo.Controleur.ControleurChangementTableau;
import Tablo.Controleur.ControleurTableauClicDroit;
import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Classe VueDifferentTableaux qui hérite de VBox et qui implémente l'interface Observateur
 */
public class VueDifferentTableaux extends VBox implements Observateur {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Liste des boutons qui permettent de changer de tableau
     */
    private ArrayList<Button> boutonsTableaux;

    /**
     * Constructeur de la classe VueDifferentTableaux
     *
     * @param modele Modele de l'application
     */
    public VueDifferentTableaux(Modele modele) {
        super();

        // On récupère le modele
        this.modele = modele;

        // On créé la liste des boutons
        this.boutonsTableaux = new ArrayList<>();

        // On ajoute les boutons dans la vue
        for (Tableau tableau : modele.getTableaux()) {
            Button b = new Button(tableau.getTitre());
            this.boutonsTableaux.add(b);
            this.getChildren().add(b);
        }

    }

    /**
     * Méthode qui permet de mettre à jour la vue
     *
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        // On récupère le modele
        this.modele = (Modele) s;

        // Si il y a plus de tableaux dans le modele que de boutons dans la vue, on ajoute des boutons
        if (this.boutonsTableaux.size() < this.modele.getTableaux().size()) {
            for (int i = this.boutonsTableaux.size(); i < this.modele.getTableaux().size(); i++) {
                Button b = new Button(this.modele.getTableaux().get(i).getTitre());
                this.boutonsTableaux.add(b);
                this.getChildren().add(b);
            }
        }

        // Si il y a moins de tableaux dans le modele que de boutons dans la vue, on supprime des boutons
        if (this.boutonsTableaux.size() > this.modele.getTableaux().size()) {

            //On supprime tous les boutons et on les remet
            this.getChildren().clear();
            this.boutonsTableaux.clear();

            // On ajoute les boutons dans la vue
            for (Tableau tableau : modele.getTableaux()) {
                Button b = new Button(tableau.getTitre());
                this.boutonsTableaux.add(b);
                this.getChildren().add(b);
            }
        }

        // Si quelque chose a changé dans le modele, on met à jour la vue
        for (int i = 0; i < this.modele.getTableaux().size(); i++) {
            if (!this.boutonsTableaux.get(i).getText().equals(this.modele.getTableaux().get(i).getTitre())) {
                this.boutonsTableaux.get(i).setText(this.modele.getTableaux().get(i).getTitre());
            }
        }

        // Si on clique sur un tableau avec le clic droit, on affiche un menu contextuel
        for (Button boutonsTableau : this.boutonsTableaux) {
            boutonsTableau.setOnMousePressed(new ControleurTableauClicDroit(this.modele));
            boutonsTableau.setOnMouseClicked(new ControleurChangementTableau(this.modele));
        }
    }
}
