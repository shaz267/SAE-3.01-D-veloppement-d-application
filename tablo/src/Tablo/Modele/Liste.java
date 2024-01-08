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
import java.util.Collection;
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
            l.id = rs.getInt("id");
            listP.add(l);
        }
        return listP;
    }

    /**
     * Méthode findById permettant de récupérer une liste de la base de données en fonction de son id
     * @param id id de la liste à récupérer
     * @return Liste
     * @throws SQLException
     */
    public static Liste findById(int id) throws SQLException {
        String SQLPrep = "SELECT * FROM `LISTE` WHERE id = ?;";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setInt(1,id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        if(rs.next() == false){
            return null;
        }
        Liste l = new Liste(rs.getString("titre"));
        l.id = rs.getInt("id");
        return l;
    }

    /**
     * Méthode findByName permettant de récupérer une liste de la base de données en fonction de son titre
     * @param titre
     * @return
     * @throws SQLException
     */
    public static ArrayList<Liste> findByName(String titre) throws SQLException {
        String SQLPrep = "SELECT * FROM `LISTE` WHERE nom = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLPrep);
        prep1.setString(1,titre);
        ResultSet rs = prep1.executeQuery();
        ArrayList<Liste> listP = new ArrayList<>();
        while (rs.next()) {
            Liste l = new Liste(rs.getString("titre"));
            l.id = rs.getInt("id");
            listP.add(l);
        }
        return listP;
    }

    /**
     * Méthode delete permettant de supprimer une liste de la base de données
     * @throws SQLException
     */
    public void delete() throws SQLException {
        String SQLDel = "DELETE FROM `LISTE` WHERE titre = ?";
        PreparedStatement prep1 = DBConnection.getConnection().prepareStatement(SQLDel);
        prep1.setString(1,titre);
        prep1.executeUpdate();
        id = -1;
    }

    /**
     * Méthode save permettant de sauvegarder une liste dans la base de données
     * @throws SQLException
     */
    public void save() throws SQLException {
        if(id == -1){
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
        String SQLPrep = "INSERT INTO `LISTE` (`titre`) VALUES (?);";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
        // auto-increment)
        prep.setString(1, titre);
        prep.execute();

        ResultSet generatedKeys = prep.getGeneratedKeys();
        if (generatedKeys.next()) {
            int id = generatedKeys.getInt(1);
            this.id = id;
        }
    }

    /**
     * Méthode update permettant de mettre à jour une liste dans la base de données
     * @throws SQLException
     */
    public void update() throws SQLException {
        String SQLsave = "UPDATE `PERSONNE` SET `titre` = ? WHERE `id_liste` = ?";
        PreparedStatement prep = DBConnection.getConnection().prepareStatement(SQLsave);
        prep.setString(1,titre);
        prep.setInt(2,id);
        prep.executeUpdate();
    }

    /**
     * Setter pour l'attribut titre
     *
     * @param titre nouvelle valeur de l'attribut
     */
    public void changerTitreListe(String titre) {

        Loggeur.enregistrer("Changement du titre de la liste " + this.titre + " en " + titre);
        this.titre = titre;
    }

    /**
     * Permet d'ajouter une tâche à l'attribut taches
     *
     * @param t tâche à ajouter
     */
    public void ajouterTache(Tache t) {

        Loggeur.enregistrer("Ajout de la tâche " + t.getTitre() + " à la liste " + this.titre);
        this.taches.add(t);
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
	 * Permet de déplacer une tâche dans une autre liste
	 * @param tache
	 */
	public void deplacerTache(Tache tache, int idListeDestination) {

		for (Tache t : this.taches) {
			if (t.getId() == Modele.getTacheCourante()) {
				//retirerTache(t);
			}
		}
	}

	/**
	 * Permet de mettre une tâche en terminée
	 */
	public void fini(){

        for (Tache t : this.taches) {
            if (t.getNumTache() == Modele.getTacheCourante()) {

                //Pour chaques sous taches des taches de la liste portant le même titre que la tache courante. On met la sous tache en terminée
                for (Tache tache : this.taches) {
                    if (tache.getSousTaches() != null) {
                        for (Tache sousTache : tache.getSousTaches()) {
                            if (sousTache.getTitre().equals(t.getTitre())) {
                                sousTache.fini();
                            }
                        }
                    }
                }

                // On met la tâche en terminée
                t.fini();
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


    /**
     * Méthode qui convertie la tache courante en tache mère.
     */
    public void tacheCouranteEnMere() {

        //On appele le constructeur de la classe TacheMere. Qui prend une tache en paramètre. Pour la tache courante.
        for (Tache t : this.taches) {

            if (t.getNumTache() == Modele.getTacheCourante()) {

                TacheMere tacheMere = new TacheMere(t);
                this.taches.set(this.taches.indexOf(t), tacheMere);
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
}
