public class RekeningBank {
    private final String nomorRekening;
    private String namaPemilik;
    private double saldo;

    public RekeningBank(String nomorRekening, String namaPemilik, double saldoAwal) {
        if (nomorRekening == null || nomorRekening.isBlank()) {
            throw new IllegalArgumentException("Nomor rekening tidak boleh kosong");
        }
        if (saldoAwal < 0) {
            throw new IllegalArgumentException("Saldo awal tidak boleh negatif");
        }
        this.nomorRekening = nomorRekening;
        this.namaPemilik = namaPemilik;
        this.saldo = saldoAwal;
    }

    public void setor(double jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah setor harus lebih dari 0");
        }
        this.saldo += jumlah;
    }

    public void tarik(double jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah tarik harus lebih dari 0");
        }
        if (jumlah > this.saldo) {
            throw new IllegalArgumentException("Saldo tidak cukup");
        }
        this.saldo -= jumlah;
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public double getSaldo() {
        return saldo;
    }

    public void tampilkan() {
        System.out.printf("Rekening: %s | Pemilik: %s | Saldo: Rp%.2f%n",
                nomorRekening, namaPemilik, saldo);
    }
}