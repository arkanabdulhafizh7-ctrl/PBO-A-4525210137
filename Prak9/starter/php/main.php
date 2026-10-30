<?php
declare(strict_types=1);

require __DIR__ . '/vendor/autoload.php';

use Perpustakaan\Model\Anggota;
use Perpustakaan\Model\Buku;
use Perpustakaan\Service\PerpustakaanService;
use Perpustakaan\Util\Format;

$service = new PerpustakaanService();
$service->tambahKoleksi(new Buku('B001', 'Pemrograman Berorientasi Objek', 'Budi', 2024, 2));
$service->daftarkanAnggota(new Anggota('A001', 'Arkan'));

echo '=== ' . Format::judul('SISTEM PERPUSTAKAAN') . " ===\n";

echo "\n-- Koleksi awal --\n";
foreach ($service->getKoleksi() as $b) {
    echo $b . "\n";
}

$transaksi = $service->pinjam('A001', 'B001');
echo "\n-- Setelah A001 meminjam B001 --\n";
foreach ($service->getKoleksi() as $b) {
    echo $b . "\n";
}
echo Format::kolom('Jatuh tempo', 16) . ': ' . $transaksi->getJatuhTempo()->format('d-m-Y') . "\n";
echo Format::kolom('Sedang dipinjam', 16) . ': ' . count($service->getPeminjamanAktif()) . "\n";

$service->kembalikan('A001', 'B001');
echo "\n-- Setelah dikembalikan --\n";
foreach ($service->getKoleksi() as $b) {
    echo $b . "\n";
}

echo "\n-- Uji error --\n";
try {
    $service->pinjam('A001', 'B999');
} catch (OutOfBoundsException $e) {
    echo 'Gagal: ' . $e->getMessage() . "\n";
}