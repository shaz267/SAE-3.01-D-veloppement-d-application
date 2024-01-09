package Tablo.Modele.Templates;

import Tablo.Modele.Modele;
import Tablo.Modele.Tableau;
import Tablo.Modele.Tache;
import Tablo.Modele.TacheSimple;
import Tablo.Vue.VueTableau;

public class TemplateReuHebdo implements TemplatesStrategie{
    
    /**
     * Modele du template
     */
    private Modele modele;
    
    public TemplateReuHebdo(Modele modele) {
        this.modele = modele;
    }
    @Override
    public void creerTableau() {

        //On vérifie que le titre n'est pas déjà utilisé
        if (modele.verifTableauExistant("Réunion hebdomadaire")){
            // Création du tableau
            Tableau t = new Tableau(modele.getTableaux().size() + 1, "Réunion hebdomadaire");

            //On ajoute le tableau au modele
            modele.ajouterTableau(t);

            // On ajoute la vue du tableau au modele
            modele.enregistrerObservateur(new VueTableau());

            // Création des différentes listes qui composent le template
            modele.ajouterListe("Sujet de la prochaine réunion");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache = new TacheSimple(modele.getTaches().size() + 1, "Nouveau projet Circuit WEB");
            //On ajoute la tache au modele
            modele.ajouterTache(tache);


            modele.ajouterListe("Sujet des dernières réunions");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache2 = new TacheSimple(modele.getTaches().size() + 1, "------>");
            //On ajoute la tache au modele
            modele.ajouterTache(tache2);


            modele.ajouterListe("13 Décembre");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache3 = new TacheSimple(modele.getTaches().size() + 1, "15min : Présentation du projet par Marin");
            //On ajoute la tache au modele
            modele.ajouterTache(tache3);

            // Création de la tache
            Tache tache4 = new TacheSimple(modele.getTaches().size() + 1, "Affectation des tâches");
            //On ajoute la tache au modele
            modele.ajouterTache(tache4);


            modele.ajouterListe("30 Février");
            Modele.setListeCourante(modele.getListes().get(modele.getListes().size() - 1).getNumListe());

            // Création de la tache
            Tache tache5 = new TacheSimple(modele.getTaches().size() + 1, "Brainstorming sur le design du site");
            //On ajoute la tache au modele
            modele.ajouterTache(tache5);

            // Création de la tache
            Tache tache6 = new TacheSimple(modele.getTaches().size() + 1, "Démonstration du site");
            //On ajoute la tache au modele
            modele.ajouterTache(tache6);
        }
    }
}
