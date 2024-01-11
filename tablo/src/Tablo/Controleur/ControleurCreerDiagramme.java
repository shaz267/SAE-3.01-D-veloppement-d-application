package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueDiagrammeGantt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControleurCreerDiagramme implements EventHandler<ActionEvent> {


    /**
     * Modele de l'application
     */
    private final Modele modele;

    /**
     * Constructeur de la classe ControleurCréerDiagramme
     * @param m Modele de l'application
     */
    public  ControleurCreerDiagramme(Modele m) {
        this.modele = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        // On crée la fenêtre
        Stage popupStage  = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Diagramme de Gantt");

        // On crée la vue du diagramme de Gantt
        VueDiagrammeGantt vueDiagrammeGantt = new VueDiagrammeGantt(this.modele);

        // On ajoute la vue à la fenêtre
        popupStage.setScene(new Scene(vueDiagrammeGantt, 1000, 600));

        popupStage.showAndWait();
    }
}
