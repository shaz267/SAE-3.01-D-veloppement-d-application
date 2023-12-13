package Tablo;

import java.util.ArrayList;

public class Modele implements Sujet{

    /**
     * Attribut tableauCourant de la classe Modele qui est un entier qui représente l'id du tableau courant.
     */
    private static int tableauCourant = -1;

    /**
     * Attribut listeCourante de la classe Modele qui est un entier qui représente l'id de la liste courante.
     */
    private static int listeCourante = -1;

    /**
     * Attribut tacheCourante de la classe Modele qui est un entier qui représente l'id de la tâche courante.
     */
    private static int tacheCourante = -1;

    /**
     * Attribut tableaux de la classe Modele qui est un tableau de tableaux qui représente les tableaux de l'application.
     */
    private ArrayList<Tableau> tableaux;

    /**
     * Attribut observateurs de la classe Modele qui est un tableau d'observateurs qui représente les observateurs de l'application.
     */
    private ArrayList<Observateur> observateurs;

    /**
     * Constructeur de la classe Modele qui initialise les attributs tableauCourant, listeCourante et tacheCourante à -1.
     */
    public Modele() {

        this.tableaux = new ArrayList<Tableau>();
        this.observateurs = new ArrayList<Observateur>();
    }

    /**
     * Méthode qui ajoute un tableau à l'application.
     * @param tableau
     */
    public void ajouterTableau(Tableau tableau) {

        this.tableaux.add(tableau);
        this.notifierObservateurs();
    }

    /**
     * Méthode qui ajoute une tache à la liste courante. et au tableau courant
     * @param tache
     */
    public void ajouterTache(Tache tache) {

        for (Tableau tableau : tableaux) {

            if (tableau.getId() == tableauCourant) {

                tableau.ajouterTache(tache);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retire une tache du tableau courant de la liste courante.
     * @param tache
     */
    public void retirerTache(Tache tache) {

        for (Tableau tableau : tableaux) {

            if (tableau.getId() == tableauCourant) {

                tableau.retirerTache(tache);
                this.notifierObservateurs();
            }
        }
    }

    public void changerTitreTache(String nouveauTitre) {

        for (Tableau tableau : tableaux) {

            if (tableau.getId() == tableauCourant) {

                tableau.changerTitreTache(nouveauTitre);
                this.notifierObservateurs();
            }
        }
    }



    public void enregistrerObservateur(Observateur o) {

        this.observateurs.add(o);
    }

    public void supprimerObservateur(Observateur o) {

        this.observateurs.remove(o);
    }

    public void notifierObservateurs() {

        for (Observateur observateur : observateurs) {

            observateur.actualiser(this);
        }
    }

    public static int getTableauCourant() {
        return tableauCourant;
    }

    public static int getListeCourante() {
        return listeCourante;
    }

    public static int getTacheCourante() {
        return tacheCourante;
    }
}
