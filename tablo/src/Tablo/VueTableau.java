package Tablo;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VueTableau extends ScrollPane implements Observateur {

    private int numTableau;
    private Button ajouterListe;
    private HBox listeContainer;

    public VueTableau(int numTableau, String titre) {
        super();

        this.numTableau = numTableau;

        ajouterListe = new Button("Ajouter une liste");
        ajouterListe.setStyle("-fx-font-weight: bold;");
        ajouterListe.setStyle("-fx-font-size: 20px;");
        ajouterListe.setStyle("-fx-background-color: #f8b9a7;");
        ajouterListe.setPrefSize(200, 50);

        listeContainer = new HBox();
        listeContainer.setSpacing(10);
        listeContainer.setFillHeight(true);

        HBox container = new HBox();
        container.getChildren().addAll(listeContainer, ajouterListe);

        this.setContent(container);

        this.numTableau = Modele.nombresTableaux;
        Modele.nombresTableaux++;
    }

    @Override
    public void actualiser(Sujet s) {
        Modele m = (Modele) s;
        Tableau t = m.getTableaux().get(this.numTableau);

        listeContainer.getChildren().clear();

        for (Liste liste : t.getListes()) {
            VueListe vueListe = new VueListe(liste.getId(), liste.getTitre());
            vueListe.setOnMouseClicked(new ControleurListeCliquee(m));
            listeContainer.getChildren().add(vueListe);
        }
    }

    public Button getAjouterListe() {
        return ajouterListe;
    }
}
