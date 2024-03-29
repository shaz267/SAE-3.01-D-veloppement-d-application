package Tablo.Modele;

import Tablo.DBConnection;
import Tablo.Loggeur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
     * Attribut estArchivee de la classe Tache qui est un booléen qui représente si la tâche est archivée ou non.
     */
    protected boolean estArchivee;

    /**
     * Attribut numTache de la classe Tache qui est un entier qui représente la place de la tâche dans la liste.
     */
    protected int numTache;

    /**
     * Attribut numListe de la classe Tache qui est un entier qui représente la liste à laquelle appartient la tâche.
     */
    protected int numListe;

    /**
     * Attribut selectionnee de la classe Tache qui est un booléen qui représente si la tâche est sélectionnée ou non.
     */
    protected boolean selectionnee;

    /**
     * Attribut tacheMere de la classe Tache qui est une tâche qui représente la tâche mère de la tâche courante.
     */
    protected TacheMere tacheMere;

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
        this.selectionnee = false;
        this.tacheMere = null;
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
     * Méthode findAllByListeId permettant de récupérer toutes les tâches d'une liste depuis la bd
     * @param id, id de la liste
     * @return retourne une liste des tâches de la liste
     */
    public static ArrayList<Tache> findAllByListeId(int id) {
        ArrayList<Tache> listT = new ArrayList<>();
        try {
            String SQLPrep = "SELECT t.id_tache, t.num_tache FROM TACHE t INNER JOIN tacheliste tl ON t.id_tache = tl.id_tache\n" +
                    "WHERE tl.id_liste = ?;";
            PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
            prep1.setInt(1, id);
            ResultSet rs = prep1.executeQuery();
            while (rs.next()) {
                Tache t = Tache.findById(rs.getInt("id_tache"));
                t.numTache = rs.getInt("num_tache");
                listT.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        String SQLPrep = "INSERT INTO `TACHE` (`id_tache`,`titre`,`contenu`,`date_deb`,`date_fin`,`estarchivee`, `type`, `id_user`, `num_tache`) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep.setInt(1, id);
        prep.setString(2, titre);
        prep.setString(3, contenu);
        prep.setString(4, dateDebut.toString());
        prep.setString(5, dateLimite.toString());
        prep.setBoolean(6, estArchivee);
        prep.setString(7, this.getClass().getSimpleName());
        prep.setInt(8, Modele.user.getId());
        prep.setInt(9, numTache);
        prep.execute();
    }

    /**
     * Méthode update permettant de mettre à jour une tache dans la base de données
     * @throws SQLException
     */
    public void update() throws SQLException {
        String SQLsave = "UPDATE `TACHE` SET `titre` = ?, `contenu` = ?, `date_deb` = ?, `date_fin` = ?, `estarchivee` = ?, `type` = ? WHERE `id_tache` = ?;";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLsave);
        prep.setString(1,titre);
        prep.setString(2,contenu);
        prep.setString(3,dateDebut.toString());
        prep.setString(4,dateLimite.toString());
        prep.setBoolean(5,estArchivee);
        prep.setString(6, this.getClass().getSimpleName());
        prep.setInt(7, id);
        prep.executeUpdate();
    }

    /**
     * Méthode createTable permettant de créer la table TACHE dans la base de données (à utiliser pour les tests)
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String SQLPrep = "CREATE TABLE `TACHE` (`id_tache` INT NOT NULL, `titre` varchar(100), `contenu` varchar(500), `date_deb` DATE, `date_fin` DATE, `estarchivee` TINYINT(1) DEFAULT 0, `type` varchar(15), `id_user` int NOT NULL, `num_tache` INT NOT NULL, PRIMARY KEY (`id_tache`,`titre`));";
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
     * @param titre titre de la tâche
     */
    public void changerTitre(String titre) {
        Loggeur.enregistrer("Changement du titre de la tâche " + this.titre + " en " + titre);
        this.titre = titre;
        // Si l'utilisateur est connecté, on enregistre la tâche dans la base de données
        if(Modele.user != null) {
            try {
                this.save();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Méthode qui change le contenu de la tâche.
     * @param contenu contenu de la tâche
     */
    public void changerContenu(String contenu) {
        //On enregistre l'action dans les logs et on change le contenu de la tâche.
        Loggeur.enregistrer("Changement du contenu de la tâche " + this.titre + " en " + contenu);
        this.contenu = contenu;
        // Si l'utilisateur est connecté, on enregistre le contenu de la tâche dans la base de données
        if(Modele.user != null) {
            try {
                this.save();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Méthode qui set la date limite de la tâche.
     * @param dateDebut
     */
    public void modifierDateDebut(LocalDate dateDebut) {
        Loggeur.enregistrer("Changement de la date de début de la tâche " + this.titre + " en " + dateDebut);
        this.dateDebut = dateDebut;
        // Si l'utilisateur est connecté, on enregistre la date de début de la tâche dans la base de données
        if(Modele.user != null) {
            try {
                this.save();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Méthode qui set la date limite de la tâche
     * @param dateLimite
     */
    public void modifierDateLimite(LocalDate dateLimite) {
        //On enregistre l'action dans les logs et on change la date limite de la tâche.
        Loggeur.enregistrer("Changement de la date limite de la tâche " + this.titre + " en " + dateLimite);
        this.dateLimite = dateLimite;
        // Si l'utilisateur est connecté, on enregistre la date limite de la tâche dans la base de données
        if(Modele.user != null) {
            try {
                this.save();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Méthode qui ajoute une tâche fille à la tâche mère. Elle serra redéfinie dans la classe TacheMere. Et dans la classe TacheSimple, elle retournera false.
     * @param tache tâche à ajouter
     * @return true si la tâche a été ajoutée, false sinon
     */
    public abstract boolean ajouterTache(Tache tache);


    /**
     * Méthode qui supprime une tâche fille de la tâche mère. Elle serra redéfinie dans la classe TacheMere. Et dans la classe TacheSimple, elle retournera false.
     * @param tache tâche à supprimer
     * @return true si la tâche a été supprimée, false sinon
     */
    public abstract boolean supprimerTache(Tache tache);

    /**
     * Méthode qui retourne l'attribut titre de la tâche.
     * @return titre
     */
    public String getTitre() {
        return this.titre;
    }

    public int getId() {
        return this.id;
    }

    public boolean isEstSelectionnee() {
        return selectionnee;
    }

    /**
     * Getter qui retourne l'attribut contenu de la tâche
     * @return contenu
     */
    public String getContenu() {
        return this.contenu;
    }

    /**
     * Getter qui retourne l'attribut dateLimite de la tache
     * @return LocalDate
     */
    public LocalDate getDateLimite() {
        return dateLimite;
    }

    /**
     * Getter qui retourne l'attribut dateDebut de la tache
     * @return LocalDate
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * Getter qui retourne l'attribut numTache de la tache
     * @return
     */
    public int getNumTache() {
        return this.numTache;
    }

    /**
     * Setter qui set l'attribut numListe de la tache
     */
    public void setNumListe() {
        this.numListe = Modele.getListeCourante();
    }

    /**
     * Setter qui set l'attribut numTache de la tache
     * @param numTache numéro de la tâche
     */
    public void setNumTache(int numTache) {
        this.numTache = numTache;
    }

    /**
     * Getter qui retourne l'attribut numListe de la tache
     * @return numListe
     */
    public int getNumListe() {
        return this.numListe;
    }

    public abstract List<Tache> getSousTaches();



    /**
     * On redéfinit la méthode equals pour que deux tâches soient égales si elles ont le même titre. Ce qui nous permettra de les comparer dans les listes.
     * @param o objet à comparer
     * @return true si les deux tâches sont égales, false sinon
     */
    @Override
    public boolean equals(Object o) {
        return this.numTache == ((Tache) o).getNumTache();
    }

    /**
     * On redéfinit la méthode hashCode pour que deux tâches soient égales si elles ont le même titre. Ce qui nous permettra de les comparer dans les listes.
     * @return numTache
     */
    @Override
    public int hashCode() {

        return this.numTache;
    }

    /**
     * Méthode qui archive ou désarchive une tâche
     * @param estArchivee true si on veut archiver la tâche, false sinon
     */
    public void archiver(boolean estArchivee) {
        this.estArchivee = estArchivee;
        // Si l'utilisateur est connecté, on enregistre l'etat d'achivage de la tâche dans la base de données
        if(Modele.user != null){
            try {
                this.save();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Getter qui retourne l'attribut estArchivee de la tache
     * @return estArchivee
     */
    public boolean isArchivee() {
        return this.estArchivee;
    }

    /**
     * Méthode qui permet de savoir si une tache appartient à une liste
     * @param l la liste à tester
     * @return true si la tache appartient a la liste, false sinon
     */
    public boolean appartientA(Liste l) throws SQLException {
        String SQLPrep = "SELECT * FROM TACHELISTE WHERE id_liste = ? AND id_tache = ?";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep.setInt(1,l.getId());
        prep.setInt(2,this.id);
        prep.execute();

        ResultSet rs = prep.getResultSet();
        // S'il y a un resultat
        if (rs.next()) {
            return true;
        }
        return false;
    }

    /**
     * Méthode qui retourne une liste des sous taches de la tache courante
     * @return la liste des sous taches
     */
    public ArrayList<Tache> findAllSousTaches() throws SQLException {
        // On crée l'arraylist a retourner
        ArrayList<Tache> soustaches = new ArrayList<Tache>();

        // On récupère les sous taches de la tache courante dans la base de données
        String SQLPrep = "SELECT id_tachefille FROM ESTSOUSTACHE WHERE id_tachemere = ?";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep.setInt(1,this.getId());
        prep.execute();

        ResultSet rs = prep.getResultSet();
        // S'il y a un resultat
        if (rs.next()) {
            Tache t = Tache.findById(rs.getInt("id_tachefille"));
            soustaches.add(t);
        }
        return soustaches;
    }

    public void setSelectionnee(boolean selectionnee) {
        this.selectionnee = selectionnee;
    }

    public boolean isSelectionnee() {
        return this.selectionnee;
    }

    /**
     * Méthode qui permet de supprimer une sous tâche de la tâche courante.
     *
     * @param sousTache
     * @return
     */
    public abstract boolean supprimerSousTache(int sousTache);

    /**
     * Méthode qui permet de retourner les sous tâches de la tâche courante. et qui est récursive. Ce qui signifie qu'elle retourne les sous tâches des sous tâches etc
     * @return
     */
    public abstract ArrayList<Tache> getSousTachesReccursif();


    /**
     * Méthode qui permet de retourner la tâche mère de la tâche courante.
     * @return
     */
    public TacheMere getTacheMere() {
        return this.tacheMere;
    }

    public void setTacheMere(TacheMere tacheMere) {
        this.tacheMere = tacheMere;
    }
}
