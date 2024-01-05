package Tablo.Modele;

import Tablo.Loggeur;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe abstraite qui permet de gérer les tâches de notre application.
 */
public abstract class Tache {

    /**
     * Attribut id de la classe Tache qui est un entier qui représente l'identifiant de la tâche.
     */
    protected int id;

    /**
     * Attribut titre de la classe Tache qui est une chaîne de caractères qui représente le titre de la tâche.
     */
    protected String titre;

    /**
     * Attribut contenu de la classe Tache qui est une chaîne de caractères qui représente le contenu de la tâche.
     */
    protected String contenu;

    /**
     * Attribut dateLimite de la classe Tache qui est une date qui représente la date limite de la tâche.
     */
    protected LocalDate dateLimite;

    /**
     * Attribut dateDebut de la classe Tache qui est une date qui représente la date de début de la tâche.
     */
    protected LocalDate dateDebut;

    /**
     * Attribut estTerminee de la classe Tache qui est un booléen qui représente si la tâche est terminée ou non.
     */
    protected boolean estTerminee;

    /**
     * Attribut estArchivee de la classe Tache qui est un booléen qui représente si la tâche est archivée ou non.
     */
    protected boolean estArchivee;

    protected int numTache;

    /**
     * Constructeur de la classe Tache qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     *
     * @param titre
     */
    public Tache(int numTache, String titre) {

        //On initialise l'id à -1 car il n'est pas encore défini selon le patron Active Record.
        this.id = -1;

        this.numTache = numTache;

        this.titre = titre;
        this.contenu = "";
        this.estTerminee = false;

        this.dateDebut = LocalDate.now();
        this.dateLimite = LocalDate.now().plusDays(1);

        this.estArchivee = false;
    }

    /**
     * Méthode qui change le titre de la tâche.
     *
     * @param titre
     */
    public void changerTitre(String titre) {

        Loggeur.enregistrer("Changement du titre de la tâche " + this.titre + " en " + titre);
        this.titre = titre;
    }

    /**
     * Méthode qui change le contenu de la tâche.
     *
     * @param contenu
     */
    public void changerContenu(String contenu) {

        //On enregistre l'action dans les logs et on change le contenu de la tâche.
        Loggeur.enregistrer("Changement du contenu de la tâche " + this.titre + " en " + contenu);
        this.contenu = contenu;
    }

    /**
     * Méthode qui set la date limite de la tâche.
     *
     * @param dateDebut
     */
    public void modifierDateDebut(LocalDate dateDebut) {

        Loggeur.enregistrer("Changement de la date de début de la tâche " + this.titre + " en " + dateDebut);
        this.dateDebut = dateDebut;
    }

    /**
     * Méthode qui set la date limite de la tâche.
     *
     * @param dateLimite
     */
    public void modifierDateLimite(LocalDate dateLimite) {

        //On enregistre l'action dans les logs et on change la date limite de la tâche.
        Loggeur.enregistrer("Changement de la date limite de la tâche " + this.titre + " en " + dateLimite);
        this.dateLimite = dateLimite;
    }

    /**
     * Méthode qui ajoute une tâche fille à la tâche mère. Elle serra redéfinie dans la classe TacheMere. Et dans la classe TacheSimple, elle retournera false.
     *
     * @param tache
     * @return
     */
    public abstract boolean ajouterTache(Tache tache);


    /**
     * Méthode qui supprime une tâche fille de la tâche mère. Elle serra redéfinie dans la classe TacheMere. Et dans la classe TacheSimple, elle retournera false.
     *
     * @param tache
     * @return
     */
    public abstract boolean supprimerTache(Tache tache);



    /**
     * Méthode qui met la tâche en terminée.
     *
     * @return titre
     */
    public void fini() {

        Loggeur.enregistrer("La tâche " + this.titre + " est terminée");
        this.estTerminee = true;
    }


    /**
     * Méthode qui retourne l'attribut titre de la tâche.
     *
     * @return titre
     */
    public String getTitre() {
        return this.titre;
    }

    public int getId() {
        return this.id;
    }

    public boolean isEstTerminee() {
        return estTerminee;
    }

    /**
     * Getter qui retourne l'attribut contenu de la tâche
     *
     * @return contenu
     */
    public String getContenu() {
        return this.contenu;
    }

    /**
     * Getter qui retourne l'attribut dateLimite de la tache
     *
     * @return LocalDate
     */
    public LocalDate getDateLimite() {
        return dateLimite;
    }

    /**
     * Getter qui retourne l'attribut dateDebut de la tache
     *
     * @return LocalDate
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public int getNumTache() {
        return this.numTache;
    }

    public abstract List<Tache> getSousTaches();

    /**
     * On redéfinit la méthode equals pour que deux tâches soient égales si elles ont le même titre. Ce qui nous permettra de les comparer dans les listes.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {

        return this.numTache == ((Tache) o).getNumTache();
    }

    /**
     * On redéfinit la méthode hashCode pour que deux tâches soient égales si elles ont le même titre. Ce qui nous permettra de les comparer dans les listes.
     *
     * @return
     */
    @Override
    public int hashCode() {

        return this.numTache;
    }

    public void archiver(boolean estArchivee) {

        this.estArchivee = estArchivee;
    }

    public boolean isArchivee() {

        return this.estArchivee;
    }
}
