module com.example.tablo_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tablo_app to javafx.fxml;
    exports com.example.tablo_app;
    opens com.example.tablo_app.model to javafx.fxml;
    exports com.example.tablo_app.controller;
    opens com.example.tablo_app.controller to javafx.fxml;
}