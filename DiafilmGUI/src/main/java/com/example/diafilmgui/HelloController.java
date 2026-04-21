package com.example.diafilmgui;

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
import java.util.Scanner;

public class HelloController {


    public ListView diafilmekLV;
    public ComboBox cbox;
    public Label darab;
    public CheckBox ff;
    public CheckBox szines;



    private class Diafilm {
        public String cim;
        public int ev;
        public int kocka;
        public String szines;


        public Diafilm(String sor) {
            String[] s = sor.split(";");
            cim = s[0];
            ev = Integer.parseInt(s[1]);
            kocka = Integer.parseInt(s[2]);
            szines = s[3];


        }

        @Override
        public String toString() {
            return String.format("%s (%d, %d kocka, %s)", cim, ev, kocka, szines.equals("I")?"színes":"fekete-fehér");
        }
    }





    private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) diafilmek.add(new Diafilm(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }

    }

    private ObservableList<Diafilm> diafilmek = FXCollections.observableArrayList();


    private FileChooser fc = new FileChooser();

    public void initialize() {

        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        diafilmekLV.setItems(diafilmek);


    }

    public void onOpenClick() {
        File fajl = fc.showOpenDialog(diafilmekLV.getScene().getWindow());
        if(fajl != null) betolt(fajl);
        darab.setText(diafilmek.size() + " darab");
        ObservableList<Integer> evek = FXCollections.observableArrayList(diafilmek.stream().map(obj->obj.ev).distinct().sorted().toList());
        cbox.setItems(evek);
        cbox.getSelectionModel().select(0);
    }

    public void onCloseClick() {
        Platform.exit();
    }

    public void onAboutClick() {
        Alert nevjegy = new Alert(Alert.AlertType.INFORMATION);
        nevjegy.setTitle("Névjegy");
        nevjegy.setHeaderText("");
        nevjegy.setContentText("Diafilmek v1.0.0\n(C)Kandó");
        nevjegy.showAndWait();
    }


    public void filter() {

        ObservableList<Diafilm> filtered = null;
        if(!szines.isSelected() && !ff.isSelected()) filtered = FXCollections.observableArrayList();
        else if(szines.isSelected() && ff.isSelected()) filtered = FXCollections.observableArrayList(diafilmek.stream().filter(obj->obj.ev==(int)cbox.getSelectionModel().getSelectedItem()).toList());
        else if(!szines.isSelected() && ff.isSelected()) filtered = FXCollections.observableArrayList(diafilmek.stream().filter(obj->obj.ev==(int)cbox.getSelectionModel().getSelectedItem() && obj.szines.equals("N")).toList());
        else if(szines.isSelected() && !ff.isSelected()) filtered = FXCollections.observableArrayList(diafilmek.stream().filter(obj->obj.ev==(int)cbox.getSelectionModel().getSelectedItem() && obj.szines.equals("I")).toList());

        darab.setText(filtered.size() + " darab");
        diafilmekLV.setItems(filtered);

    }


}