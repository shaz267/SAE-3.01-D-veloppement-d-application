package Tablo.Modele;

import Tablo.Loggeur;

import java.time.LocalDate;
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

	private int numListe;

	/**
	 * Constructeur de l'objet Liste
	 * @param t titre de la Liste
	 */
	public Liste(int numListe, String t){
		// l'id est initialisé à -1 conformément au patron activeRecord
		this.id = -1;
		this.titre = t;
		this.taches = new ArrayList<>();

		this.numListe = numListe;
	}

	/**
	 * Setter pour l'attribut titre
	 * @param titre nouvelle valeur de l'attribut
	 */
	public void changerTitre(String titre) {

		Loggeur.enregistrer("Changement du titre de la liste " + this.titre + " en " + titre);
		this.titre = titre;
	}

	/**
	 * Permet d'ajouter une tâche à l'attribut taches
	 * @param t tâche à ajouter
	 */
	public void ajouterTache(Tache t){

		Loggeur.enregistrer("Ajout de la tâche " + t.getTitre() + " à la liste " + this.titre);
		this.taches.add(t);
	}

	/**
	 * Permet de supprimer une tâche de l'attribut taches
	 * @param t tâche à supprimer
	 */
	public void retirerTache(Tache t){

		Loggeur.enregistrer("Suppression de la tâche " + t.getTitre() + " de la liste " + this.titre);
		this.taches.remove(t);
	}

	/**
	 * Permet de changer le titre d'une tâche dans la liste des taches
	 * @param nouveauTitre
	 */
	public void changerTitreTache(String nouveauTitre){

		for (Tache t : this.taches) {
			if (t.getId() == Modele.getTacheCourante()) {
				t.changerTitre(nouveauTitre);
			}
		}
	}

	/**
	 * Permet de changer le contenu d'une tâche dans la liste des taches
	 * @param nouveauContenu
	 */
	public void changerContenuTache(String nouveauContenu){

		for (Tache t : this.taches) {
			if (t.getId() == Modele.getTacheCourante()) {
				t.changerContenu(nouveauContenu);
			}
		}
	}

	/**
	 * Permet de changer la date limite d'une tâche dans la liste des taches
	 * @param dateDebut
	 */
	public void modifierDateDebut(LocalDate dateDebut) {

		for (Tache t : this.taches) {
			if (t.getId() == Modele.getTacheCourante()) {
				t.modifierDateDebut(dateDebut);
			}
		}
	}

	/**
	 * Permet de changer la date limite d'une tâche dans la liste des taches
	 * @param dateLimite
	 */
	public void modifierDateLimite(LocalDate dateLimite) {

		for (Tache t : this.taches) {
			if (t.getId() == Modele.getTacheCourante()) {
				t.modifierDateLimite(dateLimite);
			}
		}
	}

	/**
	 * Permet de mettre une tâche en terminée
	 */
	public void fini(){

		for (Tache t : this.taches) {
			if (t.getId() == Modele.getTacheCourante()) {
				t.fini();
			}
		}
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

	public int getNumListe() {
		return this.numListe;
	}


}
