package Tablo.Modele;

import Tablo.DBConnection;
import Tablo.Loggeur;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
     * Constructeur de l'objet Tableau
     * @param t titre du tableau
     */
    public Tableau(String t) {
        this.id = -1;
        this.titre = t;
        this.listes = new ArrayList<>();
    }

    /**
     * Méthode findAll permettant de récupérer tout les tableaux de la base de données
     * @return ArrayList de Tableau
     * @throws SQLException
     */
    public static ArrayList<Tableau> findAll() throws SQLException {
        String SQLPrep = "SELECT * FROM `TABLEAU`;";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        ArrayList<Tableau> listT = new ArrayList<>();
        while (rs.next()) {
            Tableau t = new Tableau(rs.getString("titre"));
            t.id = rs.getInt("id_tableau");
            listT.add(t);
        }
        return listT;
    }

    /**
     * Méthode findById permettant de récupérer un tableau de la base de données en fonction de son id
     * @param id id du Tableau à récupérer
     * @return Tableau
     * @throws SQLException
     */
    public static Tableau findById(int id) throws SQLException {
        String SQLPrep = "SELECT * FROM `TABLEAU` WHERE `id_tableau` = ?;";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setInt(1,id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        if(rs.next() == false){
            return null;
        }
        Tableau t = new Tableau(rs.getString("titre"));
        t.id = rs.getInt("id_tableau");
        return t;
    }

    /**
     * Méthode findByTitre permettant de récupérer un tableau de la base de données en fonction de son titre
     * @param titre
     * @return
     * @throws SQLException
     */
    public static ArrayList<Tableau> findByTitre(String titre) throws SQLException {
        String SQLPrep = "SELECT * FROM `TABLEAU` WHERE `titre` = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setString(1,titre);
        ResultSet rs = prep1.executeQuery();
        ArrayList<Tableau> listT = new ArrayList<>();
        while (rs.next()) {
            Tableau t = new Tableau(rs.getString("titre"));
            t.id = rs.getInt("id_tableau");
            listT.add(t);
        }
        return listT;
    }

    /**
     * Méthode delete permettant de supprimer un tableau de la base de données
     * @throws SQLException
     */
    public void delete() throws SQLException {
        String SQLDel = "DELETE FROM `TABLEAU` WHERE titre = ? AND id_tableau = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLDel);
        prep1.setString(1,titre);
        prep1.setInt(2,id);
        prep1.executeUpdate();
        id = -1;
    }

    /**
     * Méthode save permettant de sauvegarder un tabeau dans la base de données
     * @throws SQLException
     */
    public void save() throws SQLException {
        if(id == -1){
            // On récupère l'id de la tâche qui vient d'être créée
            ArrayList<Tableau> tableaux = Tableau.findAll();
            int id = tableaux.size() + 1;
            this.id = id;
            saveNew();
        } else {
            update();
        }
    }

    /**
     * Méthode saveNew permettant de sauvegarder un nouveau tableau dans la base de données
     * @throws SQLException
     */
    public void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO `TABLEAU` (`id_tableau`,`titre`, `id_user`) VALUES (?,?,?);";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep.setInt(1,id);
        prep.setString(2, titre);
        prep.setInt(3, Modele.user.getId());
        prep.execute();
    }

    /**
     * Méthode update permettant de mettre à jour un tableau dans la base de données
     * @throws SQLException
     */
    public void update() throws SQLException {
        String SQLsave = "UPDATE `TABLEAU` SET `titre` = ? WHERE `id_tableau` = ?";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLsave);
        prep.setString(1,titre);
        prep.setInt(2,id);
        prep.executeUpdate();
    }

    /**
     * Méthode createTable permettant de créer la table TABLEAU dans la base de données (à utiliser pour les tests)
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String SQLPrep = "CREATE TABLE `TABLEAU` (`id_tableau` INT NOT NULL, `titre` varchar(30), `id_user` INT NOT NULL, PRIMARY KEY (`id_tableau`))";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(SQLPrep);
    }

    /**
     * Méthode deleteTable permettant de supprimer la table TABLEAU dans la base de données (à utiliser pour les tests)
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        String SQLPrep = "DROP TABLE `TABLEAU`";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(SQLPrep);
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
        System.out.println("deplacerTache");
        //On récupère la liste de destination
        Liste listeDestination = this.listes.get(numListeDestination);
        Modele.setListeDestination(numListeDestination);

        //On vérifie que la liste de destination n'est pas la liste courante
        if (listeDestination.getNumListe() != Modele.getListeCourante()) {

            //On vérifie que la liste de destination existe
            if (listeDestination != null) {

                //On vérifie que la tache existe
                if (tache != null) {

                    //Si la tache possède des sous tâches, on les déplace aussi
                    if (tache.getSousTaches() != null) {

                        //On parcourt les sous tâches de la tache
                        for (Tache sousTache : tache.getSousTaches()) {

                            //On déplace la sous tâche
                            this.deplacerTache(sousTache, numListeDestination);
                        }
                    }
                    //On ajoute la tache à la liste de destination
                    listeDestination.ajouterTache(tache);
                    System.out.println("tache ajoutée à la liste de destination");
                    //On retire la tache de la liste courante
                    listes.get(Modele.getListeCourante()).retirerTache(tache, listes.get(Modele.getListeDestination()));

                    //On change la liste courante
                    Modele.setListeCourante(numListeDestination);
                    System.out.println("Tache déplacée " + tache.getTitre() + " dans la liste " + numListeDestination);
                    res = true;
                }
            }
        }
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
     * Ajoute une liste à l'objet Tableau
     *
     * @param l liste à ajouter
     */
    public void ajouterListe(Liste l) {
        this.listes.add(l);
        Loggeur.enregistrer("Ajout de la liste " + l.getTitre() + " au tableau " + this.titre);

        if(Modele.user != null){
            //On ajoute la liste à la base de données
            String SQLPrep = "INSERT INTO `TABLEAULISTE` (`id_tableau`,`id_liste`) VALUES (?,?);";
            try {
                PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
                prep.setInt(1, this.id);
                prep.setInt(2, l.getId());
                prep.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
}
