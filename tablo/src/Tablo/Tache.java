package Tablo;

import java.util.Date;

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
    protected Date dateLimite;

    /**
     * Attribut dateDebut de la classe Tache qui est une date qui représente la date de début de la tâche.
     */
    protected Date dateDebut;

    /**
     * Constructeur de la classe Tache qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     * @param titre
     * @param contenu
     */
    public Tache(String titre, String contenu) {

        //On initialise l'id à -1 car il n'est pas encore défini selon le patron Active Record.
        this.id = -1;

        this.titre = titre;
        this.contenu = contenu;
        this.dateDebut = new Date();

        //On initialise la date limite à la date du jour. Elle serra ensuite changée par la méthode modifierDateLimite.
        this.dateLimite = new Date();
    }

    /**
     * Méthode qui change le titre de la tâche.
     * @param titre
     */
    public void changerTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Méthode qui change le contenu de la tâche.
     * @param contenu
     */
    public void changerContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * Méthode qui initialise la date limite de la tâche.
     * @param dateLimite
     */
    public void modifierDateLimite(Date dateLimite) {
        this.dateLimite = dateLimite;
    }

    /**
     * Méthode qui change la date de début de la tâche.
     * @param dateDebut
     */
    public void initialiserDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * Méthode qui retourne l'attribut titre de la tâche.
     * @return titre
     */
    public String getTitre() {
        return this.titre;
    }

}
