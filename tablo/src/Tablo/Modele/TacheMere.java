package Tablo.Modele;

import Tablo.Loggeur;

import java.util.ArrayList;
import java.util.List;

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
    public TacheMere(int numTache, String titre) {
        //On appelle le constructeur de la classe mère.
        super(numTache, titre);
        this.taches = new ArrayList<Tache>();
    }

    /**
     * Constructeur qui prend un Objet TacheSimple en paramètre.
     */
    public TacheMere(Tache tache) {

        super(tache.getNumTache(), tache.getTitre());
        this.taches = new ArrayList<Tache>();
    }

    /**
     * Méthode qui ajoute une tâche fille à la tâche mère.
     *
     * @param tache
     */
    public boolean ajouterTache(Tache tache) {

        //System.out.println("Tache : " + tache.hashCode() + " TacheMere : " + this.hashCode());

        //Si la tâche est déjà dans la liste on ne l'ajoute pas. ou si la tâche est la tâche mère.
        if (this.taches.contains(tache) || tache.hashCode() == this.hashCode()) {
            return false;
        }
        else {
            Loggeur.enregistrer("Ajout de la tâche " + tache.getTitre() + " à la tâche mère " + this.titre);
            return this.taches.add(tache);
        }
    }

    /**
     * Méthode qui supprime une tâche fille de la tâche mère.
     *
     * @param tache
     */
    public boolean supprimerTache(Tache tache) {

        Loggeur.enregistrer("Suppression de la tâche " + tache.getTitre() + " de la tâche mère " + this.titre);
        return this.taches.remove(tache);
    }

    /**
     * Méthode qui retourne le tableau de tâches filles de la tâche mère.
     * @return
     */
    public ArrayList<Tache> getTaches() {
        return taches;
    }

    /**
     * Affiche sous la forme d'un arbre les tâches filles de la tâche mère.
     * @return
     */
    public String toString() {

        String s = "-" + this.titre + "\n";
        for (Tache element : taches) {

            for (String line : element.toString().split("\n")) {
                s += "| " + line + "\n";
            }
        }
        return s;
    }


    public List<Tache> getSousTaches() {

        return this.taches;
    }

}
