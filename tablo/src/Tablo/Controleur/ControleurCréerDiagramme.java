package Tablo.Controleur;

import Tablo.Modele.Modele;
import Tablo.Vue.VueDiagrammeGantt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControleurCréerDiagramme implements EventHandler<ActionEvent> {


    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurCréerDiagramme
     * @param m
     */
    public ControleurCréerDiagramme(Modele m) {
        this.modele = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        Stage popupStage  = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Diagramme de Gantt");

        // On crée la vue du diagramme de Gantt
        VueDiagrammeGantt vueDiagrammeGantt = new VueDiagrammeGantt(this.modele);

        popupStage.setScene(new Scene(vueDiagrammeGantt, 1000, 600));

        popupStage.showAndWait();
    }
}
