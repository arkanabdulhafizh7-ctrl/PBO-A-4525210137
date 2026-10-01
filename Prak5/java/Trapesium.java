public class Trapesium extends BangunDatar {

    private final double sisiAtas;
    private final double sisiBawah;
    private final double tinggi;

    public Trapesium(double sisiAtas, double sisiBawah, double tinggi) {
        super("Trapesium");
        if (sisiAtas <= 0 || sisiBawah <= 0 || tinggi <= 0) {
            throw new IllegalArgumentException("Sisi dan tinggi trapesium harus > 0.");
        }
        this.sisiAtas = sisiAtas;
        this.sisiBawah = sisiBawah;
        this.tinggi = tinggi;
    }

    @Override public double luas() {
        return ((sisiAtas + sisiBawah) / 2) * tinggi;
    }

    @Override public double keliling() {
        double setengahSelisih = Math.abs(sisiBawah - sisiAtas) / 2;
        double sisiMiring = Math.hypot(setengahSelisih, tinggi);
        return sisiAtas + sisiBawah + 2 * sisiMiring;
    }
}
