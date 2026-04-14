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

    private class Kigyo {
        public String fajta;
        public int hossz;
        public String elofordulas;
        public String merges;

        public Kigyo(String sor) {
            String[] s = sor.split(";");
            fajta = s[0];
            hossz = Integer.parseInt(s[1]);
            elofordulas = s[2];
            merges = s[3];

        }

    }

    private ArrayList<Kigyo> kigyok = new ArrayList<>();

    public Main() {
        betolt("kigyok.csv");
        System.out.printf("0) Összesen %d kígyó adata beolvasva.\n",kigyok.size());
        List<Kigyo> mergesK = kigyok.stream().filter(obj->obj.merges.equals("Igen")).toList();
        List<Kigyo> nemMergesK = kigyok.stream().filter(obj->obj.merges.equals("Nem")).toList();
        System.out.printf("\tKözülük %d mérges és %d nem mérges.\n",mergesK.size(), nemMergesK.size());


        int osszhosszMeter = kigyok.stream().mapToInt(obj->obj.hossz).sum();
        System.out.printf("1) A kígyók teljes hossza méterben: %.2fm\n", (double) osszhosszMeter/100);

        int leghosszabb = mergesK.stream().mapToInt(obj->obj.hossz).max().getAsInt();
        Kigyo leghosszabbKigyo = mergesK.stream().filter(obj->obj.hossz==leghosszabb).toList().get(0);
        System.out.printf("2) A leghosszabb mérges kígyó: %s (%dcm)\n", leghosszabbKigyo.fajta, leghosszabbKigyo.hossz);

        List<String> elofordulasok = kigyok.stream().map(obj->obj.elofordulas).distinct().sorted().toList();
        System.out.printf("3) A kígyók származási helye (abc): %s\n", String.join(", ", elofordulasok));

        Kigyo randomKigyo = kigyok.get((int)(Math.random() * kigyok.size()));
        System.out.printf("4) Egy véletlen kiválasztott mérges kígyó: %s\n" +
                "\tSzármazási helye %s, hossza %dcm\n", randomKigyo.fajta, randomKigyo.elofordulas, randomKigyo.hossz);

        System.out.println("5) Adott fajhoz (abc) tartozó kígyók darabszáma:");
        TreeMap<String, Integer> stat = new TreeMap<>();
        for(Kigyo k : kigyok){
            String kat = k.fajta.split(" ").length == 1 ? k.fajta : k.fajta.split(" ")[1];
            if(!stat.containsKey(kat)) stat.put(kat, 1); else stat.put(kat, stat.get(kat)+1);
        }


        for(String kat : stat.keySet()){
            System.out.printf("     %s : %d féle\n", kat, stat.get(kat));
        }

        Kigyo utsoMamba = kigyok.stream().filter(obj->obj.fajta.contains("Mamba")).toList().getLast();
        System.out.printf("6) Az utolsó Mamba fajtája: %s\n", utsoMamba.fajta);


        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("kobra.txt"), "utf-8");
            for(Kigyo k : kigyok){
                if(k.fajta.contains("Kobra")) ki.printf("%s (%dcm)\r\n", k.fajta, k.hossz);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(ki != null) ki.close();
        }
        System.out.printf("7) Minden Kobra mentve a kobra.txt fájlba");
    }

    private void betolt(String fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(new File(fajlnev), "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) kigyok.add(new Kigyo(beolvasas.nextLine()));
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