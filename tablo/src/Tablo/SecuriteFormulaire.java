package Tablo;

import javafx.scene.control.Alert;

/**
 * Classe SecuriteFormulaire qui permet de vérifier les champs de saisie
 */
public class SecuriteFormulaire {

    /**
     * Méthode qui permet de limiter la taille du pseudo
     * @param pseudoRecup Pseudo à vérifier
     * @return Vrai si le pseudo est valide, faux sinon
     */
    public static boolean pseudoVerif(String pseudoRecup) {
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
    public static boolean emailVerif(String emailRecup) {
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
    public static boolean mdpVerif(String mdpRecup) {
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
}
