package Tablo.Vue;

import Tablo.Modele.Modele;
import Tablo.Modele.Tache;
import Tablo.Observateur;
import Tablo.Sujet;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

public class VueDiagrammeGantt extends ScrollPane{

    /**
     * Modele de l'application
     */
    private Modele modele;

    /**
     * Grille qui va contenir les taches
     */
    private GridPane gridPane;

    /**
     * HashMap qui contient les coordonnées des fleches qui relient les taches filles à la tache mère
     */
    private HashMap<Tache, ArrayList<Integer>> coordonneesFlechesTacheFilleFinXYDebutXY;

    /**
     * Constructeur de la classe VueDiagrammeGantt
     */
    public VueDiagrammeGantt(Modele m) {
        super();
        this.coordonneesFlechesTacheFilleFinXYDebutXY = new HashMap<>();
        this.modele = m;

        this.setPrefSize(1000, 600);
        //On rend le scrollpane possible a scroll
        this.setPannable(true);

        //On créé la grille
        this.gridPane = new GridPane();


        //On récupere la date de début des taches selectionnées
        LocalDate dateDebut = this.modele.getDateDebutTachesSelectionnees();

        //On récupere la date de fin des taches selectionnées
        LocalDate dateFin = this.modele.getDateFinTachesSelectionnees();

        //Si la date de début ou de fin est nulle, c'est à dire il n'y a aucune tache séléctionnées On ne fais plus rien
        if (dateDebut == null || dateFin == null) {

            //On créé un texte qui affiche qu'il n'y a aucune tache selectionnée
            Text text = new Text("Aucune tache selectionnée");
            text.setStyle("-fx-font-weight: bold;");
            text.setTextAlignment(TextAlignment.CENTER);
            text.setWrappingWidth(1000);

            //On ajoute le texte à la grille
            this.setContent(text);

            return;
        }

        //--On affiche le bandeau du dessus avec les dates (mois par mois)--
        Period duree = dateDebut.until(dateFin);

        //variable qui permet de savoir à quelle place on doit mettre le texte dans la grille
        int placeDansLaGrille = 1;

        //variable qui permet de savoir combien de jours il y a dans le mois précédent on l'initialise au nombre de jours dans le mois de la date de début
        int nombresDeJoursDansLeMois = dateDebut.lengthOfMonth() - dateDebut.getDayOfMonth() + 1;

        //On affiche la date de début au format dd/mm/yyyy
        Text textDateDebut = new Text(dateDebut.getDayOfMonth() + "/" + dateDebut.getMonthValue() + "/" + dateDebut.getYear());
        textDateDebut.setStyle("-fx-font-weight: bold;");
        //On met le texte a droite dans la cellule
        textDateDebut.setTextAlignment(TextAlignment.RIGHT);
        textDateDebut.setWrappingWidth(100);

        //On ajoute le texte à la grille
        this.gridPane.add(textDateDebut, 0, 0);


        //Pour chaque mois
        for (int i = 0; i <= duree.toTotalMonths() + 1; i++) {
            //On récupère le mois courant
            LocalDate currentMonth = dateDebut.plusMonths(i).withDayOfMonth(1);

            //On créé un texte qui contient le mois et l'année
            Text text = new Text(currentMonth.getMonth().toString() + " " + currentMonth.getYear());
            text.setStyle("-fx-font-weight: bold;");
            text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

            //On redimensionne le texte pour qu'il ne dépasse pas 10 * nombresDeJoursDansLeMois
            // Largeur maximale en pixels
            double maxWidth = 10 * nombresDeJoursDansLeMois;

            // Obtient la largeur actuelle du texte
            double textWidth = text.getBoundsInLocal().getWidth();

            // Coupe le texte si la largeur dépasse la limite
            if (textWidth > maxWidth) {
                double ratio = maxWidth / textWidth;
                int newEndIndex = (int) (text.getText().length() * ratio);
                text.setText(text.getText().substring(0, newEndIndex));
            }


            //On crée un rectangle transparent avec les contours noirs qui va contenir le texte et qui est de la taille du nombres de cases du mois
            Rectangle rectangle = new Rectangle(10 * nombresDeJoursDansLeMois, 30, javafx.scene.paint.Color.TRANSPARENT);
            rectangle.setStroke(javafx.scene.paint.Color.BLACK);


            //On met le texte et le rectangle dans un StackPane
            StackPane stack = new StackPane();
            stack.getChildren().addAll(rectangle, text);

            //On ajoute le texte à la grille
            this.gridPane.add(stack, placeDansLaGrille, 0, nombresDeJoursDansLeMois, 1);

            //On ajoute le nombre de jours du mois précédent à la place dans la grille
            placeDansLaGrille += nombresDeJoursDansLeMois;

            //On récupère le nombre de jours dans le mois
            nombresDeJoursDansLeMois = currentMonth.lengthOfMonth();
        }

        ArrayList<Tache> tachesSelectionnees = (ArrayList<Tache>) this.modele.getTachesSelectionnesTriees();

        //On rajoute les taches selectionnées à gauche du diagramme de gantt
        for (int i = 0; i < tachesSelectionnees.size(); i++) {

            //Couleur aléatoire
            Color color = javafx.scene.paint.Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));

            //On récupère la tache courante
            Tache t = tachesSelectionnees.get(i);

            //Liste des sous taches de la tache courante
            ArrayList<Tache> sousTaches = new ArrayList<>();
            //On récupère la liste des sous taches de la tache courante
            if (t.getSousTaches() != null) {

                sousTaches = (ArrayList<Tache>) t.getSousTaches();
            }


            //On récupère le titre de la tache
            Text text = new Text(t.getTitre() + "\n" + t.getDateDebut().getDayOfMonth() + "/" + t.getDateDebut().getMonthValue() + "/" + t.getDateDebut().getYear() + " - " + t.getDateLimite().getDayOfMonth() + "/" + t.getDateLimite().getMonthValue() + "/" + t.getDateLimite().getYear());
            text.setStyle("-fx-font-weight: bold;");
            text.setWrappingWidth(130);
            text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);


            //On rajoute un rectangle autour du texte
            Rectangle rectangle = new Rectangle(130, 30, javafx.scene.paint.Color.TRANSPARENT);
            rectangle.setStroke(javafx.scene.paint.Color.BLACK);

            //On met le texte et le rectangle dans un StackPane
            StackPane stack = new StackPane();
            //On limite la taille du stackPane
            stack.setMaxWidth(130);
            stack.setMaxHeight(30);
            stack.getChildren().addAll(rectangle, text);

            //On ajoute le texte à la grille
            this.gridPane.add(stack, 0, i + 1, 1, 1);

            int nbColonnes = this.gridPane.getColumnCount();

            //On colorie les cases de la grille en une couleur aléatoire quand la case est entre la date de début et de fin de la tache courante dans la boucle . Sachant que chaque case représente un jour
            //Pour chaques cases
            for (int j = 1; j < nbColonnes; j++) {

                //On récupère la date de la case courante
                LocalDate dateCaseCourante = dateDebut.plusDays(j - 1);

                //Si la date de la case courante est entre la date de début et de fin de la tache courante
                if (dateCaseCourante.isAfter(t.getDateDebut().minusDays(1)) && dateCaseCourante.isBefore(t.getDateLimite().plusDays(1))) {

                    //On colorie la case en une couleur aléatoire
                    this.gridPane.add(new javafx.scene.shape.Rectangle(10, 30, color), j, i + 1, 1, 1);
                } else {//Sinon

                    //On colorie la case en blanc
                    this.gridPane.add(new javafx.scene.shape.Rectangle(10, 30, Color.TRANSPARENT), j, i + 1, 1, 1);
                }


                //----------------------Les fleches qui relient les taches filles à la tache mère ------------------

                //Si la date de la case courante est égale à la date de début de la tache courante et que la tache courante est dans la liste des coordonnées des fleches
                if (dateCaseCourante.isEqual(t.getDateDebut()) && this.coordonneesFlechesTacheFilleFinXYDebutXY.containsKey(t)) {

                    ArrayList<Integer> coordonneesFinTacheMere = this.coordonneesFlechesTacheFilleFinXYDebutXY.get(t);

                    System.out.println(j * 10 - coordonneesFinTacheMere.get(0) * 10 + 10);
                    System.out.println((i + 1) * 30 - coordonneesFinTacheMere.get(1) * 30 + 30);

                    Line line = new Line(5, 15, j * 10 - coordonneesFinTacheMere.get(0) * 10 + 10, (i + 1) * 30 - coordonneesFinTacheMere.get(1) * 30 + 30);
                    line.setStrokeWidth(2);
                    line.setStroke(Color.BLACK);

                    this.gridPane.add(line, coordonneesFinTacheMere.get(0), coordonneesFinTacheMere.get(1), nbColonnes - coordonneesFinTacheMere.get(0), this.gridPane.getRowCount() - coordonneesFinTacheMere.get(1));
                }
                //Sinon si la date de la case Courante est égale a la date limite de la tache courante
                if (dateCaseCourante.isEqual(t.getDateLimite())) {

                    for (Tache sousTache : sousTaches) {

                        ArrayList<Integer> coordonneesFinTacheMere = new ArrayList<>();
                        coordonneesFinTacheMere.add(j);

                        coordonneesFinTacheMere.add((i + 1));

                        this.coordonneesFlechesTacheFilleFinXYDebutXY.put(sousTache, coordonneesFinTacheMere);
                    }
                }
            }


        }

        this.setContent(this.gridPane);
    }
}
