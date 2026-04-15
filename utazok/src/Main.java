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

    }

    private ArrayList<Utazo> utazok = new ArrayList<>();

    public Main() {
        betolt("utazok.csv");
        System.out.printf("0) 50 utazó adata beolvasva.\n",utazok.size());

        List<String> varosok = utazok.stream().map(obj->obj.varos).distinct().toList();
        String randomVaros = varosok.get((int)(Math.random() * varosok.size()));
        int db = utazok.stream().filter(obj->obj.varos.equals(randomVaros)).toList().size();
        System.out.printf("1) Összesen %d városba utaztak\n" +
                "    Közülük egy véletlenszerűen kiválasztott: %s\n" +
                "    Ebbe a városba %d utazó érkezett\n", varosok.size(), randomVaros, db);


        String[] legkorabbi = utazok.getFirst().indulas.split(":");
        Utazo legkorabbiUtazo = utazok.getFirst();
        int delelottiIndulasok = 0;
        for(Utazo u : utazok){
            String[] indulas = u.indulas.split(":");
            if(Integer.parseInt(indulas[1]) < Integer.parseInt(legkorabbi[1]) && Integer.parseInt(indulas[0]) < Integer.parseInt(legkorabbi[0])){
                legkorabbiUtazo = u;
            }
            if(Integer.parseInt(indulas[0])<12) delelottiIndulasok++;
        }
        System.out.printf(" 2) Legkorábbi indulás: %s\n" +
                "    Összesen %d utazás kezdődött délelőtt\n", legkorabbiUtazo.indulas, delelottiIndulasok);

        TreeMap<String, Integer> stat = new TreeMap<>();
        for(Utazo u : utazok){
            String kat = u.datum.split("\\.")[0];
            if(!stat.containsKey(kat)) stat.put(kat, 1); else stat.put(kat, stat.get(kat)+1);
        }

        System.out.println("3) Utazások száma hónaponként:");
        for(String kat : stat.keySet()){
            System.out.printf("      %s.hó : %d utazás\n", kat, stat.get(kat));
        }

        TreeMap<String, Integer> nevek = new TreeMap<>();
        for(Utazo u : utazok){
            String kat = u.nev.split(" ")[0];
            if(!nevek.containsKey(kat)) nevek.put(kat, 1); else nevek.put(kat, nevek.get(kat)+1);
        }
        List<String> tobbszoriNevek = new ArrayList<>();
        for(String kat : nevek.keySet()){
            if(nevek.get(kat) > 1) tobbszoriNevek.add(kat);
        }
        System.out.printf("4) Többször szereplő vezetéknevek:\n" +
                "    %s \n", String.join(" ", tobbszoriNevek));

        TreeMap<String, Integer> napok = new TreeMap<>();
        for(Utazo u : utazok){
            String kat = u.datum;
            if(!napok.containsKey(kat)) napok.put(kat, 1); else napok.put(kat, napok.get(kat)+1);
        }
        List<String> tobbszoriNapok = new ArrayList<>();
        for(String kat : napok.keySet()){
            if(napok.get(kat) > 2) {
             String beirando = kat+"("+napok.get(kat)+")";
             tobbszoriNapok.add(beirando);
            }
        }
        System.out.printf("5) Ugyanazon a napon kettőnél több utazás: %s\n", String.join(" ", tobbszoriNapok));

        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("szeged.txt"), "utf-8");
            for(Utazo u : utazok){
                if(u.varos.equals("Szeged")) ki.printf("%s (%s %s)\r\n", u.nev, u.datum, u.indulas);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(ki != null) ki.close();
        }
        System.out.printf("6) Szegedre utazók kiírva a szeged.txt fájlba");

    }

    private void betolt(String fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(new File(fajlnev), "utf-8");
            while(beolvasas.hasNextLine()) utazok.add(new Utazo(beolvasas.nextLine()));
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