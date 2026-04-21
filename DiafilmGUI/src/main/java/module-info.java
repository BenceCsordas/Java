module com.example.diafilmgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.diafilmgui to javafx.fxml;
    exports com.example.diafilmgui;
}