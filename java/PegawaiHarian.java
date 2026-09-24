public class PegawaiHarian extends Pegawai {

    private final int jumlahHariKerja;
    private final double upahPerHari;

    public PegawaiHarian(String nip, String nama, double gajiPokok, int jumlahHariKerja, double upahPerHari) {
        super(nip, nama, gajiPokok);
        this.jumlahHariKerja = jumlahHariKerja;
        this.upahPerHari = upahPerHari;
    }

    @Override
    public double hitungGaji() {
        // Gaji pokok dasar + total upah harian
        return super.hitungGaji() + (jumlahHariKerja * upahPerHari);
    }

    @Override
    public String jenis() {
        return "HARIAN";
    }

    public int getJumlahHariKerja() {
        return jumlahHariKerja;
    }

    public double getUpahPerHari() {
        return upahPerHari;
    }
}