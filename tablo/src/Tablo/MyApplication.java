package Tablo;

import Tablo.Controleur.ControleurAjouterListe;
import Tablo.Modele.*;
import Tablo.Vue.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe MyApplication qui hérite de Application et qui permet de lancer l'application
 */
public class MyApplication extends Application {

    // On crée le modèle
    private static Modele modele = new Modele();

    /**
     * Méthode qui permet de lancer l'écran de connexion d'inscription et de mode invité et qui permet de lancer l'application principale
     *
     * @param stage Stage de l'application
     */
    @Override
    public void start(Stage stage) {

        // On crée les composantes graphiques qui récupèrent le logo
        Image logo = new Image("Logo_Tablo.png");
        ImageView view = new ImageView(logo);

        //On redimensionne l'image
        view.setFitHeight(40);
        view.setFitWidth(40);


        //On utilise cette image pour l'image de l'application
        stage.getIcons().add(logo);

        //On crée la Hbox principal
        VBox root = new VBox();

        //On crée la scène
        Scene scene = new Scene(root, 1200, 700); // Fenêtre 1200x700 pixels

        //On crée le titre Connexion
        Text titreConnexion = new Text("Connexion");
        titreConnexion.setStyle("-fx-font-weight: bold;-fx-font-size: 40px;-fx-font-family: 'Roboto Light';-fx-underline: true;");


        //Email à remplir pour se connecter
        HBox EmailConnection = new HBox();

        //On crée le texte Email
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

        //On ajoute les composantes graphiques à la Hbox EmailConnection
        EmailConnection.getChildren().addAll(emailField);

        //Mot de passe à remplir pour se connecter
        HBox MdpConnection = new HBox();

        //On crée le texte Mot de passe qui va être caché
        PasswordField mdpField = new PasswordField();
        mdpField.setPromptText("Mot de passe");
        mdpField.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

        //On ajoute les composantes graphiques à la Hbox MdpConnection
        MdpConnection.getChildren().addAll(mdpField);

        //Bouton connexion pour se connecter sur le stage principal
        Button boutonConnexion = new Button("Connexion");
        boutonConnexion.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';"); // Couleur du bouton
        boutonConnexion.setOnMouseEntered(e -> boutonConnexion.setStyle("-fx-background-color: #808080;-fx-font-family: 'Roboto Light';")); // Changement de couleur au survol
        boutonConnexion.setOnMouseExited(e -> boutonConnexion.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';"));  // Changement de couleur à la sortie du survol
        boutonConnexion.setOnMousePressed(e -> boutonConnexion.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';")); // Changement de couleur au clic


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
            } else if (emailVerif(emailRecup) && mdpVerif(mdpRecup)) {

                // On vérifie que le mail et le mdp correspondent à un utilisateur de la base
                // On commence par sélectionner l'utilisateur de la base qui correspond au mail entré pour la connexion
                try {
                    Modele.setUser(Utilisateur.findByEmail(emailRecup));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // On vérifie par la suite son mdp
                // On hache le mdp entré
                String mdpRecupHashe = Utilisateur.passwordHash(mdpRecup);

                // On récupère le mdp de l'utilisateur de la base
                String mdpUser = "";
                try {
                    if(Modele.user == null){
                        // On affiche une erreur si l'utilisateur n'existe pas
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur");
                        alert.setContentText("L'utilisateur n'existe pas");
                        alert.showAndWait();
                    }
                    else
                        mdpUser += Modele.user.getMdp();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // On vérifie que le mdp entré correspond bien au mdp de l'utilisateur de la base
                if(mdpUser.equals(mdpRecupHashe)){
                    //lancer l'application
                    try {
                        // Appel de startApplication avec le nouveau stage
                        startApplication(stage);
                    } catch (IOException e) {
                        // On relance une exception en cas d'erreur
                        throw new RuntimeException(e);
                    }
                    // On importe les données correspondant au user connecte depuis la bd
                    try {
                        MyApplication.importerDonnees();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    // On affiche une erreur si le mdp entré ne correspond pas au mdp de l'utilisateur de la base
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur");
                    alert.setContentText("Le mot de passe est incorrect");
                    alert.showAndWait();
                }
            }
        });

        //Inscription à Tabl'o
        HBox Inscription = new HBox();

        //On crée le texte Inscription
        Text inscription = new Text("Si vous n'avez pas de compte, inscrivez-vous : ");
        inscription.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 13px");

        //On crée le bouton ici pour l'inscription
        Button boutonInscription = new Button("ici");
        boutonInscription.setStyle("-fx-background-color: #C0C0C0;"); // Couleur du bouton
        boutonInscription.setOnMouseEntered(e -> boutonInscription.setStyle("-fx-background-color: #808080;")); // Changement de couleur au survol
        boutonInscription.setOnMouseExited(e -> boutonInscription.setStyle("-fx-background-color: #C0C0C0;"));  // Changement de couleur à la sortie du survol
        boutonInscription.setOnMousePressed(e -> boutonInscription.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;")); // Changement de couleur au clic


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
            nomUtilisateur.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

            //On crée le texte field pour l'email
            TextField email = new TextField();
            email.setPromptText("Email");
            email.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

            //On crée le texte field pour le mot de passe
            PasswordField mdp = new PasswordField();
            mdp.setPromptText("Mot de passe");
            mdp.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

            //On crée le texte field pour la confirmation du mot de passe
            PasswordField confirmationMdp = new PasswordField();
            confirmationMdp.setPromptText("Confirmation du mot de passe");
            confirmationMdp.setStyle("-fx-border-radius: 510px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-background-color: transparent;");

            //On crée le bouton valider
            Button valider = new Button("Valider");
            valider.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';-fx-font-size: 15px;"); // Couleur du bouton
            valider.setOnMouseEntered(e -> valider.setStyle("-fx-background-color: #808080;-fx-font-family: 'Roboto Light';-fx-font-size: 15px;")); // Changement de couleur au survol
            valider.setOnMouseExited(e -> valider.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';-fx-font-size: 15px;"));  // Changement de couleur à la sortie du survol
            valider.setOnMousePressed(e -> valider.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;-fx-font-family: 'Roboto Light';-fx-font-size: 15px;")); // Changement de couleur au clic

            valider.setOnMouseClicked(event1 -> {

                //On récupère les champs de saisie
                String nomUtilisateurRecup = nomUtilisateur.getText();
                String emailRecup = email.getText();
                String mdpRecup = mdp.getText();
                String confirmationMdpRecup = confirmationMdp.getText();

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
                    if (mdpRecup.equals(confirmationMdpRecup)) {
                        // On insère l'utilisateur avec le mdp, mail et pseudo récupérés en sauvegardant un objet Utilisateur
                        Modele.setUser(new Utilisateur(nomUtilisateurRecup, emailRecup, mdpRecup));
                        try {
                            // On insère l'utilisateur dans la base de données
                            Modele.user.save();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

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
                    } else {
                        //On affiche une erreur si le mot de passe et la confirmation du mot de passe ne sont pas identiques
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Erreur");
                        alert.setContentText("Le mot de passe et la confirmation du mot de passe ne sont pas identiques");
                        alert.showAndWait();
                    }
                }

            });

            valider.setMinSize(80, 35);

            //On ajoute les composantes graphiques à la VBox rootInscription
            rootInscription.getChildren().addAll(nomUtilisateur, email, mdp, confirmationMdp, valider);
            rootInscription.setAlignment(javafx.geometry.Pos.CENTER);
            rootInscription.setSpacing(20);

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
        modeInviter.setStyle("-fx-font-weight: bold; -fx-font-family: 'Roboto Light';-fx-font-size: 20px;");

        //On crée le bouton ici pour le mode invité
        Button boutonInviter = new Button("ici");
        boutonInviter.setStyle("-fx-background-color: #C0C0C0;"); // Couleur du bouton
        boutonInviter.setOnMouseEntered(e -> boutonInviter.setStyle("-fx-background-color: #808080;")); // Changement de couleur au survol
        boutonInviter.setOnMouseExited(e -> boutonInviter.setStyle("-fx-background-color: #C0C0C0;"));  // Changement de couleur à la sortie du survol
        boutonInviter.setOnMousePressed(e -> boutonInviter.setStyle("-fx-border-width: 1px; -fx-border-color: #696969; -fx-background-color: #C0C0C0;")); // Changement de couleur au clic


        //On attribue une action au bouton inviter
        boutonInviter.setOnMouseClicked(event -> {

            //On lance l'application
            try {
                // Appel de startApplication avec le stage
                startApplication(stage);
            } catch (IOException e) {
                // On relance une exception en cas d'erreur
                throw new RuntimeException(e);
            }
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

        // On crée la racine
        BorderPane root = new BorderPane();

        // On crée la scène
        Scene scene = new Scene(root, 1200, 700); // Fenêtre 1200x700 pixels

        // On crée les composantes graphiques pour la zone 'top'
        Image logo = new Image("Logo_Tablo.png");
        ImageView view = new ImageView(logo);

        //On redimensionne l'image
        view.setFitHeight(40);
        view.setFitWidth(40);

        //On utilise cette image pour l'image de l'application
        stage.getIcons().add(logo);

        // On crée les composantes graphiques
        VueHboxTop top = new VueHboxTop(this.modele);
        this.modele.enregistrerObservateur(top);

        // On place les composantes graphiques dans la zone 'top'
        root.setTop(top);

        // On crée les composantes graphiques pour la zone 'left'
        VueVboxLeft left = new VueVboxLeft(this.modele);
        this.modele.enregistrerObservateur(left);

        // On place les composantes graphiques dans la zone 'left'
        root.setLeft(left);

        // On crée les composantes graphiques pour le centre
        VueTableau tableauCentre = new VueTableau();

        //On enregistre le centre comme observateur du modèle
        modele.enregistrerObservateur(tableauCentre);

        // On ajoute un évènement pour le bouton ajouter une liste
        tableauCentre.getAjouterListe().setOnMouseClicked(new ControleurAjouterListe(modele));

        // On définit les propriétés de la VBox
        tableauCentre.setPadding(new Insets(20));

        //On ajoute le tableau au centre
        root.setCenter(tableauCentre);

        //On ajoute les composants graphiques à la racine
        stage.setScene(scene);
        stage.setTitle("Tabl'o"); // Titre de la fenetre
        stage.show(); // On affiche le stage

        //Si on veut mettre en plein écran il faut décommenter cette ligne :
//        stage.setFullScreen(true);
    }

    /**
     * Méthode main qui permet de lancer l'application
     * @param args Arguments
     */
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
        if (pseudoRecup.length() <= 30) {
            return true;
        } else {
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
    public boolean mdpVerif(String mdpRecup) {
        //On vérifie que le mot de passe contient au moins 8 caractères et qu'il contient au moins une majuscule, une minuscule et un chiffre
        if (mdpRecup.length() >= 8 && mdpRecup.matches(".*[A-Z].*") && mdpRecup.matches(".*[a-z].*") && mdpRecup.matches(".*[0-9].*"))
            return true;

            //On limite le champs de saisie du mot de passe pour éviter les injections SQL
        else if (mdpRecup.length() > 30) {
            //On affiche une erreur si le mot de passe dépasse 30 caractères
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");
            alert.setContentText("Le mot de passe ne doit pas dépasser 30 caractères");
            alert.showAndWait();
            return false;
        } else {
            //On affiche une erreur si le mot de passe ne respecte pas les conditions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur");
            alert.setContentText("Le mot de passe doit contenir au moins 8 caractères, au moins une majuscule, une minuscule et un chiffre");
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Méthode qui permet d'importer les données d'un user (tableaux, listes, taches..) depuis la bd lors de la connexion
     */
    public static void importerDonnees() throws SQLException {
        // On récupère tous les tableaux de l'utilisateur
        ArrayList<Tableau> tableaux = Tableau.findAllByUserId(Modele.user.getId());
        // Pour chaque tableau
        for (Tableau tableau : tableaux) {
            // On met à jour le tableau courant
            modele.changerTableauCourant(tableau.getNumTableau());
            // On insère le tableau
            modele.ajouterTableau(tableau);
            // On récupère toutes les listes du tableau
            ArrayList<Liste> listes = Liste.findAllByTabId(tableau.getId());
            // Pour chaque liste
            for (Liste liste : listes) {
                // On met à jour la liste courante
                Modele.setListeCourante(liste.getNumListe());
                // On insère la liste dans le tableau
                modele.ajouterListe(liste.getTitre());
                // On récupère toutes les tâches de la liste
                ArrayList<Tache> taches = Tache.findAllByListeId(liste.getId());
                // Pour chaque tâche
                for (Tache tache : taches) {
                    // On met à jour le numéro de la tâche courante
                    Modele.setTacheCourante(tache.getNumTache());
                    // On ajoute la tâche à la liste
                    modele.ajouterTache(tache);
                    // On récupère toutes les sous tâches de la tâche
                    ArrayList<Tache> sousTaches = tache.findAllSousTaches();

                    // On boucle sur l'ensemble des sous taches
                    for (Tache sousTache : sousTaches) {
                        // On ajoute la tâche fille à la tâche mère. Si le résultat est false on change la tache courante en TacheMere
                        if (!modele.ajouterSousTache(sousTache)) {

                            // On change la tâche courante en tâche mère
                            modele.tacheCouranteEnMere();

                            // On ajoute la tâche fille à la tâche mère
                            modele.ajouterSousTache(sousTache);
                        }
                    }
                }
            }
        }
    }
}