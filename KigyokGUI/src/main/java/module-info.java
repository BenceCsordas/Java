module com.example.kigyokgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kigyokgui to javafx.fxml;
    exports com.example.kigyokgui;
}