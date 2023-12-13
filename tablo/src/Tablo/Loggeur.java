package Tablo;

import java.io.*;
import java.util.Date;

/**
 * Classe permettant d'enregistrer les logs dans un fichier
 */
public class Loggeur {

    /**
     * Enregistre un message dans un fichier
     * @param message Message à enregistrer
     */
    public static void enregistrer(String message) throws IOException {

        //Creation d'un BufferedReader qui va lire le fichier log.txt
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));

        //Creation d'un BufferedWriter qui va écrire dans le fichier log.txt
        BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true));

        // Récupération de la date actuelle
        Date date = new Date();

        // Lecture de la première ligne du fichier
        String line = reader.readLine();

        // Si la première ligne est null, on écrit le message sur la première ligne
        if (line == null) {
            writer.write("");

            // Sinon on écrit le message sur une nouvelle ligne
        }else {
            writer.write("\n");
        }

        // Écriture du message dans le fichier
        writer.write(date.getHours() + ":" + date.getMinutes()+ "," + date.getSeconds() + "s" + " -- " + message);

        // Fermeture du fichier
        writer.close();
    }
}
