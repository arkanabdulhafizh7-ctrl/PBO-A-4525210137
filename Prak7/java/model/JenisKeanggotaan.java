package model;

/**
 * Strategi denda yang dapat diperluas tanpa mengubah Anggota ketika jenis
 * keanggotaan baru ditambahkan.
 */
public interface JenisKeanggotaan {
    long dendaPerHari();
}
