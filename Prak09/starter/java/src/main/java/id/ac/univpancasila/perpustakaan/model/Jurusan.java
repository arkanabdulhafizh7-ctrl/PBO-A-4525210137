package id.ac.univpancasila.perpustakaan.model;

import java.util.*;

/** Agregasi: objek Mahasiswa datang dari luar dan tetap hidup tanpa Jurusan. */
public class Jurusan {

    private final String kode;
    private final String nama;
    private final List<Mahasiswa> mahasiswa = new ArrayList<>();

    public Jurusan(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    public void daftarkan(Mahasiswa m) {
        if (m == null) {
            throw new IllegalArgumentException("Mahasiswa tidak boleh null.");
        }
        if (mahasiswa.stream().noneMatch(terdaftar -> terdaftar.getNim().equals(m.getNim()))) {
            mahasiswa.add(m);
        }
    }

    public void keluarkan(Mahasiswa m) {
        if (m != null) {
            mahasiswa.removeIf(terdaftar -> terdaftar.getNim().equals(m.getNim()));
        }
    }

    public List<Mahasiswa> getMahasiswa() { return List.copyOf(mahasiswa); }
    public String getKode() { return kode; }
    public String getNama() { return nama; }

    @Override
    public String toString() {
        return String.format("%s - %s (%d mahasiswa)", kode, nama, mahasiswa.size());
    }

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