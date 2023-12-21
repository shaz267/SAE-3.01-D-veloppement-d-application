package Tablo.Modele;

import Tablo.MyApplication;
import Tablo.Observateur;
import Tablo.Sujet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Modele implements Sujet {

    /**
     * Attribut tableauCourant de la classe Modele qui est un entier qui représente l'id du tableau courant.
     */
    private static int tableauCourant = 0;

    /**
     * Attribut listeCourante de la classe Modele qui est un entier qui représente l'id de la liste courante.
     */
    private static int listeCourante = 0;

    /**
     * Attribut tacheCourante de la classe Modele qui est un entier qui représente l'id de la tâche courante.
     */
    private static int tacheCourante = 0;

    /**
     * Attribut tableaux de la classe Modele qui est un tableau de tableaux qui représente les tableaux de l'application.
     */
    private ArrayList<Tableau> tableaux;

    /**
     * Attribut observateurs de la classe Modele qui est un tableau d'observateurs qui représente les observateurs de l'application.
     */
    private ArrayList<Observateur> observateurs;

    private MyApplication application;

    /**
     * Constructeur de la classe Modele qui initialise les attributs tableauCourant, listeCourante et tacheCourante à -1.
     */
    public Modele() {

        this.tableaux = new ArrayList<Tableau>();
        this.tableaux.add(new Tableau("Tableau 1"));
        this.observateurs = new ArrayList<Observateur>();
        System.out.println(this.tableaux.get(0).getId());
        System.out.println(this.tableaux.get(0).getNumTableau());
        System.out.println(tableauCourant);

    }

    /**
     * Méthode qui permet de changer le tableau courant.
     */
    public void changerTableauCourant(int id) {
        tableauCourant = id;
    }

    /**
     * Méthode qui permet de changer la liste courante.
     */
    public void changerListeCourante(int num) {

        listeCourante = num;
    }

    /**
     * Méthode qui permet de changer la tâche courante.
     */
    public void changerTacheCourante(int id) {

        tacheCourante = id;
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

            if (tableau.getNumTableau() == tableauCourant) {

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

    /**
     * Méthode qui change le titre de la tache dans la liste courante et dans le tableau courant.
     * @param nouveauTitre
     */
    public void changerTitreTache(String nouveauTitre) {

        for (Tableau tableau : tableaux) {

            if (tableau.getId() == tableauCourant) {

                tableau.changerTitreTache(nouveauTitre);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change le contenu de la tache dans le tableau courant, dans la liste courante
     * @param nouveauContenu
     */
    public void changerContenuTache(String nouveauContenu) {

        for (Tableau tableau : tableaux) {

            if (tableau.getId() == tableauCourant) {

                tableau.changerContenuTache(nouveauContenu);
                this.notifierObservateurs();
            }
        }
    }
    /**
     * Méthode qui change une tache de liste dans le tableau courant.
     * @param tache
     */
    public void deplacerTache(Tache tache) {

        for (Tableau tableau : tableaux) {

            if (tableau.getId() == tableauCourant) {

                tableau.deplacerTache(tache);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change le titre de la liste courante dans le tableau courant.
     * @param dateLimite
     */
    public void modifierDateLimite(Date dateLimite) {

        for (Tableau tableau : tableaux) {

            if (tableau.getId() == tableauCourant) {

                tableau.modifierDateLimite(dateLimite);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui met la tâche en terminée.
     */
    public void fini() {

        for (Tableau tableau : tableaux) {

            if (tableau.getId() == tableauCourant) {

                tableau.fini();
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui ajoute une liste au tableau courant.
     * @param l
     */
    public void ajouterListe(Liste l) {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.ajouterListe(l);

                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retire une liste au tableau courant
     * @param l
     */
    public void retirerListe(Liste l){
        // On parcourt les tableaux
        for(Tableau tableau : tableaux){
            // Quand on a trouvé le tableau courant alors on retire la liste et on notifie les observateurs
            if(tableau.getNumTableau() == tableauCourant){
                tableau.retirerListe(l);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retourne le tableau courant.
     * @return
     */
    public ArrayList<Tableau> getTableaux() {
        return tableaux;
    }

    /**
     * Méthode qui retourne la liste de liste du tableau courant.
     * @return
     */
    public ArrayList<Liste> getListes() {
        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getListes();
            }
        }
        return null;
    }


    public void enregistrerObservateur(Observateur o) {

        this.observateurs.add(o);
    }

    public void supprimerObservateur(Observateur o) {

        this.observateurs.remove(o);
    }

    public synchronized void notifierObservateurs() {

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

    public List<Tache> getTaches() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getTaches();
            }
        }
        return null;
    }

    public int getNumTableaux(String titre) {

        for (Tableau tableau : this.getTableaux()) {
            if (tableau.getTitre().equals(titre)) {

                return tableau.getNumTableau();
            }
        }
        return -1;

    }

    public static void setListeCourante(int numListe) {
        listeCourante = numListe;
    }
}
