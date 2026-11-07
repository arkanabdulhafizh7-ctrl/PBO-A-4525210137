<?php
declare(strict_types=1);

use Perpustakaan\Service\LibraryService;

function createReport(LibraryService $service, string $filePath): void
{
    $fileHandle = fopen($filePath, 'wb');
    if ($fileHandle === false) {
        throw new RuntimeException("Gagal membuka file laporan '{$filePath}'.");
    }

    try {
        fwrite($fileHandle, "==========================================================================\n");
        fwrite($fileHandle, "                    LAPORAN DAFTAR BUKU PERPUSTAKAAN                      \n");
        fwrite($fileHandle, "==========================================================================\n");
        fwrite($fileHandle, sprintf("%-12s | %-35s | %-8s | %-15s\n", 'ISBN', 'JUDUL BUKU', 'STOK', 'STATUS'));
        fwrite($fileHandle, "--------------------------------------------------------------------------\n");

        $daftarJudul = $service->getSemuaJudul();
        if ($daftarJudul === []) {
            fwrite($fileHandle, "Belum ada data buku dalam sistem.\n");
        }

        foreach ($daftarJudul as $isbn => $judulBuku) {
            $isbn = (string) $isbn;
            $stokSisa = $service->sisaEksemplar($isbn);
            $status = $stokSisa > 0 ? 'Tersedia' : 'Tidak Tersedia';
            fwrite($fileHandle, sprintf("%-12s | %-35s | %-8d | %-15s\n", $isbn, $judulBuku, $stokSisa, $status));
        }

        fwrite($fileHandle, "==========================================================================\n");
    } finally {
        fclose($fileHandle);
    }
}
