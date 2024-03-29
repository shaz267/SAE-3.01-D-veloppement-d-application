package Tablo.Modele;

import Tablo.DBConnection;
import Tablo.Loggeur;
import Tablo.Vue.VueTache;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Classe qui permet de gérer les Listes de tâches de notre application. Cette classe
 * respecte le patron activeRecord.
 */
public class Liste {

    // Correspond à l'id de l'objet Liste, utile principalement pour le patron activeRecord
    private int id;

    // Correspond au titre/nom de l'objet Liste
    private String titre;

    // Correspond à la selection de la liste
    private boolean selectionnee;

    // Correspond à l'ensemble des tâches dans un objet Liste
    private List<Tache> taches;

    private int numListe;

    /**
     * Constructeur de l'objet Liste
     *
     * @param t titre de la Liste
     */
    public Liste(int numListe, String t) {
        // l'id est initialisé à -1 conformément au patron activeRecord
        this.id = -1;
        this.titre = t;
        this.taches = new ArrayList<>();
        this.numListe = numListe;
    }

    /**
     * Constructeur de l'objet Liste
     * @param t
     */
    public Liste(String t) {
        // l'id est initialisé à -1 conformément au patron activeRecord
        this.id = -1;
        this.titre = t;
        this.taches = new ArrayList<>();
    }

    /**
     * Méthode findAll permettant de récupérer toutes les listes de la base de données
     * @return ArrayList de Liste
     * @throws SQLException
     */
    public static ArrayList<Liste> findAll() throws SQLException {
        String SQLPrep = "SELECT * FROM `LISTE`;";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        ArrayList<Liste> listP = new ArrayList<>();
        while (rs.next()) {
            Liste l = new Liste(rs.getString("titre"));
            l.id = rs.getInt("id_liste");
            listP.add(l);
        }
        return listP;
    }

    /**
     * Méthode permettatnt de récupérer toutes les listes d'un tableau donné
     * @param id, id du tableau
     * @return retourne la liste de ses listes
     * @throws SQLException
     */
    public static ArrayList<Liste> findAllByTabId(int id) throws SQLException {
String SQLPrep = "SELECT l.id_liste, l.titre, l.num_liste FROM LISTE l INNER JOIN TABLEAULISTE tl ON l.id_liste = tl.id_liste\n" +
        "WHERE tl.id_tableau = ?;";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setInt(1,id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        ArrayList<Liste> listes = new ArrayList<>();
        while (rs.next()) {
            Liste l = new Liste(rs.getString("titre"));
            l.id = rs.getInt("id_liste");
            l.numListe = rs.getInt("num_liste");
            listes.add(l);
        }
        return listes;
    }

    /**
     * Méthode findById permettant de récupérer une liste de la base de données en fonction de son id
     * @param id id de la liste à récupérer
     * @return Liste
     * @throws SQLException
     */
    public static Liste findById(int id) throws SQLException {
        String SQLPrep = "SELECT * FROM `LISTE` WHERE `id_liste` = ?;";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setInt(1,id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        if(rs.next() == false){
            return null;
        }
        Liste l = new Liste(rs.getString("titre"));
        l.id = rs.getInt("id_liste");
        return l;
    }

    /**
     * Méthode findByTitre permettant de récupérer une liste de la base de données en fonction de son titre
     * @param titre
     * @return
     * @throws SQLException
     */
    public static ArrayList<Liste> findByTitre(String titre) throws SQLException {
        String SQLPrep = "SELECT * FROM `LISTE` WHERE `titre` = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setString(1,titre);
        ResultSet rs = prep1.executeQuery();
        ArrayList<Liste> listP = new ArrayList<>();
        while (rs.next()) {
            Liste l = new Liste(rs.getString("titre"));
            l.id = rs.getInt("id_liste");
            listP.add(l);
        }
        return listP;
    }

    /**
     * Méthode findAllTacheFromListe permettant de récupérer toutes les tâches d'une liste de la base de données
     * @param id_liste id de la liste dont on veut récupérer les tâches
     * @return ArrayList de Tache
     * @throws SQLException
     */
    public static ArrayList<Tache> findAllTacheFromListe(int id_liste) throws SQLException {
        String SQLPrep = "SELECT * FROM `TACHELISTE` WHERE `id_liste` = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setInt(1,id_liste);
        ResultSet rs = prep1.executeQuery();
        ArrayList<Tache> listP = new ArrayList<>();
        while (rs.next()) {
            Tache t = Tache.findById(rs.getInt("id_tache"));
            listP.add(t);
        }
        return listP;
    }

    /**
     * Méthode deleteAllTacheFromListe permettant de supprimer toutes les tâches d'une liste de la base de données
     * @throws SQLException
     */
    public void deleteAllTacheFromListe() throws SQLException {
        // On récupère toutes les tâches de la liste
        ArrayList<Tache> listTache = findAllTacheFromListe(this.id);
        // On supprime toutes les tâches de la base de données
        for(Tache t : listTache){
            String SQLSousTache = "DELETE FROM `ESTSOUSTACHE` WHERE `id_tachefille` = ? OR `id_tachemere` = ?";
            PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLSousTache);
            prep1.setInt(1,t.getId());
            prep1.setInt(2,t.getId());
            prep1.executeUpdate();
            t.delete();
            String SQLPrep = "DELETE FROM `TACHELISTE` WHERE `id_liste` = ?";
            PreparedStatement prep2 = DBConnection.getConnection().prepareStatement(SQLPrep);
            prep2.setInt(1,this.id);
            prep2.executeUpdate();
        }

    }

    /**
     * Méthode delete permettant de supprimer une liste de la base de données
     * @throws SQLException
     */
    public void delete() throws SQLException {
        String SQLDel = "DELETE FROM `LISTE` WHERE titre = ? AND id_liste = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLDel);
        prep1.setString(1,titre);
        prep1.setInt(2,id);
        prep1.executeUpdate();
        id = -1;
    }

    /**
     * Méthode save permettant de sauvegarder une liste dans la base de données
     * @throws SQLException
     */
    public void save() throws SQLException {
        if(id == -1){
            // On récupère l'id de la tâche qui vient d'être créée
            ArrayList<Liste> lists = Liste.findAll();
            int id = lists.size() + 1;
            this.id = id;
            saveNew();
        } else {
            update();
        }
    }

    /**
     * Méthode saveNew permettant de sauvegarder une nouvelle liste dans la base de données
     * @throws SQLException
     */
    public void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO `LISTE` (`id_liste`,`titre`, `id_user`, `num_liste`) VALUES (?,?,?,?);";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep.setInt(1,id);
        prep.setString(2, titre);
        prep.setInt(3,Modele.user.getId());
        prep.setInt(4,numListe);
        prep.execute();
    }

    /**
     * Méthode update permettant de mettre à jour une liste dans la base de données
     * @throws SQLException
     */
    public void update() throws SQLException {
        String SQLsave = "UPDATE `LISTE` SET `titre` = ? WHERE `id_liste` = ?";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLsave);
        prep.setString(1,titre);
        prep.setInt(2,id);
        prep.executeUpdate();
    }

    /**
     * Méthode createTable permettant de créer la table LISTE dans la base de données (à utiliser pour les tests)
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String SQLPrep = "CREATE TABLE `LISTE` (`id_liste` INT NOT NULL, `titre` varchar(30), `id_user` int NOT NULL, `num_liste` int NOT NULL, PRIMARY KEY (`id_liste`))";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(SQLPrep);
    }

    /**
     * Méthode deleteTable permettant de supprimer la table LISTE dans la base de données (à utiliser pour les tests)
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        String SQLPrep = "DROP TABLE `LISTE`";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(SQLPrep);
    }

    /**
     * Setter pour l'attribut titre
     *
     * @param titre nouvelle valeur de l'attribut
     */
    public void changerTitreListe(String titre) {

        Loggeur.enregistrer("Changement du titre de la liste " + this.titre + " en " + titre);
        this.titre = titre;
        if(Modele.user != null){
            try {
                this.save();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Permet d'ajouter une tâche à l'attribut taches
     *
     * @param t tâche à ajouter
     */
    public void ajouterTache(Tache t) {
        try {
            if(Modele.user != null) {
                // On veut vérifier que la tache n'est pas déjà dans la table TACHELISTE
                String SQLPrep = "SELECT * FROM `TACHELISTE` WHERE `id_liste` = ? AND `id_tache` = ?;";
                PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
                prep.setInt(1, this.id);
                prep.setInt(2, t.getId());
                prep.execute();
                ResultSet rs = prep.getResultSet();

                //Si la tache n'est pas dans la table TACHELISTE
                if (!rs.next()) {
                    // On enregistre la tache dans la base de données si l'user est connecté
                    try {
                        // On enregistre la liste dans la base de données
                        this.save();
                        // On enregistre la tache dans la base de données
                        t.save();
                        // On enregistre le lien tache-liste dans la base de données
                        String SQLQuery = "INSERT INTO `TACHELISTE` (`id_liste`,`id_tache`) VALUES (?,?);";
                        PreparedStatement prep2 = DBConnection.getConnection().prepareStatement(SQLQuery);
                        prep2.setInt(1, this.id);
                        prep2.setInt(2, t.getId());
                        prep2.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
                //Si la liste est vide alors on initialise le numéro de la tache à 1 sinon on l'initialise à la taille de la liste + 1
                if (this.taches == null) {
                    t.setNumTache(1);
                } else {
                    t.setNumTache(this.taches.size() + 1);
                }
                //Si la liste n'est pas vide alors on vérifie que la tache n'existe pas déjà
                if (this.taches != null) {
                    //Si une tache qui a le même titre existe déjà alors on ne l'ajoute pas
                    for (Tache tache : this.taches) {
                        if (tache.getTitre().equals(t.getTitre())) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setHeaderText("Erreur de Tâche");
                            alert.setContentText("Vous ne pouvez pas ajouter une tâche qui a le même titre qu'une autre tâche.");
                            alert.showAndWait();
                            return;
                        }
                    }
                }
                Loggeur.enregistrer("Ajout de la tâche " + t.getTitre() + " à la liste " + this.titre);

                // afficher la liste en question
                t.setNumListe();
                this.taches.add(t);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Permet de supprimer une tâche de l'attribut taches
     */
    public boolean archiverTache() {

        for (Tache tache : this.taches) {

            //Si la tache est la tache courante
            if (tache.getNumTache() == Modele.getTacheCourante()) {

                //Si la tache a des sous taches
                if (tache.getSousTaches() != null && !tache.getSousTaches().isEmpty()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur de Sous Tâches");
                    alert.setContentText("Vous ne pouvez pas archiver une tâche qui a des sous tâches.");
                    alert.showAndWait();
                    return false;
                }

                //On supprime toutes les sous taches de chaques taches de cette liste étant la tache courante
                for (Tache tache2 : this.taches) {
                    if (tache2.getSousTaches() != null) {
                        tache2.getSousTaches().remove(tache);
                    }
                }

                tache.archiver(true);
                Loggeur.enregistrer("Archivage de la tâche " + tache.getTitre() + " de la liste " + this.titre);
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de supprimer une tâche de la liste courante
     * @param tache
     */
    public void supprimerTache(Tache tache) {

        //On supprime la tache de la liste
        this.taches.remove(tache);

        //On supprime la tache de toutes les sous taches de la liste
        for (Tache t : this.taches) {
            if (t.getSousTaches() != null) {
                t.getSousTaches().remove(tache);
            }

            //On met à jour le numéro de la tache cad on décrémente le numéro de la tache de 1 si le numéro de la tache est supérieur au numéro de la tache qu'on veut supprimer
            if (t.getNumTache() > tache.getNumTache()) {

                t.setNumTache(t.getNumTache() - 1);
            }
        }
    }


    /**
     * Permet de changer le titre d'une tâche dans la liste des taches
     *
     * @param nouveauTitre
     */
    public void changerTitreTache(String nouveauTitre) {

        for (Tache t : this.taches) {
            //Si la tache est la tache courante
            if (t.getNumTache() == Modele.getTacheCourante()) {

                //Pour chaques sous taches des taches de la liste portant le même titre que la tache courante. On change le titre de la sous tache en nouveauTitre
                for (Tache tache : this.taches) {
                    if (tache.getSousTaches() != null) {
                        for (Tache sousTache : tache.getSousTaches()) {
                            if (sousTache.getTitre().equals(t.getTitre())) {
                                sousTache.changerTitre(nouveauTitre);
                            }
                        }
                    }
                }

                //On change le titre de la tâche
                t.changerTitre(nouveauTitre);
            }
        }
    }

    /**
     * Permet de changer le contenu d'une tâche dans la liste des taches
     *
     * @param nouveauContenu
     */
    public void changerContenuTache(String nouveauContenu) {

        for (Tache t : this.taches) {

            // Si la tâche est la tâche courante
            if (t.getNumTache() == Modele.getTacheCourante()) {

                //Pour chaques sous taches des taches de la liste portant le même titre que la tache courante. On change le contenu de la sous tache en nouveauContenu
                for (Tache tache : this.taches) {
                    if (tache.getSousTaches() != null) {
                        for (Tache sousTache : tache.getSousTaches()) {
                            if (sousTache.getTitre().equals(t.getTitre())) {
                                sousTache.changerContenu(nouveauContenu);
                            }
                        }
                    }
                }

                // On change le contenu de la tâche
                t.changerContenu(nouveauContenu);
            }
        }
    }

    /**
     * Permet de changer la date limite d'une tâche dans la liste des taches
     *
     * @param dateDebut
     */
    public void modifierDateDebut(LocalDate dateDebut) {

        for (Tache t : this.taches) {
            // Si la tâche est la tâche courante
            if (t.getNumTache() == Modele.getTacheCourante()) {

                //On regarde si la tache courante est une sous tache d'une autre tache Si oui, on vérifie que la date de début qu'on veut assigner à la tache courante n'est pas inférieure à la date de fin de la tache mère.
                for (Tache tache : this.taches) {
                    if (tache.getSousTaches() != null) {
                        for (Tache sousTache : tache.getSousTaches()) {
                            //Si la sous tache est la tache courante et que la date de début qu'on veut assigner à la tache courante est inférieure à la date de fin de la tache mère.
                            if (sousTache.equals(t) && dateDebut.isBefore(tache.getDateLimite().plusDays(1))) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erreur");
                                alert.setHeaderText("Erreur de Sous Tâches");
                                alert.setContentText("La date de début de la tâche ne peut pas être inférieure ou égale,  à la date de fin de la tâche mère.");
                                //On aggrandit la fenêtre
                                alert.getDialogPane().setPrefSize(480, 200);
                                alert.showAndWait();
                                return;
                            }
                        }
                    }
                }

                //Pour chaques sous taches des taches de la liste portant le même titre que la tache courante. On change la date de début de la sous tache en dateDebut
                for (Tache tache : this.taches) {
                    if (tache.getSousTaches() != null) {
                        for (Tache sousTache : tache.getSousTaches()) {
                            if (sousTache.getTitre().equals(t.getTitre())) {
                                sousTache.modifierDateDebut(dateDebut);
                            }
                        }
                    }
                }

                // On change la date de début de la tâche
                t.modifierDateDebut(dateDebut);
            }
        }
    }

    /**
     * Permet de changer la date limite d'une tâche dans la liste des taches
     *
     * @param dateLimite
     */
    public void modifierDateLimite(LocalDate dateLimite) {

        for (Tache t : this.taches) {
            // Si la tâche est la tâche courante
            if (t.getNumTache() == Modele.getTacheCourante()) {

                //Pour chaques sous taches des taches de la liste portant le même titre que la tache courante. On change la date limite de la sous tache en dateLimite
                for (Tache tache : this.taches) {
                    if (tache.getSousTaches() != null) {
                        for (Tache sousTache : tache.getSousTaches()) {
                            if (sousTache.getTitre().equals(t.getTitre())) {
                                sousTache.modifierDateLimite(dateLimite);
                            }
                        }
                    }
                }

                // On change la date limite de la tâche
                t.modifierDateLimite(dateLimite);
            }
        }
    }

    /**
     * Permet de rajouter une sous tâche à la tache courante
     */
    public boolean ajouterSousTache(Tache t) {

        boolean res = false;

        for (Tache tache : this.taches) {

            //Si la tache est la tache courante
            if (tache.getNumTache() == Modele.getTacheCourante()) {
                res = tache.ajouterTache(t);
            }
        }

        return res;
    }

    /**
     * Permet de récupérer l'attribut titre
     *
     * @return titre de l'objet Liste
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * Permet de récupérer l'attribut id
     *
     * @return id de l'objet Liste
     */
    public int getId() {
        return this.id;
    }

    /**
     * Permet de récupérer l'attribut taches
     *
     * @return taches de l'objet Liste
     */
    public List<Tache> getTaches() {
        return this.taches;
    }

    public int getNumListe() {
        return this.numListe;
    }

    public void setSelectionnee(boolean b) {
        this.selectionnee = b;
    }
    public boolean estSelectionnee() {
        return this.selectionnee;
    }




    /**
     * Méthode qui convertie la tache courante en tache mère.
     */
    public void tacheCouranteEnMere() {


        Tache tacheCourante = null;
        TacheMere tacheMere = null;

        //On appele le constructeur de la classe TacheMere. Qui prend une tache en paramètre. Pour la tache courante.
        for (Tache t : this.taches) {

            if (t.getNumTache() == Modele.getTacheCourante()) {

                tacheCourante = t;

                //On crée une tache mère à partir de la tache courante
                tacheMere = new TacheMere(t);
                //On ajoute la tache mère à la liste
                this.taches.set(this.taches.indexOf(t), tacheMere);
            }
        }

        //Pour chaques taches de la liste on regarde si il possede une sous tache qui est la tache courante. Si oui, on la remplace par la tache mère.
        for (Tache t : this.taches) {
            if (t.getSousTaches() != null) {
                for (Tache sousTache : t.getSousTaches()) {
                    if (sousTache.equals(tacheCourante)) {
                        t.getSousTaches().set(t.getSousTaches().indexOf(sousTache), tacheMere);
                    }
                }
            }
        }
    }

    /**
     * Permet de récupérer les sous taches de la tache courante
     *
     * @return
     */
    public List<Tache> getSousTaches() {

        for (Tache t : this.taches) {

            if (t.getNumTache() == Modele.getTacheCourante()) {

                return t.getSousTaches();
            }
        }
        return null;
    }

    /**
     * Permet de récupérer les taches archivées de la liste
     *
     * @return
     */
    public Collection<? extends Tache> getTachesArchivees() {

        ArrayList<Tache> tachesArchivees = new ArrayList<Tache>();

        //On parcours toutes les taches de la liste
        for (Tache t : this.taches) {

            //Si la tache est archivée
            if (t.isArchivee()) {

                tachesArchivees.add(t);
            }
        }

        return tachesArchivees;
    }

    /**
     * Permet de changer le numéro de la liste
     *
     * @param i
     */
    public void setNumListe(int i) {
        this.numListe = i;
    }

    /**
     * Méthode qui permet de savoir si la tache courante est selectionnée
     * @return true si la tache est selectionnée, false sinon
     */
    public boolean isSelectionnee() {

        //On parcourt les taches de la liste
        for (Tache t : this.taches) {

            //Si la tache est la tache courante
            if (t.getNumTache() == Modele.getTacheCourante()) {

                return t.isSelectionnee();
            }
        }
        return false;
    }
    /**
     * Méthode qui permet de rendre la tache courante selectionnée ou non
     * @param b
     */
    public void setTacheCouranteSelectionnee(boolean b) {

        //On parcourt les taches de la liste
        for (Tache t : this.taches) {

            //Si la tache est la tache courante
            if (t.getNumTache() == Modele.getTacheCourante()) {

                //On change la selection de la tache
                t.setSelectionnee(b);
            }
        }
    }

    /**
     * Retourne la date de début des taches selectionnées
     * @return
     */
    public LocalDate getDateDebutTachesSelectionnees(){

        LocalDate dateDebut = null;

        //On parcourt les listes du tableau
        for (Tache t : this.taches) {

            if (t.isSelectionnee()) {

                if (dateDebut == null)
                    dateDebut = t.getDateDebut();
                else if (t.getDateDebut().isBefore(dateDebut))
                    dateDebut = t.getDateDebut();
            }

        }

        return dateDebut;
    }

    /**
     * Retourne la date de fin des taches selectionnées
     * @return
     */
    public LocalDate getDateFinTachesSelectionnees() {

        LocalDate dateFin = null;

        //On parcourt les listes du tableau
        for (Tache t : this.taches) {

            if (t.isSelectionnee()) {

                if (dateFin == null)
                    dateFin = t.getDateLimite();
                else if (t.getDateLimite().isAfter(dateFin))
                    dateFin = t.getDateLimite();
            }

        }

        return dateFin;
    }

    /**
     * Méthode qui permet de récupérer les taches selectionnées
     * @return
     */
    public ArrayList<Tache> getTachesSelectionnees() {

            ArrayList<Tache> tachesSelectionnees = new ArrayList<Tache>();

            //On parcourt les listes du tableau
            for (Tache t : this.taches) {

                //Si la tache est selectionnée et non archivée
                if (t.isSelectionnee() && !t.isArchivee()) {

                    tachesSelectionnees.add(t);
                }

            }

            return tachesSelectionnees;
    }

    /**
     * Méthode qui permet de supprimer une sous tache
     * @return
     */
    public boolean supprimerSousTache(int sousTache) {

        for (Tache t : this.taches) {

            //Si la tache est la tache courante
            if (t.getNumTache() == Modele.getTacheCourante()) {

                //On supprime la sous tache
                return t.supprimerSousTache(sousTache);
            }
        }
        return false;
    }
}
