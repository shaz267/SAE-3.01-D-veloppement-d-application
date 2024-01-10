package Tablo.Modele;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableauTest {

    @Test
    void ajouterTache() {
        Tableau tableau = new Tableau(1, "Mon Tableau");
        TacheMere tache = new TacheMere(1, "Ma Tâche");
        tableau.ajouterTache(tache);

        List<Tache> taches = tableau.getTaches();
        assertTrue(taches.contains(tache));
    }

    @Test
    void deplacerTache() {
        Tableau tableau = new Tableau(1, "Mon Tableau");
        Liste liste1 = new Liste(1, "Liste 1");
        Liste liste2 = new Liste(2, "Liste 2");
        TacheMere tache = new TacheMere(2,"Tâche à déplacer");

        liste1.ajouterTache(tache);
        tableau.ajouterListe(liste1);
        tableau.ajouterListe(liste2);

        assertTrue(tableau.deplacerTache(tache, 2));

        // Vérifie que la tâche a été déplacée dans la liste 2
        assertFalse(liste1.getTaches().contains(tache));
        assertTrue(liste2.getTaches().contains(tache));
    }

    @Test
    void retirerListe() {
        Tableau tableau = new Tableau(1, "Mon Tableau");
        Liste liste1 = new Liste(1, "Liste 1");
        Liste liste2 = new Liste(2, "Liste 2");

        tableau.ajouterListe(liste1);
        tableau.ajouterListe(liste2);

        // Sélectionne la liste courante à "Liste 1"
        tableau.setNumTableau(1);

        assertTrue(tableau.retirerListe());

        // Vérifie que la liste 1 a été retirée et la liste 2 est maintenant la courante
        assertEquals("Liste 2", tableau.getListes().get(0).getTitre());
    }
}
