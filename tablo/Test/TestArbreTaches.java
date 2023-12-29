import Tablo.Modele.Tache;
import Tablo.Modele.TacheMere;
import Tablo.Modele.TacheSimple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestArbreTaches {

    private TacheMere tacheMereA;

    /**
     * Méthode qui initialise les tâches avant chaque test.
     */
    @BeforeEach
    public void avantTest() throws IOException {

        Tache tacheSimpleB = new TacheSimple(1, "Tache Simple B");
        Tache tacheSimpleD = new TacheSimple(2, "Tache Simple D");
        Tache tacheSimpleE = new TacheSimple(3, "Tache Simple E");

        Tache tacheMereC = new TacheMere(4, "Contenu de la tâche mère 1");
        tacheMereC.ajouterTache(tacheSimpleD);
        tacheMereC.ajouterTache(tacheSimpleE);

        tacheMereA = new TacheMere(5, "Contenu de la tâche mère 2");
        tacheMereA.ajouterTache(tacheSimpleB);
        tacheMereA.ajouterTache(tacheMereC);
    }

    @Test
    public void testAvecToString() throws IOException {

        String arbre = "-Tache Mere A\n" +
                "| > Tache Simple B\n" +
                "| -Tache Mere C\n" +
                "| | > Tache Simple D\n" +
                "| | > Tache Simple E\n";

        assertEquals(arbre, tacheMereA.toString());
    }

    @Test
    public void testSupprimerTache() throws IOException {

        String arbre = "-Tache Mere A\n" +
                "| > Tache Simple B\n" +
                "| -Tache Mere C\n" +
                "| | > Tache Simple D\n" +
                "| | > Tache Simple E\n";

        assertEquals(arbre, tacheMereA.toString());

        tacheMereA.supprimerTache(tacheMereA.getTaches().get(1));

        String arbre2 = "-Tache Mere A\n" +
                "| > Tache Simple B\n";

        assertEquals(arbre2, tacheMereA.toString());
    }
}
