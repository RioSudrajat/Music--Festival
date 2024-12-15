# Skenario 1: Pengalaman Pelanggan Baru

## _Tujuan_: Memastikan sistem mampu menangani registrasi pelanggan baru, pencarian produk, dan pemrosesan pesanan dengan efisien dan akurat.

1. **Membuat akun baru:**
   `Input`: Pilih opsi "Buat akun baru". Masukkan data pelanggan contoh:
   Sidiq Jabar
   sidiqjabar@gmail.com
   1237050015
   2122 Kerawang
   Kerawang
   IND
   32801

````
`Hasil yang Diharapkan`: Konfirmasi akun berhasil dibuat. Data pelanggan tersimpan dengan benar di sistem

2. Masuk Sebagai Pelanggan Baru:

`Input:` Login menggunakan kredensial akun yang baru dibuat:
```bash
sidiqjabar@gmail.com
1237050015
````

`Hasil yang Diharapkan:` Login berhasil. Pesan sambutan ditampilkan dengan nama pelanggan.

3. **Cari Festival Berdasarkan Kunci Utama (Nama)::**

`Input:` Cari festival berdasarkan nama

```bash
INDIE FEST 2024
```

`Hasil yang Diharapkan:` Detail festival ditampilkan. Jika tidak ditemukan, tampilkan pesan yang sesuai

```bash
Nama: INDIE FEST 2024
Tanggal: 2024-30-12
Harga: 100000
Kota: Bandung
Negara: IND
Ticket tersisa: 80
Genre:
  Indie
  Pop
Artis Unggulan:
  Fiersa Besari
  Dzawin Nur
```

4. **Cari Festival Berdasarkan Kunci Sekunder (Tanggal + Kota):**
   `Input:` Cari festival berdasarkan kunci sekunder.

```bash
2024-30-12, Bandung
```

`Hasil yang Diharapkan:` Daftar festival yang sesuai dengan kunci sekunder ditampilkan. Jika tidak ada, tampilkan pesan yang sesuai.

```bash
Nama: INDIE FEST 2024
Tanggal: 2024-30-12
Harga: 100000
Kota: Bandung
Negara: IND
Ticket tersisa: 80
Genre:
  Indie
  Pop
Artis Unggulan:
  Fiersa Besari
  Dzawin Nur
```

5. **Order Festival (Termasuk yang Habis Terjual dan Tersedia):**

`Input:` Pilih festival untuk dipesan. Sistem akan menanyakan apakah ingin melanjutkan belanja festival (0 - tidak, 1 - ya). Jika tidak, tanyakan kecepatan pengiriman.

```bash
INDIE FEST 2024
1
```

`Hasil yang Diharapkan:` Jika festival habis terjual, tampilkan pesan.

Jika festival tidak ditemukan di katalog, tampilkan pesan yang sesuai.

Daftar festival yang valid ditambahkan ke daftar pesanan sementara.

Tampilkan menu utama setelah selesai.

7. **Riview pembayaran**

`Input:` poloh opsi "View Purchases."

```bash
<int atau char untuk melihat pesanan>
<int atau char untuk konfirmasi pesanan>
<tambahkan ke daftar unshippedOrders>
```

`Hasil yang Diharapkan:` Tampilkan pesanan sementara bersama dengan pesanan sebelumnya.

```bash
Order ID: <generated order id upon processing data and temporary orders>
Nama: sidiq jabar
Email: sidiq jabar@gmail.com
Tanggal Pembayaran: 2024-12-14
Music Festival:
  Nama: INDIE FEST 2024
  Tanggal: 2024-12-30
  Harga: $366.00
  Kota: Bandung
  Negara: IND
  Tiket Tersisa: 80
  Genre:
    Indie
    Pop
  Artis Unggulan:
    Fiersa Besari
    Dzawin Nur


<Prompt untuk konfirmasi pembelian>
<Selamat atas pembelian Anda>
```

# Skenario 2: Penanganan Pesanan oleh Karyawan

## _Tujuan_: Menilai fungsionalitas yang tersedia untuk karyawan, dengan penekanan pada manajemen pesanan dan layanan pelanggan.

- **Login Karyawan:**

`Input:` Pilih opsi "Login sebagai Karyawan" dan masukkan kredensial karyawan.

```bash
4
yulialestari@gmail.com
1237050099
```

`Hasil yang diharapkan:` Login berhasil dengan akses ke menu khusus karyawan.

- **mencari Order:**

`Input:` Gunakan ID pesanan atau nama pelanggan untuk mencari pesanan.

```bash
<int atau char untuk mencari pesanan>
<int atau char untuk mencari berdasarkan ID>
100000001
<int atau char untuk keluar dari pencarian berdasarkan ID>
<int atau char untuk mencari berdasarkan nama>
Sidik Jabar
<int atau char untuk keluar dari pencarian berdasarkan nama>
```

`Hasil yang diharapkan:` Detail pesanan ditampilkan berdasarkan kriteria pencarian.

````bash
<tampilan menu karyawan>

<Masukkan *int atau char* untuk melihat pesanan berdasarkan ID atau *int atau char* untuk melihat pesanan berdasarkan Nama pesan pertama dan terakhir>.

<Enter Oder ID message>

Order ID: 100000001
Nama: Sidiq Jabar
Email: sidiqjabar@gmail.com
Tanggal pembayaran: 2024-12-30
Music Festivals:
  INDIE FEST 2024
Total Harga: 1000000
Kecpatan pengiriman: STANDARD
Status Pengiriman: false

<Masukkan kunci *int atau char* untuk melihat lebih banyak pesanan berdasarkan pesan ID Pesanan>

<Menu karyawan ditampilkan>

<Masukkan *int atau char* untuk melihat pesanan berdasarkan ID atau *int atau char* untuk melihat pesanan berdasarkan Nama pesan pertama dan terakhir>

<Masukkan Nama Depan diikuti dengan pesan Nama Belakang>

Sidiq Jabar

Order ID: 100000001
Nama: sidiq jabar
Email: sidiq jabar@gmail.com
Tanggal Purchased: 2024-12-14
Music Festival:
  Nama: INDIE FEST 2024
  Tanggal: 2024-12-30
  Harga: $366.00
  Kota: Bandung
  Negara: IND
  Tiket Tersisa: 80
  Genre:
    Indie
    Pop
  Artis Unggulan:
    Fiersa Besari
    Dzawin Nur


Total Harga: 100000
Kecepatan Pengiriman: Standard
Status Pengiriman: false

Order ID: 100000001
Nama: sidiq jabar
Email: sidiq jabar@gmail.com
Tanggal Purchased: 2024-12-14
Music Festival:
  Nama: INDIE FEST 2024
  Tanggal: 2024-12-30
  Harga: 100000
  Kota: Bandung
  Negara: IND
  Tiket Tersisa: 80
  Genre:
    Indie
    Pop
  Artis Unggulan:
    Fiersa Besari
    Dzawin Nur


Total Harga: 100000
Kecepatan Pengiriman: Standard
Status Pengiriman: True

- **Lihat dan Prioritaskan Pesanan:**

`Input:` Lihat pesanan dengan prioritas tertinggi.
``` bash
<int atau tombol char untuk menampilkan pesanan yang belum terkirim>
``` bash
`Hasil yang Diharapkan:` Pesanan ditampilkan berdasarkan prioritas, dimulai dari yang tertinggi.

``` bash
<Menu karyawan ditampilkan>

<Pesanan ditampilkan dari heap toString>
````

- **Kirim Pesanan:**

`Input:` Pilih pesanan yang akan dikirim dan konfirmasi pengiriman.

```bash
<int atau char key untuk memproses pesanan yang berada di depan heap (prioritas tertinggi)>

<int atau char key untuk memproses pesanan baru yang ada di depan heap>

<int atau tombol char untuk keluar dari pengiriman menu pesanan>
```

`Hasil yang Diharapkan:` Status pesanan naikTanggald menjadi dikirim. Pesanan dipindahkan dari daftar pesanan yang belum terkirim ke daftar pesanan terkirim.

```bash

```

- **Penutupan Sesi:**

`Input:` Pilih untuk keluar dan menulis perubahan pada file.

```bash

```

`Hasil yang Diharapkan:` Semua perubahan disimpan. Sistem bersiap untuk sesi login berikutnya tanpa kehilangan data.

```bash

```

# Skenario 3: Manajer Memperbarui Katalog dan Menangani Produk

## _Tujuan:_ Untuk menguji kemampuan sistem dalam mengakomodasi katalog produk Tanggal dan pengelolaan inventaris oleh seorang manajer.

- **Login Manajer:**

`Input:` Pilih "Masuk sebagai Manajer" dan gunakan kredensial manajer.

```bash
riosudrajat@gmail.com
1237050057
```

`Hasil yang Diharapkan:` Login berhasil dengan akses ke opsi manajerial tingkat lanjut.

```bash
Selamat datang manajer Rio Sudrajat
```

- **Tanggal Festival Catalog**

`Input:` Cari produk berdasarkan kunci utamanya dan upTanggal detailnya.

```bash
<int atau tombol char untuk masuk ke Festival Tanggal>

INDIE FEST 2024

<int atau tombol char untuk mengubah harga>

200000

<int atau tombol char untuk terus melakukan perubahan>

<int atau tombol char untuk menambahkan Artis Unggulan>

Slank, Sheila On 7

<int atau tombol char untuk mengonfirmasi perubahan>
```

```bash
<masuk ke Festival dengan kunci utama dan pesan detail upTanggal>


Music festival to upTanggal:
Nama: sidiq jabar
Email: sidiq jabar@gmail.com
Tanggal Pembayaran: 2024-12-14
Music Festival:
  Nama: INDIE FEST 2024
  Tanggal: 2024-12-30
  Harga: 100000
  Kota: Bandung
  Negara: IND
  Tiket Tersisa: 80
  Genre:
    Indie
    Pop
  Artis Unggulan:
    Fiersa Besari
    Dzawin Nur

<Menampilkan menu atribut untuk mengubah Festival>

<Ubah pesan harga berdasarkan jumlah>

<Lanjutkan melakukan perubahan atau keluar dari pesan>

<Menampilkan menu atribut untuk mengubah Festival>

<Tambahkan artis yang dipisahkan dengan pesan koma>

<Lanjutkan melakukan perubahan atau keluar dari pesan>

Music festival upTanggald:
Nama: sidiq jabar
Email: sidiq jabar@gmail.com
Tanggal Pembayaran: 2024-12-14
Music Festival:
  Nama: INDIE FEST 2024
  Tanggal: 2024-12-30
  Harga: 200000
  Kota: Bandung
  Negara: IND
  Tiket Tersisa: 80
  Genre:
    Indie
    Pop
  Artis Unggulan:
    Fiersa Besari
    Dzawin Nur
    Slank
    Sheila on 7
```

- **Tambahkan Festival Baru:**

`Input:` Berikan detail untuk produk baru.

```bash
<int atau tombol char untuk menambahkan Festival baru>
INDIE FEST 2024
30-12-2024
100000.0
bandung
IND
80
2
Indie
Pop
2
Fiersa Besari
Dzawin Nur
<int atau tombol char untuk keluar dari loop untuk menambahkan festival>
```

`Hasil yang Diharapkan:` Festival Baru ditambahkan ke katalog. Pesan konfirmasi ditampilkan.

```bash
<Menu manajer ditampilkan>
<Masukkan pesan Nama>
<Masukkan pesan Tanggal>
<Masukkan pesan harga>
<Masukkan pesan kota>
<Masukkan pesan negara>
<Masukkan pesan tiket Tersisa>
<Masukkan string pesan Genre yang dipisahkan koma>
<Masukkan berapa pesan Artis Unggulan>
**** Mungkin tidak ada pesan yang ditampilkan selama entri Artis Unggulan ****

New Tambah Festival:
  Nama: INDIE FEST 2024
  Tanggal: 2024-12-30
  Harga: 200000
  Kota: Bandung
  Negara: IND
  Tiket Tersisa: 80
  Genre:
    Indie
    Pop
  Artis Unggulan:
    Fiersa Besari
    Dzawin Nur
    Slank
    Sheila on 7
```

- **Hapus Produk:**

`Input:` Cari produk berdasarkan kunci utamanya dan konfirmasi penghapusannya.

```bash
<int atau kunci char untuk menghapus Festival>

Festival Musik Ultra Miami 2024

<int atau kunci char untuk mengonfirmasi penghapusan>

<int atau tombol char untuk keluar dari loop penghapusan festival>
```

`Hasil yang Diharapkan:` Produk dihapus dari katalog. Konfirmasi penghapusan ditampilkan.

```bash
<Menu manajer ditampilkan>

<Masuk ke Festival Musik untuk menghapus berdasarkan pesan Namanya>


Menghapus Music Festival:
  Nama: INDIE FEST 2024
  Tanggal: 2024-12-30
  Harga: 200000
  Kota: Bandung
  Negara: IND
  Tiket Tersisa: 80
  Genre:
    Indie
    Pop
  Artis Unggulan:
    Fiersa Besari
    Dzawin Nur
    Slank
    Sheila on 7
<Konfirmasi? pesan>

Dihapus: Festival Musik Ultra Miami 2024
```

- **Penutupan Sesi:**

`Input:` Akhiri sesi dengan menulis perubahan pada file.

```bash
<int atau tombol char untuk keluar dari menu Manajer>

<int atau tombol char untuk keluar dari sesi>
```

`Hasil yang Diharapkan:` Semua perubahan disimpan dengan benar, memastikan integritas data untuk sesi berikutnya.

```bash
<Menu manajer ditampilkan>

<Menu utama ditampilkan>

<Menyimpan perubahan pada pesan file>

Selamat tinggal!
```
