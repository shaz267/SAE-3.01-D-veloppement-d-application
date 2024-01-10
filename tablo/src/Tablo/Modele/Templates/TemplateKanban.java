package Tablo.Modele.Templates;

import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Modele.Tache;
import Tablo.Modele.TacheSimple;
import Tablo.Vue.VueTableau;

public class TemplateKanban implements TemplatesStrategie{

    /**
     * Modele du template
     */
    private Modele modele;

    public TemplateKanban(Modele modele) {
        this.modele = modele;
    }

    @Override
    public void creerTableau() {
        //On vérifie que le titre n'est pas déjà utilisé
        if (modele.verifTableauExistant("Modèle Kanban")) {

            // Création du tableau
            Tableau t = new Tableau(modele.getTableaux().size() + 1, "Modèle Kanban");

            //On ajoute la liste au modele
            modele.ajouterTableau(t);

            // On ajoute la vue du tableau au modele
            modele.enregistrerObservateur(new VueTableau());

            // Création des différentes listes qui composent le template
            modele.ajouterListe("Backlog");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache = new TacheSimple(modele.getTaches().size() + 1, "Backlog");
            //On ajoute la tache au modele
            modele.ajouterTache(tache);

            // Création de la tache
            Tache tache2 = new TacheSimple(modele.getTaches().size() + 1, "[Exemple de tâche]");
            //On ajoute la tache au modele
            modele.ajouterTache(tache2);


            modele.ajouterListe("Conception");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache3 = new TacheSimple(modele.getTaches().size() + 1, "Conception & Recherche");
            //On ajoute la tache au modele
            modele.ajouterTache(tache3);

            // Création de la tache
            Tache tache4 = new TacheSimple(modele.getTaches().size() + 1, "[Exemple de tâche de conception]");
            //On ajoute la tache au modele
            modele.ajouterTache(tache4);


            modele.ajouterListe("A faire");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache5 = new TacheSimple(modele.getTaches().size() + 1, "A faire");
            //On ajoute la tache au modele
            modele.ajouterTache(tache5);


            modele.ajouterListe("En cours");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache6 = new TacheSimple(modele.getTaches().size() + 1, "En cours");
            //On ajoute la tache au modele
            modele.ajouterTache(tache6);


            modele.ajouterListe("Revision du code");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache7 = new TacheSimple(modele.getTaches().size() + 1, "Revision du code");
            //On ajoute la tache au modele
            modele.ajouterTache(tache7);

            // Création de la tache
            Tache tache8 = new TacheSimple(modele.getTaches().size() + 1, "[Exemple de tâche de révision du code]");
            //On ajoute la tache au modele
            modele.ajouterTache(tache8);


            modele.ajouterListe("Test");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache9 = new TacheSimple(modele.getTaches().size() + 1, "Test");
            //On ajoute la tache au modele
            modele.ajouterTache(tache9);


            modele.ajouterListe("Terminé");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache10 = new TacheSimple(modele.getTaches().size() + 1, "Terminé");
            //On ajoute la tache au modele
            modele.ajouterTache(tache10);

            // Création de la tache
            Tache tache11 = new TacheSimple(modele.getTaches().size() + 1, "[Tâche terminée]");
            //On ajoute la tache au modele
            modele.ajouterTache(tache11);
        } 
    }
}
