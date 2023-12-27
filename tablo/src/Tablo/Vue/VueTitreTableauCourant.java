package Tablo.Vue;

import Tablo.Modele.Modele;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.text.Text;

public class VueTitreTableauCourant extends Text implements Observateur {

    private Modele modele;

    public VueTitreTableauCourant(String titre) {
        super(titre);

        //On change  le style du titre
        this.setStyle("-fx-font-weight: bold;");
        this.setStyle("-fx-font-size: 20px;");
    }

    @Override
    public void actualiser(Sujet s) {
        this.modele = (Modele) s;

        this.setText(modele.getTableaux().get(Modele.getTableauCourant()).getTitre());
    }
}
