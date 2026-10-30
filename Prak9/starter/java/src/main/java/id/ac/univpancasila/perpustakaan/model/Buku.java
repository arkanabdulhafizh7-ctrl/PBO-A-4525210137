package id.ac.univpancasila.perpustakaan.model;

import java.util.*;

public final class Buku {
    private final String nomorInduk;
    private final String judul;
    private final String penulis;
    private final int tahunTerbit;
    private final int jumlahEksemplar;
    private int eksemplarTersedia;
    private final List<Bab> bab = new ArrayList<>();

    public Buku(String nomorInduk, String judul, String penulis, int tahunTerbit,
                int jumlahEksemplar) {
        if (nomorInduk == null || nomorInduk.isBlank()) {
            throw new IllegalArgumentException("Nomor induk tidak boleh kosong.");
        }
        if (judul == null || judul.isBlank()) {
            throw new IllegalArgumentException("Judul tidak boleh kosong.");
        }
        if (penulis == null || penulis.isBlank()) {
            throw new IllegalArgumentException("Penulis tidak boleh kosong.");
        }
        if (jumlahEksemplar < 0) {
            throw new IllegalArgumentException("Jumlah eksemplar tidak boleh negatif.");
        }
        this.nomorInduk = nomorInduk;
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.jumlahEksemplar = jumlahEksemplar;
        this.eksemplarTersedia = jumlahEksemplar;
    }

    /** Komposisi: Bab dibuat DI DALAM Buku, tidak diterima dari luar. */
    public void tambahBab(int nomor, String judulBab) {
        if (nomor < 1) {
            throw new IllegalArgumentException("Nomor bab harus positif.");
        }
        if (judulBab == null || judulBab.isBlank()) {
            throw new IllegalArgumentException("Judul bab tidak boleh kosong.");
        }
        if (bab.stream().anyMatch(b -> b.getNomor() == nomor)) {
            throw new IllegalArgumentException("Nomor bab sudah digunakan.");
        }
        bab.add(new Bab(nomor, judulBab));
    }

    public List<Bab> getBab() { return List.copyOf(bab); }

    public int masaPinjamHari() { return 14; }
    public boolean bolehDipinjam() { return true; }
    public boolean tersedia() { return eksemplarTersedia > 0; }

    void pinjamEksemplar() {
        if (eksemplarTersedia == 0) {
            throw new IllegalStateException("Tidak ada eksemplar tersedia.");
        }
        eksemplarTersedia--;
    }

    void tambahEksemplar() {
        if (eksemplarTersedia >= jumlahEksemplar) {
            throw new IllegalStateException("Semua eksemplar sudah ada di perpustakaan.");
        }
        eksemplarTersedia++;
    }

    public String getNomorInduk() { return nomorInduk; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public int getTahunTerbit() { return tahunTerbit; }
    public int getJumlahEksemplar() { return jumlahEksemplar; }
    public int getEksemplarTersedia() { return eksemplarTersedia; }

    @Override
    public String toString() {
        return String.format("%-32s %-16s [%d eks tersedia, %d bab]",
                judul, penulis, eksemplarTersedia, bab.size());
    }

    public static final class Bab {
        private final int nomor;
        private final String judul;

        private Bab(int nomor, String judul) {
            this.nomor = nomor;
            this.judul = judul;
        }

        public int getNomor() { return nomor; }
        public String getJudul() { return judul; }

        @Override public String toString() { return "Bab " + nomor + ": " + judul; }
    }
}