package Tablo.Modele;

import Tablo.Observateur;
import Tablo.Sujet;
import java.time.LocalDate;
import java.util.ArrayList;
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

    /**
     * Constructeur de la classe Modele qui initialise les attributs tableauCourant, listeCourante et tacheCourante à -1.
     */
    public Modele() {

        this.tableaux = new ArrayList<Tableau>();
        this.observateurs = new ArrayList<Observateur>();
    }

    /**
     * Méthode qui permet de changer le tableau courant.
     */
    public static void setTableauCourant(int numTableau) {

        tableauCourant = numTableau;
    }

    /**
     * Méthode qui permet de changer la tâche courante.
     */
    public static void setTacheCourante(int numTache) {

        tacheCourante = numTache;
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
     * Méthode qui permet de changer le tableau courant.
     */
    public void changerTableauCourant(int id) {

        tableauCourant = id;
    }

    /**
     * Méthode qui ajoute une tache à la liste courante. et au tableau courant
     *
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
     *
     */
    public boolean archiverTache() {

        for (Tableau tableau : tableaux) {

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.archiverTache();
            }
        }
        return false;
    }

    /**
     * Méthode qui met la tâche en terminée.
     */
    public void fini() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.fini();
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

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerTitreTache(nouveauTitre);
                // On notifie les observateurs
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

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerContenuTache(nouveauContenu);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change la date de début de la tâche courante
     * @param dateDebut
     */
    public void modifierDateDebut(LocalDate dateDebut) {

        for (Tableau tableau : tableaux) {

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                //On appel la méthode modifierDateDebut de la classe Tableau
                tableau.modifierDateDebut(dateDebut);
                // On notifie les observateurs
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change la date de fin de la tâche courante
     * @param dateLimite
     */
    public void modifierDateLimite(LocalDate dateLimite) {

        for (Tableau tableau : tableaux) {

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.modifierDateLimite(dateLimite);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Permet de rajouter une sous tâche à la tache courante
     */
    public boolean ajouterSousTache(Tache t){

        boolean res = false;

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                res = tableau.ajouterSousTache(t);
                this.notifierObservateurs();
            }
        }

        return res;
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
     * @return true si la liste a été retirée, false sinon
     */
    public boolean retirerListe(){
        // On parcourt les tableaux
        for(Tableau tableau : tableaux){
            // Quand on a trouvé le tableau courant alors on retire la liste et on notifie les observateurs
            if(tableau.getNumTableau() == tableauCourant){
                if (tableau.retirerListe()) {
                    this.notifierObservateurs();
                    return true;
                }
            }
        }

        return false;
    }

    public void changerTitreListe(String nouveauTitre){
        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerTitreListe(nouveauTitre);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retourne le tableau courant.
     *
     * @return les tableaux
     */
    public ArrayList<Tableau> getTableaux() {
        return tableaux;
    }

    /**
     * Méthode qui retourne la liste de liste du tableau courant.
     *
     * @return les listes
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

    /**
     * Méthode qui convertie la tache courante en tache mère.
     */
    public void tacheCouranteEnMere() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.tacheCouranteEnMere();
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retourne la liste de tâches filles de la tâche courante
     * @return
     */
    public List<Tache> getSousTaches() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getSousTaches();
            }
        }
        return null;
    }

    /**
     * Méthode qui retourne la tache associé au titre passé en paramètre dans la liste courante et le tableau courant.
     * @param titre
     * @return
     */
    public Tache getTache(String titre) {

        for (Tache tache : this.getTaches()) {

            if (tache.getTitre().equals(titre)) {

                return tache;
            }
        }

        return null;
    }

    public String getTitreTableau() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getTitre();
            }
        }
        return null;
    }

    public ArrayList<Tache> getTachesArchivees() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getTachesArchivees();
            }
        }
        return null;
    }

    /**
     * Methode qui permet de supprimer un tableau
     * @param numTableau Numéro du tableau à supprimer
     */
    public void retirerTableau(int numTableau) {

        if (this.tableaux.size() > 1) {

            //On retire le tableau
            this.tableaux.remove(numTableau);

            //On change le tableau courant
            this.changerTableauCourant(0);

            //On change le numéro des tableaux
            for (int i = 0; i < this.tableaux.size(); i++) {
                this.tableaux.get(i).setNumTableau(i);
            }

            //On change le numéro des tableauxMax et on notifie les observateurs
            this.getTableaux().get(0).setNumTableauMax(this.getTableaux().size());
            this.notifierObservateurs();


        }
    }
}
