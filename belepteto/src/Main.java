import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private class Adat {
        public String kod;
        public String ido;
        public int esemeny;

        public Adat(String sor){
            String[] s = sor.split(" ");
            kod = s[0];
            ido = s[1];
            esemeny = Integer.parseInt(s[2]);
        }
    }

    private ArrayList<Adat> adatok = new ArrayList<>();
    private final int BELEP = 1;
    private final int EBED = 3;
    private final int  KOLCSON = 4;
    private final int  KILEP = 2;
    public Main() {
        betolt("bedat.txt");

        System.out.println("2. feladat:");
        System.out.printf("Az első tanuló %s-kor lépett be a főkapun.\n", adatok.getFirst().ido);
        System.out.printf("Az utolsó tanuló %s-kor lépett be a főkapun.\n", adatok.getLast().ido);


        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("kesok.txt"), "utf-8");
            for(Adat a : adatok){
                if(a.esemeny == BELEP && a.ido.compareTo("07:50") > 0 && a.ido.compareTo("08:15") <= 0){
                    ki.printf("%s %s\r\n", a.ido, a.kod);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            if(ki != null) ki.close();
        }

        int ebedDb = 0;
        for(Adat a : adatok) if (a.esemeny == EBED) ebedDb++;
        System.out.printf("4. feladat:\n");
        System.out.printf("A menzán aznap %d tanuló ebédelt.\n", ebedDb);

        int kolcsonDb = 0;
        TreeSet<String> mar = new TreeSet<>();
        for(Adat a : adatok){
            if (a.esemeny == KOLCSON && !mar.contains(a.kod)){
                kolcsonDb++; mar.add(a.kod);
            }
        }
        System.out.printf("5. feladat:\n");
        System.out.printf("Aznap %d tanuló kölcsönzött a könyvtárban.\n", kolcsonDb);


        TreeMap<String, Boolean> bent = new TreeMap<>();
        ArrayList<String> szokeveny = new ArrayList<>();
        for(Adat a : adatok){
            if(a.esemeny == BELEP && !bent.containsKey(a.kod)){
                bent.put(a.kod, true);
            }else if(a.esemeny == BELEP && bent.containsKey(a.kod)){
                szokeveny.add(a.kod);
            }else if(a.esemeny == KILEP) bent.put(a.kod, false);
        }
        System.out.printf("6. feladat:\n");
        System.out.printf("Az érintett tanulók:\n");
        System.out.printf("%s\n", String.join(" ", szokeveny));

        Scanner input = new Scanner(System.in);
        System.out.printf("7. feladat:\n");
        System.out.printf("Egy tanuló azonosítója=");
        String kod = input.nextLine();
        Adat belep = null;
        Adat kilep = null;
        for(Adat a : adatok){
            if(a.kod.equals(kod)){
                if(belep == null && a.esemeny == BELEP) belep = a;
                if(a.esemeny == KILEP) kilep = a;
            }

        }
        if(belep != null){
            System.out.printf("Belépés-kilépés: %s-%s\n", belep.ido, kilep.ido);
            String[] beIdo = belep.ido.split(":");
            String[] kiIdo = kilep.ido.split(":");
            int bePerc = Integer.parseInt(beIdo[0])*60 + Integer.parseInt(beIdo[1]);
            int kiPerc = Integer.parseInt(kiIdo[0])*60 + Integer.parseInt(kiIdo[1]);
            int ora = (kiPerc - bePerc) / 60;
            int perc = (kiPerc - bePerc) % 60;
            System.out.printf("A tanuló érkezése és távozása között %d óra %d perc telt el.", ora, perc);
        }else {
            System.out.printf("Ilyen azonosítójú tanuló aznap nem volt az iskolában.");
        }
    }

    private void betolt(String fajlnev){
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            while(be.hasNextLine()) adatok.add(new Adat(be.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            if(be != null) be.close();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}