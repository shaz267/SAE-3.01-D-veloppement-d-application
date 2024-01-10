module tablo {

    requires javafx.controls;
    requires javafx.base;
	requires java.sql;

	exports Tablo;
    exports Tablo.Modele;
    exports Tablo.Vue;
    exports Tablo.Controleur;
}