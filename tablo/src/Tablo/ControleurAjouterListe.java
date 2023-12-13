package Tablo;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurAjouterListe implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurAjouterListe
     * @param m Modele de l'application
     */
    public ControleurAjouterListe(Modele m){
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer l'ajout d'une liste
     * @param mouseEvent
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On récupère le bouton qui a été cliqué
        //Button b = (Button) mouseEvent.getSource();

        //On crée la liste
        Liste l = new Liste("Nouvelle liste");

        //On ajoute la liste au tableau
        modele.ajouterListe(l);
    }
}
