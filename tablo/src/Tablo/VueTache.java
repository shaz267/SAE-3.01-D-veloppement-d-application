package Tablo;

import javafx.scene.text.Text;

public class VueTache extends Text implements Observateur {

    /**
     * Attribut qui permet de reconnaitre une tache
     */
    private int numTache;

    /**
     * Constructeur de la classe VueTache
     * @param numTache
     * @param titre
     */
    public VueTache(int numTache, String titre){

        super(titre);
        this.numTache = numTache;
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {


    }
}
