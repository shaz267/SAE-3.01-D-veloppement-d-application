package Tablo.Modele.Templates;

import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Modele.Tache;
import Tablo.Modele.TacheSimple;
import Tablo.Vue.VueTableau;

public class TemplateConduiteProjet implements TemplatesStrategie{
    
    /**
     * Modele du template
     */
    private Modele modele;

    /**
     * Instance du template qui est utilisée pour le patron singleton
     */
    private static TemplateConduiteProjet instance;

    /**
     * Constructeur de la classe TemplateConduiteProjet
     * @param modele
     */
    private TemplateConduiteProjet(Modele modele) {
        this.modele = modele;
    }

    /**
     * Méthode qui permet de récupérer l'instance du template
     * @param modele Modele de l'application
     * @return L'instance du template
     */
    public static synchronized TemplateConduiteProjet getInstance(Modele modele) {
        if (instance == null) {
            instance = new TemplateConduiteProjet(modele);
        }
        return instance;
    }
    
    @Override
    public void creerTableau() {

        //On vérifie que le titre n'est pas déjà utilisé
        if (modele.verifTableauExistant("Conduite de projet")) {

            // Création du tableau
            Tableau t = new Tableau(modele.getTableaux().size() + 1, "Conduite de projet");

            //On ajoute la liste au modele
            modele.ajouterTableau(t);

            // On ajoute la vue du tableau au modele
            modele.enregistrerObservateur(new VueTableau());

            // Création des différentes listes qui composent le template
            modele.ajouterListe("Project Structure");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache = new TacheSimple(modele.getTaches().size() + 1, "Parties prenantes");
            //On ajoute la tache au modele
            modele.ajouterTache(tache);

            // Création de la tache
            Tache tache2 = new TacheSimple(modele.getTaches().size() + 1, "Revue hebdomadaire");
            //On ajoute la tache au modele
            modele.ajouterTache(tache2);


            modele.ajouterListe("Sujet de la prochaine réunion");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache3 = new TacheSimple(modele.getTaches().size() + 1, "Problèmes HTML !");
            //On ajoute la tache au modele
            modele.ajouterTache(tache3);

            // Création de la tache
            Tache tache4 = new TacheSimple(modele.getTaches().size() + 1, "Activer les cookies !");
            //On ajoute la tache au modele
            modele.ajouterTache(tache4);

            // Création de la tache
            Tache tache5 = new TacheSimple(modele.getTaches().size() + 1, "Répartition des tâches");
            //On ajoute la tache au modele
            modele.ajouterTache(tache5);


            modele.ajouterListe("Tâches à faire");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache6 = new TacheSimple(modele.getTaches().size() + 1, "Création bannière du site");
            //On ajoute la tache au modele
            modele.ajouterTache(tache6);

            // Création de la tache
            Tache tache7 = new TacheSimple(modele.getTaches().size() + 1, "Création page de remerciement");
            //On ajoute la tache au modele
            modele.ajouterTache(tache7);

            // Création de la tache
            Tache tache8 = new TacheSimple(modele.getTaches().size() + 1, "Création cahier des charges");
            //On ajoute la tache au modele
            modele.ajouterTache(tache8);


            modele.ajouterListe("Tâches en cours");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache9 = new TacheSimple(modele.getTaches().size() + 1, "Création page d'annexe");
            //On ajoute la tache au modele
            modele.ajouterTache(tache9);


            modele.ajouterListe("Tâches terminées");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache10 = new TacheSimple(modele.getTaches().size() + 1, "Création page de contact");
            //On ajoute la tache au modele
            modele.ajouterTache(tache10);

            // Création de la tache
            Tache tache11 = new TacheSimple(modele.getTaches().size() + 1, "Création page d'accueil");
            //On ajoute la tache au modele
            modele.ajouterTache(tache11);

        }
    }
}
