package Tablo.Modele;

public class TacheSimple extends Tache{
    /**
     * Constructeur de la classe TacheSimple qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     *
     * @param titre
     * @param contenu
     */
    public TacheSimple(String titre, String contenu) {
        super(titre, contenu);
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
}