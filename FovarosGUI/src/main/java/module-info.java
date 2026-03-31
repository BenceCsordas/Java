module com.example.fovarosgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fovarosgui to javafx.fxml;
    exports com.example.fovarosgui;
}