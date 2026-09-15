<?php
declare(strict_types=1);
require 'RekeningBank.php';

// 1. Buat objek valid, tampilkan
$rekening = new RekeningBank("1234567890", "Arkan", 500000);
$rekening->tampilkan();

// 2. Perubahan sah
$rekening->setor(100000);
echo "Setelah setor 100000:\n";
$rekening->tampilkan();

// 3. Dua operasi tidak sah
try {
    $rekeningSalah = new RekeningBank("9999999999", "Arkan", -50000);
} catch (InvalidArgumentException $e) {
    echo "Gagal buat rekening: " . $e->getMessage() . "\n";
}

try {
    $rekening->tarik(999999999);
} catch (InvalidArgumentException $e) {
    echo "Gagal tarik: " . $e->getMessage() . "\n";
}