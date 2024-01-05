package Tablo.Modele;

import Tablo.Loggeur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de gérer les Listes de tâches de notre application.
 */
public class Tableau {

    // Correspond à l'id de l'objet Liste
    private int id;

    // Correspond au titre/nom du tableau
    private String titre;

    // Correspond à l'ensemble des listes dans un objet Tableau
    private List<Liste> listes;

    private static int numTableauMax = 0;


    private int numTableau;

    /**
     * Constructeur de l'objet Tableau
     *
     * @param t titre du tableau
     */
    public Tableau(String t) {
        this.id = -1;
        this.titre = t;
        this.numTableau = numTableauMax;
        numTableauMax++;
        this.listes = new ArrayList<>();
    }

    /**
     * Ajoute une tâche à la liste courante
     *
     * @param t tâche à ajouter
     */
    public void ajouterTache(Tache t) {

        for (Liste l : this.listes) {

            if (l.getNumListe() == Modele.getListeCourante()) {

                //On ajoute la tâche à la liste courante
                l.ajouterTache(t);
            }
        }
    }

    /**
     * Retire une tâche de la liste courante
     */
    public boolean archiverTache() {

        for (Liste l : this.listes) {
            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                return l.archiverTache();
            }
        }
        return false;
    }

    /**
     * Change le titre de la tache dans la liste courante
     *
     * @param nouveauTitre nouveau titre de la tache
     */
    public void changerTitreTache(String nouveauTitre) {
        for (Liste l : this.listes) {
            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                l.changerTitreTache(nouveauTitre);
            }
        }
    }

    /**
     * Change le titre de la liste courante
     *
     * @param nouveauContenu nouveau contenu de la tache
     */
    public void changerContenuTache(String nouveauContenu) {

        for (Liste l : this.listes) {
            //On vérifie que la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                //On change le contenu de la tâche
                l.changerContenuTache(nouveauContenu);
            }
        }
    }

    /**
     * Deplacer une tache dans une autre liste
     * @param tache
     * @param idListeDestination
     * retirer la tache de la liste courante
     * ajouter la tache à la liste de destination
     */
    public void deplacerTache(Tache tache, int idListeDestination) {
        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {
               // l.retirerTache(tache);
            }

        }
    }


    /**
     * Modifie la date limite de la tache dans la liste courante
     *
     * @param date date de début de la tache
     */
    public void modifierDateDebut(LocalDate date) {
        for (Liste l : this.listes) {
            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                //On change la date de début de la tâche
                l.modifierDateDebut(date);
            }
        }
    }

    /**
     * Modifie la date limite de la tache dans la liste courante
     *
     * @param date date limite de la tache
     */
    public void modifierDateLimite(LocalDate date) {
        for (Liste l : this.listes) {
            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                l.modifierDateLimite(date);
            }
        }
    }

    /**
     * Appelle la méthode fini() de la liste courante
     */
    public void fini(){

        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {

                l.fini();
            }
        }
    }

    /**
     * Ajoute une liste à l'objet Tableau
     *
     * @param l liste à ajouter
     */
    public void ajouterListe(Liste l) {
        this.listes.add(l);
        Loggeur.enregistrer("Ajout de la liste " + l.getTitre() + " au tableau " + this.titre);
    }

    /**
     * Retire une liste de l'objet Tableau
     *
     * @return true si on a supprimé la liste courante, false sinon
     */
    public boolean retirerListe() {

        //int qui permet de savoir si on a supprimé la liste courante et à partir de là on décrémente les numéros des listes suivantes
        int rangSuppr = -1;

        //On parcourt les listes du tableau pour trouver la liste à supprimer et on décrémente les numéros des listes suivantes
        for (int i = 0; i < this.listes.size(); i++) {

            //On récupère la liste
            Liste liste = this.listes.get(i);

            //Si on a trouvé la liste à supprimer
            if (liste.getNumListe() == Modele.getListeCourante()) {
                if (this.listes.remove(liste)) {
                    //On enregistre l'action dans les logs
                    Loggeur.enregistrer("Suppression de la liste " + liste.getTitre() + " du tableau " + this.titre);
                    //On récupère le rang de la liste à supprimer
                    rangSuppr = i;
                    //On décrémente la liste courante
                    Modele.setListeCourante(Modele.getListeCourante() - 1);
                }
            }
        }

        //On décrémente les numéros des listes suivantes si on a supprimé la liste courante
        if (rangSuppr != -1) {
            for (int i = rangSuppr; i < this.listes.size(); i++) {
                Liste liste = this.listes.get(i);
                liste.setNumListe(liste.getNumListe() - 1);
            }

            //On retourne true si on a supprimé la liste courante
            return true;
        }
        //On retourne false si on n'a pas supprimé la liste courante
        return false;
    }

    public void changerTitreListe(String nouveauTitre) {
        for (Liste l : this.listes) {
            if (l.getNumListe() == Modele.getListeCourante()) {
                l.changerTitreListe(nouveauTitre);
            }
        }
    }

    /**
     * Retire l'Id de la liste de l'objet Tableau
     *
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retourne la liste des listes de l'objet Tableau
     *
     * @return listes
     */
    public ArrayList<Liste> getListes() {
        return (ArrayList<Liste>) this.listes;
    }

    /**
     * Retourne le numéro du tableau
     *
     * @return numTableau
     */
    public int getNumTableau() {
        return numTableau;
    }

    public List<Tache> getTaches() {

        for (Liste l : this.listes) {

            if (l.getNumListe() == Modele.getListeCourante()) {

                return l.getTaches();
            }
        }
        return null;
    }

    /**
     * Permet de rajouter une sous tâche à la tache courante
     */
    public boolean ajouterSousTache(Tache t) {

        boolean res = false;

        for (Liste l : this.listes) {
            if (l.getNumListe() == Modele.getListeCourante()) {
                res = l.ajouterSousTache(t);
            }
        }

        return res;
    }

    /**
     * Retourne le titre du tableau
     *
     * @return titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Retourne le numéro des tableaux créés
     *
     * @return numTableauMax
     */
    public int getNumTableauMax() {
        return numTableauMax;
    }

    /**
     * Méthode qui convertie la tache courante en tache mère.
     */
    public void tacheCouranteEnMere() {

        for (Liste l : this.listes) {
            if (l.getNumListe() == Modele.getListeCourante()) {
                l.tacheCouranteEnMere();
            }
        }
    }

    public List<Tache> getSousTaches() {

        for (Liste l : this.listes) {
            if (l.getNumListe() == Modele.getListeCourante()) {
                return l.getSousTaches();
            }
        }
        return null;
    }

    public ArrayList<Tache> getTachesArchivees() {

        ArrayList<Tache> tachesArchivees = new ArrayList<>();

        for (Liste l : this.listes) {
            tachesArchivees.addAll(l.getTachesArchivees());
        }

        return tachesArchivees;
    }

    /**
     * Change le numéro des tableaux créés
     *
     * @param numTableauMax nouveau numéro des tableaux créés
     */
    public void setNumTableauMax(int numTableauMax) {
        Tableau.numTableauMax = numTableauMax;
    }

    /**
     * Change le titre du tableau
     *
     * @param nouveauTitre nouveau titre du tableau
     */
    public void changerTitre(String nouveauTitre) {
        Loggeur.enregistrer("Changement du titre du tableau " + this.titre + " en " + nouveauTitre);
        this.titre = nouveauTitre;
    }

    /**
     * Change le numéro du tableau
     *
     * @param numTableau nouveau numéro du tableau
     */
    public void setNumTableau(int numTableau) {
        this.numTableau = numTableau;
    }
}
