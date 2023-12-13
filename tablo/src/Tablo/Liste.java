package Tablo;

import java.util.ArrayList;
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

	/**
	 * Constructeur de l'objet Liste
	 * @param t titre de la Liste
	 */
	public Liste(String t){
		// l'id est initialisé à -1 conformément au patron activeRecord
		this.id = -1;
		this.titre = t;
		this.taches = new ArrayList<>();
	}

	/**
	 * Setter pour l'attribut titre
	 * @param titre nouvelle valeur de l'attribut
	 */
	public void changerTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * Permet d'ajouter une tâche à l'attribut taches
	 * @param t tâche à ajouter
	 */
	public void ajouterTache(Tache t){
		this.taches.add(t);
	}

	/**
	 * Permet de supprimer une tâche de l'attribut taches
	 * @param t tâche à supprimer
	 */
	public void retirerTache(Tache t){
		this.taches.remove(t);
	}

	/**
	 * Permet de récupérer l'attribut titre
	 * @return titre de l'objet Liste
	 */
	public String getTitre() {
		return this.titre;
	}

	/**
	 * Permet de récupérer l'attribut id
	 * @return id de l'objet Liste
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Permet de récupérer l'attribut taches
	 * @return taches de l'objet Liste
	 */
	public List<Tache> getTaches() {
		return this.taches;
	}


}
