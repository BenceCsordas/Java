package com.example.leltargui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloController {
    public ListView<Leltar> lview;
    public ListView lview2;
    public ComboBox cbox;
    private ObservableList<Leltar> leltar = FXCollections.observableArrayList();

    public void asd() {
        System.out.println("asd");
        loadL2();
    }


    public class Leltar {
        public String megnevezes;
        public int beszEv;
        public int darab;
        public int egysegAr;

        public Leltar(String sor){
            String[] s = sor.split(";");
            megnevezes = s[0];
            beszEv = Integer.parseInt(s[1]);
            darab = Integer.parseInt(s[2]);
            egysegAr = Integer.parseInt(s[3]);
        }

        @Override
        public String toString() {
            return darab + " x " + megnevezes + " = " + darab*egysegAr + ",-Ft";
        }
    }
    private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) leltar.add(new Leltar(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            List<Integer> evek = leltar.stream().map(obj->obj.beszEv).sorted().distinct().toList();
            evek.forEach(obj->cbox.getItems().add(obj + ""));

            cbox.getSelectionModel().select(0);
            loadL2();
            if(beolvasas != null) beolvasas.close();
        }

    }

    private void loadL2() {
        String sel = String.valueOf(cbox.getSelectionModel().getSelectedItem());
        System.out.println(sel);
        ObservableList<Leltar> filtered = FXCollections.observableArrayList(leltar.stream().filter(obj->obj.beszEv==Integer.parseInt(sel)).toList());
        System.out.println(filtered);
        lview2.setItems(filtered);
    }


    private FileChooser fc = new FileChooser();

    public void initialize() {
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        lview.setItems(leltar);
    }




    public void openFile() {
        File fajl = fc.showOpenDialog(lview.getScene().getWindow());
        if(fajl != null) betolt(fajl);
    }

    public void Close(ActionEvent actionEvent) {
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