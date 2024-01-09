package Tablo.Modele;

import Tablo.Loggeur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui représente une tâche mère. C'est à dire une tâche qui peut contenir des sous tâches.
 */
public class TacheMere extends Tache {

    /**
     * Attribut taches de la classe TacheMere qui est un tableau de tâches qui représente les tâches filles de la tâche mère.
     */
    private ArrayList<Tache> taches;

    /**
     * Constructeur de la classe Tache qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     *
     * @param titre
     */
    public TacheMere(int numTache, String titre) {
        //On appelle le constructeur de la classe mère.
        super(numTache, titre);
        this.taches = new ArrayList<Tache>();
    }

    /**
     * Constructeur qui prend un Objet TacheSimple en paramètre. Et le transforme en TacheMere.
     */
    public TacheMere(Tache tache) {
        super(tache.getNumTache(), tache.getTitre());

        this.id = tache.getId();
        this.contenu = tache.getContenu();
        this.dateDebut = tache.getDateDebut();
        this.estTerminee = tache.isEstTerminee();
        this.dateDebut = tache.getDateDebut();
        this.dateLimite = tache.getDateLimite();
        this.estArchivee = tache.isArchivee();
        this.selectionnee = tache.isSelectionnee();

        this.taches = new ArrayList<Tache>();
    }

    /**
     * Méthode qui ajoute une tâche fille à la tâche mère.
     *
     * @param tache
     */
    public boolean ajouterTache(Tache tache) {

        //On crée une condition qui vérifie si la tache contient des sous taches afin d'éviter une Exception lors de l'appel de la méthode contains.
        boolean tacheEstUneSousTacheDeLaTacheMere = false;
        if (tache.getSousTaches() != null) {

            tacheEstUneSousTacheDeLaTacheMere = tache.getSousTaches().contains(this);
        }

        //Si la tâche est déjà dans la liste on ne l'ajoute pas. ou si c'est la meme tache qui à été changé en tache mère. Ou encore si la tache courante est une sous tache de la tache mère.
        if (this.taches.contains(tache) || tache.hashCode() == this.hashCode() || tacheEstUneSousTacheDeLaTacheMere) {
            return false;
        } else {

            Loggeur.enregistrer("Ajout de la tâche " + tache.getTitre() + " à la tâche mère " + this.titre);

            //On récupere la durée de la tache fille. C'est a dire la différence entre la date de début et la date de fin.
            LocalDate dureeTache = tache.getDateLimite().minusDays(tache.getDateDebut().getDayOfYear());

            //Si la date de début de la tache fille est inférieure à la date de fin de la tache mère.
            if (tache.getDateDebut().isBefore(this.dateLimite)) {

                //On modifie la date de la tache fille pour qu'elle soit la même que la tache mère + 1 jour.
                tache.modifierDateDebut(this.dateLimite.plusDays(1));

                //On modifie la date de fin de la tache fille pour que la durée de la tache fille ne change pas.
                tache.modifierDateLimite(tache.getDateDebut().plusDays(dureeTache.getDayOfYear()));
            }

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
     * Méthode qui set la date limite de la tâche.
     *
     * @param dateLimite
     */
    @Override
    public void modifierDateLimite(LocalDate dateLimite) {

        //Pour chaque tache fille de la tache mère On modifie la date de début de la tache fille pour qu'elle soit la même que la date limite de la tache mère + 1 jour.
        for (Tache tache : this.taches) {

            LocalDate dureeTache = tache.getDateLimite().minusDays(tache.getDateDebut().getDayOfYear());

            //Si la date de début de la tache fille est inférieure à la date de fin de la tache mère.
            if (tache.getDateDebut().isBefore(dateLimite)) {

                //On modifie la date de la tache fille pour qu'elle soit la même que la tache mère + 1 jour.
                tache.modifierDateDebut(dateLimite.plusDays(1));

                //On modifie la date de fin de la tache fille pour que la durée de la tache fille ne change pas.
                tache.modifierDateLimite(tache.getDateDebut().plusDays(dureeTache.getDayOfYear()));
            }
        }

        //On enregistre l'action dans les logs et on change la date limite de la tâche.
        Loggeur.enregistrer("Changement de la date limite de la tâche " + this.titre + " en " + dateLimite);
        this.dateLimite = dateLimite;
    }

    public boolean supprimerSousTache(int sousTache){

        //Pour chaques sous taches de la tache courante
        for (Tache tache : this.taches) {
            if(tache.getNumTache() == sousTache){
                return this.taches.remove(tache);
            }
        }
        return false;
    }

    /**
     * Méthode qui retourne le tableau de tâches filles de la tâche mère.
     *
     * @return
     */
    public ArrayList<Tache> getTaches() {
        return taches;
    }

    /**
     * Affiche sous la forme d'un arbre les tâches filles de la tâche mère.
     *
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


    /**
     * Méthode qui retourne la liste de tâches filles de la tâche mère.
     *
     * @return
     */
    public List<Tache> getSousTaches() {

        return this.taches;
    }
}
