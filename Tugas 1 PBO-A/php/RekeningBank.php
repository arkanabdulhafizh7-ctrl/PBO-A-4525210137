<?php
declare(strict_types=1);

class RekeningBank {
    private string $nomorRekening;
    private string $namaPemilik;
    private float $saldo;

    public function __construct(string $nomorRekening, string $namaPemilik, float $saldoAwal) {
        if (trim($nomorRekening) === '') {
            throw new InvalidArgumentException("Nomor rekening tidak boleh kosong");
        }
        if ($saldoAwal < 0) {
            throw new InvalidArgumentException("Saldo awal tidak boleh negatif");
        }
        $this->nomorRekening = $nomorRekening;
        $this->namaPemilik = $namaPemilik;
        $this->saldo = $saldoAwal;
    }

    public function setor(float $jumlah): void {
        if ($jumlah <= 0) {
            throw new InvalidArgumentException("Jumlah setor harus lebih dari 0");
        }
        $this->saldo += $jumlah;
    }

    public function tarik(float $jumlah): void {
        if ($jumlah <= 0) {
            throw new InvalidArgumentException("Jumlah tarik harus lebih dari 0");
        }
        if ($jumlah > $this->saldo) {
            throw new InvalidArgumentException("Saldo tidak cukup");
        }
        $this->saldo -= $jumlah;
    }

    public function getNomorRekening(): string {
        return $this->nomorRekening;
    }

    public function getSaldo(): float {
        return $this->saldo;
    }

    public function tampilkan(): void {
        printf("Rekening: %s | Pemilik: %s | Saldo: Rp%.2f\n",
            $this->nomorRekening, $this->namaPemilik, $this->saldo);
    }
}