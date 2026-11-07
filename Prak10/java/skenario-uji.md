# Skenario uji

`PerpustakaanException` diturunkan dari `RuntimeException` (unchecked).
Kesalahan seperti anggota tidak terdaftar, ISBN tidak ada, stok habis, dan
batas peminjaman terlampaui berasal dari permintaan yang tidak valid menurut
aturan domain. Pemanggil dapat menangkap `PerpustakaanException` sekaligus
menampilkan alasan spesifik tanpa diwajibkan mendeklarasikan atau menangkap
setiap exception pada seluruh lapisan.

## Kasus peminjaman

1. Anggota terdaftar meminjam buku yang tersedia; stok berkurang satu.
2. Anggota yang tidak terdaftar meminjam buku; `AnggotaTidakDitemukanException`
   membawa ID anggota.
3. Anggota terdaftar meminjam ISBN yang tidak tercatat;
   `BukuTidakDitemukanException` membawa ISBN.
4. Anggota terdaftar meminjam buku dengan stok nol;
   `BukuSedangDipinjamException` membawa ISBN dan judul.
5. Anggota yang sudah meminjam tiga buku mencoba meminjam lagi;
   `BatasPinjamTerlampauiException` membawa ID anggota dan batas maksimum.

Pada setiap kasus gagal, stok dan jumlah pinjaman harus tetap tidak berubah.
