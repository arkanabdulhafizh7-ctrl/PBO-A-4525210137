<?php
declare(strict_types=1);

/**
 * Sesi 3 — PHP tidak punya constructor overloading.
 * Padanannya: default parameter + named constructor (static factory).
 */
class RekeningBank
{
    // TODO 1: konstanta bernama (tidak ada lagi angka ajaib di badan method)
    public const BUNGA_TAHUNAN = 0.025;
    public const BIAYA_ADMIN = 5000;
    public const BATAS_PENARIKAN_SEKALI = 5000000;

    // TODO 2: properti statis penghitung jumlah rekening
    private static int $jumlahRekening = 0;

    private float $saldo;

    /**
     * Default parameter menggantikan constructor overloading.
     * TODO 3: validasi nomor kosong dan saldo awal negatif.
     * TODO 4: naikkan penghitung jumlah rekening.
     */
    public function __construct(
        private readonly string $nomor,
        private readonly string $pemilik,
        float $saldoAwal = 0,
    ) {
        // TODO 3
        if (trim($nomor) === '') {
            throw new InvalidArgumentException('Nomor rekening tidak boleh kosong');
        }
        if ($saldoAwal < 0) {
            throw new InvalidArgumentException('Saldo awal tidak boleh negatif');
        }

        $this->saldo = $saldoAwal;

        // TODO 4
        self::$jumlahRekening++;
    }

    /**
     * TODO 5: named constructor — rekening pelajar, saldo awal nol.
     *         Memakai `new static()`, BUKAN `new self()`, agar subclass
     *         yang memanggil factory ini mendapat objek bertipe subclass
     *         itu (late static binding).
     */
    public static function rekeningPelajar(string $nomor, string $pemilik): static
    {
        return new static($nomor, $pemilik, 0);
    }

    public function setor(float $jumlah): void
    {
        // TODO 6
        if ($jumlah <= 0) {
            throw new InvalidArgumentException('Jumlah setoran harus lebih dari 0');
        }
        $this->saldo += $jumlah;
    }

    public function tarik(float $jumlah): void
    {
        // TODO 7
        if ($jumlah <= 0) {
            throw new InvalidArgumentException('Jumlah penarikan harus lebih dari 0');
        }
        if ($jumlah > $this->saldo) {
            throw new RuntimeException('Jumlah penarikan melebihi saldo');
        }
        if ($jumlah > self::BATAS_PENARIKAN_SEKALI) {
            throw new RuntimeException('Jumlah penarikan melebihi batas transaksi');
        }
        $this->saldo -= $jumlah;
    }

    /** TODO 8: kurangi saldo sebesar biaya admin, tetapi jangan sampai negatif. */
    public function potongBiayaAdmin(): void
    {
        $this->saldo = max(0.0, $this->saldo - self::BIAYA_ADMIN);
    }

    /** TODO 9: jumlah rekening yang pernah dibuat. */
    public static function getJumlahRekening(): int
    {
        return self::$jumlahRekening;
    }

    /** TODO 10: method statis utilitas, tidak membaca keadaan objek mana pun. */
    public static function bungaSetahun(float $pokok): float
    {
        return $pokok * self::BUNGA_TAHUNAN;
    }

    public function getSaldo(): float { return $this->saldo; }
    public function getNomor(): string { return $this->nomor; }

    public function __toString(): string
    {
        return sprintf('Rekening[%s] %-14s Rp%s',
            $this->nomor, $this->pemilik, number_format($this->saldo, 2, ',', '.'));
    }
}