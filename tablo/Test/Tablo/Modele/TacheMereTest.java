package Tablo.Modele;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TacheMereTest {

    @Test
    void ajouterTache()
    {
        // Données de test
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        TacheSimple tacheSimple = new TacheSimple(2, "Tâche Simple");

        // Appel de la méthode à tester
        boolean resultat = tacheMere.ajouterTache(tacheSimple);

        // Vérification du résultat
        assertTrue(resultat);
        assertTrue(tacheMere.getSousTaches().contains(tacheSimple));
    }

    @Test
    void supprimerTache() {
        // Données de test
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        TacheSimple tacheSimple = new TacheSimple(2, "Tâche Simple");

        // Appel de la méthode à tester
        boolean resultat = tacheMere.supprimerTache(tacheSimple);

        // Vérification du résultat
        assertFalse(tacheMere.getSousTaches().contains(tacheSimple));
    }

    @Test
    void modifierDateLimite() {
        // Donné de test
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");

        // Appel de la méthode à tester
        tacheMere.modifierDateLimite(LocalDate.now());

        // Vérification du résultat
        assertEquals(LocalDate.now(), tacheMere.getDateLimite());
    }

    @Test
    void supprimerSousTache() {
        // Donnée de test
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        TacheSimple tacheSimple = new TacheSimple(2, "Tâche Simple");
        TacheSimple tacheSimple2 = new TacheSimple(3, "Tâche Simple 2");
        tacheMere.ajouterTache(tacheSimple);
        tacheMere.ajouterTache(tacheSimple2);

        // Appel de la méthode à tester
        boolean resultat = tacheMere.supprimerSousTache(2);

        // Vérification du résultat
        assertTrue(resultat);
        assertFalse(tacheMere.getSousTaches().contains(tacheSimple));
    }


    @Test
    void getSousTaches() {
        // Donnée de test
        TacheMere tacheMere = new TacheMere(1, "Tâche Mère");
        TacheSimple tacheSimple = new TacheSimple(2, "Tâche Simple");
        TacheSimple tacheSimple2 = new TacheSimple(3, "Tâche Simple 2");
        TacheSimple tacheSimple3 = new TacheSimple(4, "Tâche Simple 3");
        tacheMere.ajouterTache(tacheSimple);
        tacheMere.ajouterTache(tacheSimple2);
        tacheMere.ajouterTache(tacheSimple3);

        // Appel de la méthode à tester
        int resultat = tacheMere.getSousTaches().size();

        // Vérification du résultat
        assertEquals(3, resultat);

    }
}