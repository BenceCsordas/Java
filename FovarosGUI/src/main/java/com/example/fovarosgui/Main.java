package com.example.fovarosgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

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

    }

    private ArrayList<Fovaros> fovarosok = new ArrayList<>();

    public Main() {
        betolt("fovaros.csv");
        System.out.printf("0) Összesen 49 ország adata lett beolvasva.\n",fovarosok.size());

        Fovaros leg = fovarosok.get(0);
        for(Fovaros f : fovarosok) if(f.lakos > leg.lakos) leg = f;
        Fovaros leg2 = null; int lak2 = 0;
        for(Fovaros f : fovarosok) if(f.lakos > lak2 && f.lakos < leg.lakos) {leg2 = f; lak2 = f.lakos;};
        System.out.printf("1) Az ország, ahol a legtöbben élnek: %s, %d fő\n" +
                "    A második legnagyobb népesség: %s, %d fő\n", leg.orszag, leg.lakos, leg2.orszag, leg2.lakos);

        int i = 0; while (i<fovarosok.size() && !fovarosok.get(i).fovaros.equals("Budapest")) i++;
        Fovaros Bp = fovarosok.get(i);
        int db = 0;
        for(Fovaros fovaros : fovarosok){
            if(fovaros.folakos < Bp.folakos){
                db++;
            }
        }
        System.out.printf("2) Összesen %d fővárosban élnek kevesebben, mint Budapesten!\n", db);


        TreeSet<String> rovek = new TreeSet<>();
        for(Fovaros f : fovarosok) if(f.rovidites.contains("C")) rovek.add(f.rovidites);
        System.out.printf(" 3) Országjel, amiben szerepel 'C' betű: %s.\n", String.join(", ", rovek));

        int osszes = 0;
        for(Fovaros f : fovarosok) if (f.lakos < 20_000_000) osszes += f.folakos;
        System.out.printf("4) A 20 millió főnél kisebb országok fővárosainak össznépessége: %,d fő\n", osszes);

        TreeMap<Integer, Integer> stat = new TreeMap<>();
        for(Fovaros f : fovarosok){
            int kat = f.folakos / 5_000_000;
            if(!stat.containsKey(kat)) stat.put(kat, 1); else stat.put(kat, stat.get(kat)+1);
        }

        for(Integer kat : stat.keySet()){
            System.out.printf("     %,10d - %,10d : %d\n", kat*5_000_000, (kat+1)*5_000_000-1, stat.get(kat));
        }

        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("nagyok.txt"), "utf-8");
            for(Fovaros f : fovarosok){
                if(f.lakos > 200_000_000) ki.printf("%s, %,d\r\n", f.orszag, f.lakos);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(ki != null) ki.close();
        }
        System.out.printf("6) Nagy népességű országok a nagyok.txt fájlban!");

    }

    private void betolt(String fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(new File(fajlnev), "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) fovarosok.add(new Fovaros(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }

    }

    public static void main(String[] args) {
        new Main();
    }

}
