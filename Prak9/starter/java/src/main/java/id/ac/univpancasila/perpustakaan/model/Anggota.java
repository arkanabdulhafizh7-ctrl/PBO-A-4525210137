package id.ac.univpancasila.perpustakaan.model;

import java.util.*;

public class Anggota {
    private static final int BATAS_PINJAM = 3;

    private final String nomorAnggota;
    private final String nama;
    private final Jurusan jurusan; // agregasi: datang dari luar, boleh null
    private final List<Buku> dipinjam = new ArrayList<>();

    public Anggota(String nomorAnggota, String nama) {
        this(nomorAnggota, nama, null);
    }

    public Anggota(String nomorAnggota, String nama, Jurusan jurusan) {
        if (nomorAnggota == null || nomorAnggota.isBlank()) {
            throw new IllegalArgumentException("Nomor anggota tidak boleh kosong.");
        }
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        this.nomorAnggota = nomorAnggota;
        this.nama = nama;
        this.jurusan = jurusan;
    }

    public void pinjam(Buku buku) {
        if (buku == null) {
            throw new IllegalArgumentException("Buku tidak boleh null.");
        }
        if (dipinjam.size() >= BATAS_PINJAM) {
            throw new IllegalStateException("Batas peminjaman tercapai.");
        }
        if (dipinjam.contains(buku)) {
            throw new IllegalStateException("Buku ini sudah Anda pinjam.");
        }
        buku.pinjamEksemplar();
        dipinjam.add(buku);
    }

    public void kembalikan(Buku buku) {
        if (!dipinjam.remove(buku)) {
            throw new IllegalStateException("Buku ini tidak sedang Anda pinjam.");
        }
        buku.tambahEksemplar();
    }

    public List<Buku> getDipinjam() { return List.copyOf(dipinjam); }
    public String getNomorAnggota() { return nomorAnggota; }
    public String getNama() { return nama; }
    public Jurusan getJurusan() { return jurusan; }

    @Override
    public String toString() { return nomorAnggota + " - " + nama; }
}