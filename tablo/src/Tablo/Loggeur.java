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
    public static void enregistrer(String message){

        try{
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
        catch (IOException e){

            System.out.println("Erreur lors de l'écriture du fichier log.txt");
        }

    }

    /**
     * Affiche le contenu du fichier log.txt du plus récent au plus ancien
     * @return Le contenu du fichier log.txt
     */
    public static String afficherLog() {
    	StringBuilder log = new StringBuilder();
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
    		String line = reader.readLine();
    		while (line != null) {
    			log.insert(0, line + "\n");
    			line = reader.readLine();
    		}
    		reader.close();
    	}
    	catch (IOException e) {
    		System.out.println("Erreur lors de la lecture du fichier log.txt");
    	}
    	return log.toString();
    }
}
