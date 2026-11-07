package model;

public final class Majalah extends Koleksi {
    public Majalah(String nomorInduk, String judul, String penulis, int tahun,
                   int jumlahEksemplar) {
        super(nomorInduk, judul, penulis, tahun, jumlahEksemplar);
    }

    @Override
    public int masaPinjamHari() { return 3; }

    @Override
    public boolean bolehDipinjam() { return true; }
}
