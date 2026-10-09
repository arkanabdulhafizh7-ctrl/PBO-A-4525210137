/**
 * Sesi 3 — constructor berdelegasi, anggota statis, dan konstanta.
 *
 * Invariant:
 *   1. saldo tidak pernah negatif
 *   2. nomor rekening tidak berubah setelah objek dibuat
 *   3. setoran dan penarikan selalu bernilai positif
 */
public class RekeningBank {

    // TODO 1: konstanta bernama (tidak ada lagi angka literal di badan method)
    public static final double BUNGA_TAHUNAN = 0.025;
    public static final double BIAYA_ADMIN = 5000;
    public static final double BATAS_PENARIKAN_SEKALI = 5_000_000;

    // TODO 2: field statis penghitung jumlah rekening (static, privat, awal 0)
    private static int jumlahRekening = 0;

    private final String nomor;
    private final String pemilik;
    private double saldo;

    /**
     * Constructor ringkas.
     * TODO 3: didelegasikan ke constructor lengkap dengan this(...),
     *         sehingga validasi tidak disalin ke sini.
     */
    public RekeningBank(String nomor, String pemilik) {
        this(nomor, pemilik, 0);
    }

    /** Constructor lengkap — SATU-SATUNYA tempat validasi berada. */
    public RekeningBank(String nomor, String pemilik, double saldoAwal) {
        // TODO 4: tolak nomor kosong dan saldo awal negatif
        if (nomor == null || nomor.trim().isEmpty()) {
            throw new RuntimeException("Nomor rekening tidak boleh kosong");
        }
        if (saldoAwal < 0) {
            throw new RuntimeException("Saldo awal tidak boleh negatif");
        }

        this.nomor = nomor;
        this.pemilik = pemilik;
        this.saldo = saldoAwal;

        // TODO 5: penghitung dinaikkan HANYA di constructor lengkap.
        // Constructor ringkas sudah memanggil this(...), jadi kalau ia juga
        // menaikkan penghitung, rekening dari constructor ringkas terhitung
        // dua kali (hasilnya 4, bukan 3).
        jumlahRekening++;
    }

    public void setor(double jumlah) {
        // TODO 6: tolak jumlah <= 0, lalu tambahkan ke saldo
        if (jumlah <= 0) {
            throw new RuntimeException("Jumlah setoran harus lebih dari 0");
        }
        saldo += jumlah;
    }

    public void tarik(double jumlah) {
        // TODO 7: tolak jumlah <= 0, melebihi saldo, dan melebihi batas sekali tarik
        if (jumlah <= 0) {
            throw new RuntimeException("Jumlah penarikan harus lebih dari 0");
        }
        if (jumlah > saldo) {
            throw new RuntimeException("Jumlah penarikan melebihi saldo");
        }
        if (jumlah > BATAS_PENARIKAN_SEKALI) {
            throw new RuntimeException("Jumlah penarikan melebihi batas transaksi");
        }
        saldo -= jumlah;
    }

    /** TODO 8: kurangi saldo sebesar biaya admin, tetapi jangan sampai negatif. */
    public void potongBiayaAdmin() {
        saldo = Math.max(0, saldo - BIAYA_ADMIN);
    }

    /** TODO 9: method statis — jumlah rekening yang pernah dibuat. */
    public static int getJumlahRekening() {
        return jumlahRekening;
    }

    /**
     * TODO 10: method statis utilitas — hitung bunga setahun dari pokok.
     * Tidak membaca keadaan objek mana pun, itulah alasan ia pantas static.
     */
    public static double bungaSetahun(double pokok) {
        return pokok * BUNGA_TAHUNAN;
    }

    public double getSaldo()  { return saldo; }
    public String getNomor()  { return nomor; }

    @Override
    public String toString() {
        return String.format("Rekening[%s] %-14s Rp%,.2f", nomor, pemilik, saldo);
    }
}