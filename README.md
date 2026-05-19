# Jawa Akademik

Jawa Akademik adalah aplikasi pembelajaran online berbasis Android. Aplikasi ini dibuat untuk membantu pengguna mengakses materi pembelajaran pemrograman secara sederhana melalui perangkat mobile.

Aplikasi ini berfokus pada materi dasar seperti pemrograman mobile, dasar pemrograman, website development, backend dan API, basis data, jaringan komputer, Git dan GitHub, serta kecerdasan buatan.

## Fitur Utama

- Landing page sebagai halaman awal aplikasi.
- Login sederhana menggunakan username dan password.
- Penyimpanan username menggunakan SharedPreferences.
- Dashboard dengan sapaan sesuai nama pengguna.
- Daftar topik belajar pada halaman beranda.
- Halaman materi dengan filter Web, Video, dan Semua.
- Materi dibuka menggunakan WebView.
- Halaman profil untuk mengganti nama pengguna.
- Fitur logout untuk menghapus data pengguna lokal.
- Halaman tentang aplikasi.
- Bottom navigation untuk berpindah halaman Beranda, Materi, dan Profil.

## Teknologi yang Digunakan

- Android Studio
- Java
- XML Layout
- Gradle
- SharedPreferences
- RecyclerView
- WebView
- Material Components

## Penyimpanan Data

Aplikasi ini tidak menggunakan database seperti MySQL, SQLite, Room, atau Firebase.

Data kecil seperti username disimpan menggunakan SharedPreferences. SharedPreferences digunakan karena data yang disimpan masih sederhana dan tidak membutuhkan server atau database online.

Contoh data yang disimpan:

- Username pengguna
- Data sesi sederhana setelah login

Saat pengguna logout, data lokal tersebut akan dihapus dan aplikasi kembali ke landing page.

## Struktur Halaman

Beberapa halaman utama dalam aplikasi:

- LandingActivity: halaman awal aplikasi.
- LoginActivity: halaman login pengguna.
- DashboardActivity: halaman beranda dan daftar topik belajar.
- MateriActivity: halaman daftar materi.
- WebContentActivity: halaman untuk membuka materi melalui WebView.
- ProfileActivity: halaman profil, ganti nama, dan logout.
- AboutActivity: halaman tentang aplikasi.

## Cara Menjalankan Project

1. Buka Android Studio.
2. Pilih Open Project.
3. Buka folder project JawaAkademik.
4. Tunggu proses Gradle selesai.
5. Jalankan aplikasi menggunakan emulator atau HP Android.

## Cara Build APK

Untuk membuat file APK:

1. Buka project di Android Studio.
2. Pilih menu Build.
3. Pilih Build Bundle(s) / APK(s).
4. Pilih Build APK(s).
5. Setelah proses selesai, file APK dapat ditemukan di:

```text
app/build/outputs/apk/debug/app-debug.apk
```

APK tersebut dapat diinstall di perangkat Android untuk mencoba aplikasi.

## Catatan

Aplikasi ini dibuat tanpa database agar lebih sederhana dan sesuai dengan kebutuhan project. Materi masih disimpan secara statis di dalam aplikasi, sedangkan konten pembelajaran diarahkan ke halaman web atau video menggunakan WebView.

## Dibuat Oleh

Kelompok 5  
Mahasiswa Teknik Informatika  
Universitas Pamulang
