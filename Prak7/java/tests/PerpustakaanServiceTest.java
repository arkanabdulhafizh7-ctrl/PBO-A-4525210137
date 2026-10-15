import model.Anggota;
import model.Buku;
import model.JenisKeanggotaan;
import model.KeanggotaanStandar;
import model.Koleksi;
import model.Majalah;
import model.Peminjaman;
import model.Skripsi;
import model.StatusKoleksi;
import model.Jurusan;
import service.PerpustakaanService;

import java.time.LocalDate;

public final class PerpustakaanServiceTest {
    private PerpustakaanServiceTest() {}

    public static void main(String[] args) {
        testKomposisiDanAgregasi();
        testPeminjamanDanAturanBisnis();
        testDendaDanJenisKeanggotaanEkstensibel();
        System.out.println("Semua pengujian PerpustakaanService berhasil.");
    }

    private static void testKomposisiDanAgregasi() {
        Buku buku = new Buku("B-1", "Buku Uji", "Penulis", 2024, 1);
        buku.tambahBab(1, "Bab Satu");
        expect(UnsupportedOperationException.class, () -> buku.getBab().clear());
        expect(IllegalArgumentException.class, () -> buku.tambahBab(1, "Duplikat"));
        expect(IllegalArgumentException.class,
                () -> new Buku("B-NEG", "Negatif", "Penulis", 2024, -1));

        Jurusan jurusan = new Jurusan("TI", "Teknik Informatika");
        Jurusan.Mahasiswa ani = new Jurusan.Mahasiswa("001", "Ani");
        jurusan.daftarkan(ani);
        jurusan.daftarkan(new Jurusan.Mahasiswa("001", "Duplikat"));
        check(jurusan.getMahasiswa().size() == 1, "NIM duplikat tidak boleh terdaftar.");
        expect(UnsupportedOperationException.class, () -> jurusan.getMahasiswa().clear());
        jurusan.keluarkan(ani);
        check(jurusan.getMahasiswa().isEmpty(), "Mahasiswa harus terlepas dari jurusan.");
        check("001 Ani".equals(ani.toString()), "Objek mahasiswa tetap hidup setelah dilepas.");
    }

    private static void testPeminjamanDanAturanBisnis() {
        PerpustakaanService layanan = new PerpustakaanService();
        Anggota anggota = new Anggota("A-1", "Ani", KeanggotaanStandar.MAHASISWA);
        layanan.daftarkanAnggota(anggota);
        expect(IllegalArgumentException.class, () -> layanan.daftarkanAnggota(anggota));

        Buku buku1 = buku("B-1", "PBO");
        Buku buku2 = buku("B-2", "Basis Data");
        Majalah majalah = new Majalah("M-1", "Majalah Sains", "Editor", 2025, 1);
        Buku buku4 = buku("B-4", "Algoritma");
        for (Koleksi item : new Koleksi[]{buku1, buku2, majalah, buku4}) {
            layanan.tambahKoleksi(item);
        }
        expect(IllegalArgumentException.class, () -> layanan.tambahKoleksi(buku1));

        Peminjaman transaksiBuku = layanan.pinjam("A-1", "B-1");
        layanan.pinjam("A-1", "B-2");
        Peminjaman transaksiMajalah = layanan.pinjam("A-1", "M-1");
        check(transaksiBuku.getTanggalJatuhTempo().equals(LocalDate.now().plusDays(14)),
                "Masa pinjam buku harus 14 hari.");
        check(transaksiMajalah.getTanggalJatuhTempo().equals(LocalDate.now().plusDays(3)),
                "Masa pinjam majalah harus 3 hari.");
        check(buku1.getStatus() == StatusKoleksi.DIPINJAM,
                "Status berubah saat ada peminjaman aktif.");
        expect(IllegalStateException.class, () -> layanan.pinjam("A-1", "B-4"));

        layanan.kembalikan("A-1", "B-1");
        check(!transaksiBuku.isAktif(), "Pengembalian harus menutup transaksi.");
        check(buku1.getEksemplarTersedia() == 1, "Pengembalian harus menambah stok tersedia.");
        check(buku1.getStatus() == StatusKoleksi.TERSEDIA,
                "Status kembali tersedia setelah pengembalian.");
        check(layanan.getRiwayatPeminjaman("A-1").size() == 3,
                "Riwayat harus menyimpan pinjaman yang telah selesai.");
        check(layanan.getDaftarKoleksiYangSedangDipinjam().size() == 2,
                "Daftar aktif tidak boleh memuat transaksi yang sudah selesai.");
        check(layanan.cariKoleksiByKataKunci("sains").size() == 1,
                "Pencarian harus menemukan koleksi berdasarkan judul.");

        Skripsi skripsi = new Skripsi("S-1", "Skripsi Uji", "Penulis", 2025, 1);
        layanan.tambahKoleksi(skripsi);
        expect(IllegalStateException.class, () -> layanan.pinjam("A-1", "S-1"));

        Buku rusak = buku("B-R", "Koleksi Rusak");
        layanan.tambahKoleksi(rusak);
        rusak.ubahStatus(StatusKoleksi.RUSAK);
        expect(IllegalStateException.class, () -> layanan.pinjam("A-1", "B-R"));

        buku4.ubahStatus(StatusKoleksi.HILANG);
        check(!buku4.tersedia(), "Koleksi hilang tidak boleh tersedia untuk dipinjam.");

        Buku multiEksemplar = new Buku("B-M", "Banyak Eksemplar", "Penulis", 2024, 2);
        Anggota anggotaLain = new Anggota("A-2", "Rina", KeanggotaanStandar.DOSEN);
        Peminjaman pinjamanPertama = anggotaLain.pinjam(multiEksemplar, LocalDate.now());
        Peminjaman pinjamanKedua = anggotaLain.pinjam(multiEksemplar, LocalDate.now());
        check(multiEksemplar.getStatus() == StatusKoleksi.DIPINJAM,
                "Status dipinjam harus berlaku selama ada pinjaman aktif.");
        anggotaLain.kembalikan(pinjamanPertama, LocalDate.now());
        check(multiEksemplar.getStatus() == StatusKoleksi.DIPINJAM
                        && multiEksemplar.tersedia(),
                "Stok parsial tersedia sementara status tetap dipinjam.");
        anggotaLain.kembalikan(pinjamanKedua, LocalDate.now());
        check(multiEksemplar.getStatus() == StatusKoleksi.TERSEDIA,
                "Status tersedia setelah seluruh eksemplar dikembalikan.");
    }

    private static void testDendaDanJenisKeanggotaanEkstensibel() {
        Buku buku = buku("B-DENDA", "Pengujian Denda");
        Anggota mahasiswa = new Anggota("A-DENDA", "Budi", KeanggotaanStandar.MAHASISWA);
        LocalDate tanggalPinjam = LocalDate.of(2025, 1, 1);
        Peminjaman transaksi = mahasiswa.pinjam(buku, tanggalPinjam);
        LocalDate jatuhTempo = tanggalPinjam.plusDays(14);
        check(transaksi.hitungDenda(jatuhTempo.plusDays(2)) == 2_000,
                "Denda mahasiswa harus Rp1.000 per hari.");
        mahasiswa.kembalikan(transaksi, jatuhTempo.plusDays(1));
        check(transaksi.hitungDenda(jatuhTempo.plusDays(3)) == 1_000,
                "Denda berhenti dihitung pada tanggal kembali.");

        Anggota kehormatan = new Anggota(
                "A-KEH", "Citra", KeanggotaanStandar.KEHORMATAN);
        Peminjaman bebasDenda = kehormatan.pinjam(buku("B-KEH", "Buku Kehormatan"), tanggalPinjam);
        check(bebasDenda.hitungDenda(jatuhTempo.plusDays(5)) == 0,
                "Anggota kehormatan bebas denda.");

        JenisKeanggotaan jenisBaru = () -> 250;
        Anggota anggotaBaru = new Anggota("A-BARU", "Dewi", jenisBaru);
        Peminjaman dendaBaru = anggotaBaru.pinjam(buku("B-BARU", "Buku Tarif Baru"), tanggalPinjam);
        check(dendaBaru.hitungDenda(jatuhTempo.plusDays(2)) == 500,
                "Jenis keanggotaan baru harus dapat menentukan tarifnya sendiri.");
    }

    private static Buku buku(String nomor, String judul) {
        return new Buku(nomor, judul, "Penulis", 2024, 1);
    }

    private static void check(boolean kondisi, String pesan) {
        if (!kondisi) {
            throw new AssertionError(pesan);
        }
    }

    private static void expect(Class<? extends RuntimeException> jenis, Runnable operasi) {
        try {
            operasi.run();
        } catch (RuntimeException exception) {
            if (jenis.isInstance(exception)) {
                return;
            }
            throw new AssertionError(
                    "Jenis exception salah: " + exception.getClass().getName(), exception);
        }
        throw new AssertionError("Exception tidak terjadi: " + jenis.getName());
    }
}
