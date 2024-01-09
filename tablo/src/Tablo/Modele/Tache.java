package Tablo.Modele;

import Tablo.DBConnection;
import Tablo.Loggeur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite qui permet de gérer les tâches de notre application.
 */
public abstract class   Tache {

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
     * Attribut estArchivee de la classe Tache qui est un booléen qui représente si la tâche est archivée ou non.
     */
    protected boolean estArchivee;

    protected int numTache;
    protected int numListe;

    /**
     * Constructeur de la classe Tache qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu.
     * @param titre
     */
    public Tache(int numTache, String titre) {

        //On initialise l'id à -1 car il n'est pas encore défini selon le patron Active Record.
        this.id = -1;

        this.numTache = numTache;

        this.titre = titre;
        this.contenu = "";

        this.dateDebut = LocalDate.now();
        this.dateLimite = LocalDate.now().plusDays(1);

        this.estArchivee = false;
        setNumListe();
    }

    /**
     * Constructeur de la classe Tache qui prend en paramètre un entier id, une chaîne de caractères titre et une chaîne de caractères contenu
     * @param titre
     */
    public Tache(String titre, String contenu, LocalDate dateDebut, LocalDate dateLimite, boolean estArchivee) {

        //On initialise l'id à -1 selon le patron Active Record.
        this.id = -1;

        this.titre = titre;
        this.contenu = contenu;

        this.dateDebut = dateDebut;
        this.dateLimite = dateLimite;

        this.estArchivee = estArchivee;
    }

    /**
     * Méthode findAll permettant de récupérer toutes les tâches de la base de données
     * @return ArrayList de Tache
     * @throws SQLException
     */
    public static ArrayList<Tache> findAll() throws SQLException {
        String SQLPrep = "SELECT * FROM `TACHE`;";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        ArrayList<Tache> listT = new ArrayList<>();
        while (rs.next()) {
            Tache t = null;
            if(rs.getString("type").equals("TacheMere")) {
                t = new TacheMere(rs.getString("titre"), rs.getString("contenu"), rs.getDate("date_deb").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getBoolean("estarchivee"));
            } else if(rs.getString("type").equals("TacheSimple")) {
                t = new TacheSimple(rs.getString("titre"), rs.getString("contenu"), rs.getDate("date_deb").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getBoolean("estarchivee"));
            }
            t.id = rs.getInt("id_tache");
            listT.add(t);
        }
        return listT;
    }

    /**
     * Méthode findById permettant de récupérer une tâche de la base de données en fonction de son id
     * @param id id de la tache à récupérer
     * @return Tache
     * @throws SQLException
     */
    public static Tache findById(int id) throws SQLException {
        String SQLPrep = "SELECT * FROM `TACHE` WHERE `id_tache` = ?;";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setInt(1,id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        if(rs.next() == false){
            return null;
        }
        Tache t = new TacheMere(rs.getString("titre"),rs.getString("contenu"),rs.getDate("date_deb").toLocalDate(),rs.getDate("date_fin").toLocalDate(),rs.getBoolean("estarchivee"));
        t.id = rs.getInt("id_tache");
        return t;
    }

    /**
     * Méthode findByName permettant de récupérer une tâche de la base de données en fonction de son titre
     * @param titre
     * @return
     * @throws SQLException
     */
    public static ArrayList<Tache> findByName(String titre) throws SQLException {
        String SQLPrep = "SELECT * FROM `TACHE` WHERE `titre` = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setString(1,titre);
        ResultSet rs = prep1.executeQuery();
        ArrayList<Tache> listT = new ArrayList<>();
        while (rs.next()) {
            Tache t = null;
            if(rs.getString("type").equals("TacheMere")) {
                t = new TacheMere(rs.getString("titre"), rs.getString("contenu"), rs.getDate("date_deb").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getBoolean("estarchivee"));
            } else if(rs.getString("type").equals("TacheSimple")) {
                t = new TacheSimple(rs.getString("titre"), rs.getString("contenu"), rs.getDate("date_deb").toLocalDate(), rs.getDate("date_fin").toLocalDate(), rs.getBoolean("estarchivee"));
            }
            t.id = rs.getInt("id_tache");
            listT.add(t);
        }
        return listT;
    }

    /**
     * Méthode delete permettant de supprimer une tache de la base de données
     * @throws SQLException
     */
    public void delete() throws SQLException {
        String SQLDel = "DELETE FROM `TACHE` WHERE titre = ? AND id_tache = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLDel);
        prep1.setString(1,titre);
        prep1.setInt(2,id);
        prep1.executeUpdate();
        id = -1;
    }

    /**
     * Méthode save permettant de sauvegarder une tache dans la base de données
     * @throws SQLException
     */
    public void save() throws SQLException {
        if(id == -1){
            // On récupère l'id de la tâche qui vient d'être créée
            ArrayList<Tache> tasks = Tache.findAll();
            int id = tasks.size() + 1;
            this.id = id;
            saveNew();
        } else {
            update();
        }
    }

    /**
     * Méthode saveNew permettant de sauvegarder une nouvelle tache dans la base de données
     * @throws SQLException
     */
    public void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO `TACHE` (`id_tache`,`titre`,`contenu`,`date_deb`,`date_fin`,`estarchivee`, `type`) VALUES (?,?,?,?,?,?,?);";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep.setInt(1, id);
        prep.setString(2, titre);
        prep.setString(3, contenu);
        prep.setString(4, dateDebut.toString());
        prep.setString(5, dateLimite.toString());
        prep.setBoolean(6, estArchivee);
        prep.setString(7, this.getClass().getSimpleName());
        prep.execute();

    }

    /**
     * Méthode update permettant de mettre à jour une tache dans la base de données
     * @throws SQLException
     */
    public void update() throws SQLException {
        String SQLsave = "UPDATE `TACHE` SET `titre` = ? WHERE `id_tache` = ?";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLsave);
        prep.setString(1,titre);
        prep.setInt(2,id);
        prep.executeUpdate();
    }

    /**
     * Méthode createTable permettant de créer la table TACHE dans la base de données (à utiliser pour les tests)
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String SQLPrep = "CREATE TABLE `TACHE` (`id_tache` INT NOT NULL, `titre` varchar(100), `contenu` varchar(500), `date_deb` DATE, `date_fin` DATE, `estarchivee` TINYINT(1) DEFAULT 0, `type` varchar(10), PRIMARY KEY (`id_tache`))";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(SQLPrep);
    }

    /**
     * Méthode deleteTable permettant de supprimer la table TACHE dans la base de données (à utiliser pour les tests)
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        String SQLPrep = "DROP TABLE `TACHE`";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(SQLPrep);
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

    public void setNumListe() {
        this.numListe = Modele.getListeCourante();
    }
    public void setNumTache(int numTache) {
        this.numTache = numTache;
    }
    public int getNumListe() {
        return this.numListe;
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
