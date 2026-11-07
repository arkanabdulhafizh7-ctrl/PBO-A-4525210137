package id.ac.univpancasila.perpustakaan.util;

import org.apache.commons.lang3.StringUtils;

public class Format {
    private Format() { }

    /** Rata kiri dengan lebar tetap. */
    public static String kolom(String teks, int lebar) {
        return StringUtils.rightPad(teks, lebar);
    }

    /** Huruf kapital hanya di awal kalimat. */
    public static String judul(String teks) {
        return StringUtils.capitalize(StringUtils.lowerCase(teks));
    }

    /** Garis pemisah tabel dua kolom. */
    public static String garis(int kiri, int kanan) {
        return "+" + StringUtils.repeat('-', kiri + 2)
                + "+" + StringUtils.repeat('-', kanan + 2) + "+";
    }

    /** Satu baris tabel dua kolom. */
    public static String baris(String label, String nilai, int kiri, int kanan) {
        return "| " + StringUtils.rightPad(label, kiri)
                + " | " + StringUtils.rightPad(nilai, kanan) + " |";
    }
}