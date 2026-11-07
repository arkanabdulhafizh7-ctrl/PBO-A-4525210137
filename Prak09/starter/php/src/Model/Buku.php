<?php
declare(strict_types=1);

namespace Perpustakaan\Model;

use InvalidArgumentException;
use RuntimeException;

final class Buku
{
    private int $eksemplarTersedia;

    public function __construct(
        private readonly string $nomorInduk,
        private readonly string $judul,
        private readonly string $penulis,
        private readonly int $tahunTerbit,
        private readonly int $jumlahEksemplar
    ) {
        if (trim($nomorInduk) === '' || trim($judul) === '' || trim($penulis) === '') {
            throw new InvalidArgumentException('Nomor induk, judul, dan penulis tidak boleh kosong.');
        }
        if ($jumlahEksemplar < 0) {
            throw new InvalidArgumentException('Jumlah eksemplar tidak boleh negatif.');
        }
        $this->eksemplarTersedia = $jumlahEksemplar;
    }

    public function masaPinjamHari(): int { return 14; }
    public function tersedia(): bool { return $this->eksemplarTersedia > 0; }

    public function pinjamEksemplar(): void
    {
        if ($this->eksemplarTersedia === 0) {
            throw new RuntimeException('Tidak ada eksemplar tersedia.');
        }
        $this->eksemplarTersedia--;
    }

    public function tambahEksemplar(): void
    {
        if ($this->eksemplarTersedia >= $this->jumlahEksemplar) {
            throw new RuntimeException('Semua eksemplar sudah ada di perpustakaan.');
        }
        $this->eksemplarTersedia++;
    }

    public function getNomorInduk(): string { return $this->nomorInduk; }
    public function getJudul(): string { return $this->judul; }
    public function getPenulis(): string { return $this->penulis; }
    public function getTahunTerbit(): int { return $this->tahunTerbit; }
    public function getEksemplarTersedia(): int { return $this->eksemplarTersedia; }

    public function __toString(): string
    {
        return sprintf('%-32s %-8s [%d eks tersedia]',
            $this->judul, $this->penulis, $this->eksemplarTersedia);
    }
}