package Tablo;

import Tablo.Controleur.ControleurAjouterListe;
import Tablo.Controleur.ControleurAjouterTableau;
import Tablo.Controleur.ControleurParametre;
import Tablo.Modele.Modele;
import Tablo.Vue.VueDifferentTableaux;
import Tablo.Vue.VueTableau;
import Tablo.Vue.VueTitreTableau;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Classe MyApplication qui hérite de Application et qui permet de lancer l'application
 */
public class MyApplication extends Application {

    // On crée le modèle
    private Modele modele = new Modele();

    /**
     * Méthode qui permet de lancer l'écran de connexion d'inscription et de mode invité et qui permet de lancer l'application principale
     *
     * @param stage Stage de l'application
     */
    @Override
    public void start(Stage stage) {

        //On crée la Hbox principal
        VBox root = new VBox();

        //On crée la scène
        Scene scene = new Scene(root, 1200, 700); // Fenêtre 600x350 pixels

        //On crée le titre Connexion
        Text titreConnexion = new Text("Connexion");
        titreConnexion.setStyle("-fx-font-weight: bold;");
        titreConnexion.setStyle("-fx-font-size: 40px;");

        //Email à remplir pour se connecter
        HBox EmailConnection = new HBox();

        //On crée le texte Email
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        //On ajoute les composantes graphiques à la Hbox EmailConnection
        EmailConnection.getChildren().addAll(emailField);

        //Mot de passe à remplir pour se connecter
        HBox MdpConnection = new HBox();

        //On crée le texte Mot de passe qui va être caché
        PasswordField mdpField = new PasswordField();
        mdpField.setPromptText("Mot de passe");

        //On ajoute les composantes graphiques à la Hbox MdpConnection
        MdpConnection.getChildren().addAll(mdpField);

        //Bouton connexion pour se connecter sur le stage principal
        Button boutonConnexion = new Button("Connexion");

        //On attribue une action au bouton connexion
        boutonConnexion.setOnMouseClicked(event -> {

            //On récupère les champs de saisie
            String emailRecup = emailField.getText();
            String mdpRecup = mdpField.getText();

            //On vérifie que les champs de saisie ne sont pas vides
            if (emailRecup.isEmpty() || mdpRecup.isEmpty()) {

                //On affiche une erreur si un ou plusieurs des champs de saisie sont vides
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();

                //On limite le champs de saisie du mot de passe pour éviter les injections SQL
            } else if (emailVerif(emailRecup) && mdpVerif(mdpRecup)){

                //TODO : relier la connexion à la base de données

                //lancer l'application
                try {
                    // Appel de startApplication avec le nouveau stage
                    startApplication(stage);
                } catch (IOException e) {
                    // On relance une exception en cas d'erreur
                    throw new RuntimeException(e);
                }
            }
        });

        //Inscription à Tabl'o
        HBox Inscription = new HBox();

        //On crée le texte Inscription
        Text inscription = new Text("Si vous n'avez pas de compte, inscrivez-vous : ");
        inscription.setStyle("-fx-font-weight: bold;");

        //On crée le bouton ici pour l'inscription
        Button boutonInscription = new Button("ici");

        //On attribue une action au bouton inscription
        boutonInscription.setOnMouseClicked(event -> {

            //On crée la boite de dialogue pour l'inscription
            ChoiceDialog<VBox> dialog = new ChoiceDialog<>();
            dialog.setTitle("Inscription");
            dialog.setHeaderText("Inscription");

            //On crée la VBox pour l'inscription
            VBox rootInscription = new VBox();

            //On crée le texte field pour le nom d'utilisateur
            TextField nomUtilisateur = new TextField();
            nomUtilisateur.setPromptText("Nom d'utilisateur");

            //On crée le texte field pour l'email
            TextField email = new TextField();
            email.setPromptText("Email");

            //On crée le texte field pour le mot de passe
            PasswordField mdp = new PasswordField();
            mdp.setPromptText("Mot de passe");

            //On crée le texte field pour la confirmation du mot de passe
            PasswordField confirmationMdp = new PasswordField();
            confirmationMdp.setPromptText("Confirmation du mot de passe");

            //On crée le bouton valider
            Button valider = new Button("Valider");
            valider.setOnMouseClicked(event1 -> {

                //On récupère les champs de saisie
                String nomUtilisateurRecup = nomUtilisateur.getText();
                String emailRecup = email.getText();
                String mdpRecup = mdp.getText();
                String confirmationMdpRecup = confirmationMdp.getText();

                //On crée une variable qui va contenir le mot de passe hasher
                String mdpHash = "";

                //On vérifie que les champs de saisie ne sont pas vides
                if (nomUtilisateurRecup.isEmpty() || emailRecup.isEmpty() || mdpRecup.isEmpty() || confirmationMdpRecup.isEmpty()) {

                    //On affiche une erreur si un ou plusieurs des champs de saisie sont vides
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur");
                    alert.setContentText("Veuillez remplir tous les champs");
                    alert.showAndWait();

                    //On vérifie que le pseudo, l'email et le mot de passe sont valides
                } else if (pseudoVerif(nomUtilisateurRecup) && emailVerif(emailRecup) && mdpVerif(mdpRecup)) {

                    //On vérifie que le mot de passe et la confirmation du mot de passe sont identiques
                    if (!mdpRecup.equals(confirmationMdpRecup)) {

                        //On affiche une erreur si le mot de passe et la confirmation du mot de passe ne sont pas identiques
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur");
                        alert.setContentText("Le mot de passe et la confirmation du mot de passe ne sont pas identiques");
                        alert.showAndWait();

                    } else {

                        //TODO : ajouter le mot de passe hasher à la base de données avec le nom d'utilisateur et l'email

                        //lancer l'application
                        try {
                            // Appel de startApplication avec le nouveau stage
                            startApplication(stage);
                            // On ferme la boite de dialogue
                            dialog.getDialogPane().getScene().getWindow().hide();
                        } catch (IOException e) {
                            // On relance une exception en cas d'erreur
                            throw new RuntimeException(e);
                        }
                    }
                }

            });

            //On ajoute les composantes graphiques à la VBox rootInscription
            rootInscription.getChildren().addAll(nomUtilisateur, email, mdp, confirmationMdp, valider);

            //On ajoute la VBox rootInscription à la boite de dialogue
            dialog.getDialogPane().setContent(rootInscription);

            //On montre la boite de dialogue et on attend la réponse de l'utilisateur
            dialog.showAndWait();

        });

        //On ajoute les composantes graphiques à la Hbox Inscription
        Inscription.getChildren().addAll(inscription, boutonInscription);

        //Mode invité
        HBox ModeInviter = new HBox();

        //On crée le texte Mode invité
        Text modeInviter = new Text("Mode invité : ");
        modeInviter.setStyle("-fx-font-weight: bold;");
        modeInviter.setStyle("-fx-font-size: 20px;");

        //On crée le bouton ici pour le mode invité
        Button boutonInviter = new Button("ici");

        //On attribue une action au bouton inviter
        boutonInviter.setOnMouseClicked(event -> {

            //Ouverture boite de dialogue avec un nom d'utilisateur à rentrer
            TextInputDialog dialog = new TextInputDialog("Nom d'utilisateur");
            dialog.setTitle("Mode invité");
            dialog.setHeaderText("Mode invité");
            dialog.setContentText("Veuillez entrer votre nom d'utilisateur :");

            //On montre la boite de dialogue et on attend la réponse de l'utilisateur
            dialog.showAndWait().ifPresent(nomUtilisateur -> {

                //Si le nom d'utilisateur est vide ou si on clique sur annuler on ne fait rien sinon on lance l'application
                if (!nomUtilisateur.isEmpty() || dialog.getDialogPane().getButtonTypes().get(0).equals(ButtonType.CANCEL)) {

                    //On limite le champs de saisie du nom d'utilisateur
                    if (pseudoVerif(nomUtilisateur)) {

                        //On lance l'application
                        try {
                            // Appel de startApplication avec le stage
                            startApplication(stage);
                        } catch (IOException e) {
                            // On relance une exception en cas d'erreur
                            throw new RuntimeException(e);
                        }
                    }
                }

            });

            //TODO : mettre le nom d'utilisateur dans le modèle


        });

        //On ajoute les composantes graphiques à la Hbox ModeInviter
        ModeInviter.getChildren().addAll(modeInviter, boutonInviter);

        //On met les composantes graphiques dans les Vbox
        root.getChildren().addAll(titreConnexion, EmailConnection, MdpConnection, boutonConnexion, Inscription, ModeInviter);

        //On met les composantes graphiques au centre
        root.setAlignment(javafx.geometry.Pos.CENTER);
        EmailConnection.setAlignment(javafx.geometry.Pos.CENTER);
        MdpConnection.setAlignment(javafx.geometry.Pos.CENTER);
        Inscription.setAlignment(javafx.geometry.Pos.CENTER);
        ModeInviter.setAlignment(javafx.geometry.Pos.CENTER);

        //On place le mode invité tout en bas
        ModeInviter.setTranslateY(100);

        //On met un espacement entre les composantes graphiques
        root.setSpacing(20);
        root.setPadding(new Insets(20, 20, 20, 20));

        //On ajoute la scène au stage
        stage.setScene(scene);
        // Titre de la fenetre
        stage.setTitle("Connexion à Tabl'o");
        // On affiche le stage
        stage.show();


    }

    /**
     * Méthode qui permet de lancer l'application principale
     *
     * @param stage Stage de l'application
     * @throws IOException Exception
     */
    public void startApplication(Stage stage) throws IOException {

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
        ComboBox<String> espaceTravail = new ComboBox<>();

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
        ComboBox<String> templates = new ComboBox<>();
        //On redimensionne le comboBox
        templates.setMinWidth(110);
        templates.setMinHeight(40);

        //On met le titre du comboBox en "Espace de travail"
        templates.setValue("Templates");
        //On met en gras le titre du comboBox
        templates.setStyle("-fx-font-weight: bold;");

        templates.getItems().add("Template 1");

        //Titre du tableau
        VueTitreTableau titreTableau = new VueTitreTableau();
        modele.enregistrerObservateur(titreTableau);

        //On met le titre du tableau au centre
        titreTableau.setTranslateX(300);

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
        text.setX(80);
        text.setY(110);

        StackPane stack = new StackPane();
        // On crée un padding pour décaler le StackPane à droite (on voulait le bouton du compte sur la droite)
        stack.setPadding(new Insets(0, 0, 0, 755));
        stack.getChildren().addAll(circle, text);

        //TODO : mettre le StackPane à droite


        //On ajoute les composantes graphiques à la racine
        top.getChildren().addAll(view, espaceTravail, templates, titreTableau, stack);
        root.setTop(top);

        VBox left = new VBox();

        HBox boutonTableaux = new HBox();

        HBox boutonCollaborateurs = new HBox();

        // On crée les composantes graphiques pour la zone 'left'
        //Tableaux
        Text tableaux = new Text("Tableaux");
        tableaux.setStyle("-fx-font-weight: bold;");
        tableaux.setStyle("-fx-font-size: 20px;");

        Button ajouterTableau = new Button("+");

        ajouterTableau.setOnMouseClicked(new ControleurAjouterTableau(modele));

        boutonTableaux.setSpacing(70);

        boutonTableaux.getChildren().addAll(tableaux, ajouterTableau);

        VueDifferentTableaux vueDifferentTableaux = new VueDifferentTableaux(modele);
        vueDifferentTableaux.setSpacing(20);

        modele.enregistrerObservateur(vueDifferentTableaux);

        //Collaborateurs
        Text collaborateurs = new Text("Collaborateurs");
        collaborateurs.setStyle("-fx-font-weight: bold;");
        collaborateurs.setStyle("-fx-font-size: 20px;");

        Button ajouterCollaborateur = new Button("+");

        HBox parametre = new HBox();

        Text titreParametres = new Text("Paramètres");
        titreParametres.setStyle("-fx-font-weight: bold;");
        titreParametres.setStyle("-fx-font-size: 20px;");

        Button boutonParametre = new Button("+");

        parametre.setSpacing(50);

        parametre.getChildren().addAll(titreParametres, boutonParametre);

        boutonParametre.setOnMouseClicked(new ControleurParametre(modele));


        //On ajoute les composantes graphiques à la racine
//        boutonTableaux.getChildren().addAll(tableaux, ajouterTableau);
        boutonCollaborateurs.getChildren().addAll(collaborateurs, ajouterCollaborateur);

//        boutonTableaux.setSpacing(70);
        boutonCollaborateurs.setSpacing(20);

        left.getChildren().addAll(boutonTableaux, vueDifferentTableaux, boutonCollaborateurs, parametre);

        left.setSpacing(20);

        //On décale les composantes graphiques un peu en bas pour qu'ils ne soient pas collés au bord
        left.setTranslateY(30);

        root.setLeft(left);


        VueTableau tableauCentre = new VueTableau();
        //On enregistre le centre comme observateur du modèle
        modele.enregistrerObservateur(tableauCentre);
        tableauCentre.getAjouterListe().setOnMouseClicked(new ControleurAjouterListe(modele));


        //On ajoute les composantes graphiques à la racine

        root.setCenter(tableauCentre);


        stage.setScene(scene);
        stage.setTitle("Tabl'o"); // Titre de la fenetre
        stage.show(); // On affiche le stage
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Méthode qui permet de limiter la taille du pseudo
     * @param pseudoRecup Pseudo à vérifier
     * @return Vrai si le pseudo est valide, faux sinon
     */
    public boolean pseudoVerif(String pseudoRecup) {
        //On vérifie que le pseudo ne dépasse pas 30 caractères
        if (pseudoRecup.length() <= 30){
            return true;
        }else{
            //On affiche une erreur si le pseudo dépasse 30 caractères
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");
            alert.setContentText("Le pseudo ne doit pas dépasser 30 caractères");
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Méthode qui permet de vérifier les emails
     *
     * @param emailRecup Email à vérifier
     * @return Vrai si l'email est valide, faux sinon
     */
    public boolean emailVerif(String emailRecup) {
        //On vérifie que l'email contient un @ et que l'email ne dépasse pas 50 caractères
        if (emailRecup.contains("@") && emailRecup.length() <= 50)
            return true;
        else {
            //On affiche une erreur si l'email ne contient pas de @
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");
            alert.setContentText("L'email est invalide ou dépasse 50 caractères");
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Méthode qui permet de vérifier le mot de passe et de limiter la taille du mot de passe
     * @param mdpRecup Mot de passe à vérifier
     * @return Vrai si le mot de passe est valide, faux sinon
     */
    public boolean mdpVerif(String mdpRecup){
        //On vérifie que le mot de passe contient au moins 8 caractères et qu'il contient au moins une majuscule, une minuscule et un chiffre
        if (mdpRecup.length() >= 8 && mdpRecup.matches(".*[A-Z].*") && mdpRecup.matches(".*[a-z].*") && mdpRecup.matches(".*[0-9].*"))
            return true;

            //On limite le champs de saisie du mot de passe pour éviter les injections SQL
        else if (mdpRecup.length() > 30 ){
            //On affiche une erreur si le mot de passe dépasse 30 caractères
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");
            alert.setContentText("Le mot de passe ne doit pas dépasser 30 caractères");
            alert.showAndWait();
            return false;
        }
        else {
            //On affiche une erreur si le mot de passe ne respecte pas les conditions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");
            alert.setContentText("Le mot de passe doit contenir au moins 8 caractères, au moins une majuscule, une minuscule et un chiffre");
            alert.showAndWait();
            return false;
        }
    }
}