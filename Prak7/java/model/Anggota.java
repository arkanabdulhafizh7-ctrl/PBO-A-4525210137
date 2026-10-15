package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Anggota {
    private final String nomorAnggota;
    private final String nama;
    private final JenisKeanggotaan jenisKeanggotaan;
    private final List<Peminjaman> riwayatPeminjaman = new ArrayList<>();

    public Anggota(String nomorAnggota, String nama, JenisKeanggotaan jenisKeanggotaan) {
        if (nomorAnggota == null || nomorAnggota.isBlank()) {
            throw new IllegalArgumentException("Nomor anggota tidak boleh kosong.");
        }
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama anggota tidak boleh kosong.");
        }
        if (jenisKeanggotaan == null || jenisKeanggotaan.dendaPerHari() < 0) {
            throw new IllegalArgumentException("Jenis keanggotaan atau tarif denda tidak valid.");
        }
        this.nomorAnggota = nomorAnggota;
        this.nama = nama;
        this.jenisKeanggotaan = jenisKeanggotaan;
    }

    public Peminjaman pinjam(Koleksi koleksi, LocalDate tanggalPinjam) {
        long pinjamanAktif = riwayatPeminjaman.stream().filter(Peminjaman::isAktif).count();
        if (pinjamanAktif >= 3) {
            throw new IllegalStateException("Anggota tidak boleh memiliki lebih dari 3 pinjaman aktif.");
        }
        Peminjaman transaksi = new Peminjaman(this, koleksi, tanggalPinjam);
        koleksi.pinjamEksemplar();
        riwayatPeminjaman.add(transaksi);
        return transaksi;
    }

    public void kembalikan(Peminjaman transaksi, LocalDate tanggalKembali) {
        if (!riwayatPeminjaman.contains(transaksi) || !transaksi.isAktif()) {
            throw new IllegalArgumentException("Peminjaman aktif milik anggota ini tidak ditemukan.");
        }
        transaksi.getKoleksi().kembalikanEksemplar();
        transaksi.kembalikan(tanggalKembali);
    }

    public List<Peminjaman> getRiwayatPeminjaman() {
        return List.copyOf(riwayatPeminjaman);
    }

    public String getNomorAnggota() { return nomorAnggota; }
    public String getNama() { return nama; }
    public JenisKeanggotaan getJenisKeanggotaan() { return jenisKeanggotaan; }
}
