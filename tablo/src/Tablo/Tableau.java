package Tablo;

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

    // Correspond au numéro de la liste courante
    private int listeCourante;

    // Correspond à l'ensemble des listes dans un objet Tableau
    private List<Liste> listes;

    /**
     * Constructeur de l'objet Tableau
     * @param t titre du tableau
     */
    public Tableau(String t){
        this.id = -1;
        this.titre = t;
        this.listeCourante = -1;
        this.listes = new ArrayList<>();
    }

    /**
     * Ajoute une tâche à la liste courante
     * @param t
     */
    public void ajouterTache(Tache t){
        for (Liste l : this.listes) {
            if (l.getId() == this.listeCourante) {
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
            if (l.getId() == this.listeCourante) {
                l.retirerTache(t);
            }
        }
    }

    /**
     * Change le titre de la tache dans la liste courante
     * @param titre
     * @param nouveauTitre
     */
    public void changerTitreTache(String titre, String nouveauTitre){
        for (Liste l : this.listes) {
            if (l.getId() == this.listeCourante) {
                for (Tache t : l.getTaches()) {
                    if (t.getTitre().equals(titre)) {
                        t.changerTitre(nouveauTitre);
                    }
                }
            }
        }
    }

    /**
     * Change le titre de la liste courante
     * @param titre
     * @param nouveauContenu
     */
    public void changerContenuTache(String titre, String nouveauContenu){
        for (Liste l : this.listes) {
            if (l.getId() == this.listeCourante) {
                for (Tache t : l.getTaches()) {
                    if (t.getTitre().equals(titre)) {
                        t.changerContenu(nouveauContenu);
                    }
                }
            }
        }
    }

    /**
     * initialise la date de début de la tache dans la liste courante
     * @param titre
     * @param date
     */
    public void initialiserDateDebut(String titre, Date date){
        for (Liste l : this.listes) {
            if (l.getId() == this.listeCourante) {
                for (Tache t : l.getTaches()) {
                    if (t.getTitre().equals(titre)) {
                        t.initialiserDateDebut(date);
                    }
                }
            }
        }
    }

    /**
     * Modifie la date limite de la tache dans la liste courante
     * @param titre
     * @param date
     */
    public void modifierDateLimite(String titre, Date date){
        for (Liste l : this.listes) {
            if (l.getId() == this.listeCourante) {
                for (Tache t : l.getTaches()) {
                    if (t.getTitre().equals(titre)) {
                        t.modifierDateLimite(date);
                    }
                }
            }
        }
    }

}
