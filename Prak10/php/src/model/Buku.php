<?php

namespace Perpustakaan\Model;

class Buku
{
    private string $isbn;
    private string $judul;
    private int $eksemplar;
    private bool $dipinjam = false;

    public function __construct(string $isbn, string $judul, int $eksemplar)
    {
        $this->isbn = $isbn;
        $this->judul = $judul;
        $this->eksemplar = $eksemplar;
    }

    public function getIsbn(): string { return $this->isbn; }
    public function getJudul(): string { return $this->judul; }
    public function getEksemplar(): int { return $this->eksemplar; }
    public function isDipinjam(): bool { return $this->dipinjam; }
    public function setDipinjam(bool $dipinjam): void { $this->dipinjam = $dipinjam; }
}