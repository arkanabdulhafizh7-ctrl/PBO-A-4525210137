<?php
declare(strict_types=1);

namespace Perpustakaan\Model;

use Carbon\Carbon;
use RuntimeException;

final class Peminjaman
{
    private readonly Carbon $tanggalPinjam;
    private readonly Carbon $jatuhTempo;
    private ?Carbon $tanggalKembali = null;

    public function __construct(private readonly Buku $buku)
    {
        $this->tanggalPinjam = Carbon::now();
        $this->jatuhTempo = $this->tanggalPinjam->copy()->addDays($buku->masaPinjamHari());
    }

    public function tandaiKembali(): void
    {
        if ($this->tanggalKembali !== null) {
            throw new RuntimeException('Peminjaman sudah dikembalikan.');
        }
        $this->tanggalKembali = Carbon::now();
    }

    public function isAktif(): bool { return $this->tanggalKembali === null; }
    public function getBuku(): Buku { return $this->buku; }
    public function getTanggalPinjam(): Carbon { return $this->tanggalPinjam; }
    public function getJatuhTempo(): Carbon { return $this->jatuhTempo; }

    public function __toString(): string
    {
        return $this->buku->getJudul() . ' (jatuh tempo ' . $this->jatuhTempo->format('d-m-Y') . ')';
    }
}