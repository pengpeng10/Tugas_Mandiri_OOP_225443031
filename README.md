## Tugas Mandiri 1 - Sistem Manajemen Perpustakaan Digital

---

## Identitas Mahasiswa
- Nama : Edra Christian D
- NIM  : 225443031
- Kelas: 2AEC2
- Dosen Pengampu: M Harry K Saputra

---

##  Deskripsi Singkat Program
Program ini merupakan implementasi Sistem Manajemen Perpustakaan Digital berbasis Object-Oriented Programming (OOP) menggunakan bahasa Kotlin. Sistem mengelola berbagai koleksi item perpustakaan (Buku, Jurnal, DVD), data anggota perpustakaan, pencatatan transaksi peminjaman & pengembalian, status transaksi, serta perhitungan denda keterlambatan secara otomatis.

---

## Cara Menjalankan Program

### Menggunakan IntelliJ IDEA
1. Buka folder proyek ini di **IntelliJ IDEA**.
2. Tunggu hingga proses indexing selesai.
3. Buka file src/Main.kt.
4. Klik ikon hijau **Run** (segitiga hijau) di sebelah kiri fungsi `fun main()` atau tekan `Shift + F10`.

---

## Struktur Kelas
```
Tugas_Mandiri_OOP_225443031_Edra_Christian/
├── src/
│   ├── Item.kt               # Class abstrak untuk semua jenis item koleksi
│   ├── Book.kt               # Subclass Item buku
│   ├── Journal.kt            # Subclass Item jurnal ilmiah
│   ├── DVD.kt                # Subclass Item DVD multimedia
│   ├── TransactionStatus.kt  # Sealed class untuk Status transaksi
│   ├── Transaction.kt        # Class transaksi peminjaman & pengembalian
│   ├── Member.kt             # Class anggota perpustakaan
│   ├── Library.kt            # Class utama pengelola sistem perpustakaan
│   └── Main.kt               # Entry point program
```

---