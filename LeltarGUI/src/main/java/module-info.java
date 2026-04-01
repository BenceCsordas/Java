module com.example.leltargui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.leltargui to javafx.fxml;
    exports com.example.leltargui;
}