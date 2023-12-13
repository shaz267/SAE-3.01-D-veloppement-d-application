package Tablo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe permettant d'enregistrer les logs dans un fichier
 */
public class Loggeur {

    /**
     * Enregistre un message dans un fichier
     * @param message Message à enregistrer
     */
    public static void enregistrer(String message) throws IOException {

        // Création d'un BufferedWriter qui va écrire dans le fichier log.txt
        BufferedWriter writer = new BufferedWriter(new FileWriter("../log.txt"));

        // Écriture du message dans le fichier
        writer.write(message);

        // Fermeture du fichier
        writer.close();
    }
}
