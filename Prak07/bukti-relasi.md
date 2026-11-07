# Bukti implementasi komposisi dan agregasi

## Java: komposisi `Buku`–`Bab`

1. `Bab` adalah kelas bersarang `Buku.Bab` dengan constructor privat; klien
   hanya dapat membuat bagian melalui `Buku.tambahBab(nomor, judul)`.
2. `Buku` memvalidasi nomor dan judul bab serta menolak nomor bab duplikat.
3. `Buku.getBab()` mengembalikan `List.copyOf(bab)`. Mengosongkan daftar hasil
   getter melempar `UnsupportedOperationException`, bukan mengubah daftar buku.
4. `Buku.kurangiStok()` menolak pengurangan saat jumlah tersedia nol.
5. Peminjaman dibuat melalui `Anggota.pinjam`; constructor transaksi dan
   operasi penutupannya tidak dapat diakses langsung dari luar paket model.

Jalankan contoh:

```powershell
Set-Location 'D:\PRAK PBO ARKAN\Prak7\java'
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
java -cp out Main
```

Pada percobaan “mengubah koleksi internal dari luar”, keluaran yang benar
memuat `Ditolak — koleksi bab tidak bisa diubah dari luar. Benar.` Bab tetap
tercetak sebelum dan sesudah percobaan.

## Java: agregasi `Jurusan`–`Mahasiswa`

Mahasiswa dibuat oleh pemanggil dan diberikan ke `Jurusan.daftarkan`. Daftar
hasil getter tidak dapat dimutasi; pendaftaran NIM yang sama tidak menggandakan
anggota. `Jurusan.keluarkan` hanya melepaskan referensi jurusan. Variabel
`budi` pada `Main` tetap merujuk ke objek setelah dikeluarkan dan masih dapat
dicetak.

## PHP: komposisi dan agregasi

`Buku.tambahBab` membuat `Bab` dari nomor dan judul, bukan menerima objek `Bab`
dari pemanggil. PHP tidak menyediakan nested class dengan constructor privat
yang hanya dapat dipanggil oleh kelas luar tertentu. Karena itu constructor
`Bab` harus dapat dipanggil dan aturan “dibuat hanya oleh Buku” dijaga sebagai
konvensi API. Objek `Bab` bersifat `readonly`, sedangkan array dari
`Buku.getBab()` dikembalikan secara by-value: struktur array pemanggil terpisah,
dan objek yang dibagikan di dalamnya tidak dapat diubah.

`Jurusan.daftarkan` menerima objek `Mahasiswa` yang dibuat dari luar dan
mencegah NIM terdaftar dua kali. `keluarkan` menghapus keanggotaan saja; objek
mahasiswa tetap dapat digunakan oleh pemanggil.

Jalankan demo PHP:

```powershell
Set-Location 'D:\PRAK PBO ARKAN\Prak7\php'
php -l relasi.php
php -l main.php
php main.php
```

Output harus menampilkan tiga bab, jumlah mahasiswa turun dari dua menjadi satu
setelah Budi dikeluarkan, dan objek Budi tetap tercetak.
