package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class Peminjaman {
    private final Anggota anggota;
    private final Koleksi koleksi;
    private final LocalDate tanggalPinjam;
    private final LocalDate tanggalJatuhTempo;
    private LocalDate tanggalKembali;

    Peminjaman(Anggota anggota, Koleksi koleksi, LocalDate tanggalPinjam) {
        if (anggota == null || koleksi == null || tanggalPinjam == null) {
            throw new IllegalArgumentException("Anggota, koleksi, dan tanggal pinjam wajib diisi.");
        }
        if (!koleksi.bolehDipinjam()) {
            throw new IllegalArgumentException("Jenis koleksi ini tidak dapat dipinjam.");
        }
        this.anggota = anggota;
        this.koleksi = koleksi;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalPinjam.plusDays(koleksi.masaPinjamHari());
    }

    void kembalikan(LocalDate tanggal) {
        if (tanggal == null || tanggal.isBefore(tanggalPinjam)) {
            throw new IllegalArgumentException("Tanggal kembali tidak valid.");
        }
        if (tanggalKembali != null) {
            throw new IllegalStateException("Peminjaman ini sudah dikembalikan.");
        }
        tanggalKembali = tanggal;
    }

    public long hitungHariTerlambat(LocalDate tanggalEvaluasi) {
        if (tanggalEvaluasi == null) {
            throw new IllegalArgumentException("Tanggal evaluasi wajib diisi.");
        }
        LocalDate tanggalAcuan = tanggalKembali == null
                ? tanggalEvaluasi
                : (tanggalKembali.isBefore(tanggalEvaluasi) ? tanggalKembali : tanggalEvaluasi);
        return Math.max(0, ChronoUnit.DAYS.between(tanggalJatuhTempo, tanggalAcuan));
    }

    public long hitungDenda(LocalDate tanggalEvaluasi) {
        return Math.multiplyExact(
                hitungHariTerlambat(tanggalEvaluasi),
                anggota.getJenisKeanggotaan().dendaPerHari());
    }

    public Anggota getAnggota() { return anggota; }
    public Koleksi getKoleksi() { return koleksi; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public LocalDate getTanggalJatuhTempo() { return tanggalJatuhTempo; }
    public LocalDate getTanggalKembali() { return tanggalKembali; }
    public boolean isAktif() { return tanggalKembali == null; }
}
