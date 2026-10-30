<?php
declare(strict_types=1);

namespace Perpustakaan\Model;

use InvalidArgumentException;
use RuntimeException;

class Anggota
{
    private const BATAS_PINJAM = 3;

    /** @var Peminjaman[] */
    private array $riwayat = [];

    public function __construct(
        private readonly string $nomorAnggota,
        private readonly string $nama
    ) {
        if (trim($nomorAnggota) === '' || trim($nama) === '') {
            throw new InvalidArgumentException('Nomor anggota dan nama tidak boleh kosong.');
        }
    }

    public function pinjam(Buku $buku): Peminjaman
    {
        $aktif = array_filter($this->riwayat, fn (Peminjaman $p) => $p->isAktif());
        if (count($aktif) >= self::BATAS_PINJAM) {
            throw new RuntimeException('Batas peminjaman tercapai.');
        }
        $buku->pinjamEksemplar();
        $transaksi = new Peminjaman($buku);
        $this->riwayat[] = $transaksi;
        return $transaksi;
    }

    public function kembalikan(Buku $buku): void
    {
        foreach ($this->riwayat as $p) {
            if ($p->isAktif() && $p->getBuku() === $buku) {
                $p->tandaiKembali();
                $buku->tambahEksemplar();
                return;
            }
        }
        throw new RuntimeException('Tidak ada peminjaman aktif untuk buku tersebut.');
    }

    /** @return Peminjaman[] */
    public function getRiwayat(): array { return $this->riwayat; }
    public function getNomorAnggota(): string { return $this->nomorAnggota; }
    public function getNama(): string { return $this->nama; }

    public function __toString(): string { return "{$this->nomorAnggota} - {$this->nama}"; }
}