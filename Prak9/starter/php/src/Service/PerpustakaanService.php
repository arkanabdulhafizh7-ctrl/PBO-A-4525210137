<?php
declare(strict_types=1);

namespace Perpustakaan\Service;

use InvalidArgumentException;
use OutOfBoundsException;
use Perpustakaan\Model\Anggota;
use Perpustakaan\Model\Buku;
use Perpustakaan\Model\Peminjaman;

final class PerpustakaanService
{
    /** @var array<string, Buku> */
    private array $koleksi = [];
    /** @var array<string, Anggota> */
    private array $anggota = [];

    public function tambahKoleksi(Buku $buku): void
    {
        if (isset($this->koleksi[$buku->getNomorInduk()])) {
            throw new InvalidArgumentException('Nomor induk buku sudah terdaftar.');
        }
        $this->koleksi[$buku->getNomorInduk()] = $buku;
    }

    public function daftarkanAnggota(Anggota $orang): void
    {
        if (isset($this->anggota[$orang->getNomorAnggota()])) {
            throw new InvalidArgumentException('Nomor anggota sudah terdaftar.');
        }
        $this->anggota[$orang->getNomorAnggota()] = $orang;
    }

    public function cariKoleksi(string $nomorInduk): Buku
    {
        return $this->koleksi[$nomorInduk]
            ?? throw new OutOfBoundsException("Buku tidak ditemukan: $nomorInduk");
    }

    public function pinjam(string $nomorAnggota, string $nomorInduk): Peminjaman
    {
        $buku = $this->cariKoleksi($nomorInduk);
        if (!$buku->tersedia()) {
            throw new \RuntimeException('Buku tidak tersedia untuk dipinjam.');
        }
        return $this->cariAnggota($nomorAnggota)->pinjam($buku);
    }

    public function kembalikan(string $nomorAnggota, string $nomorInduk): void
    {
        $this->cariAnggota($nomorAnggota)->kembalikan($this->cariKoleksi($nomorInduk));
    }

    /** @return Peminjaman[] */
    public function getPeminjamanAktif(): array
    {
        $hasil = [];
        foreach ($this->anggota as $orang) {
            foreach ($orang->getRiwayat() as $p) {
                if ($p->isAktif()) {
                    $hasil[] = $p;
                }
            }
        }
        return $hasil;
    }

    /** @return Buku[] */
    public function getKoleksi(): array { return array_values($this->koleksi); }

    private function cariAnggota(string $nomorAnggota): Anggota
    {
        return $this->anggota[$nomorAnggota]
            ?? throw new OutOfBoundsException("Anggota tidak ditemukan: $nomorAnggota");
    }
}