package Tablo.Modele;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ListeTest {

    @Test
    void changerTitreListe() {
        // Données de test
        Liste liste = new Liste(1, "Liste");
        String nouveauTitre = "Nouveau titre";

        // Appel de la méthode à tester
        liste.changerTitreListe(nouveauTitre);

        // Vérification du résultat
        assertEquals(nouveauTitre, liste.getTitre());
    }

    @Test
    void ajouterTache() {
        // Données de test
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");

        // Appel de la méthode à tester
        liste.ajouterTache(tacheMere);

        // Vérification du résultat
        assertTrue(liste.getTaches().contains(tacheMere));
    }

    @Test
    void archiverTache() {
        // Donnée de test
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        liste.ajouterTache(tacheMere);

        // Appel de la méthode à tester
        Modele.setTacheCourante(tacheMere.getNumTache());
        liste.archiverTache();
    }

    @Test
    void supprimerTache() {
        // Donnée de test
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        liste.ajouterTache(tacheMere);

        // Appel de la méthode à tester
        liste.supprimerTache(tacheMere);

        // Vérification du résultat
        assertFalse(liste.getTaches().contains(tacheMere));
    }

    @Test
    void changerTitreTache() {
        // Donnée de test
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        liste.ajouterTache(tacheMere);

        // Appel de la méthode à tester
        Modele.setTacheCourante(tacheMere.getNumTache());
        liste.changerTitreTache("Nouveau titre");

        // Vérification du résultat
        assertEquals("Nouveau titre", tacheMere.getTitre());
    }

    @Test
    void changerContenuTache() {
        // Donnée de test
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        liste.ajouterTache(tacheMere);

        // Appel de la méthode à tester
        Modele.setTacheCourante(tacheMere.getNumTache());
        liste.changerContenuTache("Nouveau contenu");

        // Vérification du résultat
        assertEquals("Nouveau contenu", tacheMere.getContenu());
    }

    @Test
    void modifierDateDebut() {
        // Donnée de test
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        liste.ajouterTache(tacheMere);

        // Appel de la méthode à tester
        Modele.setTacheCourante(tacheMere.getNumTache());
        liste.modifierDateDebut(LocalDate.now());

        // Vérification du résultat
        assertEquals(LocalDate.now(), tacheMere.getDateDebut());
    }

    @Test
    void modifierDateLimite() {
        // Donnée de test
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        liste.ajouterTache(tacheMere);

        // Appel de la méthode à tester
        Modele.setTacheCourante(tacheMere.getNumTache());
        liste.modifierDateLimite(LocalDate.now());

        // Vérification du résultat
        assertEquals(LocalDate.now(), tacheMere.getDateLimite());
    }

    @Test
    void ajouterSousTache() {
        // Donnée de test
        Liste liste = new Liste(1, "Liste");
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        liste.ajouterTache(tacheMere);
        TacheSimple tacheSimple = new TacheSimple(2, "Tâche Simple");

        // Appel de la méthode à tester
        Modele.setTacheCourante(tacheMere.getNumTache());
        liste.ajouterSousTache(tacheSimple);

        // Vérification du résultat
        assertTrue(tacheMere.getSousTaches().contains(tacheSimple));
    }
}