import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ArrayList<Repulo> repulok = new ArrayList<>();

        try {
            Scanner beolvasas = new Scanner(new File("src/repulok.csv"));
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()){
                String[] sor = beolvasas.nextLine().split(";");
                String tipus = sor[0];
                double hossz = Double.parseDouble(sor[1]);
                int suly = Integer.parseInt(sor[2]);
                int ferohely = Integer.parseInt(sor[3]);
                int uzemanyagtank = Integer.parseInt(sor[4]);
                Repulo repulo = new Repulo(tipus, hossz, suly, ferohely, uzemanyagtank);
                repulok.add(repulo);
            }
            beolvasas.close();
        } catch (FileNotFoundException e) {
            System.err.println("Beolvasási hiba: " + e);
        }

        System.out.printf("0) Összesen %d repülő adata beolvasva.\n", repulok.size());
        Repulo randomRepulo = repulok.get((int)(Math.random() * repulok.size()));
        System.out.printf("\tKözülük egy véletlen kiválasztott: %s\n", randomRepulo.getTipus());

        Repulo legtobbFerohely = repulok.get(0);
        for(Repulo repulo : repulok){
            if(repulo.getFerohelyek() > legtobbFerohely.getFerohelyek()){
                legtobbFerohely = repulo;
            }
        }
        Repulo masodiklegtobbFerohely = repulok.get(0);
        for(Repulo repulo : repulok){
            if(repulo.getFerohelyek() > legtobbFerohely.getFerohelyek() && repulo.getFerohelyek() != legtobbFerohely.getFerohelyek()){
                masodiklegtobbFerohely = repulo;
            }
        }
        System.out.printf("1) Legtöbb férőhellyel rendelkezik: %s (%d hely)\n", legtobbFerohely.getTipus(), legtobbFerohely.getFerohelyek());
        System.out.printf("\tA második legtöbb férőhely: %s (%d hely)\n", masodiklegtobbFerohely.getTipus(), masodiklegtobbFerohely.getFerohelyek());


        int sulySum = repulok.stream().mapToInt(obj->obj.getSuly() < 100000 ? obj.getSuly() : 0).sum();
        int kisebbRepulok = repulok.stream().filter(obj->obj.getSuly() < 100000).toList().size();
        System.out.printf("2) A 100000kg súlynál kisebb gépek (%d darab) átlagsúlya: %.2fkg\n", kisebbRepulok, (double)sulySum/kisebbRepulok);

        List<String> szamNelkuli = repulok.stream().map(obj->obj.getTipus()).filter(obj->!obj.matches(".*\\d.*")).toList();
        System.out.printf("3) Típusok, amelyikben nincs szám: %s\n", String.join(", ", szamNelkuli));

        List<String> gyartok = repulok.stream().map(obj->obj.getTipus().split(" ")[0]).distinct().sorted().toList();
        System.out.printf("4) Gyártók: %s\n",String.join(", ", gyartok));
        String randomGyarto = gyartok.get((int)(Math.random() * gyartok.size()));

        System.out.println("Közülük egy véletlen kiválasztott: " + randomGyarto);
        for(Repulo repulo : repulok){
            if(repulo.getTipus().contains(randomGyarto)){
                System.out.println(" - " + repulo.getTipus());
            }
        }

        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("sokutas.txt"), "utf-8");
            for(Repulo f : repulok){
                if(f.getFerohelyek() > 300) ki.printf(f.toString());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(ki != null) ki.close();
        }
        System.out.printf("5) A 300 főnél több férőhelyű gépek adatai a sokutas.txt fájlba mentve");


    }
}