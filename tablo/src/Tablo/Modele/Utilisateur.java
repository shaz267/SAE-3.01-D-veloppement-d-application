package Tablo.Modele;

import Tablo.DBConnection;
import Tablo.MyApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

public class Utilisateur {

    private int id_user;
    private String pseudo;
    private String email;
    /**
     * mdp, attribut mot de passe hashé lors de la création de l'utilisateur
     */
    private String mdp;

    public Utilisateur(String ps, String email, String mdp){
        this.id_user = -1; // -1 de base car l'objet n'est pas enregistré dans la bd
        this.pseudo = ps;
        this.email = email;
        // On hashe le mot de passe
        if(mdp.length() > 30){
            this.mdp = mdp;
        } else {
            this.mdp = Utilisateur.passwordHash(mdp);
        }

    }

    /**
     * Méthode save, pour enregistrer un tuple Utilisateur dans la table ou le maj si il y est déja
     * @throws SQLException
     */
    public void save() throws SQLException{
        // Si l'id est -1
        if(this.id_user == -1){
            // On récupère l'id pour le mettre à jour
            ArrayList<Utilisateur> personnes = Utilisateur.findAll();
            // On le met à jour
            this.id_user = personnes.size() + 1;
            // On enregistre l'objet dans la bd
            this.saveNew();
        }
        // Sinon
        else{
            // On met l'objet à jour dans la bd
            this.update();
        }
    }

    /**
     * Méthode saveNew pour enregistrer un utilisateur dans la table qui n'y est pas déja
     * @throws SQLException
     */
    private void saveNew() throws SQLException{
        String query = "INSERT INTO UTILISATEUR(id_user, pseudo, email, mdp) VALUES(?,?,?,?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
        ps.setInt(1, this.id_user);
        ps.setString(2, this.pseudo);
        ps.setString(3, this.email);
        ps.setString(4, this.mdp);
        ps.execute();
    }

    /**
     * Méthode d'accès de l'attribut id_user
     * @return
     */
    public int getId(){
        return this.id_user;
    }

    /**
     * Méthode delete pour supprimer un utilisateur de la table
     * @throws SQLException
     */
    public void delete() throws SQLException{
        // On récup l'id de la personne
        int id = this.id_user;

        // Si l'id est different de -1
        if(id != -1){
            // On supprime donc la personne qui a cet id dans la table
            PreparedStatement prep = DBConnection.getConnection().prepareStatement("DELETE FROM UTILISATEUR WHERE id_user=?");
            prep.setInt(1, id);
            prep.execute();
            // On repasse donc l'id à -1 car il n'est plus dans la table
            this.id_user = -1;
        }
    }

    /**
     * Méthode update pour mettre à jour un utilisateur déja existant dans la table
     * @throws SQLException
     */
    private void update() throws SQLException {

        String SQLprep = "update UTILISATEUR set pseudo=?, email=?, mdp=? where id=?;";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLprep);
        prep.setString(1, this.pseudo);
        prep.setString(2, this.email);
        prep.setString(3, this.mdp);
        prep.setInt(4, this.id_user);
        prep.execute();
    }

    /**
     * Méthode permettant de selectionner un utilisateur par son id
     * @param id, id de l'utilisateur a selectionner
     * @return user , l'utilisateur selectionne
     * @throws SQLException
     */
    public static Utilisateur findById(int id) throws SQLException{
        // On prépare la requête
        String query = "SELECT * FROM UTILISATEUR WHERE id_user=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        // S'il y a un resultat
        if (rs.next()) {
            String pseudo = rs.getString("pseudo");
            String email = rs.getString("email");
            String mdp = rs.getString("mdp");
            int id_a_set = rs.getInt("id_user");
            Utilisateur user = new Utilisateur(pseudo, email, mdp);
            user.setId(id);
            return user;
        }
        // Sinon
        else{
            return null;
        }
    }

    /**
     *  Méthode permettant de selectionner un utilisateur par son pseudo
     * @param psdo, pseudo a chercher
     * @return
     */
    public static Utilisateur findByPseudo(String psdo) throws SQLException {
        String SQLPrep = "SELECT * FROM UTILISATEUR WHERE pseudo=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(SQLPrep);
        ps.setString(1, psdo);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        // S'il y a un resultat on crée l'objet et le retourne sinon on retourne null
        if (rs.next() == false) {
            return null;
        }
        int id_a_set = rs.getInt("id_user");
        Utilisateur user = new Utilisateur(rs.getString("pseudo"), rs.getString("email"), rs.getString("mdp"));
        user.setId(id_a_set);
        return user;
    }

    /**
     *  Méthode permettant de selectionner un utilisateur par son email
     * @param mail, email a chercher
     * @return
     */
    public static Utilisateur findByEmail(String mail) throws SQLException {
        String SQLPrep = "SELECT * FROM UTILISATEUR WHERE email=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(SQLPrep);
        ps.setString(1, mail);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        // S'il y a un resultat on crée l'objet et le retourne sinon on retourne null
        if (rs.next() == false) {
            return null;
        }
        int id_a_set = rs.getInt("id_user");
        Utilisateur user = new Utilisateur(rs.getString("pseudo"), rs.getString("email"), rs.getString("mdp"));
        user.setId(id_a_set);
        return user;
    }

    /**
     * Méthode d'accès de l'attribut email
     * @return
     */
    private Object getEmail() {
        return this.email;
    }

    /**
     * Méthode findAll qui permet de retourner une liste des utilisateurs de la base
     * @return users, la liste des utilisateurs de la base
     * @throws SQLException
     */
    public static ArrayList<Utilisateur> findAll() throws SQLException{
        ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();

        String query = "SELECT * FROM UTILISATEUR";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
        ps.execute();
        ResultSet rs = ps.getResultSet();

        // Pour chaque personne de la base
        while(rs.next()){
            // On récupère id, pseudo, email, mdp
            int iduser = rs.getInt("id_user");
            String psdo = rs.getString("pseudo");
            String mail = rs.getString("email");
            String mdpasse = rs.getString("mdp");
            // On crée l'utilisateur
            Utilisateur user = new Utilisateur(psdo, mail, mdpasse);
            user.setId(iduser);
            // On ajoute la personne à la liste
            users.add(user);
        }

        return users;
    }

    /**
     * Méthode de création de la table Utilisateur
     * @throws SQLException
     */
    public static void createTable() throws SQLException{
        String createString = "CREATE TABLE `UTILISATEUR` (`id_user` INT NOT NULL, `pseudo` varchar(30), `email` varchar(254), `mdp` varchar(128), PRIMARY KEY (`id_user`,`pseudo`,`email`))";
        Statement stmt = DBConnection.getConnection().createStatement();
        stmt.executeUpdate(createString);
    }

    /**
     * Méthode pour suppr la table utilisateur
     * @throws SQLException si erreur dans la requête
     */
    public static void deleteTable() throws SQLException{
        String query = "DROP TABLE UTILISATEUR";
        Statement st = DBConnection.getConnection().createStatement();
        st.executeUpdate(query);
    }

    /**
     * Méthode qui permet de hasher le mot de passe
     *
     * @param mdpRecup Mot de passe à hasher
     * @return Mot de passe hasher
     */
    public static String passwordHash(String mdpRecup) {

        String mdpHash = "";
        //On hash le mot de passe
        try {

            //On récupère l'algorithme de hashage SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //On hash le mot de passe
            byte[] hash = digest.digest(mdpRecup.getBytes());

            //On convertit le hash en base 64
            mdpHash = Base64.getEncoder().encodeToString(hash);


        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return mdpHash;
    }

    /**
     * Méthode permettant la modification de l'id d'un utilisateur
     * @param id, le nouvel id de l'utilisateur
     */
    private void setId(int id){
        this.id_user = id;
    }

    /**
     * Méthode d'accès du pseudo de l'utilisateur
     * @return retourne le pseudo de l'utilisateur
     */
    public String getPseudo(){
        return this.pseudo;
    }

    /**
     * Méthode d'accès du mot de passe de l'utilisateur (hashé) directement dans la bd
     * @return
     */
    public String getMdp() throws SQLException {
        return this.mdp;
    }

}
