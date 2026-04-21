//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

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

    }

    private ArrayList<Diafilm> diafilmek = new ArrayList<>();

    public Main() {
        betolt("diafilm.csv");


        List<Diafilm> feketeFeher = diafilmek.stream().filter(obj->obj.szines.equals("N")).toList();
        System.out.printf("0) %d diafilm adata beolvasva\n" +
                "    Közülük %d még fekete-fehér\n",diafilmek.size(), feketeFeher.size());

        Diafilm legregebbi = diafilmek.stream().sorted(Comparator.comparingInt(obj->obj.ev)).toList().get(0);
        System.out.printf("1) A legrégebbi diafilm: %s (%d)\n" +
                "    De ugyanebben az évben készült még:\n", legregebbi.cim, legregebbi.ev);

        for(Diafilm d : diafilmek){
            if(d.ev == legregebbi.ev && !d.cim.equals(legregebbi.cim)) System.out.printf("    - %s (%d)\n", d.cim, d.ev);
        }

        double avgKockaszam2000elott = diafilmek.stream().filter(obj->obj.ev<2000).mapToInt(obj->obj.kocka).average().getAsDouble();
        double avgKockaszam2000utan = diafilmek.stream().filter(obj->obj.ev>2000).mapToInt(obj->obj.kocka).average().getAsDouble();
        System.out.printf("2) A 2000 előtt készült diafilmek átlagos kockaszáma: %.1f\n" +
                "    A később készült diafilmeknél az áltag: %.1f\n", avgKockaszam2000elott, avgKockaszam2000utan);


        TreeMap<String, Integer> stat = new TreeMap<>();
        for(Diafilm d : diafilmek){
            int ev1 = (d.ev/10)*10;
            int ev2 = (d.ev/10)*10+9;
            String kat = ev1+"-"+ev2;
            if(!stat.containsKey(kat)) stat.put(kat, 1); else stat.put(kat, stat.get(kat)+1);
        }

        System.out.println("3) Az egyes évtizedekben készült diafilmek száma:");
        for(String kat : stat.keySet()){
            System.out.printf("     %s : %d darab\n", kat, stat.get(kat));
        }

        TreeMap<Integer, Integer> evek = new TreeMap<>();
        for(Diafilm d : diafilmek){
            int kat = d.ev;
            if(!evek.containsKey(kat)) evek.put(kat, d.kocka); else evek.put(kat, evek.get(kat)+d.kocka);
        }



        List<Map.Entry<Integer, Integer>> rendezettKockak = evek.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        System.out.printf("4) A legtöbb kocka (%d db) készítésének éve: %d\n" +
                "    A második legtöbb kocka (%d db) éve: %d\n", rendezettKockak.getLast().getValue(), rendezettKockak.getLast().getKey(), rendezettKockak.get(rendezettKockak.size()-2).getValue(), rendezettKockak.get(rendezettKockak.size()-2).getKey());


        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("200x.txt"), "utf-8");
            for(Diafilm d: diafilmek){
                if(d.ev>1999 && d.ev<2010) ki.printf("%s;%d;%d;%s\r\n", d.cim, d.ev, d.kocka, d.szines);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(ki != null) ki.close();
        }
        System.out.printf("5) A 200x évben megjelent diák adatai elmentve (200x.txt)");
    }

    private void betolt(String fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(new File(fajlnev), "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) diafilmek.add(new Diafilm(beolvasas.nextLine()));
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