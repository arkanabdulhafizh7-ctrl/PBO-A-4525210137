<?php

namespace Perpustakaan\Model;

class Anggota
{
    public const BATAS_PINJAM = 3;

    private string $id;
    private string $nama;
    private array $isbnDipinjam = [];

    public function __construct(string $id, string $nama = '')
    {
        $this->id = $id;
        $this->nama = $nama;
    }

    public function getId(): string { return $this->id; }
    public function getNama(): string { return $this->nama; }
    public function getIsbnDipinjam(): array { return $this->isbnDipinjam; }
    public function jumlahPinjaman(): int { return count($this->isbnDipinjam); }

    public function sudahMencapaiBatas(): bool
    {
        return count($this->isbnDipinjam) >= self::BATAS_PINJAM;
    }

    public function tambahPinjaman(string $isbn): void
    {
        $this->isbnDipinjam[] = $isbn;
    }
}