package Tablo;

import Tablo.Modele.Liste;
import Tablo.Modele.Tableau;
import Tablo.Modele.Tache;
import Tablo.Modele.Utilisateur;

import java.util.ArrayList;

/**
 * Classe Init pour initialiser la base de données
 * Vous devez lancer cette classe une fois la bd 'tablo' créée.
 * Elle va créer les tables nécessaires au fonctionnement de l'application
 * Vous pouvez la lancer plusieurs fois sans problème, les tables ne seront pas recréées ou écrasées
 */
public class Init {

	public static void main(String[] args) {
		// On crée les tables
		String creationESTSOUSTACHE = "CREATE TABLE `ESTSOUSTACHE` (`id_tachemere` int(11) NOT NULL,`id_tachefille` int(11) NOT NULL, PRIMARY KEY (`id_tachemere`,`id_tachefille`))";
		String creationTABLEAULISTE = "CREATE TABLE `TABLEAULISTE` (`id_tableau` int(11) NOT NULL,`id_liste` int(11) NOT NULL, PRIMARY KEY (`id_tableau`,`id_liste`))";
		String creationTACHELISTE = "CREATE TABLE `TACHELISTE` (`id_liste` int(11) NOT NULL,`id_tache` int(11) NOT NULL)";

		// On les ajoute dans une liste
		ArrayList<String> requetes = new ArrayList<String>();
		requetes.add(creationESTSOUSTACHE);
		requetes.add(creationTABLEAULISTE);
		requetes.add(creationTACHELISTE);

		// On les exécute
		for (String requete : requetes) {
			try {
				DBConnection.getConnection().createStatement().executeUpdate(requete);
			} catch (Exception e) {
                throw new RuntimeException(e);
            }
		}
		try{
			Utilisateur.createTable();
			Tache.createTable();
			Liste.createTable();
			Tableau.createTable();
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
