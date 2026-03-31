import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public class Konyv {
        public int ev;
        public int negyedev;
        public String eredet;
        public String leiras;
        public int peldany;

        public Konyv(String sor){
            String[] s = sor.split(";");
            ev = Integer.parseInt(s[0]);
            negyedev = Integer.parseInt(s[1]);
            eredet = s[2];
            leiras = s[3];
            peldany = Integer.parseInt(s[4]);
        }
    }

    private ArrayList<Konyv> konyvek = new ArrayList<>();

    public Main() {
        betolt("src/kiadas.txt");
        System.out.printf("1. feladat: %d adat beolvasva\n", konyvek.size());

        System.out.println("2. feladat:");
        Konyv legnagyobb = konyvek.get(0);
        int elofordult = 0;
        for(Konyv k : konyvek){
            if(k.peldany > legnagyobb.peldany){
                legnagyobb = k;
                elofordult = 0;
            }if(k.peldany == legnagyobb.peldany){
                elofordult++;
            }
        }
        System.out.printf("Legnagyobb példányszámban kiadott könyv: %s, %d példányban, előfordult: %d alkalommal.\n", legnagyobb.leiras, legnagyobb.peldany, elofordult);

        System.out.println("4. feladat:");


    }

    public void betolt(String fajlnev){
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            while(be.hasNextLine()) konyvek.add(new Konyv(be.nextLine()));
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