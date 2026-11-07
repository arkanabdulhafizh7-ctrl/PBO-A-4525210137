package id.ac.univpancasila.perpustakaan;

import id.ac.univpancasila.perpustakaan.model.*;
import id.ac.univpancasila.perpustakaan.service.*;
import id.ac.univpancasila.perpustakaan.util.*;

public class App {
    private static final int KIRI = 18;
    private static final int KANAN = 36;

    public static void main(String[] args) {
        PerpustakaanService service = new PerpustakaanService();

        // Agregasi: Mahasiswa dibuat di luar, lalu didaftarkan ke Jurusan
        Jurusan informatika = new Jurusan("IF", "Informatika");
        informatika.daftarkan(new Jurusan.Mahasiswa("2024001", "Arkan"));

        // Komposisi: Bab dibuat di dalam Buku
        Buku buku = new Buku("B001", "Pemrograman Berorientasi Objek", "Budi", 2024, 2);
        buku.tambahBab(1, "Class dan Object");
        buku.tambahBab(2, "Komposisi dan Agregasi");

        Anggota arkan = new Anggota("A001", "Arkan", informatika);
        service.tambahKoleksi(buku);
        service.daftarkanAnggota(arkan);

        System.out.println("Sebelum dipinjam");
        cetak(arkan, buku, informatika, service);

        service.pinjam("A001", "B001");
        System.out.println("\nSetelah dipinjam");
        cetak(arkan, buku, informatika, service);

        service.kembalikan("A001", "B001");
        System.out.println("\nSetelah dikembalikan");
        cetak(arkan, buku, informatika, service);
    }

    private static void cetak(Anggota a, Buku b, Jurusan j, PerpustakaanService s) {
        System.out.println(Format.garis(KIRI, KANAN));
        System.out.println(Format.baris(Format.judul("SISTEM PERPUSTAKAAN"), "Detail", KIRI, KANAN));
        System.out.println(Format.garis(KIRI, KANAN));
        System.out.println(Format.baris("Jurusan", j.toString(), KIRI, KANAN));
        System.out.println(Format.baris("Anggota", a.toString(), KIRI, KANAN));
        System.out.println(Format.baris("Buku", b.getJudul(), KIRI, KANAN));
        System.out.println(Format.baris("Jumlah bab", String.valueOf(b.getBab().size()), KIRI, KANAN));
        System.out.println(Format.baris("Stok tersedia", String.valueOf(b.getEksemplarTersedia()), KIRI, KANAN));
        System.out.println(Format.baris("Sedang dipinjam",
                String.valueOf(s.getDaftarKoleksiYangSedangDipinjam().size()), KIRI, KANAN));
        System.out.println(Format.garis(KIRI, KANAN));
    }
}