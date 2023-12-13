import Tablo.Loggeur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test de la classe Loggeur
 */
public class TestLoggeur {

    @BeforeEach
    public void AvantTest() throws IOException {
        // Ecrire un message dans le fichier log.txt
        Loggeur.enregistrer("test");
    }

    /**
     * Teste la méthode enregistrer de la classe Loggeur
     */
    @Test
    public void testEnregistrer() throws IOException {

        // Création d'un BufferedReader qui va lire le fichier log.txt
        BufferedReader reader = new BufferedReader(new FileReader("../log.txt"));

        // Verification que le message a bien été écrit dans le fichier
        assertEquals("test", reader.readLine());

    }

    @AfterEach
    public void ApresTest() throws IOException {
        // Supprimer le fichier log.txt
        File file = new File("../log.txt");
        file.delete();
    }

}
