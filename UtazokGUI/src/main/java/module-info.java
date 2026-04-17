module com.example.utazokgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.utazokgui to javafx.fxml;
    exports com.example.utazokgui;
}