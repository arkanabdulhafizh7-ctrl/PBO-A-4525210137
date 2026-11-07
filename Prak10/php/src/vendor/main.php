<?php
require dirname(__DIR__) . '/vendor/autoload.php';

use Perpustakaan\Service\LibraryService;
use Perpustakaan\Exception\PerpustakaanException;

require_once __DIR__ . '/util/laporan_buku.php';

/*
 * Demo peminjaman buku dan ekspor laporan.
 */
$s = new LibraryService();
$s->tambahBuku('111', 'Laskar Pelangi', 1);
$s->tambahBuku('222', 'Bumi Manusia', 5);
$s->daftarkanAnggota('A-001');
$s->daftarkanAnggota('A-002');

$percobaan = [
    ['A-001', '111'],
    ['A-002', '111'],
    ['A-002', '999'],
    ['X-999', '222'],
    ['A-001', '222'],
    ['A-001', '222'],
    ['A-001', '222'],
];

foreach ($percobaan as [$id, $isbn]) {
    echo "Anggota $id meminjam ISBN '$isbn' ... ", PHP_EOL;
    try {
        $s->pinjam($id, $isbn);
        echo "[BERHASIL]\n";
    } catch (PerpustakaanException $e) {
        echo "[GAGAL] " . $e->getMessage() . "\n";
    }
}

echo PHP_EOL . "--- Ekspor Laporan Buku ---" . PHP_EOL;
$namaFile = __DIR__ . '/laporan_buku.txt';

try {
    createReport($s, $namaFile);
    echo ">> BERHASIL: Laporan buku beserta statusnya disimpan ke berkas 'laporan_buku.txt'.\n";
} catch (\RuntimeException $e) {
    echo "[ERROR SISTEM] Gagal menyelesaikan laporan: " . $e->getMessage() . "\n";
}
