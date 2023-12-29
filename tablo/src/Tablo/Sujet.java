package Tablo;

public interface Sujet {

    /**
     * Méthode qui permet d'enregistrer un observateur
     *
     * @param o
     */
    public void enregistrerObservateur(Observateur o);

    /**
     * Méthode qui permet de supprimer un observateur
     *
     * @param o
     */
    public void supprimerObservateur(Observateur o);

    /**
     * Méthode qui permet de notifier les observateurs
     */
    public void notifierObservateurs();
}
