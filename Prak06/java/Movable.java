/**
 * Sesi 6 — kontrak "bisa bergerak".
 * Interface menjawab: APA YANG BISA dilakukan, bukan APA benda ini.
 */
public interface Movable {

    void bergerak();

    double kecepatanMaksimum();

    default String ringkasanGerak() {
        return String.format("kecepatan maksimum %.0f km/jam", kecepatanMaksimum());
    }
}

