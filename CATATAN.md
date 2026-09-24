# Catatan Laporan Praktikum PBO - Modul 4 (Inheritance)

## 1. Overview Praktikum
Praktikum ini membahas penerapan **Pewarisan (Inheritance)** dan **Polimorfisme** pada dua bahasa pemrograman: **Java** dan **PHP**. Program merepresentasikan sistem pengelolaan gaji pegawai dengan hirarki class sebagai berikut:

* **Superclass / Abstract Class:** `Pegawai`[cite: 3]
* **Subclass (Turunan):**
  * `PegawaiTetap` (Turunan dari `Pegawai`)[cite: 2]
  * `Dosen` (Turunan dari `PegawaiTetap`)[cite: 2]
  * `PegawaiKontrak` (Turunan dari `Pegawai`)[cite: 2, 3, 4]
  * `PegawaiHarian` (Turunan dari `Pegawai`)[cite: 4]

---

## 2. Rincian Implementasi Kode

### A. Java
1. **`Pegawai.java` (Abstract Class):**
   * Menyimpan atribut `nip`, `nama`, dan `gajiPokok` dengan akses modifier `protected`[cite: 3].
   * Melakukan validasi gaji pokok tidak boleh negatif (melempar `IllegalArgumentException`)[cite: 3].
   * Mengatur method default `hitungGaji()` dan method abstrak `jenis()`[cite: 3].

2. **`PegawaiHarian.java`:**
   * Mengisi atribut tambahan `jumlahHariKerja` dan `upahPerHari`[cite: 4].
   * Meng-override method `hitungGaji()` dengan rumus: `super.hitungGaji() + (jumlahHariKerja * upahPerHari)`[cite: 4].

3. **`Dosen.java`:**
   * Mewarisi class `PegawaiTetap`[cite: 2].
   * Meng-override method `hitungGaji()` dengan menambah `tunjanganFungsional`[cite: 2].

### B. PHP
1. **`Pegawai.php` & `main.php`:**
   * Menerapkan konsep OOP PHP serupa dengan versi Java[cite: 5, 8].
   * Menggunakan method `__construct()` untuk inisialisasi class dan polymorphism saat iterasi daftar pegawai[cite: 5].

---

## 3. Hasil Output & Running Program

### Running Java (`java/Main.java`)
```text
=== Daftar Gaji ===
198701012010    TETAP     Ani Lestari         Rp0,00
K-2024-007      KONTRAK   Budi Santoso        Rp5.000.000,00
199003052018    DOSEN     Dr. Candra          Rp2.500.000,00
H-2024-001      HARIAN    Dedi Kurnia         Rp5.000.000,00

Total beban gaji: Rp12.500.000,00

Periksa: Ani (pokok 6.000.000, masa kerja 15 tahun)
tunjangan 15 x 2% = 30%, jadi gaji seharusnya Rp7.800.000,00
```[cite: 7]

### Running PHP (`php/main.php`)
```text
=== Daftar Gaji ===
198701012010    TETAP     Ani Lestari         Rp7.800.000,00
K-2024-007      KONTRAK   Budi Santoso        Rp5.000.000,00

Total beban gaji: Rp12.800.000,00

Periksa: Ani (pokok 6.000.000, masa kerja 15 tahun)
tunjangan 15 x 2% = 30%, jadi gaji seharusnya Rp7.800.000,00
```[cite: 8]

---

## 4. Evaluasi & Catatan Peningkatan
* **Perhitungan Gaji `PegawaiTetap` (Java):** Terjadi kejanggalan nilai `Rp0,00` pada output Java untuk pegawai Ani Lestari dikarenakan kalkulasi tunjangan masa kerja belum sepenuhnya terakumulasi pada method overriding[cite: 7].
* **Penggunaan File Gambar pada README:** Pastikan tag HTML `<img src="..." />` pada file `README.md` menggunakan tanda sama dengan (`=`) agar gambar screenshot terenkapsulasi secara sempurna di layar GitHub[cite: 1, 6].