# Deskripsi Kebutuhan — Sistem Perpustakaan

Deskripsi ini menjadi dasar seluruh pekerjaan Anda dari sesi 7 sampai sesi 14.
Bacalah dengan saksama; setiap kalimat mengandung informasi rancangan.

---

Perpustakaan Universitas Pancasila ingin mengganti pencatatan manual dengan
sebuah sistem.

Perpustakaan menyimpan **koleksi**. Setiap koleksi punya nomor induk, judul,
penulis, tahun terbit, dan jumlah eksemplar. Ada tiga jenis koleksi: **buku**
yang dapat dipinjam selama 14 hari, **majalah** yang hanya dapat dipinjam
selama 3 hari, dan **skripsi** yang sama sekali tidak dapat dipinjam dan hanya
boleh dibaca di tempat.

Setiap buku terdiri atas beberapa **bab**, masing-masing punya nomor dan judul.
Bab tidak punya arti apa pun di luar buku yang memuatnya, dan tidak pernah
berpindah dari satu buku ke buku lain.

**Anggota** perpustakaan punya nomor anggota, nama, dan jenis keanggotaan:
mahasiswa, dosen, atau anggota kehormatan. Nomor anggota tidak pernah berubah
setelah didaftarkan. Seorang anggota tidak boleh meminjam lebih dari tiga
koleksi pada saat bersamaan.

Setiap **peminjaman** mencatat satu anggota, satu koleksi, tanggal pinjam,
tanggal jatuh tempo, dan tanggal kembali. Tanggal jatuh tempo dihitung dari
tanggal pinjam ditambah masa pinjam koleksi yang bersangkutan. Selama belum
dikembalikan, tanggal kembali masih kosong.

Koleksi hanya dapat dipinjam bila masih ada eksemplar tersedia. Setiap
peminjaman mengurangi jumlah eksemplar tersedia sebanyak satu, dan setiap
pengembalian menambahkannya kembali.

Keterlambatan pengembalian dikenai **denda** per hari. Besar denda per hari
berbeda menurut jenis keanggotaan: mahasiswa Rp1.000, dosen Rp500, dan anggota
kehormatan bebas denda. Pihak perpustakaan memperkirakan akan ada jenis
keanggotaan baru di kemudian hari.

Sebuah koleksi dapat berstatus **tersedia, dipinjam, rusak, atau hilang**.
Koleksi yang berstatus rusak atau hilang tidak dapat dipinjam apa pun jenisnya.

Petugas perpustakaan dapat menambah koleksi, mendaftarkan anggota, mencatat
peminjaman dan pengembalian, serta melihat daftar koleksi yang sedang dipinjam.
Anggota dapat mencari koleksi dan melihat riwayat peminjamannya sendiri.

---

## Yang harus Anda lakukan

1. Tandai seluruh **kata benda** — calon kelas dan calon atribut.
2. Tandai seluruh **kata kerja** — calon method.
3. Tandai seluruh kalimat yang memuat **"tidak boleh", "harus", "hanya"** —
   itulah invariant dan aturan bisnisnya.
4. Coret kata benda yang ternyata hanya atribut, bukan kelas. Tuliskan alasannya.
5. Perhatikan kalimat *"Bab tidak punya arti apa pun di luar buku yang
   memuatnya"* dan *"Pihak perpustakaan memperkirakan akan ada jenis
   keanggotaan baru"*. Keduanya adalah petunjuk rancangan yang kuat —
   pikirkan apa maksudnya.

---

## Hasil penandaan dan analisis

Penandaan di bawah ini mengelompokkan kata benda menjadi calon kelas dan
atribut, serta kata kerja menjadi calon operasi. Kata benda yang dicoret
adalah atribut, bukan kelas mandiri.

### 1. Kata benda: calon kelas dan atribut

| Kata benda yang ditandai | Hasil | Alasan |
|---|---|---|
| **Perpustakaan**, **petugas** | `PerpustakaanService` | Mengatur pencatatan dan operasi layanan; petugas merupakan peran pengguna, bukan entitas yang dimodelkan. |
| **Koleksi** | `Koleksi` (kelas abstrak) | Memiliki data dan perilaku umum yang diwariskan oleh tiga jenis koleksi. |
| **Buku**, **Majalah**, **Skripsi** | Kelas konkret | Masing-masing mempunyai masa pinjam dan aturan peminjaman berbeda. |
| **Bab** | Kelas bagian `Buku.Bab` | Memiliki identitas dan siklus hidup yang bergantung pada buku. |
| **Anggota** | Kelas | Memiliki identitas tetap, jenis keanggotaan, dan riwayat pinjaman. |
| **Peminjaman** | Kelas | Merekam anggota, koleksi, tanggal, dan status pengembalian sebagai satu transaksi. |
| **Status koleksi** | `StatusKoleksi` (enum) | Himpunan statusnya terbatas: tersedia, dipinjam, rusak, atau hilang. |
| **Jenis keanggotaan** | `JenisKeanggotaan` (strategi/interface) | Perilaku tarif denda dapat diperluas tanpa mengubah kelas anggota. |
| **Mahasiswa**, **Dosen**, **Anggota kehormatan** | Nilai `KeanggotaanStandar` | Dalam kebutuhan ini ketiganya membedakan tarif denda, bukan mempunyai data/identitas anggota yang terpisah. |
| ~~**nomor induk**~~, ~~**judul**~~, ~~**penulis**~~, ~~**tahun terbit**~~, ~~**jumlah eksemplar**~~ | Atribut `Koleksi` | Nilai data koleksi; tidak memiliki identitas atau perilaku mandiri dalam kebutuhan ini. |
| ~~**nomor anggota**~~, ~~**nama**~~ | Atribut `Anggota` | Data identitas anggota. Nomor anggota immutable setelah pendaftaran. |
| ~~**nomor**~~, ~~**judul**~~ (bab) | Atribut `Bab` | Data bagian buku yang tidak memiliki siklus hidup mandiri. |
| ~~**tanggal pinjam**~~, ~~**tanggal jatuh tempo**~~, ~~**tanggal kembali**~~ | Atribut `Peminjaman` | Fakta-fakta pencatatan satu transaksi, bukan objek domain mandiri. |
| ~~**masa pinjam**~~, ~~**denda per hari**~~, ~~**jumlah tersedia**~~ | Atribut/perilaku terkait `Koleksi` atau strategi keanggotaan | Nilainya dipakai sebagai aturan pinjam, inventaris, dan perhitungan denda. |

Kata benda lain yang muncul—**pencatatan**, **eksemplar**, **keterlambatan**,
**pengembalian**, **daftar**, **petugas**, dan **riwayat**—menamai proses,
bagian inventaris, peran pengguna, atau keluaran. Kata-kata tersebut tidak
perlu menjadi kelas terpisah pada model sesi ini: proses menjadi method,
sedangkan riwayat merupakan kumpulan `Peminjaman`.

### 2. Kata kerja: calon method

Kata kerja yang ditandai pada kebutuhan dan calon operasi yang dihasilkan:

| Kata kerja/frasa kerja | Calon method atau tanggung jawab |
|---|---|
| **ingin mengganti** pencatatan manual | Tujuan sistem; tidak menjadi method domain tersendiri. |
| **menyimpan** koleksi | `tambahKoleksi(koleksi)` |
| **punya** / **ada** | Menyatakan kepemilikan atribut, multiplicity, atau keberadaan stok. |
| **memiliki** / **terdiri atas** | Relasi dan akses data koleksi/bab |
| **memuat** bab | `Buku.tambahBab(nomor, judul)` |
| **berpindah** | Tidak disediakan operasi pemindahan bab; bab terikat pada buku pembuatnya. |
| **dapat dipinjam** / **meminjam** / **dipinjam** | `bolehDipinjam()`, `pinjam(...)`, `Koleksi.pinjamEksemplar()` |
| **dibaca** di tempat | Aturan bahwa skripsi tidak memiliki operasi peminjaman keluar. |
| **mendaftarkan** anggota | `daftarkanAnggota(anggota)` |
| **berubah** | Status anggota tidak dapat diubah; identitas anggota dijaga immutable. |
| **mencatat** peminjaman/pengembalian | `pinjam(...)`, `kembalikan(...)` |
| **dihitung** / **ditambah** / **dikembalikan** / **mengurangi** | Hitung jatuh tempo dan denda; tambah/kurangi stok tersedia. |
| **dikenai** denda / **berbeda** / **bebas** denda | `Peminjaman.hitungDenda(...)`, `JenisKeanggotaan.dendaPerHari()` |
| **memperkirakan** jenis baru | Petunjuk agar perhitungan denda dapat diperluas tanpa mengubah `Anggota`. |
| **berstatus** | `Koleksi.getStatus()` dan `Koleksi.ubahStatus(...)` |
| **menambah** / **mendaftarkan** / **mencatat** | Operasi petugas pada layanan peminjaman. |
| **melihat** daftar pinjaman | `getDaftarKoleksiYangSedangDipinjam()` |
| **mencari** koleksi | `cariKoleksi(...)`, `cariKoleksiByKataKunci(...)` |
| **melihat** riwayat sendiri | `getRiwayatPeminjaman(nomorAnggota)` |

### 3. Invariant dan aturan bisnis

Kalimat utuh berikut ditandai karena memuat kata kunci *tidak boleh* atau
*hanya* (frasa pembatasnya ditebalkan):

> Ada tiga jenis koleksi: buku yang dapat dipinjam selama 14 hari, majalah yang
> **hanya dapat dipinjam selama 3 hari**, dan skripsi yang **sama sekali tidak
> dapat dipinjam** dan **hanya boleh dibaca di tempat**.
>
> **Seorang anggota tidak boleh meminjam lebih dari tiga koleksi pada saat
> bersamaan.**
>
> Koleksi **hanya dapat dipinjam bila masih ada eksemplar tersedia**.
>
> Koleksi yang berstatus rusak atau hilang **tidak dapat dipinjam apa pun
> jenisnya**.

Kata **harus** tidak muncul pada narasi kebutuhan awal; batas wajibnya dinyatakan
dengan frasa larangan di atas dan ketentuan tanggal kembali yang masih kosong
selama pinjaman aktif. Kalimat aturan lain yang tidak memakai tiga kata kunci
tetap dicatat berikut:

1. **“Bab tidak punya arti apa pun di luar buku yang memuatnya”** dan **“tidak
   pernah berpindah dari satu buku ke buku lain.”** `Bab` adalah bagian komposisi
   `Buku`; constructor Java dibuat privat dan objek dibuat oleh `Buku`.
2. **“Nomor anggota tidak pernah berubah setelah didaftarkan.”** Nomor anggota
   bersifat immutable.
3. **“Seorang anggota tidak boleh meminjam lebih dari tiga koleksi pada saat
   bersamaan.”** Maksimal tiga peminjaman aktif per anggota.
4. **“Selama belum dikembalikan, tanggal kembali masih kosong.”** Peminjaman
   aktif mempunyai tanggal kembali `null`.
5. **“Koleksi hanya dapat dipinjam bila masih ada eksemplar tersedia.”** Stok
   tersedia harus lebih dari nol; peminjaman mengurangi satu, pengembalian
   menambah satu.
6. **“Skripsi sama sekali tidak dapat dipinjam dan hanya boleh dibaca di
   tempat.”** Skripsi menolak transaksi peminjaman.
7. **“Koleksi yang berstatus rusak atau hilang tidak dapat dipinjam apa pun
   jenisnya.”** Pengecekan status berlaku untuk semua turunan `Koleksi`.
8. Masa pinjam buku 14 hari dan majalah 3 hari; jatuh tempo adalah tanggal
   pinjam ditambah masa pinjam.
9. Denda keterlambatan per hari: mahasiswa Rp1.000, dosen Rp500, anggota
   kehormatan Rp0. Jenis baru harus dapat menetapkan tarifnya sendiri.
10. Petugas dapat menambah koleksi, mendaftarkan anggota, meminjamkan dan
    menerima pengembalian, serta melihat pinjaman aktif. Anggota dapat mencari
    koleksi dan melihat riwayatnya sendiri.

### 4. Kata benda yang dicoret dan alasannya

`~~Nomor induk~~`, `~~judul~~`, `~~penulis~~`, `~~tahun terbit~~`, `~~jumlah
eksemplar~~`, `~~nomor anggota~~`, `~~nama~~`, `~~tanggal pinjam~~`, `~~tanggal
jatuh tempo~~`, `~~tanggal kembali~~`, `~~nomor bab~~`, dan `~~judul bab~~`
adalah atribut: semuanya merupakan nilai yang melekat pada objek kelas lain.
Memisahkannya menjadi kelas tidak memberi identitas, perilaku, atau siklus hidup
mandiri yang diperlukan.

### 5. Petunjuk rancangan

- Bab mengikuti **komposisi**: buku membuat bab, constructor Java bab bersifat
  privat, dan getter mengembalikan daftar yang tidak dapat dimutasi.
- Kemungkinan jenis keanggotaan baru berarti tarif denda tidak ditanam sebagai
  percabangan tetap di `Anggota`; `JenisKeanggotaan` menyediakan perilaku tarif
  yang dapat diperluas.
- `PerpustakaanService` mengagregasi koleksi dan anggota karena objek-objek
  tersebut dibuat terpisah dan dikelola oleh layanan, bukan diciptakan sebagai
  bagian hidup-mati dari layanan.

Rincian relasi dan bukti eksekusi tersedia di [analisis.md](analisis.md) dan
[bukti-relasi.md](bukti-relasi.md).
