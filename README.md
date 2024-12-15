# Music Festival Application

## Overview

Sistem Manajemen Festival Musik adalah aplikasi berbasis Java yang dirancang untuk menyederhanakan dan mengotomatisasi operasi dalam pengelolaan festival musik. Sistem ini mencakup berbagai fungsi mulai dari pemesanan tiket, pengelolaan pelanggan dan karyawan, hingga pemuatan data dan interaksi antarmuka pengguna, menjadikannya solusi yang kuat bagi penyelenggara festival.

## Features

- **Order Management:** Mengelola semua aspek terkait pemesanan tiket, termasuk pembuatan, modifikasi, dan pelacakan order.
- **Festival Management:** Menyediakan alat untuk mengelola detail festival seperti tanggal, susunan artis, dan informasi tempat.
- **User Management:** Memiliki sistem terperinci untuk mengelola pengguna, termasuk pelanggan dan karyawan, dengan peran dan izin yang berbeda.
- **Data Mangement:** Memanfaatkan DataLoader untuk memuat dan mengelola data terkait festival secara efisien.
- **Interactive UI:** Menawarkan kelas FestivalUi yang memfasilitasi interaksi dengan sistem melalui antarmuka yang ramah pengguna.
- **Role-based Access Control:** Membedakan peran antara pelanggan dan manajer, memberikan akses dan fungsi yang sesuai untuk masing-masing.

## Project Structure

- `MusicFestival.java`: Titik masuk utama untuk aplikasi.
- `Festival.java`, `Order.java`, `User.java`, `Customer.java`, `Manager.java`, `Employee.java`: Kelas inti yang mendefinisikan logika bisnis.
- `DataLoader.java`: Mengelola pemuatan data ke dalam sistem.
- `FestivalUi.java`: Menggambarkan berbagai skenario untuk menguji sistem
