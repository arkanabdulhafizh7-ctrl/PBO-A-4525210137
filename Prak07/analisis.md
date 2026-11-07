# Analisis rancangan sistem perpustakaan

Dokumen ini menjelaskan keputusan kelas dan relasi yang diturunkan dari
[deskripsi-kebutuhan.md](deskripsi-kebutuhan.md). Penandaan kata benda, kata
kerja, atribut, dan aturan bisnis terdapat pada dokumen kebutuhan tersebut.

## Kelas dan tanggung jawab

| Kelas/tipe | Tanggung jawab utama |
|---|---|
| `Koleksi` | Data umum koleksi, stok tersedia, status, dan operasi inventaris. |
| `Buku`, `Majalah`, `Skripsi` | Menentukan masa pinjam dan apakah jenis koleksi boleh dipinjam. |
| `Buku.Bab` | Bagian buku yang dibuat dan dimiliki oleh buku tersebut. |
| `Anggota` | Identitas immutable, jenis keanggotaan, dan riwayat peminjaman. |
| `Peminjaman` | Tanggal pinjam/jatuh tempo/kembali, status aktif, dan perhitungan denda. |
| `StatusKoleksi` | Empat status koleksi yang telah ditetapkan. |
| `JenisKeanggotaan` | Strategi tarif denda; implementasi baru dapat ditambahkan tanpa mengubah `Anggota`. |
| `KeanggotaanStandar` | Tarif untuk mahasiswa, dosen, dan anggota kehormatan. |
| `PerpustakaanService` | Pendaftaran, pencarian, peminjaman/pengembalian, dan akses daftar/riwayat. |

`Koleksi` menyimpan jumlah eksemplar keseluruhan dan jumlah yang masih tersedia.
Status `DIPINJAM` menunjukkan setidaknya satu transaksi aktif dan kembali menjadi
`TERSEDIA` setelah seluruh pinjaman dikembalikan; eksemplar lain yang masih
tersedia tetap dapat dipinjam. Status `RUSAK` dan `HILANG` memblokir peminjaman.
Skripsi menolak peminjaman melalui perilaku polimorfik, bukan percabangan
berdasarkan nama jenis.

## Relasi dan multiplicity

| Relasi | Jenis | Multiplicity | Alasan |
|---|---|---|---|
| `Koleksi` → `Buku`, `Majalah`, `Skripsi` | Generalisasi | Satu induk untuk setiap subkelas | Ketiganya mewarisi data umum, tetapi masa pinjam dan kebolehan pinjam berbeda. |
| `Buku` → `Bab` | Komposisi | Satu `Buku` memiliki `0..*` `Bab`; satu `Bab` milik tepat satu `Buku` | Bab dibuat oleh buku, constructor Java privat, dan tidak berpindah atau hidup mandiri. |
| `Anggota` → `Peminjaman` | Komposisi | Satu `Anggota` memiliki `0..*` `Peminjaman`; satu `Peminjaman` terkait tepat satu `Anggota` | Riwayat merupakan milik anggota dan setiap transaksi wajib mencatat satu peminjam. |
| `Peminjaman` → `Koleksi` | Asosiasi berarah | Satu `Peminjaman` terkait tepat satu `Koleksi`; satu `Koleksi` dapat muncul pada `0..*` transaksi sepanjang waktu | Koleksi tetap ada ketika peminjaman selesai atau tidak ada transaksi aktif. |
| `Anggota` → `JenisKeanggotaan` | Asosiasi | Setiap anggota memiliki satu strategi jenis keanggotaan | Tarif denda disediakan oleh tipe strategi, bukan ditanam di transaksi atau anggota. |
| `PerpustakaanService` → `Koleksi` | Agregasi | Layanan mengelola `0..*` koleksi | Objek koleksi dibuat terpisah dan tetap dapat hidup di luar layanan. |
| `PerpustakaanService` → `Anggota` | Agregasi | Layanan mendaftarkan `0..*` anggota | Objek anggota dibuat terpisah; layanan mengelolanya tetapi bukan pemilik siklus hidupnya. |

`PerpustakaanService` juga menggunakan `Peminjaman` untuk memulai transaksi.
Riwayat transaksi disimpan oleh `Anggota`, bukan dimiliki sebagai bagian dari
layanan.

## Invariant yang diterapkan

- Nomor induk dan nomor anggota unik pada layanan; nomor anggota tidak dapat
  diganti setelah objek dibuat.
- Jumlah eksemplar tidak boleh negatif; peminjaman memerlukan stok tersedia dan
  koleksi yang boleh dipinjam serta tidak berstatus rusak/hilang.
- Seorang anggota memiliki paling banyak tiga peminjaman aktif.
- Buku mempunyai masa pinjam 14 hari, majalah 3 hari, sedangkan skripsi hanya
  untuk dibaca di tempat.
- Jatuh tempo dihitung dari tanggal pinjam. Tanggal kembali bernilai `null`
  selama pinjaman aktif.
- Denda merupakan jumlah hari terlambat dikali tarif jenis keanggotaan; jenis
  kehormatan bertarif nol.
- Getter daftar mengembalikan salinan tak dapat diubah agar koleksi internal
  tidak dapat dimutasi melalui referensi pemanggil.

## Penerapan pada implementasi

Diagram [uml/perpustakaan-kerangka.puml](uml/perpustakaan-kerangka.puml)
menggambarkan kelas dan relasi tersebut. Implementasi Java berada di
[java/model/](java/model) dan [java/service/](java/service). Implementasi PHP
di [php/relasi.php](php/relasi.php) memperagakan komposisi/agregasi Buku-Bab dan
Jurusan-Mahasiswa; ia bukan implementasi layanan perpustakaan penuh.
