public class Dosen extends PegawaiTetap {

    private final double tunjanganFungsional;

    public Dosen(String nip, String nama, double gajiPokok, int masaKerjaTahun, double tunjanganFungsional) {
        super(nip, nama, gajiPokok, masaKerjaTahun);
        this.tunjanganFungsional = tunjanganFungsional;
    }

    @Override
    public double hitungGaji() {
        // Gaji PegawaiTetap (pokok + tunjangan masa kerja) + tunjangan fungsional
        return super.hitungGaji() + tunjanganFungsional;
    }

    @Override
    public String jenis() {
        return "DOSEN";
    }

    public double getTunjanganFungsional() {
        return tunjanganFungsional;
    }
}