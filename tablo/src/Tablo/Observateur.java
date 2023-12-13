package Tablo;

public interface Observateur {

	/**
	 * Méthode qui permet d'actualiser les vues du modèle selon le patron
	 * de conception MVC
	 * @param s Objet qui implémente l'interface Sujet et qui va être actualisé
	 */
	void actualiser(Sujet s);
}
