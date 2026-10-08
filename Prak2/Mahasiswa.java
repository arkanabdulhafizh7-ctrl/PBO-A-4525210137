/**
 * Sesi 2 — enkapsulasi yang menjaga invariant.
 *
 * Deskripsi masalah:
 *   "Sistem akademik mencatat mahasiswa dengan NIM, nama, dan tiga komponen
 *    nilai: tugas, UTS, dan UAS. NIM tidak pernah berubah setelah mahasiswa
 *    terdaftar. Setiap komponen nilai berada dalam rentang 0 sampai 100.
 *    Nilai akhir dihitung 30% tugas, 30% UTS, 40% UAS."
 */
public class Mahasiswa {

    // Konstanta bobot — jangan menulis angka 0.30 dan 0.40 di dalam method.
    public static final double BOBOT_TUGAS = 0.30;
    public static final double BOBOT_UTS   = 0.30;
    public static final double BOBOT_UAS   = 0.40;

    private static final double NILAI_MIN = 0;
    private static final double NILAI_MAX = 100;

    private final String nim;
    private final String nama;
    private double nilaiTugas;
    private double nilaiUts;
    private double nilaiUas;

    public Mahasiswa(String nim, String nama, double nilaiTugas, double nilaiUts, double nilaiUas) {
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("NIM tidak boleh null atau kosong.");
        }

        pastikanNilaiSah("nilaiTugas", nilaiTugas);
        pastikanNilaiSah("nilaiUts", nilaiUts);
        pastikanNilaiSah("nilaiUas", nilaiUas);

        this.nim = nim.trim();
        this.nama = nama;
        this.nilaiTugas = nilaiTugas;
        this.nilaiUts = nilaiUts;
        this.nilaiUas = nilaiUas;
    }

    private static void pastikanNilaiSah(String namaKomponen, double nilai) {
        if (nilai < NILAI_MIN || nilai > NILAI_MAX) {
            throw new IllegalArgumentException(
                    namaKomponen + " harus berada dalam rentang " + NILAI_MIN + " sampai " + NILAI_MAX + ".");
        }
    }

    public double nilaiAkhir() {
        return (BOBOT_TUGAS * nilaiTugas)
                + (BOBOT_UTS * nilaiUts)
                + (BOBOT_UAS * nilaiUas);
    }

    public String hurufMutu() {
        double akhir = nilaiAkhir();
        if (akhir >= 80) {
            return "A";
        } else if (akhir >= 70) {
            return "B";
        } else if (akhir >= 60) {
            return "C";
        } else if (akhir >= 50) {
            return "D";
        } else {
            return "E";
        }
    }

    public String getNim()  { return nim; }
    public String getNama() { return nama; }
    public double getNilaiAkhir() { return nilaiAkhir(); }

    @Override
    public String toString() {
        return String.format("%-10s %-18s akhir=%6.2f  mutu=%s",
                nim, nama, nilaiAkhir(), hurufMutu());
    }
}
