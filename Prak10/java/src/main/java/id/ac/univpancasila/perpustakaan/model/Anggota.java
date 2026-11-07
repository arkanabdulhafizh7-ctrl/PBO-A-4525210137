package id.ac.univpancasila.perpustakaan.model;

import java.util.ArrayList;
import java.util.List;

public class Anggota {
    public static final int BATAS_PINJAM = 3;

    private String id;
    private String nama;
    private List<String> isbnDipinjam = new ArrayList<>();

    public Anggota(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }

    public List<String> getIsbnDipinjam() { return isbnDipinjam; }

    public int jumlahPinjaman() { return isbnDipinjam.size(); }

    public boolean sudahMencapaiBatas() {
        return isbnDipinjam.size() >= BATAS_PINJAM;
    }

    public void tambahPinjaman(String isbn) { isbnDipinjam.add(isbn); }
}