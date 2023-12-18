package Tablo.Controleur;


import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Modele.TacheMere;
import Tablo.Vue.VueListe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurCreationTache implements EventHandler<ActionEvent> {

    private Modele modele;

    public ControleurCreationTache(Modele m){
        this.modele = m;
    }


    @Override
    public void handle(ActionEvent actionEvent) {

        Button b = (Button) actionEvent.getSource();

        //On accède à la Vbox qui contient le bouton
        VueListe v = (VueListe) b.getParent();

        //On récupere l'id de la liste
        int idListe = v.getNumListe();
        System.out.println(idListe);
        modele.changerListeCourante(idListe);

        String titre = "Nouvelle tâche";
        String contenu = "Contenu de la tâche";

        Tache tache = new TacheMere(titre, contenu);
        modele.ajouterTache(tache);

        //On gère l'exception ConcurrentModificationException en notifiant les observateurs
        this.modele.notifierObservateurs();

    }
}