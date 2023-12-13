package Tablo;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControleurCreationTache implements EventHandler<MouseEvent> {

    private Modele modele;

    public ControleurCreationTache(Modele m){
        this.modele = m;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {

        Button b = (Button) mouseEvent.getSource();

        //On accède à la Vbox qui contient le bouton
        VBox v = (VBox) b.getParent();

        //On récupere l'id de la liste
        int idListe = Integer.parseInt(v.getId());
        modele.changerListeCourante(idListe);

        String titre = "Nouvelle tâche";
        String contenu = "Contenu de la tâche";

        Tache tache = new TacheMere(titre, contenu);
        modele.ajouterTache(tache);
    }
}
