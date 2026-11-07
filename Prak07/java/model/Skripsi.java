package model;

public final class Skripsi extends Koleksi {
    public Skripsi(String nomorInduk, String judul, String penulis, int tahun,
                   int jumlahEksemplar) {
        super(nomorInduk, judul, penulis, tahun, jumlahEksemplar);
    }

    @Override
    public int masaPinjamHari() { return 0; }

    @Override
    public boolean bolehDipinjam() { return false; }
}
