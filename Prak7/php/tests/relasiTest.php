<?php
declare(strict_types=1);

require_once __DIR__ . '/../relasi.php';

function check(bool $condition, string $message): void
{
    if (!$condition) {
        throw new RuntimeException($message);
    }
}

function expectException(string $class, callable $operation): void
{
    try {
        $operation();
    } catch (Throwable $exception) {
        if ($exception instanceof $class) {
            return;
        }
        throw new RuntimeException('Jenis exception tidak sesuai.', 0, $exception);
    }
    throw new RuntimeException('Exception yang diharapkan tidak terjadi: ' . $class);
}

$buku = new Buku('B-1', 'Buku Uji', 'Penulis', 1);
$buku->tambahBab(1, 'Bab Satu');
$buku->tambahBab(2, 'Bab Dua');
check(count($buku->getBab()) === 2, 'Buku harus menyimpan bab yang dibuatnya.');
$salinanBab = $buku->getBab();
unset($salinanBab[0]);
check(count($buku->getBab()) === 2, 'Perubahan array getter tidak boleh mengubah daftar buku.');
expectException(InvalidArgumentException::class, fn () => $buku->tambahBab(1, 'Duplikat'));
expectException(InvalidArgumentException::class,
    fn () => new Buku('B-NEG', 'Negatif', 'Penulis', -1));
$buku->kurangiStok();
check(!$buku->tersedia(), 'Stok harus habis setelah eksemplar terakhir dipinjam.');
expectException(LogicException::class, fn () => $buku->kurangiStok());

$jurusan = new Jurusan('TI', 'Teknik Informatika');
$mahasiswa = new Mahasiswa('001', 'Ani');
$jurusan->daftarkan($mahasiswa);
$jurusan->daftarkan(new Mahasiswa('001', 'Duplikat'));
check(count($jurusan->getMahasiswa()) === 1, 'Mahasiswa tidak boleh didaftarkan dua kali.');
$salinanMahasiswa = $jurusan->getMahasiswa();
unset($salinanMahasiswa[0]);
check(count($jurusan->getMahasiswa()) === 1, 'Getter harus mengembalikan array terpisah.');
$jurusan->keluarkan($mahasiswa);
check(count($jurusan->getMahasiswa()) === 0, 'Mahasiswa harus terlepas dari jurusan.');
check((string) $mahasiswa === '001 Ani', 'Objek mahasiswa harus tetap hidup setelah dilepas.');

echo "Semua pengujian relasi PHP berhasil.", PHP_EOL;
