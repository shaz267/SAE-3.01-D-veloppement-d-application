package Tablo.Modele;

import Tablo.Loggeur;

import java.util.ArrayList;
import java.util.Date;
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
     * @param t titre du tableau
     */
    public Tableau(String t){
        this.id = -1;
        this.titre = t;
        this.numTableau = numTableauMax;
        numTableauMax++;
        this.listes = new ArrayList<>();
    }

    /**
     * Ajoute une tâche à la liste courante
     * @param t
     */
    public void ajouterTache(Tache t){

        for (Liste l : this.listes) {

            if (l.getNumListe() == Modele.getListeCourante()) {
                l.ajouterTache(t);
            }
        }
    }

    /**
     * Retire une tâche de la liste courante
     * @param t
     */
    public void retirerTache(Tache t){
        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {

                l.retirerTache(t);
            }
        }
    }

    /**
     * Change le titre de la tache dans la liste courante
     * @param nouveauTitre
     */
    public void changerTitreTache(String nouveauTitre){
        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {

                l.changerTitreTache(nouveauTitre);
            }
        }
    }

    /**
     * Change le titre de la liste courante
     * @param nouveauContenu
     */
    public void changerContenuTache(String nouveauContenu){
        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {

                l.changerContenuTache(nouveauContenu);
            }
        }
    }

    /**
     * Modifie la date limite de la tache dans la liste courante
     * @param date
     */
    public void modifierDateLimite(Date date){
        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {

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
     * @param l liste à ajouter
     */
    public void ajouterListe(Liste l) {
        this.listes.add(l);
        Loggeur.enregistrer("Ajout de la liste " + l.getTitre() + " au tableau " + this.titre);
    }

    /**
     * Retire une liste de l'objet Tableau
     * @param l
     */
    public void retirerListe(Liste l){
        Loggeur.enregistrer("Suppression de la liste " + l.getTitre() + " du tableau " + this.titre);
        this.listes.remove(l);
    }

    /**
     * Retire l'Id de la liste de l'objet Tableau
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retourne la liste des listes de l'objet Tableau
     * @return listes
     */
    public ArrayList<Liste> getListes() {
        return (ArrayList<Liste>) this.listes;
    }

    /**
     * Retourne le numéro du tableau
     * @return numTableau
     */
    public int getNumTableau() {
        return numTableau;
    }

    public List<Tache> getTaches() {

        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {

                return l.getTaches();
            }
        }
        return null;
    }
}