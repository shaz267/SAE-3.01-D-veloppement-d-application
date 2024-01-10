package Tablo.Vue;

import Tablo.Controleur.ControleurAjouterTableau;
import Tablo.Controleur.ControleurCreerDiagramme;
import Tablo.Controleur.ControleurParametre;
import Tablo.Modele.Modele;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VueVboxLeft extends VBox implements Observateur {

    public VueVboxLeft(Modele modele) {

        HBox boutonTableaux = new HBox();

        // On crée les composantes graphiques pour la zone 'left'
        //Tableaux
        Text tableaux = new Text("Tableaux");
        tableaux.setStyle("-fx-font-weight: bold;-fx-font-family: 'Roboto Light';-fx-font-size: 20px;");

        Button ajouterTableau = new Button("\uD83D\uDCC5");
        ajouterTableau.setStyle("-fx-background-color: #C0C0C0;"); // Couleur du bouton
        ajouterTableau.setOnMouseEntered(e -> ajouterTableau.setStyle("-fx-background-color: #808080;")); // Changement de couleur au survol
        ajouterTableau.setOnMouseExited(e -> ajouterTableau.setStyle("-fx-background-color: #C0C0C0;"));  // Changement de couleur à la sortie du survol
        ajouterTableau.setOnMousePressed(e -> ajouterTableau.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;")); // Changement de couleur au clic


        ajouterTableau.setOnMouseClicked(new ControleurAjouterTableau(modele));

        boutonTableaux.setSpacing(70);

        boutonTableaux.getChildren().addAll(tableaux, ajouterTableau);

        VueDifferentTableaux vueDifferentTableaux = new VueDifferentTableaux(modele);
        vueDifferentTableaux.setSpacing(10);
        vueDifferentTableaux.setAlignment(javafx.geometry.Pos.CENTER);

        modele.enregistrerObservateur(vueDifferentTableaux);

        HBox parametre = new HBox();

        Text titreParametres = new Text("Paramètres");
        titreParametres.setStyle("-fx-font-weight: bold;-fx-font-size: 20px; -fx-font-family: 'Roboto Light'");

        Button boutonParametre = new Button("⚙");
        boutonParametre.setStyle("-fx-background-color: #C0C0C0;"); // Couleur du bouton
        boutonParametre.setOnMouseEntered(e -> boutonParametre.setStyle("-fx-background-color: #808080;")); // Changement de couleur au survol
        boutonParametre.setOnMouseExited(e -> boutonParametre.setStyle("-fx-background-color: #C0C0C0;"));  // Changement de couleur à la sortie du survol
        boutonParametre.setOnMousePressed(e -> boutonParametre.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;")); // Changement de couleur au clic

        parametre.setSpacing(50);

        parametre.getChildren().addAll(titreParametres, boutonParametre);

        boutonParametre.setOnMouseClicked(new ControleurParametre(modele));

        //Diagramme de Gantt
        Button boutonDiagGantt = new Button("Diagramme de Gantt");
        boutonDiagGantt.setStyle("-fx-background-color: #C0C0C0;-fx-font-size: 15px;"); // Couleur du bouton
        boutonDiagGantt.setOnMouseEntered(e -> boutonDiagGantt.setStyle("-fx-background-color: #808080;-fx-font-size: 15px;")); // Changement de couleur au survol
        boutonDiagGantt.setOnMouseExited(e -> boutonDiagGantt.setStyle("-fx-background-color: #C0C0C0;-fx-font-size: 15px;"));  // Changement de couleur à la sortie du survol
        boutonDiagGantt.setOnMousePressed(e -> boutonDiagGantt.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;-fx-font-size: 15px;")); // Changement de couleur au clic

        //On ajoute le controleur pour créer un diagramme de Gantt
        boutonDiagGantt.setOnAction(new ControleurCreerDiagramme(modele));


        this.getChildren().addAll(boutonTableaux, vueDifferentTableaux, parametre, boutonDiagGantt);
        this.setPadding(new Insets(20));
        this.setSpacing(20);

    }

    @Override
    public void actualiser(Sujet s) {

        //On change le style de la vue en fonction du mode sombre
        if (Modele.getModeSombre()) {

            //On change le fond de la vue en sombre si le mode sombre est activé
            this.setStyle("-fx-background-color: #2b2b2b;");

            //On change la couleur des Labels
            for (int i = 0; i < this.getChildren().size(); i++) {

                //Si c'est une HBox on change la couleur des Text
                if (this.getChildren().get(i) instanceof HBox) {

                    //On récupère la HBox
                    HBox hBox = (HBox) this.getChildren().get(i);

                    //On change la couleur des Text
                    for (int j = 0; j < hBox.getChildren().size(); j++) {

                        //Si c'est un Text on change la couleur
                        if (hBox.getChildren().get(j) instanceof Text) {

                            //On récupère le Text
                            Text text = (Text) hBox.getChildren().get(j);

                            //On change la couleur du Text en clair
                            text.setStyle("-fx-font-weight: bold;-fx-font-family: 'Roboto Light';-fx-font-size: 20px; -fx-fill: #ffffff;");
                        }
                    }
                }
            }
        } else {

            //On change le fond de la vue en clair si le mode sombre est désactivé
            this.setStyle("-fx-background-color: #ffffff;");

            //On change la couleur des Labels
            for (int i = 0; i < this.getChildren().size(); i++) {

                //Si c'est une HBox on change la couleur des Text
                if (this.getChildren().get(i) instanceof HBox) {

                    //On récupère la HBox
                    HBox hBox = (HBox) this.getChildren().get(i);

                    //On change la couleur des Text
                    for (int j = 0; j < hBox.getChildren().size(); j++) {

                        //Si c'est un Text on change la couleur
                        if (hBox.getChildren().get(j) instanceof Text) {

                            //On récupère le Text
                            Text text = (Text) hBox.getChildren().get(j);

                            //On change la couleur du Text en sombre
                            text.setStyle("-fx-font-weight: bold;-fx-font-family: 'Roboto Light';-fx-font-size: 20px; -fx-fill: #000000;");
                        }
                    }
                }
            }
        }
    }

}
