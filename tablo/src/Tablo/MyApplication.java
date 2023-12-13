package Tablo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        // On crée la scène
        Scene scene = new Scene(root, 1200, 700); // Fenêtre 1200x700 pixels

        // On crée les composantes graphiques
        BorderPane racine = new BorderPane();
        HBox top = new HBox();

        // On crée les composantes graphiques pour la zone 'top'
        Image logo = new Image("Logo_Tablo.png");
        ImageView view = new ImageView(logo);
        Button boutonlogo = new Button();
        boutonlogo.setGraphic(view);
        ComboBox espaceTravail = new ComboBox();
        ComboBox templates = new ComboBox();

        top.getChildren().addAll(boutonlogo, espaceTravail, templates);
        racine.setTop(top);

        VBox left = new VBox();
        HBox center = new HBox();


        root.getChildren().addAll(racine);
        stage.setScene(scene);
        stage.setTitle("Tabl'o"); // Titre de la fenetre
        stage.show(); // On affiche le stage
    }

    public static void main(String[] args) {
        launch();
    }
}