package Tablo;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Classe VueParametre qui permet de gérer les paramètres de l'application
 */
public class VueParametre extends HBox implements Observateur{

    //On créé un attribut qui va contenir le titre des paramètres
    private Text titreParametres;

    //On créé un attribut qui va contenir le bouton pour accéder aux paramètres
    private Button BoutonParametre;

    /**
     * Constructeur de la classe VueParametre
     */
    public VueParametre(){
        super();

        this.setSpacing(50);

        this.titreParametres = new Text("Paramètres");
        this.titreParametres.setStyle("-fx-font-weight: bold;");
        this.titreParametres.setStyle("-fx-font-size: 20px;");

        this.BoutonParametre = new Button("+");

        this.getChildren().addAll(titreParametres, BoutonParametre);
    }


    @Override
    public void actualiser(Sujet s) {

    }

    /**
     * Méthode qui permet de récupérer le bouton pour accéder aux paramètres
     * @return Le bouton pour accéder aux paramètres
     */
    public Button getAllerParametre(){
        return this.BoutonParametre;
    }
}
