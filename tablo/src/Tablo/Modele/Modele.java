package Tablo.Modele;

import Tablo.Loggeur;
import Tablo.Observateur;
import Tablo.Sujet;
import Tablo.Vue.VueTableau;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Modele implements Sujet {

    /**
     * Attribut tableauCourant de la classe Modele qui est un entier qui représente l'id du tableau courant.
     */
    private static int tableauCourant = 0;

    /**
     * Attribut listeCourante de la classe Modele qui est un entier qui représente l'id de la liste courante.
     */
    private static int listeCourante = 0;

    /**
     * Attribut tacheCourante de la classe Modele qui est un entier qui représente l'id de la tâche courante.
     */
    private static int tacheCourante = 0;
    /**
     * Attribut listeDestination de la classe Modele qui est un entier qui représente l'id de la liste de destination.
     */
    private static int listeDestination = 0;

    /**
     * Attribut tableaux de la classe Modele qui est un tableau de tableaux qui représente les tableaux de l'application.
     */
    private ArrayList<Tableau> tableaux;

    /**
     * Attribut observateurs de la classe Modele qui est un tableau d'observateurs qui représente les observateurs de l'application.
     */
    private ArrayList<Observateur> observateurs;

    /**
     * Constructeur de la classe Modele qui initialise les attributs tableauCourant, listeCourante et tacheCourante à -1.
     */
    public Modele() {

        this.tableaux = new ArrayList<Tableau>();
        this.observateurs = new ArrayList<Observateur>();
    }


    /**
     * Méthode qui permet de changer la tâche courante.
     */
    public static void setTacheCourante(int numTache) {

        tacheCourante = numTache;
    }

    public static void setListeDestination(int listeDestination) {
        Modele.listeDestination = listeDestination;
    }

    public static int getListeDestination() {
        return listeDestination;
    }

    /**
     * Méthode qui ajoute un tableau à l'application.
     *
     * @param tableau le tableau à ajouter
     */
    public void ajouterTableau(Tableau tableau) {

        //On ajoute le tableau à la liste des tableaux
        this.tableaux.add(tableau);

        //On change le tableau courant
        this.changerTableauCourant(tableau.getNumTableau());

        //On notifie les observateurs
        this.notifierObservateurs();
    }

    /**
     * Méthode qui permet de changer le tableau courant.
     */
    public void changerTableauCourant(int numTableau) {

        tableauCourant = numTableau;
        //On Notifie les observateurs
        this.notifierObservateurs();
    }

    /**
     * Méthode qui ajoute une tache à la liste courante. et au tableau courant
     *
     * @param tache
     */
    public void ajouterTache(Tache tache) {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.ajouterTache(tache);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui permet de déplacer une tâche d'une liste à une autre.
     */
    public void deplacerTache(Tache tache, int listeDestination) {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.deplacerTache(tache, listeDestination);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retire une tache du tableau courant de la liste courante.
     */
    public boolean archiverTache() {

        for (Tableau tableau : tableaux) {

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.archiverTache();
            }
        }
        return false;
    }

    /**
     * Méthode qui met la tâche en terminée.
     */
    public void fini() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.fini();
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change le titre de la tache dans la liste courante et dans le tableau courant.
     *
     * @param nouveauTitre nouveau titre de la tache
     */
    public void changerTitreTache(String nouveauTitre) {

        for (Tableau tableau : tableaux) {

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerTitreTache(nouveauTitre);
                // On notifie les observateurs
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change le contenu de la tache dans le tableau courant, dans la liste courante
     *
     * @param nouveauContenu le nouveau contenu de la tâche
     */
    public void changerContenuTache(String nouveauContenu) {

        for (Tableau tableau : tableaux) {

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerContenuTache(nouveauContenu);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change la date de début de la tâche courante
     *
     * @param dateDebut Date de début de la tâche
     */
    public void modifierDateDebut(LocalDate dateDebut) {

        for (Tableau tableau : tableaux) {

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                //On appel la méthode modifierDateDebut de la classe Tableau
                tableau.modifierDateDebut(dateDebut);
                // On notifie les observateurs
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change la date de fin de la tâche courante
     *
     * @param dateLimite la nouvelle date de fin
     */
    public void modifierDateLimite(LocalDate dateLimite) {

        for (Tableau tableau : tableaux) {

            // Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.modifierDateLimite(dateLimite);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Permet de rajouter une sous tâche à la tache courante
     */
    public boolean ajouterSousTache(Tache t) {

        boolean res = false;

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                res = tableau.ajouterSousTache(t);
                this.notifierObservateurs();
            }
        }

        return res;
    }

    /**
     * Méthode qui ajoute une liste au tableau courant.
     *
     * @param titre titre de la liste
     */
    public void ajouterListe(String titre) {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                int numListe = this.getListes().size() + 1;

                // Création de la liste
                Liste l = new Liste(numListe, titre);

                tableau.ajouterListe(l);

                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retire une liste au tableau courant
     *
     * @return true si la liste a été retirée, false sinon
     */
    public boolean retirerListe() {
        // On parcourt les tableaux
        for (Tableau tableau : tableaux) {
            // Quand on a trouvé le tableau courant alors on retire la liste et on notifie les observateurs
            if (tableau.getNumTableau() == tableauCourant) {

                if (tableau.retirerListe()) {
                    this.notifierObservateurs();
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Méthode qui permet de changer le titre d'un tableau
     *
     * @param titreTableau
     */
    public void changerTitreTableau(String titreTableau) {

        // On parcourt les tableaux
        for (Tableau tableau : tableaux) {

            // Quand on a trouvé le tableau courant alors on change le titre du tableau et on notifie les observateurs
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerTitre(titreTableau);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui change le titre de la liste dans le tableau courant.
     *
     * @param nouveauTitre nouveau titre de la liste
     */
    public void changerTitreListe(String nouveauTitre) {
        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.changerTitreListe(nouveauTitre);
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retourne le tableau courant.
     *
     * @return les tableaux
     */
    public ArrayList<Tableau> getTableaux() {
        return tableaux;
    }

    /**
     * Méthode qui retourne la liste de liste du tableau courant.
     *
     * @return les listes
     */
    public ArrayList<Liste> getListes() {
        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getListes();
            }
        }
        return null;
    }


    public void enregistrerObservateur(Observateur o) {

        this.observateurs.add(o);
    }

    public void supprimerObservateur(Observateur o) {

        this.observateurs.remove(o);
    }

    public synchronized void notifierObservateurs() {

        for (Observateur observateur : observateurs) {

            observateur.actualiser(this);
        }
    }

    public static int getTableauCourant() {
        return tableauCourant;
    }

    public static int getListeCourante() {
        return listeCourante;
    }

    public static int getTacheCourante() {
        return tacheCourante;
    }

    public List<Tache> getTaches() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getTaches();
            }
        }
        return null;
    }

    public int getNumTableaux(String titre) {

        for (Tableau tableau : this.getTableaux()) {
            if (tableau.getTitre().equals(titre)) {

                return tableau.getNumTableau();
            }
        }
        return -1;

    }

    public static void setListeCourante(int numListe) {
        listeCourante = numListe;
    }

    /**
     * Méthode qui convertie la tache courante en tache mère.
     */
    public void tacheCouranteEnMere() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                tableau.tacheCouranteEnMere();
                this.notifierObservateurs();
            }
        }
    }

    /**
     * Méthode qui retourne la liste de tâches filles de la tâche courante
     *
     * @return
     */
    public List<Tache> getSousTaches() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getSousTaches();
            }
        }
        return null;
    }

    /**
     * Méthode qui retourne la tache associé au titre passé en paramètre dans la liste courante et le tableau courant.
     *
     * @param titre
     * @return
     */
    public Tache getTache(String titre) {

        for (Tache tache : this.getTaches()) {

            if (tache.getTitre().equals(titre)) {

                return tache;
            }
        }

        return null;
    }

    /**
     * Méthode qui retourne la tacge associé à l'ID passé en paramètre dans la liste courante et le tableau courant.
     * @param idTache
     * @return
     */

    public int getTacheByID(int idTache) {

        for (Tache tache : this.getTaches()) {

            if (tache.getId() == idTache) {

                return tache.getNumTache();
            }
        }

        return -1;
    }


    /**
     * Méthode qui retourne le titre du tableau courant
     *
     * @return titre
     */
    public String getTitreTableau() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getTitre();
            }
        }
        return null;
    }

    /**
     * Méthode qui retourne les tâches archivées du tableau courant
     *
     * @return tachesArchivées
     */
    public ArrayList<Tache> getTachesArchivees() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getTachesArchivees();
            }
        }
        return null;
    }

    /**
     * Methode qui permet de supprimer un tableau
     *
     * @return true si on a supprimé le tableau courant, false sinon
     */
    public boolean retirerTableau() {

        //On initialise le rang du tableau à supprimer
        int rangSuppr = -1;

        //On parcourt les tableaux
        for (int i = 0; i < this.tableaux.size(); i++) {

            //On récupère le tableau
            Tableau tab = this.tableaux.get(i);

            //Si le numéro du tableau est le même que le tableau courant
            if (tab.getNumTableau() == Modele.getTableauCourant()) {

                //Si on a supprimé le tableau
                if (this.tableaux.remove(tab)) {

                    //On enregistre l'action dans les logs
                    Loggeur.enregistrer("Suppression du tableau " + tab.getTitre());

                    //On récupère le rang du tableau à supprimer
                    rangSuppr = i;

                    //Si on a supprimé le tableau 1 alors on change le tableau courant au nouveau tableau 1
                    if (Modele.getTableauCourant() == 1) {
                        this.changerTableauCourant(1);

                        //Sinon on décrémente le numéro du tableau courant
                    } else {
                        this.changerTableauCourant(Modele.getTableauCourant() - 1);
                    }

                }
            }

        }

        //On décrémente les numéros des tableaux suivants si on a supprimé le tableau courant
        if (rangSuppr != -1) {

            //On décrémente les numéros des tableaux suivants
            for (int i = rangSuppr; i < this.tableaux.size(); i++) {
                Tableau tab = this.tableaux.get(i);
                tab.setNumTableau(tab.getNumTableau() - 1);
            }

            //On notifie les observateurs
            this.notifierObservateurs();

            //On retourne true si on a supprimé le tableau courant
            return true;
        }

        //On retourne false si on n'a pas supprimé le tableau courant
        return false;
    }

    /**
     * Méthode qui permet de supprimer une sous tache
     * @return
     */
    public boolean supprimerSousTache(int sousTache) {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.supprimerSousTache(sousTache);
            }
        }

        return false;
    }

    /**
     * Méthode qui permet de savoir si la tache courante est selectionnée
     * @return true si la tache est selectionnée, false sinon
     */
    public boolean isSelectionnee() {

        //On parcourt les tableaux
        for (Tableau tableau : this.tableaux) {

            //Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.isSelectionnee();
            }
        }

        return false;
    }

    /**
     * Méthode qui permet de rendre la tache courante selectionnée ou non
     * @param b
     */
    public void setTacheCouranteSelectionnee(boolean b) {

        //On parcourt les tableaux
        for (Tableau tableau : this.tableaux) {

            //Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                tableau.setTacheCouranteSelectionnee(b);
            }
        }
    }

    /**
     * Méthode qui retourne la date de début des taches selectionnées
     * @return
     */
    public LocalDate getDateDebutTachesSelectionnees() {

        //On parcourt les tableaux
        for (Tableau tableau : this.tableaux) {

            //Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getDateDebutTachesSelectionnees();
            }
        }

        return null;
    }

    /**
     * Méthode qui retourne la date de fin des taches selectionnées
     * @return
     */
    public LocalDate getDateFinTachesSelectionnees() {

        //On parcourt les tableaux
        for (Tableau tableau : this.tableaux) {

            //Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getDateFinTachesSelectionnees();
            }
        }

        return null;
    }

    /**
     * Méthode qui retourne la liste des taches selectionnées
     * @return
     */
    public List<Tache> getTachesSelectionnees() {

        //On parcourt les tableaux
        for (Tableau tableau : this.tableaux) {

            //Si le tableau est le tableau courant
            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getTachesSelectionnees();
            }
        }

        return null;
    }

    /**
     * Methode qui vérifie si le tableau existe déjà
     * @param titre titre du tableau
     * @return true si le tableau n'existe pas, false sinon
     */
    public boolean VerifTableauExistant(String titre) {


        for (Tableau t : this.getTableaux()) {

            // Si le titre est déjà utilisé, on affiche une erreur et on quitte la méthode
            if (t.getTitre().equals(titre)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur");
                alert.setContentText("Le template est déjà présent");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    /**
     * Methode qui crée le template 'Conduite de projet'
     * @param titre titre du template
     */
    public void templateConduiteProjet(String titre){

        //On vérifie que le titre n'est pas déjà utilisé
        if (VerifTableauExistant(titre)) {

            // Création du tableau
            Tableau t = new Tableau(this.getTableaux().size() + 1, titre);

            //On ajoute la liste au modele
            this.ajouterTableau(t);

            // On ajoute la vue du tableau au modele
            this.enregistrerObservateur(new VueTableau());

            // Création des différentes listes qui composent le template
            this.ajouterListe("Project Structure");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache = new TacheSimple(this.getTaches().size() + 1, "Parties prenantes");
                //On ajoute la tache au modele
                this.ajouterTache(tache);

                // Création de la tache
                Tache tache2 = new TacheSimple(this.getTaches().size() + 1, "Revue hebdomadaire");
                //On ajoute la tache au modele
                this.ajouterTache(tache2);


            this.ajouterListe("Sujet de la prochaine réunion");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache3 = new TacheSimple(this.getTaches().size() + 1, "Problèmes HTML !");
                //On ajoute la tache au modele
                this.ajouterTache(tache3);

                // Création de la tache
                Tache tache4 = new TacheSimple(this.getTaches().size() + 1, "Activer les cookies !");
                //On ajoute la tache au modele
                this.ajouterTache(tache4);

                // Création de la tache
                Tache tache5 = new TacheSimple(this.getTaches().size() + 1, "Répartition des tâches");
                //On ajoute la tache au modele
                this.ajouterTache(tache5);


            this.ajouterListe("Tâches à faire");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache6 = new TacheSimple(this.getTaches().size() + 1, "Création bannière du site");
                //On ajoute la tache au modele
                this.ajouterTache(tache6);

                // Création de la tache
                Tache tache7 = new TacheSimple(this.getTaches().size() + 1, "Création page de remerciement");
                //On ajoute la tache au modele
                this.ajouterTache(tache7);

                // Création de la tache
                Tache tache8 = new TacheSimple(this.getTaches().size() + 1, "Création cahier des charges");
                //On ajoute la tache au modele
                this.ajouterTache(tache8);


            this.ajouterListe("Tâches en cours");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache9 = new TacheSimple(this.getTaches().size() + 1, "Création page d'annexe");
                //On ajoute la tache au modele
                this.ajouterTache(tache9);


            this.ajouterListe("Tâches terminées");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache10 = new TacheSimple(this.getTaches().size() + 1, "Création page de contact");
                //On ajoute la tache au modele
                this.ajouterTache(tache10);

                // Création de la tache
                Tache tache11 = new TacheSimple(this.getTaches().size() + 1, "Création page d'accueil");
                //On ajoute la tache au modele
                this.ajouterTache(tache11);

        }
    }

    /**
     * Methode qui crée le template 'Modèle Kanban'
     * @param titre titre du template
     */
    public void templateModeleKanban(String titre){

        //On vérifie que le titre n'est pas déjà utilisé
        if (VerifTableauExistant(titre)) {

            // Création du tableau
            Tableau t = new Tableau(this.getTableaux().size() + 1, titre);

            //On ajoute la liste au modele
            this.ajouterTableau(t);

            // On ajoute la vue du tableau au modele
            this.enregistrerObservateur(new VueTableau());

            // Création des différentes listes qui composent le template
            this.ajouterListe("Backlog");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache = new TacheSimple(this.getTaches().size() + 1, "Backlog");
                //On ajoute la tache au modele
                this.ajouterTache(tache);

                // Création de la tache
                Tache tache2 = new TacheSimple(this.getTaches().size() + 1, "[Exemple de tâche]");
                //On ajoute la tache au modele
                this.ajouterTache(tache2);


            this.ajouterListe("Conception");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache3 = new TacheSimple(this.getTaches().size() + 1, "Conception & Recherche");
                //On ajoute la tache au modele
                this.ajouterTache(tache3);

                // Création de la tache
                Tache tache4 = new TacheSimple(this.getTaches().size() + 1, "[Exemple de tâche de conception]");
                //On ajoute la tache au modele
                this.ajouterTache(tache4);


            this.ajouterListe("A faire");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache5 = new TacheSimple(this.getTaches().size() + 1, "A faire");
                //On ajoute la tache au modele
                this.ajouterTache(tache5);


            this.ajouterListe("En cours");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache6 = new TacheSimple(this.getTaches().size() + 1, "En cours");
                //On ajoute la tache au modele
                this.ajouterTache(tache6);


            this.ajouterListe("Revision du code");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache7 = new TacheSimple(this.getTaches().size() + 1, "Revision du code");
                //On ajoute la tache au modele
                this.ajouterTache(tache7);

                // Création de la tache
                Tache tache8 = new TacheSimple(this.getTaches().size() + 1, "[Exemple de tâche de révision du code]");
                //On ajoute la tache au modele
                this.ajouterTache(tache8);


            this.ajouterListe("Test");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache9 = new TacheSimple(this.getTaches().size() + 1, "Test");
                //On ajoute la tache au modele
                this.ajouterTache(tache9);


            this.ajouterListe("Terminé");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache10 = new TacheSimple(this.getTaches().size() + 1, "Terminé");
                //On ajoute la tache au modele
                this.ajouterTache(tache10);

                // Création de la tache
                Tache tache11 = new TacheSimple(this.getTaches().size() + 1, "[Tâche terminée]");
                //On ajoute la tache au modele
                this.ajouterTache(tache11);
        }
    }

    /**
     * Methode qui crée le template 'Réunion hebdomadaire'
     * @param titre titre du template
     */
    public void templateReuHebdo(String titre){

        //On vérifie que le titre n'est pas déjà utilisé
        if (VerifTableauExistant(titre)){
            // Création du tableau
            Tableau t = new Tableau(this.getTableaux().size() + 1, titre);

            //On ajoute le tableau au modele
            this.ajouterTableau(t);

            // On ajoute la vue du tableau au modele
            this.enregistrerObservateur(new VueTableau());

            // Création des différentes listes qui composent le template
            this.ajouterListe("Sujet de la prochaine réunion");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache = new TacheSimple(this.getTaches().size() + 1, "Nouveau projet Circuit WEB");
                //On ajoute la tache au modele
                this.ajouterTache(tache);


            this.ajouterListe("Sujet des dernières réunions");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache2 = new TacheSimple(this.getTaches().size() + 1, "------>");
                //On ajoute la tache au modele
                this.ajouterTache(tache2);


            this.ajouterListe("13 Décembre");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache3 = new TacheSimple(this.getTaches().size() + 1, "15min : Présentation du projet par Marin");
                //On ajoute la tache au modele
                this.ajouterTache(tache3);

                // Création de la tache
                Tache tache4 = new TacheSimple(this.getTaches().size() + 1, "Affectation des tâches");
                //On ajoute la tache au modele
                this.ajouterTache(tache4);


            this.ajouterListe("30 Février");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache5 = new TacheSimple(this.getTaches().size() + 1, "Brainstorming sur le design du site");
                //On ajoute la tache au modele
                this.ajouterTache(tache5);

                // Création de la tache
                Tache tache6 = new TacheSimple(this.getTaches().size() + 1, "Démonstration du site");
                //On ajoute la tache au modele
                this.ajouterTache(tache6);
        }
    }

    /**
     * Methode qui crée le template 'Tableau Agile'
     * @param titre titre du template
     */
    public void templateTableauAgile(String titre){

        //On vérifie que le titre n'est pas déjà utilisé
        if(VerifTableauExistant(titre)){

            // Création du tableau
            Tableau t = new Tableau(this.getTableaux().size() + 1, titre);

            //On ajoute le tableau au modele
            this.ajouterTableau(t);

            // On ajoute la vue du tableau au modele
            this.enregistrerObservateur(new VueTableau());

            // Création des différentes listes qui composent le template
            this.ajouterListe("Fait");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache = new TacheSimple(this.getTaches().size() + 1, "Reunion Nancy");
                //On ajoute la tache au modele
                this.ajouterTache(tache);

                // Création de la tache
                Tache tache2 = new TacheSimple(this.getTaches().size() + 1, "Tendances de l'application");
                //On ajoute la tache au modele
                this.ajouterTache(tache2);

            this.ajouterListe("Sprint en cours");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache3 = new TacheSimple(this.getTaches().size() + 1, "Campagne de communication");
                //On ajoute la tache au modele
                this.ajouterTache(tache3);

                // Création de la tache
                Tache tache4 = new TacheSimple(this.getTaches().size() + 1, "Deploiemment serveur");
                //On ajoute la tache au modele
                this.ajouterTache(tache4);

                // Création de la tache
                Tache tache5 = new TacheSimple(this.getTaches().size() + 1, "Deploiement de la base de données");
                //On ajoute la tache au modele
                this.ajouterTache(tache5);


            this.ajouterListe("En cours");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache6 = new TacheSimple(this.getTaches().size() + 1, "Données");
                //On ajoute la tache au modele
                this.ajouterTache(tache6);

                // Création de la tache
                Tache tache7 = new TacheSimple(this.getTaches().size() + 1, "Nouvelle page de lancement");
                //On ajoute la tache au modele
                this.ajouterTache(tache7);


            this.ajouterListe("En attente");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache8 = new TacheSimple(this.getTaches().size() + 1, "Règles CSS");
                //On ajoute la tache au modele
                this.ajouterTache(tache8);


            this.ajouterListe("A venir");
            Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache9 = new TacheSimple(this.getTaches().size() + 1, "Nouvelle page de connexion");
                //On ajoute la tache au modele
                this.ajouterTache(tache9);

                // Création de la tache
                Tache tache10 = new TacheSimple(this.getTaches().size() + 1, "Nouvelle page d'inscription");
                //On ajoute la tache au modele
                this.ajouterTache(tache10);

                // Création de la tache
                Tache tache11 = new TacheSimple(this.getTaches().size() + 1, "Nouvelle page de profil");
                //On ajoute la tache au modele
                this.ajouterTache(tache11);

                // Création de la tache
                Tache tache12 = new TacheSimple(this.getTaches().size() + 1, "Nouvelle page de contact");
                //On ajoute la tache au modele
                this.ajouterTache(tache12);

                // Création de la tache
                Tache tache13 = new TacheSimple(this.getTaches().size() + 1, "Nouvelle page de remerciement");
                //On ajoute la tache au modele
                this.ajouterTache(tache13);

                // Création de la tache
                Tache tache14 = new TacheSimple(this.getTaches().size() + 1, "Nouvelle page d'annexe");
                //On ajoute la tache au modele
                this.ajouterTache(tache14);

                // Création de la tache
                Tache tache15 = new TacheSimple(this.getTaches().size() + 1, "Nouvelle page d'accueil");
                //On ajoute la tache au modele
                this.ajouterTache(tache15);


                this.ajouterListe("Questions");
                Modele.setListeCourante(this.getListes().get(this.getListes().size() - 1).getNumListe());

                // Création de la tache
                Tache tache17 = new TacheSimple(this.getTaches().size() + 1, "Comment faire pour créer une tâches ?");
                //On ajoute la tache au modele
                this.ajouterTache(tache17);

                // Création de la tache
                Tache tache18 = new TacheSimple(this.getTaches().size() + 1, "Comment faire pour créer une liste ?");
                //On ajoute la tache au modele
                this.ajouterTache(tache18);


            this.ajouterListe("Boite à idées Marketing");

            this.ajouterListe("Tiroir à débarras");

            this.ajouterListe("Peut mieux faire");

            this.ajouterListe("Boite à idées Contenus");

            this.ajouterListe("Les Vainqueurs - WINS !");

            this.ajouterListe("J'adorerais le faire");

            this.ajouterListe("Ressources");

        }
    }
}
