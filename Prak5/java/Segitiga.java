public class Segitiga extends BangunDatar {

    private final double sisiA;
    private final double sisiB;
    private final double sisiC;

    public Segitiga(double sisiA, double sisiB, double sisiC) {
        super("Segitiga");
        if (sisiA <= 0 || sisiB <= 0 || sisiC <= 0) {
            throw new IllegalArgumentException("Semua sisi segitiga harus > 0.");
        }
        if (sisiA + sisiB <= sisiC || sisiA + sisiC <= sisiB || sisiB + sisiC <= sisiA) {
            throw new IllegalArgumentException("Ketiga sisi tidak membentuk segitiga.");
        }
        this.sisiA = sisiA;
        this.sisiB = sisiB;
        this.sisiC = sisiC;
    }

    @Override public double luas() {
        double s = keliling() / 2;
        return Math.sqrt(s * (s - sisiA) * (s - sisiB) * (s - sisiC));
    }

    @Override public double keliling() {
        return sisiA + sisiB + sisiC;
    }
}
