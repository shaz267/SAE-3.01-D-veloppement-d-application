package Tablo.Modele;

import Tablo.Loggeur;
import Tablo.Modele.Templates.TemplatesStrategie;
import Tablo.Observateur;
import Tablo.Sujet;
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
     * Attribut template de la classe Modele qui est un objet de type TemplatesStrategie qui représente le template de l'application.
     */
    private TemplatesStrategie template;

    /**
     * Constructeur de la classe Modele qui initialise les attributs tableauCourant, listeCourante et tacheCourante à -1.
     */
    public Modele() {

        this.tableaux = new ArrayList<Tableau>();
        this.observateurs = new ArrayList<Observateur>();
        this.template = null;
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
     * Méthode qui permet de récupérer la tache à déplacer et la marque comme selectionnée
     */
    public Tache getTacheADeplacer() {
        Tache tacheADeplacer = null;
        for (Tache tache : this.getTaches()) {
            if (tache.getNumTache() == Modele.getTacheCourante()) {
                tacheADeplacer = tache;
                tacheADeplacer.setEstSelectionnee(true);
            }
        }
        return tacheADeplacer;
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
        System.out.println("Notif");
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

    /**
     * Méthode qui retourne la liste de tâches du tableau courant. et de la liste courante
     *
     * @return les taches
     */
    public List<Tache> getTaches() {

        for (Tableau tableau : tableaux) {

            if (tableau.getNumTableau() == tableauCourant) {

                return tableau.getTaches();
            }
        }
        return null;
    }

    /**
     * Méthode qui retourne le numéro du tableau associé au titre passé en paramètre.
     * @param titre
     * @return
     */
    public int getNumTableau(String titre) {

        for (Tableau tableau : this.getTableaux()) {
            if (tableau.getTitre().equals(titre)) {

                return tableau.getNumTableau();
            }
        }
        return -1;

    }

    /**
     * Méthode qui retourne le numéro de la liste associé au titre passé en paramètre.
     * @param numListe
     */
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
    public boolean verifTableauExistant(String titre) {


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
     * Méthode qui permet de changer le template de l'application.
     * @param template
     */
    public void setTemplate(TemplatesStrategie template) {
        this.template = template;
    }

    /**
     * Méthode qui permet de créer un tableau. en fonction du template
     */
    public void creerTableau() {
        //SI le template n'est pas null
        if (this.template != null)
            //On crée le tableau avec le template choisi
            this.template.creerTableau();
    }
}
