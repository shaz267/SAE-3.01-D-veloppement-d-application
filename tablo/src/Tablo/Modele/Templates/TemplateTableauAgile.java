package Tablo.Modele.Templates;

import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Modele.Tache;
import Tablo.Modele.TacheSimple;
import Tablo.Vue.VueTableau;

public class TemplateTableauAgile implements TemplatesStrategie{
    
    /**
     * Modele du template
     */
    private Modele modele;

    /**
     * Instance du template qui est utilisée pour le patron singleton
     */
    private static TemplateTableauAgile instance;

    /**
     * Constructeur de la classe TemplateTableauAgile
     * @param modele Modele de l'application
     */
    private TemplateTableauAgile(Modele modele) {
        this.modele = modele;
    }

    /**
     * Méthode qui permet de récupérer l'instance du template
     * @param modele Modele de l'application
     * @return L'instance du template
     */
    public static synchronized TemplateTableauAgile getInstance(Modele modele) {
        if (instance == null) {
            instance = new TemplateTableauAgile(modele);
        }
        return instance;
    }
    
    @Override
    public void creerTableau() {

        //On vérifie que le titre n'est pas déjà utilisé
        if(modele.verifTableauExistant("Tableau Agile")){

            // Création du tableau
            Tableau t = new Tableau(modele.getTableaux().size() + 1, "Tableau Agile");

            //On ajoute le tableau au modele
            modele.ajouterTableau(t);

            // On ajoute la vue du tableau au modele
            modele.enregistrerObservateur(new VueTableau());

            // Création des différentes listes qui composent le template
            modele.ajouterListe("Fait");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache = new TacheSimple(modele.getTaches().size() + 1, "Reunion Nancy");
            //On ajoute la tache au modele
            modele.ajouterTache(tache);

            // Création de la tache
            Tache tache2 = new TacheSimple(modele.getTaches().size() + 1, "Tendances de l'application");
            //On ajoute la tache au modele
            modele.ajouterTache(tache2);

            modele.ajouterListe("Sprint en cours");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache3 = new TacheSimple(modele.getTaches().size() + 1, "Campagne de communication");
            //On ajoute la tache au modele
            modele.ajouterTache(tache3);

            // Création de la tache
            Tache tache4 = new TacheSimple(modele.getTaches().size() + 1, "Deploiemment serveur");
            //On ajoute la tache au modele
            modele.ajouterTache(tache4);

            // Création de la tache
            Tache tache5 = new TacheSimple(modele.getTaches().size() + 1, "Deploiement de la base de données");
            //On ajoute la tache au modele
            modele.ajouterTache(tache5);


            modele.ajouterListe("En cours");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache6 = new TacheSimple(modele.getTaches().size() + 1, "Données");
            //On ajoute la tache au modele
            modele.ajouterTache(tache6);

            // Création de la tache
            Tache tache7 = new TacheSimple(modele.getTaches().size() + 1, "Nouvelle page de lancement");
            //On ajoute la tache au modele
            modele.ajouterTache(tache7);


            modele.ajouterListe("En attente");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache8 = new TacheSimple(modele.getTaches().size() + 1, "Règles CSS");
            //On ajoute la tache au modele
            modele.ajouterTache(tache8);


            modele.ajouterListe("A venir");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache9 = new TacheSimple(modele.getTaches().size() + 1, "Nouvelle page de connexion");
            //On ajoute la tache au modele
            modele.ajouterTache(tache9);

            // Création de la tache
            Tache tache10 = new TacheSimple(modele.getTaches().size() + 1, "Nouvelle page d'inscription");
            //On ajoute la tache au modele
            modele.ajouterTache(tache10);

            // Création de la tache
            Tache tache11 = new TacheSimple(modele.getTaches().size() + 1, "Nouvelle page de profil");
            //On ajoute la tache au modele
            modele.ajouterTache(tache11);

            // Création de la tache
            Tache tache12 = new TacheSimple(modele.getTaches().size() + 1, "Nouvelle page de contact");
            //On ajoute la tache au modele
            modele.ajouterTache(tache12);

            // Création de la tache
            Tache tache13 = new TacheSimple(modele.getTaches().size() + 1, "Nouvelle page de remerciement");
            //On ajoute la tache au modele
            modele.ajouterTache(tache13);

            // Création de la tache
            Tache tache14 = new TacheSimple(modele.getTaches().size() + 1, "Nouvelle page d'annexe");
            //On ajoute la tache au modele
            modele.ajouterTache(tache14);

            // Création de la tache
            Tache tache15 = new TacheSimple(modele.getTaches().size() + 1, "Nouvelle page d'accueil");
            //On ajoute la tache au modele
            modele.ajouterTache(tache15);


            modele.ajouterListe("Questions");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache17 = new TacheSimple(modele.getTaches().size() + 1, "Comment faire pour créer une tâches ?");
            //On ajoute la tache au modele
            modele.ajouterTache(tache17);

            // Création de la tache
            Tache tache18 = new TacheSimple(modele.getTaches().size() + 1, "Comment faire pour créer une liste ?");
            //On ajoute la tache au modele
            modele.ajouterTache(tache18);


            modele.ajouterListe("Boite à idées Marketing");

            modele.ajouterListe("Tiroir à débarras");

            modele.ajouterListe("Peut mieux faire");

            modele.ajouterListe("Boite à idées Contenus");

            modele.ajouterListe("Les Vainqueurs - WINS !");

            modele.ajouterListe("J'adorerais le faire");

            modele.ajouterListe("Ressources");

        }
    }
}
