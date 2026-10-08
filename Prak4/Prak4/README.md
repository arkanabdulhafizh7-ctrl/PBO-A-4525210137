# Tugas 1 — Kelas RekeningBank

## Domain
Rekening Bank

## Deskripsi
Sistem mencatat rekening bank dengan nomor rekening, nama pemilik, dan saldo. Nomor rekening tidak pernah berubah setelah rekening didaftarkan. Saldo tidak boleh negatif, baik saat pembuatan maupun setelah transaksi apa pun.

## Invarian dan Alasannya

1. nomorRekening tidak boleh berubah setelah objek dibuat.
Alasan: nomor rekening adalah identitas unik nasabah di sistem perbankan. Jika nomor rekening bisa diubah setelah dibuat, riwayat transaksi dan identifikasi nasabah menjadi tidak konsisten. Field ini dideklarasikan final di Java dan readonly di PHP, serta tidak memiliki method setter.

2. saldo tidak boleh negatif.
Alasan: saldo negatif tidak punya makna di dunia nyata. Aturan ini dijaga di constructor (menolak saldo awal negatif) dan di method tarik() (menolak penarikan yang melebihi saldo yang tersedia).

## Struktur Folder
tugas-01/
- rancangan.jpg
- README.md
- java/
  - RekeningBank.java
  - Main.java
- php/
  - RekeningBank.php
  - main.php

## Cara Menjalankan

Java:
cd java
javac RekeningBank.java Main.java
java Main

PHP:
cd php
php main.php

## Contoh Output
Rekening: 1234567890 | Pemilik: Arkan | Saldo: Rp500000.00
Setelah setor 100000:
Rekening: 1234567890 | Pemilik: Arkan | Saldo: Rp600000.00
Gagal buat rekening: Saldo awal tidak boleh negatif
Gagal tarik: Saldo tidak cukup

## Deklarasi Penggunaan AI
Tugas ini dikerjakan dengan bantuan AI (Claude) untuk membantu merancang struktur class, menulis kode Java dan PHP, serta memandu proses setup Git dan GitHub. Logika invarian dan validasi dipahami dan dapat dijelaskan sendiri oleh penulis.