package Tablo;

import Tablo.Controleur.ControleurAjouterListe;
import Tablo.Controleur.ControleurParametre;
import Tablo.Modele.Modele;
import Tablo.Vue.VueParametre;
import Tablo.Vue.VueTableau;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MyApplication extends Application {

    // On crée le modèle
    private Modele modele = new Modele();

    @Override
    public void start(Stage stage) throws IOException {

        BorderPane root = new BorderPane();
        // On crée la scène
        Scene scene = new Scene(root, 1200, 700); // Fenêtre 1200x700 pixels

        // On crée les composantes graphiques
        HBox top = new HBox();


        // On crée les composantes graphiques pour la zone 'top'
        Image logo = new Image("Logo_Tablo.png");
        ImageView view = new ImageView(logo);
        //On redimensionne l'image
        view.setFitHeight(40);
        view.setFitWidth(40);

        //Espace de travail comboBox
        ComboBox<String> espaceTravail = new ComboBox<String>();

        //On redimensionne le comboBox
        espaceTravail.setMinWidth(150);
        espaceTravail.setMinHeight(40);

        //On met le titre du comboBox en "Espace de travail"
        espaceTravail.setValue("Espaces de travail");
        //On met en gras le titre du comboBox
        espaceTravail.setStyle("-fx-font-weight: bold;");

        //On ajoute l'items du comboBox
        espaceTravail.getItems().add("Espace de travail 1");


        //Templates comboBox
        ComboBox<String> templates = new ComboBox<String>();
        //On redimensionne le comboBox
        templates.setMinWidth(110);
        templates.setMinHeight(40);

        //On met le titre du comboBox en "Espace de travail"
        templates.setValue("Templates");
        //On met en gras le titre du comboBox
        templates.setStyle("-fx-font-weight: bold;");

        templates.getItems().add("Template 1");


        //Circle
        Circle circle = new Circle();
        circle.setCenterX(100.0f);
        circle.setCenterY(100.0f);
        circle.setRadius(20.0f);
        circle.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 2;");

        //On ajoute un texte dans le cercle
        Text text = new Text("A");
        text.setStyle("-fx-font-weight: bold;");
        text.setStyle("-fx-font-size: 30px;");
        text.setX(90);
        text.setY(110);

        StackPane stack = new StackPane();
        // On crée un padding pour décaler le StackPane à droite (on voulait le bouton du compte sur la droite)
        stack.setPadding(new Insets(0, 0, 0, 855));
        stack.getChildren().addAll(circle, text);

        //TODO : mettre le StackPane à droite

        //On ajoute les composantes graphiques à la racine
        top.getChildren().addAll(view, espaceTravail, templates, stack);
        root.setTop(top);

        VBox left = new VBox();

        HBox boutonTableaux = new HBox();

        HBox boutonCollaborateurs = new HBox();

        HBox boutonParametres = new HBox();

        // On crée les composantes graphiques pour la zone 'left'
        //Tableaux
        Text tableaux = new Text("Tableaux");
        tableaux.setStyle("-fx-font-weight: bold;");
        tableaux.setStyle("-fx-font-size: 20px;");

        Button ajouterTableau = new Button("+");

        //Collaborateurs
        Text collaborateurs = new Text("Collaborateurs");
        collaborateurs.setStyle("-fx-font-weight: bold;");
        collaborateurs.setStyle("-fx-font-size: 20px;");

        Button ajouterCollaborateur = new Button("+");

        //VueParametre qui permet d'accéder aux paramètres
        VueParametre parametres = new VueParametre();

        //On ajoute un évènement au bouton qui permet d'accéder aux paramètres à l'aide du controleur
        parametres.getAllerParametre().setOnMouseClicked(new ControleurParametre(modele));


        //On ajoute les composantes graphiques à la racine
        boutonTableaux.getChildren().addAll(tableaux, ajouterTableau);
        boutonCollaborateurs.getChildren().addAll(collaborateurs, ajouterCollaborateur);

        boutonTableaux.setSpacing(70);
        boutonCollaborateurs.setSpacing(20);

        left.getChildren().addAll(boutonTableaux, boutonCollaborateurs, parametres);

        left.setSpacing(20);

        //On décale les composantes graphiques un peu en bas pour qu'ils ne soient pas collés au bord
        left.setTranslateY(30);

        root.setLeft(left);


        VueTableau tableauCentre = new VueTableau(0, "Tableau 1");
        tableauCentre.getAjouterListe().setOnMouseClicked(new ControleurAjouterListe(modele));
        //On enregistre le centre comme observateur du modèle
        modele.enregistrerObservateur(tableauCentre);


        //On ajoute les composantes graphiques à la racine

        root.setCenter(tableauCentre);


        stage.setScene(scene);
        stage.setTitle("Tabl'o"); // Titre de la fenetre
        stage.show(); // On affiche le stage
    }

    public static void main(String[] args) {
        launch();
    }
}