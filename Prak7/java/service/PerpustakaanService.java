package service;

import model.Anggota;
import model.Koleksi;
import model.Peminjaman;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Layanan sirkulasi perpustakaan. Layanan menyimpan referensi ke koleksi dan
 * anggota yang dibuat pihak lain (agregasi), serta mengelola riwayat transaksi.
 */
public final class PerpustakaanService {
    private final Map<String, Koleksi> koleksi = new LinkedHashMap<>();
    private final Map<String, Anggota> anggota = new LinkedHashMap<>();

    public void tambahKoleksi(Koleksi item) {
        if (item == null) {
            throw new IllegalArgumentException("Koleksi tidak boleh null.");
        }
        if (koleksi.putIfAbsent(item.getNomorInduk(), item) != null) {
            throw new IllegalArgumentException("Nomor induk koleksi sudah terdaftar.");
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

    public Koleksi cariKoleksi(String nomorInduk) {
        Koleksi hasil = koleksi.get(nomorInduk);
        if (hasil == null) {
            throw new NoSuchElementException("Koleksi tidak ditemukan: " + nomorInduk);
        }
        return hasil;
    }

    public List<Koleksi> cariKoleksiByKataKunci(String kataKunci) {
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

    public Peminjaman pinjam(String nomorAnggota, String nomorInduk) {
        Anggota peminjam = cariAnggota(nomorAnggota);
        Koleksi item = cariKoleksi(nomorInduk);
        if (!item.bolehDipinjam()) {
            throw new IllegalStateException("Koleksi ini hanya boleh dibaca di tempat.");
        }
        if (!item.tersedia()) {
            throw new IllegalStateException("Koleksi tidak tersedia untuk dipinjam.");
        }

        return peminjam.pinjam(item, LocalDate.now());
    }

    public void kembalikan(String nomorAnggota, String nomorInduk) {
        Anggota peminjam = cariAnggota(nomorAnggota);
        Koleksi item = cariKoleksi(nomorInduk);
        Peminjaman transaksi = peminjam.getRiwayatPeminjaman().stream()
                .filter(Peminjaman::isAktif)
                .filter(record -> record.getKoleksi() == item)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Tidak ada peminjaman aktif untuk anggota dan koleksi tersebut."));

        peminjam.kembalikan(transaksi, LocalDate.now());
    }

    public List<Peminjaman> getDaftarKoleksiYangSedangDipinjam() {
        return anggota.values().stream()
                .flatMap(orang -> orang.getRiwayatPeminjaman().stream())
                .filter(Peminjaman::isAktif)
                .toList();
    }

    public List<Peminjaman> getRiwayatPeminjaman(String nomorAnggota) {
        Anggota peminjam = cariAnggota(nomorAnggota);
        return peminjam.getRiwayatPeminjaman();
    }

    public List<Koleksi> getKoleksi() {
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
