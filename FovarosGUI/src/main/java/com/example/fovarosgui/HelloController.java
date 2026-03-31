package com.example.fovarosgui;

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
    public TextField fovaros;
    public TextField fovarosLak;
    public ListView<Fovaros> listView;

    private class Fovaros {
        public String orszag;
        public String rovidites;
        public int lakos;
        public String fovaros;
        public int folakos;

        public Fovaros(String sor) {
            String[] s = sor.split(";");
            orszag = s[0];
            rovidites = s[1];
            lakos = Integer.parseInt(s[2]);
            fovaros = s[3];
            folakos = Integer.parseInt(s[4]);
        }
        public String toString(){
            return String.format("%s, (%,d): %s", orszag, lakos, rovidites);
        }
    }
    private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) fovarosok.add(new Fovaros(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }

    }

    private ObservableList<Fovaros> fovarosok = FXCollections.observableArrayList();


    private FileChooser fc = new FileChooser();

    public void initialize() {

        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        listView.setItems(fovarosok);
    }




    public void onOpenClick() {
        File fajl = fc.showOpenDialog(listView.getScene().getWindow());
        if(fajl != null) betolt(fajl);
    }

    public void onCloseClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onNevjegyClick(ActionEvent actionEvent) {
        Alert nevjegy = new Alert(Alert.AlertType.INFORMATION);
        nevjegy.setTitle("Névjegy");
        nevjegy.setHeaderText("");
        nevjegy.setContentText("Fővárosok v1.0.0\n(C)Kandó");
        nevjegy.showAndWait();
    }
}