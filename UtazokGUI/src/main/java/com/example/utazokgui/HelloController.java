package com.example.utazokgui;

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
    public ListView<Utazo> filteredUtazokList;
    public ListView<String> utazokList;

    private class Utazo {
        public String nev;
        public String varos;
        public String datum;
        public String indulas;
        public String merges;

        public Utazo(String sor) {
            String[] s = sor.split(";");
            nev = s[0];
            varos = s[1];
            datum = s[2];
            indulas = s[3];

        }

        @Override
        public String toString() {
            return String.format("%s (%s %s)", nev, datum, indulas);
        }
    }
    private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            while(beolvasas.hasNextLine()) utazok.add(new Utazo(beolvasas.nextLine()));
            ObservableList<String> varosok = FXCollections.observableArrayList(utazok.stream().map(obj->obj.varos).distinct().sorted().toList());
            utazokList.setItems(varosok);
            utazokList.setDisable(false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }

    }

    private ObservableList<Utazo> utazok = FXCollections.observableArrayList();


    private FileChooser fc = new FileChooser();

    public void initialize() {
        utazokList.setDisable(true);
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));


    }

    public void onOpenClick(ActionEvent actionEvent) {
        File fajl = fc.showOpenDialog(utazokList.getScene().getWindow());
        if(fajl != null) {

            betolt(fajl);
        };
    }

    public void onCloseClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onAboutClick(ActionEvent actionEvent) {
        Alert nevjegy = new Alert(Alert.AlertType.INFORMATION);
        nevjegy.setTitle("Névjegy");
        nevjegy.setHeaderText("");
        nevjegy.setContentText("Utazók v1.0.0\n(C)Kandó");
        nevjegy.showAndWait();
    }

    public void filterUtazok() {
        String Selectedvaros = utazokList.getSelectionModel().getSelectedItem();
        System.out.println(Selectedvaros);
        ObservableList<Utazo> filtered = FXCollections.observableArrayList(utazok.stream().filter(obj->obj.varos.equals(Selectedvaros)).toList());
        filteredUtazokList.setItems(filtered);

    }
}