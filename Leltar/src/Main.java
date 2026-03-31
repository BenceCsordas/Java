import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

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

    private ArrayList<Leltar> leltar = new ArrayList<>();

    public Main() {
        betolt("leltar.csv");
        System.out.printf("0) A beolvasott leltári tételek száma: %d\n", leltar.size());
        int osszAr = leltar.stream().mapToInt(obj->obj.egysegAr*obj.darab).sum();
        System.out.printf("\tA benne lévő eszközök összára: %,d,-Ft", osszAr);
        Leltar legdragabb = leltar.get(0);
        for(Leltar l : leltar){
            if(l.egysegAr>legdragabb.egysegAr) legdragabb = l;
        }
        System.out.printf("\n1) A legdrágább eszköz: %s (%,d,-Ft)", legdragabb.megnevezes, legdragabb.egysegAr);

        List<Integer> evek = leltar.stream().map(obj->obj.beszEv).sorted().distinct().toList();
        System.out.printf("\n2) A leltár a %d-%d éveket tartalmazza\n", evek.get(0), evek.get(evek.size()-1));

        TreeMap<Integer, Integer> vasaroltEszkozok = new TreeMap<>();
        for(Leltar l : leltar){
            if(!vasaroltEszkozok.containsKey(l.beszEv)) vasaroltEszkozok.put(l.beszEv, l.darab); else vasaroltEszkozok.put(l.beszEv, vasaroltEszkozok.get(l.beszEv)+l.darab);
        }
        System.out.print("3) Az egyes években vásárolt eszközök darabszáma:");
        for(Integer kat : vasaroltEszkozok.keySet()){
            System.out.printf("\n     %d : %d", kat, vasaroltEszkozok.get(kat));
        }

        TreeMap<Integer, Integer> osszertek = new TreeMap<>();
        for(Leltar l : leltar){
            if(!osszertek.containsKey(l.beszEv)) osszertek.put(l.beszEv, l.darab*l.egysegAr); else osszertek.put(l.beszEv, osszertek.get(l.beszEv)+l.darab*l.egysegAr);
        }
        int max = 0;
        for(Integer kat : osszertek.keySet()){
            if(osszertek.get(kat)>max){
                max = osszertek.get(kat);
            }
        }
        for(Integer kat : osszertek.keySet()){
            if(osszertek.get(kat)==max){
                System.out.printf("\n4) A legnagyobb összértékű beszerzés éve: %d\n\t" +
                        "Ekkor a beszerzés összértéke: %,d,-Ft", kat, osszertek.get(kat));
            }
        }

        int legnagyobbEr = leltar.stream().mapToInt(obj->obj.egysegAr*obj.darab).max().getAsInt();
        Leltar legnagyobb = leltar.stream().filter(obj->obj.egysegAr*obj.darab==legnagyobbEr).toList().get(0);
        System.out.printf("\n5) A legnagyobb értékű beszerzés:\n" +
                "\t%d darab %s = %,d,-Ft", legnagyobb.darab, legnagyobb.megnevezes,legnagyobb.darab*legnagyobb.egysegAr);



        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("kezdes.txt"), "utf-8");
            for(Leltar f : leltar){
                if(f.beszEv == evek.get(0)) ki.printf(f.toString() + "\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(ki != null) ki.close();
        }
        System.out.printf("\n6) Az első év adatai kiírva a kezdes.txt fájlba");



    }

    public void betolt(String fajlnev){
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            be.nextLine();
            while(be.hasNextLine()) leltar.add(new Leltar(be.nextLine()));
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