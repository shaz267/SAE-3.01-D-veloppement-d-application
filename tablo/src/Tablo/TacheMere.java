package Tablo;

import java.util.ArrayList;

public class TacheMere extends Tache{

    /**
     * Attribut taches de la classe TacheMere qui est un tableau de tâches qui représente les tâches filles de la tâche mère.
     */
    private ArrayList<Tache> taches;

    /**
     * Constructeur de la classe Tache qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     *
     * @param titre
     * @param contenu
     */
    public TacheMere(String titre, String contenu) {
        //On appelle le constructeur de la classe mère.
        super(titre, contenu);
        this.taches = new ArrayList<Tache>();
    }

    /**
     * Méthode qui ajoute une tâche fille à la tâche mère.
     *
     * @param tache
     */
    public void ajouterTache(Tache tache) {
        this.taches.add(tache);
    }

    /**
     * Méthode qui supprime une tâche fille de la tâche mère.
     *
     * @param tache
     */
    public void supprimerTache(Tache tache) {
        this.taches.remove(tache);
    }

}
