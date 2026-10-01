<?php
declare(strict_types=1);

/**
 * Langkah 6 — latihan mandiri.
 *
 * Buat hierarki Notifikasi dengan tiga turunan: Email, SMS, WhatsApp.
 * Lalu lengkapi kirimSemua() TANPA satu pun pemeriksaan tipe.
 */

abstract class Notifikasi
{
    public function __construct(private readonly string $tujuan) {}

    public function getTujuan(): string
    {
        return $this->tujuan;
    }

    abstract public function kirim(string $pesan): void;
    abstract public function saluran(): string;
}

class Email extends Notifikasi
{
    public function kirim(string $pesan): void
    {
        echo sprintf("[%s] %s: %s\n", $this->saluran(), $this->getTujuan(), $pesan);
    }

    public function saluran(): string
    {
        return 'Email';
    }
}

class SMS extends Notifikasi
{
    public function kirim(string $pesan): void
    {
        echo sprintf("[%s] %s: %s\n", $this->saluran(), $this->getTujuan(), $pesan);
    }

    public function saluran(): string
    {
        return 'SMS';
    }
}

class WhatsApp extends Notifikasi
{
    public function kirim(string $pesan): void
    {
        echo sprintf("[%s] %s: %s\n", $this->saluran(), $this->getTujuan(), $pesan);
    }

    public function saluran(): string
    {
        return 'WhatsApp';
    }
}

/**
 * @param Notifikasi[] $daftar
 */
function kirimSemua(array $daftar, string $pesan): void
{
    foreach ($daftar as $notifikasi) {
        $notifikasi->kirim($pesan);
    }
}

kirimSemua([
    new Email('arkankan4525137@univpancasila.ac.id'),
    new SMS('088213432749'),
    new WhatsApp('088213432749'),
], 'Buku yang Anda pesan sudah tersedia.');
