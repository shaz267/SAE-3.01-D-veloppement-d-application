import Tablo.Loggeur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test de la classe Loggeur
 */
public class TestLoggeur {

    @BeforeEach
    public void AvantTest() throws IOException {
        // Ecrire un message dans le fichier log.txt
        Loggeur.enregistrer("test de la classe Loggeur");
    }

    /**
     * Teste la méthode enregistrer de la classe Loggeur
     */
    @Test
    public void testEnregistrer() throws IOException {
        // Récupération de la date actuelle
        Date date = new Date();

        // Compteur de lignes du fichier
        int compteur = 0;

        // Création d'un BufferedReader qui va lire le fichier log.txt
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));

        // Lecture de la première ligne du fichier
        String line = reader.readLine();

        // Comptage du nombre de lignes du fichier
        while (reader.readLine() != null) {
            compteur++;
        }

        // Affection de la dernière ligne du fichier à la variable line
        line = Files.readAllLines(new File("log.txt").toPath()).get(compteur);

        // Vérification de la dernière ligne du fichier
        assertEquals(date.getHours() + ":" + date.getMinutes() + "," + date.getSeconds() + "s" + " -- " + "test de la classe Loggeur", line);

    }

}
