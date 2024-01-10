package Tablo.Modele;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableauTest {

    @Test
    void ajouterTache() {
        // Données de test
        Tableau tableau = new Tableau(1, "Mon Tableau");
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        tableau.ajouterListe(liste);

        // Appel de la méthode à tester
        Modele.setListeCourante(liste.getNumListe());
        tableau.ajouterTache(tacheMere);

        // Vérification du résultat
        assertTrue(tableau.getTaches().contains(tacheMere));
    }

    @Test
    void deplacerTache() {
         // Données de test
        Tableau tableau = new Tableau(1, "Mon Tableau");
        Liste liste = new Liste(1, "Liste");
        Liste liste2 = new Liste(2, "Liste 2");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        tableau.ajouterListe(liste);
        tableau.ajouterListe(liste2);
        Modele.setListeCourante(liste.getNumListe());
        tableau.ajouterTache(tacheMere);

        // Appel de la méthode à tester
        tableau.deplacerTache(tacheMere, 2);

        // Vérification du résultat
        assertTrue(tableau.getTaches().contains(tacheMere));
    }

    @Test
    void retirerListe() {
        // Données de test
        Tableau tableau = new Tableau(1, "Mon Tableau");
        Liste liste = new Liste(1, "Liste");
        tableau.ajouterListe(liste);

        // Appel de la méthode à tester
        Modele.setListeCourante(liste.getNumListe());
        tableau.retirerListe();

        // Vérification du résultat
        assertFalse(tableau.getListes().contains(liste));
    }
}
