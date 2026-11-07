# Catatan keputusan desain

## Abstract class dan interface

- `Kendaraan` adalah abstract class karena semua kendaraan memiliki data
  bersama (`merek` dan `tahun`) serta perilaku umum (`umur()`).
- `Movable` adalah interface karena bergerak merupakan kemampuan, bukan
  identitas; sepeda dan mobil dapat sama-sama bergerak tanpa harus berbagi
  implementasi kelas.
- `Fuelable` adalah interface karena hanya sebagian kendaraan membutuhkan
  bahan bakar. Sepeda tidak perlu mengimplementasikannya.

## Pewarisan Java

Java membatasi satu superclass agar pewarisan implementasi dan keadaan tidak
menciptakan konflik beberapa superclass. Banyak interface diperbolehkan karena
interface menyatakan kontrak kemampuan yang dapat digabungkan. Karena itu
`Mobil` dapat `extends Kendaraan` sekaligus `implements Movable, Fuelable`.

## Interface Segregation

`Sepeda` bukan `Fuelable`, sehingga `isiPenuh(sepeda)` ditolak saat kompilasi:
`error: incompatible types: Sepeda cannot be converted to Fuelable`. Penolakan
lebih awal mencegah pemanggilan operasi yang tidak relevan saat program berjalan.
Di PHP, pemanggilan yang sama akan menghasilkan `TypeError` karena argumen
`Sepeda` tidak memenuhi tipe parameter `Fuelable`.

## Trait PHP

`Mobil` dan `Pesanan` menggunakan `Loggable` meskipun tidak memiliki hubungan
pewarisan. Trait berguna untuk perilaku kecil yang benar-benar dapat dipakai
ulang secara horizontal. Trait menjadi berbahaya jika terlalu banyak membawa
keadaan atau ketergantungan tersembunyi, atau jika menciptakan konflik method
yang sulit dipahami antar kelas pengguna.
