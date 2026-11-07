package model;

public enum KeanggotaanStandar implements JenisKeanggotaan {
    MAHASISWA(1_000),
    DOSEN(500),
    KEHORMATAN(0);

    private final long dendaPerHari;

    KeanggotaanStandar(long dendaPerHari) {
        this.dendaPerHari = dendaPerHari;
    }

    @Override
    public long dendaPerHari() {
        return dendaPerHari;
    }
}
