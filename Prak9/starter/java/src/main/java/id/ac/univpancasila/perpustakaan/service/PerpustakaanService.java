package id.ac.univpancasila.perpustakaan.service;

import id.ac.univpancasila.perpustakaan.model.*;
import java.util.*;

public final class PerpustakaanService {
    private final Map<String, Buku> koleksi = new LinkedHashMap<>();
    private final Map<String, Anggota> anggota = new LinkedHashMap<>();

    public void tambahKoleksi(Buku item) {
        if (item == null) {
            throw new IllegalArgumentException("Buku tidak boleh null.");
        }
        if (koleksi.putIfAbsent(item.getNomorInduk(), item) != null) {
            throw new IllegalArgumentException("Nomor induk buku sudah terdaftar.");
        }
    }

    public void daftarkanAnggota(Anggota orang) {
        if (orang == null) {
            throw new IllegalArgumentException("Anggota tidak boleh null.");
        }
        if (anggota.putIfAbsent(orang.getNomorAnggota(), orang) != null) {
            throw new IllegalArgumentException("Nomor anggota sudah terdaftar.");
        }
    }

    public Buku cariKoleksi(String nomorInduk) {
        Buku hasil = koleksi.get(nomorInduk);
        if (hasil == null) {
            throw new NoSuchElementException("Buku tidak ditemukan: " + nomorInduk);
        }
        return hasil;
    }

    public List<Buku> cariKoleksiByKataKunci(String kataKunci) {
        if (kataKunci == null || kataKunci.isBlank()) {
            throw new IllegalArgumentException("Kata kunci pencarian tidak boleh kosong.");
        }
        String kueri = kataKunci.toLowerCase(Locale.ROOT);
        return koleksi.values().stream()
                .filter(item -> item.getNomorInduk().toLowerCase(Locale.ROOT).contains(kueri)
                        || item.getJudul().toLowerCase(Locale.ROOT).contains(kueri)
                        || item.getPenulis().toLowerCase(Locale.ROOT).contains(kueri))
                .toList();
    }

    public void pinjam(String nomorAnggota, String nomorInduk) {
        Anggota peminjam = cariAnggota(nomorAnggota);
        Buku item = cariKoleksi(nomorInduk);
        if (!item.bolehDipinjam()) {
            throw new IllegalStateException("Buku ini hanya boleh dibaca di tempat.");
        }
        if (!item.tersedia()) {
            throw new IllegalStateException("Buku tidak tersedia untuk dipinjam.");
        }
        peminjam.pinjam(item);
    }

    public void kembalikan(String nomorAnggota, String nomorInduk) {
        cariAnggota(nomorAnggota).kembalikan(cariKoleksi(nomorInduk));
    }

    public List<Buku> getDaftarKoleksiYangSedangDipinjam() {
        return anggota.values().stream()
                .flatMap(orang -> orang.getDipinjam().stream())
                .toList();
    }

    public List<Buku> getRiwayatPeminjaman(String nomorAnggota) {
        return cariAnggota(nomorAnggota).getDipinjam();
    }

    public List<Buku> getKoleksi() {
        return List.copyOf(koleksi.values());
    }

    private Anggota cariAnggota(String nomorAnggota) {
        Anggota hasil = anggota.get(nomorAnggota);
        if (hasil == null) {
            throw new NoSuchElementException("Anggota tidak ditemukan: " + nomorAnggota);
        }
        return hasil;
    }
}