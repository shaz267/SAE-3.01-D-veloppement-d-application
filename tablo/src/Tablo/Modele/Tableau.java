package Tablo.Modele;

import Tablo.Loggeur;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de gérer les Listes de tâches de notre application.
 */
public class Tableau {

    // Correspond à l'id de l'objet Liste
    private int id;

    // Correspond au titre/nom du tableau
    private String titre;

    // Correspond à l'ensemble des listes dans un objet Tableau
    private List<Liste> listes;

    // Correspond au numéro du tableau
    private int numTableau;

    /**
     * Constructeur de l'objet Tableau
     *
     * @param t titre du tableau
     * @param numTab numéro du tableau
     */
    public Tableau(int numTab, String t) {
        this.id = -1;
        this.titre = t;
        this.numTableau = numTab;
        this.listes = new ArrayList<>();
    }

    /**
     * Ajoute une tâche à la liste courante
     *
     * @param t tâche à ajouter
     */
    public void ajouterTache(Tache t) {

        for (Liste l : this.listes) {

            if (l.getNumListe() == Modele.getListeCourante()) {

                //On ajoute la tâche à la liste courante
                l.ajouterTache(t);
                System.out.println("Tache ajoutée à la liste " + Modele.getListeCourante());
            }
        }
    }

    /**
     * Retire une tâche de la liste courante
     */
    public boolean archiverTache() {

        for (Liste l : this.listes) {
            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                return l.archiverTache();
            }
        }
        return false;
    }

    /**
     * Change le titre de la tache dans la liste courante
     *
     * @param nouveauTitre nouveau titre de la tache
     */
    public void changerTitreTache(String nouveauTitre) {
        for (Liste l : this.listes) {
            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                l.changerTitreTache(nouveauTitre);
            }
        }
    }

    /**
     * Change le titre de la liste courante
     *
     * @param nouveauContenu nouveau contenu de la tache
     */
    public void changerContenuTache(String nouveauContenu) {

        for (Liste l : this.listes) {
            //On vérifie que la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                //On change le contenu de la tâche
                l.changerContenuTache(nouveauContenu);
            }
        }
    }

    /**
     * Deplacer une tache dans une autre liste
     * @param tache
     * @param numListeDestination
     *
     * Vérifier que le nom de la tache n'existe pas dans la liste de destination
     * Vérifier que la liste de destination n'est pas la liste courante
     * Vérifier que la liste de destination existe
     * Vérifier que la tache existe
     * Si la tache possède des sous tâches, on les déplace aussi
     * retirer la tache de la liste courante
     * ajouter la tache à la liste de destination
     *
     */
    public boolean deplacerTache(Tache tache, int numListeDestination) {
        boolean res = false;
        System.out.println("deplacerTache s'execute");
        //On récupère la liste de destination
        Liste listeDestination = this.listes.get(numListeDestination-1);
        Modele.setListeDestination(numListeDestination);
        System.out.println("listeDestination : " + listeDestination.getNumListe());

            //On vérifie que la liste de destination existe
            if (listeDestination != null) {
                //On retire la tache de la precedente liste
                for (Liste l : this.listes) {
                    //Si la liste est la liste de la tache
                    if (l.getNumListe() == tache.getNumListe()) {
                        l.supprimerTache(tache);
                    }
                }

                //On ajoute la tache à la liste de destination
                listeDestination.ajouterTache(tache);
                System.out.println(listeDestination);

                // On réinitialise la tacte courante
                Modele.setTacheCourante(-1);
                //On change la liste courante
                Modele.setListeCourante(numListeDestination);
                System.out.println("Tache déplacée " + tache.getTitre() + " dans la liste " + Modele.getListeCourante());
                res = true;

            }
        System.out.println("tache déplacée " + res);
        return res;
    }



    /**
     * Modifie la date limite de la tache dans la liste courante
     *
     * @param date date de début de la tache
     */
    public void modifierDateDebut(LocalDate date) {
        for (Liste l : this.listes) {
            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                //On change la date de début de la tâche
                l.modifierDateDebut(date);
            }
        }
    }

    /**
     * Modifie la date limite de la tache dans la liste courante
     *
     * @param date date limite de la tache
     */
    public void modifierDateLimite(LocalDate date) {
        for (Liste l : this.listes) {
            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                l.modifierDateLimite(date);
            }
        }
    }

    /**
     * Appelle la méthode fini() de la liste courante
     */
    public void fini(){

        for (Liste l : this.listes) {
            if (l.getId() == Modele.getListeCourante()) {

                l.fini();
            }
        }
    }

    /**
     * Ajoute une liste à l'objet Tableau
     *
     * @param l liste à ajouter
     */
    public void ajouterListe(Liste l) {
        this.listes.add(l);
        Loggeur.enregistrer("Ajout de la liste " + l.getTitre() + " au tableau " + this.titre);
    }

    /**
     * Cette méthode permet de déplacer une liste dans le tableau
     */
    public void deplacerListe(int numListeDestination) {

        //On récupère la liste de destination
        Liste listeDestination = this.listes.get(numListeDestination-1);

        //On vérifie que la liste de destination existe
        if (listeDestination != null) {

            //On récupère la liste courante
            Liste listeCourante = null;
            for (Liste l : this.listes) {
                if (l.getNumListe() == Modele.getListeCourante()) {
                    listeCourante = l;
                }
            }

            //On récupère le numéro de la liste courante
            int numListeCourante = listeCourante.getNumListe();

            //On change le numéro de la liste courante
            listeCourante.setNumListe(numListeDestination);

            //On change le numéro de la liste de destination
            listeDestination.setNumListe(numListeCourante);

            //On change la liste courante
            Modele.setListeCourante(numListeDestination);

            //On enregistre l'action dans les logs
            Loggeur.enregistrer("Déplacement de la liste " + listeCourante.getTitre() + " à la position " + numListeDestination + " du tableau " + this.titre);
        }
    }

    /**
     * Retire une liste de l'objet Tableau
     *
     * @return true si on a supprimé la liste courante, false sinon
     */
    public boolean retirerListe() {

        //int qui permet de savoir si on a supprimé la liste courante et à partir de là on décrémente les numéros des listes suivantes
        int rangSuppr = -1;

        //On parcourt les listes du tableau pour trouver la liste à supprimer et on décrémente les numéros des listes suivantes
        for (int i = 0; i < this.listes.size(); i++) {

            //On récupère la liste
            Liste liste = this.listes.get(i);

            //Si on a trouvé la liste à supprimer
            if (liste.getNumListe() == Modele.getListeCourante()) {
                if (this.listes.remove(liste)) {
                    //On enregistre l'action dans les logs
                    Loggeur.enregistrer("Suppression de la liste " + liste.getTitre() + " du tableau " + this.titre);
                    //On récupère le rang de la liste à supprimer
                    rangSuppr = i;
                    //On décrémente la liste courante
                    Modele.setListeCourante(Modele.getListeCourante() - 1);
                }
            }
        }

        //On décrémente les numéros des listes suivantes si on a supprimé la liste courante
        if (rangSuppr != -1) {
            for (int i = rangSuppr; i < this.listes.size(); i++) {
                Liste liste = this.listes.get(i);
                liste.setNumListe(liste.getNumListe() - 1);
            }

            //On retourne true si on a supprimé la liste courante
            return true;
        }
        //On retourne false si on n'a pas supprimé la liste courante
        return false;
    }

    public void changerTitreListe(String nouveauTitre) {
        for (Liste l : this.listes) {
            if (l.getNumListe() == Modele.getListeCourante()) {
                l.changerTitreListe(nouveauTitre);
            }
        }
    }

    /**
     * Retire l'Id de la liste de l'objet Tableau
     *
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retourne la liste des listes de l'objet Tableau
     *
     * @return listes
     */
    public ArrayList<Liste> getListes() {
        return (ArrayList<Liste>) this.listes;
    }

    /**
     * Retourne le numéro du tableau
     *
     * @return numTableau
     */
    public int getNumTableau() {
        return numTableau;
    }

    public List<Tache> getTaches() {

        for (Liste l : this.listes) {

            if (l.getNumListe() == Modele.getListeCourante()) {

                return l.getTaches();
            }
        }
        return null;
    }

    /**
     * Permet de rajouter une sous tâche à la tache courante
     */
    public boolean ajouterSousTache(Tache t) {

        boolean res = false;

        for (Liste l : this.listes) {
            if (l.getNumListe() == Modele.getListeCourante()) {
                res = l.ajouterSousTache(t);
            }
        }

        return res;
    }

    /**
     * Retourne le titre du tableau
     *
     * @return titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Méthode qui convertie la tache courante en tache mère.
     */
    public void tacheCouranteEnMere() {

        for (Liste l : this.listes) {
            if (l.getNumListe() == Modele.getListeCourante()) {
                l.tacheCouranteEnMere();
            }
        }
    }

    public List<Tache> getSousTaches() {

        for (Liste l : this.listes) {
            if (l.getNumListe() == Modele.getListeCourante()) {
                return l.getSousTaches();
            }
        }
        return null;
    }

    public ArrayList<Tache> getTachesArchivees() {

        ArrayList<Tache> tachesArchivees = new ArrayList<>();

        for (Liste l : this.listes) {
            tachesArchivees.addAll(l.getTachesArchivees());
        }

        return tachesArchivees;
    }

    /**
     * Change le titre du tableau
     *
     * @param nouveauTitre nouveau titre du tableau
     */
    public void changerTitre(String nouveauTitre) {
        Loggeur.enregistrer("Changement du titre du tableau " + this.titre + " en " + nouveauTitre);
        this.titre = nouveauTitre;
    }

    /**
     * Change le numéro du tableau
     *
     * @param numTableau nouveau numéro du tableau
     */
    public void setNumTableau(int numTableau) {
        this.numTableau = numTableau;
    }

    /**
     * Méthode qui permet de savoir si la tache courante est selectionnée
     * @return true si la tache est selectionnée, false sinon
     */
    public boolean isSelectionnee() {
        //On parcourt les listes du tableau
        for (Liste l : this.listes) {

            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                //On retourne si la tache est selectionnée
                return l.isSelectionnee();
            }
        }
        return false;
    }

    /**
     * Méthode qui permet de rendre la tache courante selectionnée ou non
     * @param b
     */
    public void setTacheCouranteSelectionnee(boolean b) {

        //On parcourt les listes du tableau
        for (Liste l : this.listes) {

            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                //On change la selection de la tache courante
                l.setTacheCouranteSelectionnee(b);
            }
        }
    }

    /**
     * Méthode qui retourne la date de début des taches selectionnées
     * @return
     */
    public LocalDate getDateDebutTachesSelectionnees() {

        LocalDate dateDebut = null;

        //On parcourt les listes du tableau
        for (Liste l : this.listes) {

            if (l.getDateDebutTachesSelectionnees() == null) {
                continue;
            }

            if (dateDebut == null) {
                dateDebut = l.getDateDebutTachesSelectionnees();
            }
            else if (l.getDateDebutTachesSelectionnees().isBefore(dateDebut)) {
                dateDebut = l.getDateDebutTachesSelectionnees();
            }
        }

        return dateDebut;
    }

    /**
     * Méthode qui retourne la date de fin des taches selectionnées
     * @return
     */
    public LocalDate getDateFinTachesSelectionnees() {

        LocalDate dateFin = null;

        //On parcourt les listes du tableau
        for (Liste l : this.listes) {

            if (l.getDateFinTachesSelectionnees() == null) {
                continue;
            }

            if (dateFin == null) {
                dateFin = l.getDateFinTachesSelectionnees();
            }
            else if (l.getDateFinTachesSelectionnees().isAfter(dateFin)) {
                dateFin = l.getDateFinTachesSelectionnees();
            }
        }

        return dateFin;
    }

    /**
     * Méthode qui retourne les taches selectionnées
     * @return
     */
    public List<Tache> getTachesSelectionnees() {

        List<Tache> tachesSelectionnees = new ArrayList<>();

        //On parcourt les listes du tableau
        for (Liste l : this.listes) {

            //On ajoute les taches selectionnées de la liste courante
            tachesSelectionnees.addAll(l.getTachesSelectionnees());
        }

        return tachesSelectionnees;
    }

    /**
     * Méthode qui permet de supprimer une sous tache
     * @return
     */
    public boolean supprimerSousTache(int sousTache) {

        //On parcourt les listes du tableau
        for (Liste l : this.listes) {

            //Si la liste est la liste courante
            if (l.getNumListe() == Modele.getListeCourante()) {

                //On supprime la sous tache
                return l.supprimerSousTache(sousTache);
            }
        }
        return false;
    }
}
