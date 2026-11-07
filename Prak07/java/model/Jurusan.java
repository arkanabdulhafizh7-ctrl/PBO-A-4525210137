package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Sesi 7 — AGREGASI, sebagai pembanding komposisi pada Buku.
 *
 * Perhatikan bedanya: di sini objek bagian DATANG DARI LUAR.
 * Mahasiswa tetap ada meski Jurusan dibubarkan.
 */
public class Jurusan {

    private final String kode;
    private final String nama;
    private final List<Mahasiswa> mahasiswa = new ArrayList<>();

    public Jurusan(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    /**
     * Menerima objek Mahasiswa dari luar; mahasiswa tetap dapat hidup di luar
     * Jurusan. Identitas NIM mencegah pendaftaran ganda.
     */
    public void daftarkan(Mahasiswa m) {
        if (m == null) {
            throw new IllegalArgumentException("Mahasiswa tidak boleh null.");
        }
        if (mahasiswa.stream().noneMatch(terdaftar -> terdaftar.getNim().equals(m.getNim()))) {
            mahasiswa.add(m);
        }
    }

    /** Melepaskan mahasiswa dari jurusan tanpa menghancurkan objeknya. */
    public void keluarkan(Mahasiswa m) {
        if (m != null) {
            mahasiswa.removeIf(terdaftar -> terdaftar.getNim().equals(m.getNim()));
        }
    }

    /** Mengembalikan salinan tak-berubah. */
    public List<Mahasiswa> getMahasiswa() {
        return List.copyOf(mahasiswa);
    }

    public String getNama() { return nama; }

    @Override
    public String toString() {
        return String.format("%s - %s (%d mahasiswa)", kode, nama, mahasiswa.size());
    }

    /** Constructor PUBLIK — bisa dibuat di luar. Bandingkan dengan Buku.Bab. */
    public static final class Mahasiswa {
        private final String nim;
        private final String nama;

        public Mahasiswa(String nim, String nama) {
            this.nim = nim;
            this.nama = nama;
        }

        public String getNim()  { return nim; }
        public String getNama() { return nama; }

        @Override public String toString() { return nim + " " + nama; }
    }
}
