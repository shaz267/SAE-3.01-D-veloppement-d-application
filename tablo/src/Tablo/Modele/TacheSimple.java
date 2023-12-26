package Tablo.Modele;

import java.util.ArrayList;
import java.util.List;

public class TacheSimple extends Tache{
    /**
     * Constructeur de la classe TacheSimple qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     *
     * @param titre
     */
    public TacheSimple(int numTache, String titre) {
        super(numTache, titre);
    }

    /**
     * Méthode qui retourne uniquement false car une tâche simple ne peut pas avoir de tâche fille.
     *
     * @param tache
     */
    public boolean ajouterTache(Tache tache) {
        return false;
    }

    /**
     * Méthode qui retourne uniquement false car une tâche simple ne peut pas avoir de tâche fille.
     * @param tache
     * @return
     */
    public boolean supprimerTache(Tache tache) {
        return false;
    }

    /**
     * Affiche sous la forme d'un arbre les tâches filles de la tâche mère.
     * @return
     */
    public String toString() {

        return "> " + this.titre;
    }

    /**
     * Retourne null car une tâche simple ne peut pas avoir de tâche fille.
     * @return
     */
    public List<Tache> getSousTaches() {
        return null;
    }
}
