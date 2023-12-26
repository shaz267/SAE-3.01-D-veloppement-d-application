package Tablo.Vue;

import Tablo.Modele.Modele;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.text.Text;
public class VueTitreTableau extends Text implements Observateur {

    public VueTitreTableau() {
        super("");

        //On change le style du titre du tableau
        this.setStyle("-fx-font-weight: bold;");
        this.setStyle("-fx-background-color: #f8b9a7;");

        //Le texte en gras
        this.setFont(javafx.scene.text.Font.font("Verdana", 35));

    }

    @Override
    public void actualiser(Sujet s) {

        Modele m = (Modele) s;

        this.setText(m.getTitreTableau());
    }
}