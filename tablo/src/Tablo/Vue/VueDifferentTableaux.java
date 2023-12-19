package Tablo.Vue;

import Tablo.Controleur.ControleurChangementTableau;
import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class VueDifferentTableaux extends VBox implements Observateur {

    private Modele modele;

    private ArrayList<Button> boutonsTableaux;

    public VueDifferentTableaux(Modele modele){
        super();

        this.modele = modele;
        this.boutonsTableaux = new ArrayList<Button>();

        for (Tableau tableau : modele.getTableaux()) {
            Button b = new Button(tableau.getTitre());
            this.boutonsTableaux.add(b);
            this.getChildren().add(b);
        }

    }


    @Override
    public void actualiser(Sujet s) {
        this.modele = (Modele) s;

        if (this.boutonsTableaux.size() < this.modele.getTableaux().size()) {
            for (int i = this.boutonsTableaux.size(); i < this.modele.getTableaux().size(); i++) {
                Button b = new Button(this.modele.getTableaux().get(i).getTitre());
                this.boutonsTableaux.add(b);
                this.getChildren().add(b);
            }
        }

        for (int i = 0; i < this.boutonsTableaux.size(); i++) {
            this.boutonsTableaux.get(i).setText(this.modele.getTableaux().get(i).getTitre());
            this.boutonsTableaux.get(i).setOnMouseClicked(new ControleurChangementTableau(this.modele));
        }
    }
}
