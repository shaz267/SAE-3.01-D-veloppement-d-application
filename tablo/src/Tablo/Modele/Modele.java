package Tablo.Modele;

import Tablo.Loggeur;
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
     * Attribut listeDestination de la classe Modele qui est un entier qui représente l'id de la liste de destination.
     */
    private static int listeDestination = 0;

    /**
     * Attribut tableaux de la classe Modele qui est un tableau de tableaux qui représente les tableaux de l'application.
     */
    private ArrayList<Tableau> tableaux;

    /**
     * Attribut observateurs de la classe Modele qui est un tableau d'observateurs qui représente les observateurs de l'application.
     */
    private ArrayList<Observateur> observateurs;

    /**
     * Attribut user de la classe Modele qui est un utilisateur qui représente l'utilisateur de l'application.
     */
    public static Utilisateur user = null;

    /**
     * Constructeur de la classe Modele qui initialise les attributs tableauCourant, listeCourante et tacheCourante à -1.
     */
    public Modele() {

        this.tableaux = new ArrayList<>();
        this.observateurs = new ArrayList<>();
    }


    /**
     * Méthode qui permet de changer la tâche courante.
     */
    public static void setTacheCourante(int numTache) {

        tacheCourante = numTache;
    }

    public static void setListeDestination(int listeDestination) {
        Modele.listeDestination = listeDestination;
    }

    public static int getListeDestination() {
        return listeDestination;
    }

    /**
     * Méthode qui ajoute un tableau à l'application.
     *
     * @param tableau le tableau à ajouter
     */
    public void ajouterTableau(Tableau tableau) {

        //On ajoute le tableau à la liste des tableaux
        this.tableaux.add(tableau);

        //On change le tableau courant
        this.changerTableauCourant(tableau.getNumTableau());

        //On notifie les observateurs
        this.notifierObservateurs();
    }

    /**
     * Méthode qui permet de changer le tableau courant.
     */
    public void changerTableauCourant(int numTableau) {

        tableauCourant = numTableau;
        //On Notifie les observateurs
        this.notifierObservateurs();
    }

    /**
     * Méthode qui ajoute une tache à la liste courante. et au tableau courant
     * @param tache la tache à ajouter
     */
    public void ajouterTache(Tache tache) {

        for (Tableau tableau : tableaux) {
            // Si le tableau est le tableau courant alors on ajoute la tâche au tableau courant
            if (tableau.getNumTableau() == tableauCourant) {
                tableau.ajouterTache(tache);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui déplace une tache dans le tableau courant.
     * @param tache la tache à déplacer
     * @param listeDestination la liste de destination
     */
    public void deplacerTache(Tache tache, int listeDestination) {

        for (Tableau tableau : tableaux) {
            // Si le tableau est le tableau courant alors on déplace la tâche dans le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {
                tableau.deplacerTache(tache, listeDestination);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui déplace une tache dans le tableau courant.
     * @return true si on a déplacé la tache, false sinon
     */
    public boolean archiverTache() {
        for (Tableau tableau : tableaux) {
            // Si le tableau est le tableau courant alors on archive la tâche dans le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {
                return tableau.archiverTache();
            }
        }
        return false;
    }

    /**
     * Méthode qui change le titre de la tache dans la liste courante et dans le tableau courant.
     * @param nouveauTitre nouveau titre de la tache
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
     * @param nouveauContenu le nouveau contenu de la tâche
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
     * @param dateDebut Date de début de la tâche
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
     * @param dateLimite la nouvelle date de fin
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
     * Méthode qui ajoute une sous tâche à la tâche courante.
     * @param t la sous tâche à ajouter
     * @return true si on a ajouté la sous tâche, false sinon
     */
    public boolean ajouterSousTache(Tache t) {

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
     * @param l la liste à ajouter
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
    public boolean retirerListe() {
        // On parcourt les tableaux
        for (Tableau tableau : tableaux) {
            // Quand on a trouvé le tableau courant alors on retire la liste et on notifie les observateurs
            if (tableau.getNumTableau() == tableauCourant) {

                if (tableau.retirerListe()) {
                    this.notifierObservateurs();
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Méthode qui permet de changer le titre d'un tableau
     * @param titreTableau
     */
    public void changerTitreTableau(String titreTableau) {

        // On parcourt les tableaux
        for (Tableau tableau : tableaux) {

            // Quand on a trouvé le tableau courant alors on change le titre du tableau et on notifie les observateurs
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerTitre(titreTableau);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change le titre de la liste dans le tableau courant.
     * @param nouveauTitre nouveau titre de la liste
     */
    public void changerTitreListe(String nouveauTitre) {
        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerTitreListe(nouveauTitre);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retourne la liste de tableaux.
     * @return les tableaux
     */
    public ArrayList<Tableau> getTableaux() {
        return tableaux;
    }

    /**
     * Méthode qui retourne la liste de liste du tableau courant.
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

    /**
     * Méthode qui ajoute un observateur à la liste des observateurs.
     * @param o l'observateur à ajouter
     */
    public void enregistrerObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    /**
     * Méthode qui supprime un observateur de la liste des observateurs.
     * @param o l'observateur à supprimer
     */
    public void supprimerObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    /**
     * Méthode qui notifie les observateurs.
     */
    public synchronized void notifierObservateurs() {
        for (Observateur observateur : observateurs) {
            observateur.actualiser(this);
        }
    }

    /**
     * Méthode qui retourne le tableau courant.
     * @return tableauCourant
     */
    public static int getTableauCourant() {
        return tableauCourant;
    }

    /**
     * Méthode qui retourne la liste courante.
     * @return listeCourante
     */
    public static int getListeCourante() {
        return listeCourante;
    }

    /**
     * Méthode qui retourne la tâche courante.
     * @return tacheCourante
     */
    public static int getTacheCourante() {
        return tacheCourante;
    }

    /**
     * Méthode qui retourne la liste de tâches du tableau courant.
     * @return taches
     */
    public List<Tache> getTaches() {
        for (Tableau tableau : tableaux) {
            // Si le tableau est le tableau courant alors on retourne la liste de tâches du tableau courant
            if (tableau.getNumTableau() == tableauCourant) {
                return tableau.getTaches();
            }
        }
        return null;
    }

    /**
     * Méthode qui retourne la liste de tâches du tableau courant.
     * @return taches
     */
    public int getNumTableaux(String titre) {
        for (Tableau tableau : this.getTableaux()) {
            // Si le titre du tableau est le même que le titre passé en paramètre alors on retourne le numéro du tableau
            if (tableau.getTitre().equals(titre)) {
                return tableau.getNumTableau();
            }
        }
        return -1;

    }

    /**
     * Méthode qui retourne la liste de tâches du tableau courant.
     * @param numListe
     */
    public static void setListeCourante(int numListe) {
        listeCourante = numListe;
    }

    /**
     * Méthode qui set l'utilisateur
     * @param u l'utilisateur à set
     */
    public static void setUser(Utilisateur u) {
        user = u;
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
            // Si le tableau est le tableau courant alors on retourne la liste de sous tâches du tableau courant
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
            // Si le titre de la tâche est le même que le titre passé en paramètre alors on retourne la tâche
            if (tache.getTitre().equals(titre)) {
                return tache;
            }
        }
        return null;
    }

    /**
     * Méthode qui retourne la tacge associé à l'ID passé en paramètre dans la liste courante et le tableau courant.
     * @param idTache
     * @return
     */
    public int getTacheByID(int idTache) {

        for (Tache tache : this.getTaches()) {

            if (tache.getId() == idTache) {

                return tache.getNumTache();
            }
        }

        return -1;
    }

    /**
     * Méthode qui retourne le titre du tableau courant
     * @return titre
     */
    public String getTitreTableau() {
        for (Tableau tableau : tableaux) {
            // Si le tableau est le tableau courant alors on retourne le titre du tableau courant
            if (tableau.getNumTableau() == tableauCourant) {
                return tableau.getTitre();
            }
        }
        return null;
    }

    /**
     * Méthode qui retourne les tâches archivées du tableau courant
     * @return tachesArchivées
     */
    public ArrayList<Tache> getTachesArchivees() {
        for (Tableau tableau : tableaux) {
            // Si le tableau est le tableau courant alors on retourne la liste de tâches archivées du tableau courant
            if (tableau.getNumTableau() == tableauCourant) {
                return tableau.getTachesArchivees();
            }
        }
        return null;
    }

    /**
     * Methode qui permet de supprimer un tableau
     * @return true si on a supprimé le tableau courant, false sinon
     */
    public boolean retirerTableau() {

        //On initialise le rang du tableau à supprimer
        int rangSuppr = -1;

        //On parcourt les tableaux
        for (int i = 0; i < this.tableaux.size(); i++) {

            //On récupère le tableau
            Tableau tab = this.tableaux.get(i);

            //Si le numéro du tableau est le même que le tableau courant
            if (tab.getNumTableau() == Modele.getTableauCourant()) {

                //Si on a supprimé le tableau
                if (this.tableaux.remove(tab)) {

                    //On enregistre l'action dans les logs
                    Loggeur.enregistrer("Suppression du tableau " + tab.getTitre());

                    //On récupère le rang du tableau à supprimer
                    rangSuppr = i;

                    //Si on a supprimé le tableau 1 alors on change le tableau courant au nouveau tableau 1
                    if (Modele.getTableauCourant() == 1) {
                        this.changerTableauCourant(1);

                        //Sinon on décrémente le numéro du tableau courant
                    } else {
                        this.changerTableauCourant(Modele.getTableauCourant() - 1);
                    }

                }
            }

        }

        //On décrémente les numéros des tableaux suivants si on a supprimé le tableau courant
        if (rangSuppr != -1) {

            //On décrémente les numéros des tableaux suivants
            for (int i = rangSuppr; i < this.tableaux.size(); i++) {
                Tableau tab = this.tableaux.get(i);
                tab.setNumTableau(tab.getNumTableau() - 1);
            }

            //On notifie les observateurs
            this.notifierObservateurs();

            //On retourne true si on a supprimé le tableau courant
            return true;
        }

        //On retourne false si on n'a pas supprimé le tableau courant
        return false;
    }
}
