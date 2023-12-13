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

    // Correspond à l'ensemble des listes dans un objet Tableau
    private List<Liste> listes;

    /**
     * Constructeur de l'objet Tableau
     * @param t titre du tableau
     */
    public Tableau(String t){
        this.id = -1;
        this.titre = t;
        this.listes = new ArrayList<>();
    }

    /**
     * Ajoute une tâche à la liste courante
     * @param t
     */
    public void ajouterTache(Tache t){
        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {
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

    public int getId() {
        return this.id;
    }
}
