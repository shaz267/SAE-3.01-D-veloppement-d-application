package Tablo.Modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui représente une tâche simple. C'est à dire une tâche qui ne peut pas contenir de sous tâches.
 */
public class TacheSimple extends Tache {
    /**
     * Constructeur de la classe TacheSimple qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     *
     * @param titre
     */
    public TacheSimple(int numTache, String titre) {
        super(numTache, titre);
    }

    /**
     * Constructeur de la classe Tache qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     * @param titre
     * @param contenu
     * @param dateDebut
     * @param dateLimite
     * @param estArchivee
     */
    public TacheSimple(String titre, String contenu, LocalDate dateDebut, LocalDate dateLimite, boolean estArchivee){
        super(titre, contenu, dateDebut, dateLimite, estArchivee);
    }

    /**
     * Méthode qui retourne uniquement false car une tâche simple ne peut pas avoir de tâche fille.
     *
     * @param tache
     */
    public boolean ajouterTache(Tache tache) {
        if(Modele.user != null) {
            try {
                this.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Méthode qui retourne uniquement false car une tâche simple ne peut pas avoir de tâche fille.
     *
     * @param tache
     * @return
     */
    public boolean supprimerTache(Tache tache) {
        return false;
    }

    public boolean supprimerSousTache(int sousTache){
        return false;
    }

    /**
     * Affiche sous la forme d'un arbre les tâches filles de la tâche mère.
     *
     * @return
     */
    public String toString() {

        return "> " + this.titre;
    }

    /**
     * Retourne null car une tâche simple ne peut pas avoir de tâche fille.
     *
     * @return
     */
    public List<Tache> getSousTaches() {
        return null;
    }

    /**
     * Méthode qui permet de retourner les sous tâches de la tâche courante. et qui est récursive. Ce qui signifie qu'elle retourne les sous tâches des sous tâches etc
     * @return
     */
    public ArrayList<Tache> getSousTachesReccursif() {
        return null;
    }
}
