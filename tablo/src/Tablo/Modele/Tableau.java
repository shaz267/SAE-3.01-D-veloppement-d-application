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
     * Méthode permettatnt de récupérer tout les tableaux d'un utilisateur
     * @param id, id de l'utilisateur
     * @return retourne la liste de ses tableaux
     * @throws SQLException
     */
    public static ArrayList<Tableau> findAllByUserId(int id) throws SQLException {
        String SQLPrep = "SELECT `titre`, `id_tableau`, `num_tab` FROM `TABLEAU` WHERE `id_user` = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setInt(1,id);
        ResultSet rs = prep1.executeQuery();
        ArrayList<Tableau> listTabs = new ArrayList<>();
        while (rs.next()) {
            Tableau t = new Tableau(rs.getString("titre"));
            t.id = rs.getInt("id_tableau");
            t.numTableau = rs.getInt("num_tab");
            listTabs.add(t);
        }
        return listTabs;
    }


    /**
     * Cherche toutes les listes d'un tableau
     * @param id_tableau id du tableau
     * @return ArrayList de Liste
     * @throws SQLException
     */
    public static ArrayList<Liste> findAllListeFromTableau(int id_tableau) throws SQLException {
        String SQLPrep = "SELECT * FROM `TABLEAULISTE` WHERE `id_tableau` = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setInt(1,id_tableau);
        ResultSet rs = prep1.executeQuery();
        ArrayList<Liste> listP = new ArrayList<>();
        while (rs.next()) {
            Liste l = Liste.findById(rs.getInt("id_liste"));
            listP.add(l);
        }
        return listP;
    }

    /**
     * Supprime toutes les listes d'un tableau
     * @throws SQLException
     */
    public void deleteAllListeFromTableau() throws SQLException{
        // On récupère toutes les listes du tableau
        ArrayList<Liste> listes = findAllListeFromTableau(this.id);
        // On supprime toutes les listes du tableau
        for(Liste l : listes){
            String SQLPrep = "DELETE FROM `TABLEAULISTE` WHERE `id_tableau` = ? AND `id_liste` = ?";
            PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
            prep.setInt(1, this.id);
            prep.setInt(2, l.getId());
            prep.execute();
            l.deleteAllTacheFromListe();
            l.delete();
        }
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
        String SQLPrep = "INSERT INTO `TABLEAU` (`id_tableau`,`titre`, `id_user`, `num_tab`) VALUES (?,?,?,?);";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep.setInt(1,id);
        prep.setString(2, titre);
        prep.setInt(3, Modele.user.getId());
        prep.setInt(4, numTableau);
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
        String SQLPrep = "CREATE TABLE `TABLEAU` (`id_tableau` INT NOT NULL, `titre` varchar(30), `id_user` INT NOT NULL, `num_tab` INT NOT NULL, PRIMARY KEY (`id_tableau`))";
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
     * @param tache tache à déplacer
     * @param numListeDestination numéro de la liste de destination
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
        //On récupère la liste de destination
        Liste listeDestination = this.listes.get(numListeDestination-1);
        Modele.setListeDestination(numListeDestination);
            //On vérifie que la liste de destination existe
            if (listeDestination != null) {
                //On retire la tache de la precedente liste
                for (Liste l : this.listes) {
                    //Si la liste est la liste de la tache<
                    if (l.getNumListe() == tache.getNumListe()) {
                        l.supprimerTache(tache);
                    }
                }

                //On ajoute la tache à la liste de destination
                listeDestination.ajouterTache(tache);

                // On réinitialise la tacte courante
                Modele.setTacheCourante(-1);
                //On change la liste courante
                Modele.setListeCourante(numListeDestination);
                res = true;

            }
        return res;
    }



    /**
     * Modifie la date limite de la tache dans la liste courante
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
            try {
                // On veut vérifier que la liste n'est pas déjà dans la table TABLEAULISTE
                String SQLPrep = "SELECT * FROM `TABLEAULISTE` WHERE `id_tableau` = ? AND `id_liste` = ?;";
                PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
                prep.setInt(1, this.id);
                prep.setInt(2, l.getId());
                prep.execute();
                ResultSet rs = prep.getResultSet();

                //Si la liste n'est pas dans la table TABLEAULISTE
                if(!rs.next()){
                    // On enregistre la liste dans la base de données
                    l.save();

                    //On ajoute le lien entre la liste et le tableau à la base de données
                    SQLPrep = "INSERT INTO `TABLEAULISTE` (`id_tableau`,`id_liste`) VALUES (?,?);";
                    prep = DBConnection.getConnection().prepareStatement(SQLPrep);
                    prep.setInt(1, this.id);
                    prep.setInt(2, l.getId());
                    prep.execute();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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
                    if(Modele.user != null){
	                    try {
                            String SQLPrep = "DELETE FROM `TABLEAULISTE` WHERE `id_tableau` = ? AND `id_liste` = ?";
                            PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep);
                            prep.setInt(1, this.id);
                            prep.setInt(2, liste.getId());
                            prep.execute();
                            liste.deleteAllTacheFromListe();
                            liste.delete();
	                    } catch (SQLException e) {
		                    throw new RuntimeException(e);
	                    }
                    }
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
        if(Modele.user != null){
            try {
                this.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Change le numéro du tableau
     *
     * @param numTableau nouveau numéro du tableau
     */
    public void setNumTableau(int numTableau) {
        this.numTableau = numTableau;
        if(Modele.user != null){
            try {
                this.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
