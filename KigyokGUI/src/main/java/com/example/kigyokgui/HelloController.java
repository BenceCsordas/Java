package com.example.kigyokgui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloController {
    public TextField kigyoTfield;
    public ListView<String> filteredKigyokList;
    public ListView<Kigyo> kigyokList;

    private class Kigyo {
        public String fajta;
        public int hossz;
        public String elofordulas;
        public String merges;

        public Kigyo(String sor) {
            String[] s = sor.split(";");
            fajta = s[0];
            hossz = Integer.parseInt(s[1]);
            elofordulas = s[2];
            merges = s[3];

        }

        @Override
        public String toString() {
            return fajta + " ("  + hossz + "cm), " + elofordulas;
        }
    }

    private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) kigyok.add(new Kigyo(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }

    }

    private ObservableList<Kigyo> kigyok = FXCollections.observableArrayList();


    private FileChooser fc = new FileChooser();

    public void initialize() {

        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        kigyokList.setItems(kigyok);
    }

    public void onOpenClick(ActionEvent actionEvent) {
        File fajl = fc.showOpenDialog(kigyokList.getScene().getWindow());
        if(fajl != null) betolt(fajl);
    }

    public void onCloseClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onAboutClick(ActionEvent actionEvent) {
        Alert nevjegy = new Alert(Alert.AlertType.INFORMATION);
        nevjegy.setTitle("Névjegy");
        nevjegy.setHeaderText("");
        nevjegy.setContentText("Kígyók v1.0.0\n(C)Kandó");
        nevjegy.showAndWait();
    }

    public void filterKigyok(ActionEvent actionEvent) {
        String szoveg = kigyoTfield.getText();
        ObservableList<String> filtered = FXCollections.observableArrayList(kigyok.stream().filter(obj->obj.fajta.toLowerCase().contains(szoveg.toLowerCase())).map(obj->obj.fajta).toList());
        filteredKigyokList.setItems(filtered);

    }
}