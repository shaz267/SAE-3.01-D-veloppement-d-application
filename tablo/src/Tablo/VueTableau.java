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

        this.numTableau = Modele.nombresTableaux;
        Modele.nombresTableaux++;
    }

    /**
     * Méthode qui permet de mettre à jour la vue
     * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
     */
    @Override
    public void actualiser(Sujet s) {

        //On efface tout
        this.getChildren().clear();

        Modele m = (Modele) s;

        //On récupère le tableau courant
        Tableau t = m.getTableaux().get(this.numTableau);


        for (Liste liste : t.getListes()) {

            VueListe vueListe = new VueListe(liste.getId(), liste.getTitre());
            vueListe.setOnMouseClicked(new ControleurListeCliquee(m));
            this.getChildren().add(vueListe);
        }
    }
}
