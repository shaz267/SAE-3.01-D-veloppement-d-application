package Tablo;

import javafx.scene.layout.HBox;

public class VueTableau extends HBox implements Observateur {

    /**
     * Attribut qui permet de reconnaitre un tableau
     */
    private int numTableau;

    /**
     * Constructeur de la classe VueTableau
     * @param numTableau
     * @param titre
     */
    public VueTableau(int numTableau, String titre) {

        super();
        this.numTableau = numTableau;
        this.setSpacing(10);
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        Modele m = (Modele) s;

        //On récupère le tableau courant
        //Tableau t = m.getTableaux().get(Modele.getTableauCourant());
        Tableau t = m.getTableaux().get(0);

        //On récupère la liste courante
        Liste l = t.getListes().get(t.getListes().size() - 1);

        //On crée la vue de la liste
        VueListe vl = new VueListe(l.getId(), l.getTitre());

        //On ajoute la vue de la liste à la vue du tableau
        this.getChildren().add(vl);
    }
}
