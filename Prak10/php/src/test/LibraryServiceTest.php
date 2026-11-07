<?php
declare(strict_types=1);

use Perpustakaan\Exception\AnggotaTidakDitemukanException;
use Perpustakaan\Exception\BatasPinjamTerlampauiException;
use Perpustakaan\Exception\BukuSedangDipinjamException;
use Perpustakaan\Exception\BukuTidakDitemukanException;
use Perpustakaan\Service\LibraryService;

require dirname(__DIR__, 2) . '/vendor/autoload.php';
require_once dirname(__DIR__) . '/util/laporan_buku.php';

function expect(bool $condition, string $message): void
{
    if (!$condition) {
        throw new RuntimeException($message);
    }
}

$service = new LibraryService();
$service->tambahBuku('111', 'Laskar Pelangi', 1);
$service->tambahBuku('222', 'Bumi Manusia', 5);
$service->daftarkanAnggota('A-001');

$service->pinjam('A-001', '111');
expect($service->sisaEksemplar('111') === 0, 'Stok harus berkurang setelah peminjaman.');

$reportPath = tempnam(sys_get_temp_dir(), 'perpustakaan-report-');
expect($reportPath !== false, 'File sementara untuk laporan harus dapat dibuat.');
try {
    createReport($service, $reportPath);
    $report = file_get_contents($reportPath);
    expect(is_string($report) && str_contains($report, 'Laskar Pelangi'), 'Laporan harus memuat judul buku.');
    expect(is_string($report) && str_contains($report, 'Tidak Tersedia'), 'Laporan harus menampilkan status stok nol.');
} finally {
    unlink($reportPath);
}

try {
    $service->pinjam('A-001', '111');
    throw new RuntimeException('Peminjaman buku habis seharusnya gagal.');
} catch (BukuSedangDipinjamException $exception) {
    expect($exception->getIsbn() === '111', 'Exception harus membawa ISBN buku.');
    expect($exception->getJudul() === 'Laskar Pelangi', 'Exception harus membawa judul buku.');
}

try {
    $service->pinjam('X-999', '222');
    throw new RuntimeException('Anggota yang tidak terdaftar seharusnya gagal.');
} catch (AnggotaTidakDitemukanException $exception) {
    expect($exception->getIdAnggota() === 'X-999', 'Exception harus membawa ID anggota.');
}

try {
    $service->pinjam('A-001', '999');
    throw new RuntimeException('ISBN yang tidak dikenal seharusnya gagal.');
} catch (BukuTidakDitemukanException $exception) {
    expect($exception->getIsbn() === '999', 'Exception harus membawa ISBN yang diminta.');
}

$limitService = new LibraryService();
$limitService->tambahBuku('222', 'Bumi Manusia', 4);
$limitService->daftarkanAnggota('A-002');
for ($i = 0; $i < LibraryService::BATAS_PINJAM; $i++) {
    $limitService->pinjam('A-002', '222');
}

try {
    $limitService->pinjam('A-002', '222');
    throw new RuntimeException('Peminjaman di atas batas seharusnya gagal.');
} catch (BatasPinjamTerlampauiException $exception) {
    expect($exception->getIdAnggota() === 'A-002', 'Exception harus membawa ID anggota.');
    expect($exception->getBatas() === LibraryService::BATAS_PINJAM, 'Exception harus membawa batas.');
    expect($limitService->sisaEksemplar('222') === 1, 'Stok tidak boleh berubah ketika peminjaman gagal.');
}

echo "Semua pengujian LibraryService berhasil.\n";
