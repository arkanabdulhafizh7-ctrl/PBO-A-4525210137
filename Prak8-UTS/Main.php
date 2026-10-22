<?php

require_once __DIR__ . "/Obat.php";
require_once __DIR__ . "/Pelanggan.php";
require_once __DIR__ . "/Transanksi.php";

$paracetamol = new ObatBebas("Bodrekkkkk", 5000, 20);
$vitaminC = new ObatBebas("Vitamin C", 12000, 10);
$amoxicillin = new ObatKeras("Minyak Telon", 15000, 5);

$daftarObat = [$paracetamol, $vitaminC, $amoxicillin];

echo "=== Daftar Obat ===\n";
foreach ($daftarObat as $obat) {
    echo $obat->getNama() . " | " . $obat->jenis() . " | stok " . $obat->getStok() . "\n";
}
echo "Jumlah obat: " . Obat::$jumlahObat . "\n\n";

$budi = new Pelanggan("Budi", "08123456789");
$sari = new Pelanggan("Sari");

$t1 = new Transaksi($budi, $paracetamol, 3);
$t2 = new Transaksi($sari, $amoxicillin, 2);
$t3 = new Transaksi($budi, $amoxicillin, 10);

echo "=== Transaksi ===\n";
$t1->bayar();
$t2->bayar();
$t3->bayar();

$t1->cetak();
$t2->cetak();
$t3->cetak();

echo "Sisa stok Amoxicillin: " . $amoxicillin->getStok() . "\n";
