public class Repulo {

    private String tipus;
    private double hossz;
    private int suly;
    private int ferohelyek;
    private int uzemanyagtank;


    public Repulo(String tipus, double hossz, int suly, int ferohelyek, int uzemanyagtank) {
        this.tipus = tipus;
        this.hossz = hossz;
        this.suly = suly;
        this.ferohelyek = ferohelyek;
        this.uzemanyagtank = uzemanyagtank;
    }

    public String getTipus() {
        return tipus;
    }

    public double getHossz() {
        return hossz;
    }

    public int getSuly() {
        return suly;
    }

    public int getFerohelyek() {
        return ferohelyek;
    }

    public int getUzemanyagtank() {
        return uzemanyagtank;
    }

    @Override
    public String toString() {
        return tipus + " / " + ferohelyek + " hely\n";
    }
}
