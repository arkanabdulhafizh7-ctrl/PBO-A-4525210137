public class Main {
    public static void main(String[] args) {
        // 1. Buat objek valid, tampilkan
        RekeningBank rekening = new RekeningBank("1234567890", "Arkan", 500000);
        rekening.tampilkan();

        // 2. Perubahan sah
        rekening.setor(100000);
        System.out.println("Setelah setor 100000:");
        rekening.tampilkan();

        // 3. Dua operasi tidak sah
        try {
            RekeningBank rekeningSalah = new RekeningBank("9999999999", "Arkan", -50000);
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal buat rekening: " + e.getMessage());
        }

        try {
            rekening.tarik(999999999);
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal tarik: " + e.getMessage());
        }
    }
}