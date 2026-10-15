        package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Sesi 7 — KOMPOSISI.
 *
 * Tiga hal yang membuat sebuah relasi benar-benar komposisi:
 *   1. tidak ada constructor atau method yang MENERIMA objek bagian dari luar
 *   2. objek bagian dibuat DI DALAM induk
 *   3. getter mengembalikan salinan yang tidak bisa diubah
 *
 * Bab dibuat sebagai bagian Buku dan tidak diekspos untuk dimutasi dari luar.
 */
public final class Buku extends Koleksi {
    private final List<Bab> bab = new ArrayList<>();

    public Buku(String nomorInduk, String judul, String penulis, int tahunTerbit,
                int jumlahEksemplar) {
        super(nomorInduk, judul, penulis, tahunTerbit, jumlahEksemplar);
    }

    /**
     * Satu-satunya cara Bab lahir: bagian dibuat oleh Buku, bukan diterima
     * sebagai objek dari luar.
     */
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

    /**
     * Mengembalikan salinan yang tidak dapat dimutasi oleh pemanggil.
     */
    public List<Bab> getBab() {
        return List.copyOf(bab);
    }

    @Override
    public int masaPinjamHari() { return 14; }

    @Override
    public boolean bolehDipinjam() { return true; }

    public void kurangiStok() {
        pinjamEksemplar();
    }

    public void tambahStok() { tambahEksemplar(); }

    @Override
    public String toString() {
        return String.format("%-32s %-16s [%d eks tersedia, %d bab]",
                getJudul(), getPenulis(), getEksemplarTersedia(), bab.size());
    }

    public static final class Bab {
        private final int nomor;
        private final String judul;

        private Bab(int nomor, String judul) {
            this.nomor = nomor;
            this.judul = judul;
        }

        public int getNomor()    { return nomor; }
        public String getJudul() { return judul; }

        @Override public String toString() { return "Bab " + nomor + ": " + judul; }
    }
}
