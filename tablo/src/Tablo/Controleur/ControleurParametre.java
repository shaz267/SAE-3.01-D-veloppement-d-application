package Tablo.Controleur;

import Tablo.Loggeur;
import Tablo.Modele.Modele;
import Tablo.Vue.VueTachesArchivees;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Classe ControleurParametre qui permet de gérer les paramètres de l'application
 */
public class ControleurParametre implements EventHandler<MouseEvent> {

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Constructeur de la classe ControleurParametre
     * @param m Modele de l'application
     */
    public ControleurParametre(Modele m){
        this.modele = m;
    }

    /**
     * Méthode qui permet de gérer les paramètres de l'application
     * @param mouseEvent Evènement qui permet de gérer les paramètres de l'application
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        //On créé une nouvelle VBox qui va contenir les paramètres
        VBox VBoxParametres = new VBox();
        VBoxParametres.setSpacing(20);

        //On créé une nouvelle HBox qui va contenir le bouton pour changer le mode sombre et le texte qui va avec
        HBox HBoxModeSombre = new HBox();

        //On créé le texte qui va être affiché
        Text titreSombre = new Text("Mode sombre : ");
        titreSombre.setStyle("-fx-font-weight: bold;");
        titreSombre.setStyle("-fx-font-size: 20px;");

        //On créé le bouton qui va permettre de changer le mode sombre
        RadioButton ModeSombre = new RadioButton();
        ModeSombre.setStyle("-fx-font-weight: bold;");
        ModeSombre.setStyle("-fx-font-size: 20px;");

        //On ajoute les composantes graphiques à la HBoxModeSombre
        HBoxModeSombre.setSpacing(20);
        HBoxModeSombre.getChildren().addAll(titreSombre, ModeSombre);

        //On créé une séparation entre les deux zones
        Separator separator = new Separator();

        //On créé une nouvelle VBox qui va contenir les logs
        VBox VBoxLog = new VBox();

        //On créé le texte qui va être affiché
        Text logText = new Text("Logs de l'application : ");
        logText.setStyle("-fx-font-size: 20px;");

        //On créé le TextArea qui va contenir les logs
        TextArea log = new TextArea();
        log.setText(Loggeur.afficherLog());

        //On ajoute les composantes graphiques à la VBoxLog
        VBoxLog.getChildren().addAll(logText, log);

        //On créé une séparation entre les deux zones
        Separator separator2 = new Separator();

        Text titreTachesArchivees = new Text("Tâches archivées : ");
        titreTachesArchivees.setStyle("-fx-font-size: 20px;");

        //On ajoute la liste des taches archivées
        VueTachesArchivees vueTachesArchivees = new VueTachesArchivees(modele);

        //On ajoute les composantes graphiques à la VBoxParametres
        VBoxParametres.getChildren().addAll(HBoxModeSombre,separator, VBoxLog, separator2, titreTachesArchivees, vueTachesArchivees);

        //On créé la boîte de dialogue
        ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
        dialog.setTitle("Paramètres");
        dialog.setHeaderText("Paramètres de l'application");

        //On ajoute les composantes graphiques à la boîte de dialogue
        dialog.getDialogPane().setContent(VBoxParametres);

        //On affiche la boîte de dialogue
        dialog.showAndWait();

    }
}
