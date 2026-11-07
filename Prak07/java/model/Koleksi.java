package model;

public abstract class Koleksi {
    private final String nomorInduk;
    private final String judul;
    private final String penulis;
    private final int tahun;
    private int jumlahEksemplar;
    private int eksemplarTersedia;
    private StatusKoleksi status = StatusKoleksi.TERSEDIA;

    protected Koleksi(String nomorInduk, String judul, String penulis, int tahun,
                      int jumlahEksemplar) {
        this.nomorInduk = teksWajib(nomorInduk, "Nomor induk");
        this.judul = teksWajib(judul, "Judul");
        this.penulis = teksWajib(penulis, "Penulis");
        if (tahun < 0) {
            throw new IllegalArgumentException("Tahun terbit tidak boleh negatif.");
        }
        if (jumlahEksemplar < 0) {
            throw new IllegalArgumentException("Jumlah eksemplar tidak boleh negatif.");
        }
        this.tahun = tahun;
        this.jumlahEksemplar = jumlahEksemplar;
        this.eksemplarTersedia = jumlahEksemplar;
    }

    private static String teksWajib(String nilai, String nama) {
        if (nilai == null || nilai.isBlank()) {
            throw new IllegalArgumentException(nama + " tidak boleh kosong.");
        }
        return nilai;
    }

    public abstract int masaPinjamHari();

    public abstract boolean bolehDipinjam();

    public final boolean tersedia() {
        return eksemplarTersedia > 0
                && status != StatusKoleksi.RUSAK
                && status != StatusKoleksi.HILANG;
    }

    public final void pinjamEksemplar() {
        if (!bolehDipinjam()) {
            throw new IllegalStateException("Jenis koleksi ini tidak dapat dipinjam.");
        }
        if (!tersedia()) {
            throw new IllegalStateException("Tidak ada eksemplar yang dapat dipinjam.");
        }
        eksemplarTersedia--;
        status = StatusKoleksi.DIPINJAM;
    }

    public final void kembalikanEksemplar() {
        if (eksemplarTersedia >= jumlahEksemplar) {
            throw new IllegalStateException("Tidak ada peminjaman aktif untuk dikembalikan.");
        }
        eksemplarTersedia++;
        if (eksemplarTersedia == jumlahEksemplar
                && status == StatusKoleksi.DIPINJAM) {
            status = StatusKoleksi.TERSEDIA;
        }
    }

    public final void tambahEksemplar() {
        jumlahEksemplar = Math.addExact(jumlahEksemplar, 1);
        eksemplarTersedia = Math.addExact(eksemplarTersedia, 1);
    }

    public final void ubahStatus(StatusKoleksi statusBaru) {
        if (statusBaru == null || statusBaru == StatusKoleksi.DIPINJAM) {
            throw new IllegalArgumentException(
                    "Status dipinjam hanya berubah melalui transaksi peminjaman.");
        }
        if (eksemplarTersedia != jumlahEksemplar) {
            throw new IllegalStateException(
                    "Status kondisi koleksi tidak dapat diubah saat ada peminjaman aktif.");
        }
        status = statusBaru;
    }

    public final String getNomorInduk() { return nomorInduk; }
    public final String getJudul() { return judul; }
    public final String getPenulis() { return penulis; }
    public final int getTahun() { return tahun; }
    public final int getJumlahEksemplar() { return jumlahEksemplar; }
    public final int getEksemplarTersedia() { return eksemplarTersedia; }
    public final StatusKoleksi getStatus() { return status; }
}
