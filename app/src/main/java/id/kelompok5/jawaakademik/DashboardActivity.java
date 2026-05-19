package id.kelompok5.jawaakademik;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView rvMataPelajaran;
    private MataPelajaranAdapter adapter;
    private List<MataPelajaran> listMataPelajaran;
    private TextView tvWelcome, tvNim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvNim = findViewById(R.id.tvNim);
        findViewById(R.id.btnBackDashboard).setOnClickListener(v -> finish());

        String username = getSharedPreferences("USER_DATA", MODE_PRIVATE)
                .getString("USERNAME", "");

        if (username == null || username.isEmpty()) {
            username = "Pelajar";
        }

        tvWelcome.setText("Halo, " + username);
        tvNim.setText("Semangat Belajarnya!!!");

        // --- 2. Konfigurasi RecyclerView ---
        rvMataPelajaran = findViewById(R.id.rvMataPelajaran);
        rvMataPelajaran.setLayoutManager(new LinearLayoutManager(this));

        // --- 3. Menyiapkan Data Mata Pelajaran ---
        siapkanDataMataPelajaran();

        // --- 4. Memasang Adapter ke RecyclerView ---
        adapter = new MataPelajaranAdapter(listMataPelajaran);
        rvMataPelajaran.setAdapter(adapter);

        // ==============================================
        // TAMBAHAN: LOGIKA BOTTOM NAVIGATION
        // ==============================================
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_beranda);

            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_beranda) {
                    return true;
                } else if (itemId == R.id.navigation_kelas) {
                    // SEKARANG SUDAH BISA KE LAYAR 7 (MATERI)
                    startActivity(new Intent(getApplicationContext(), MateriActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.navigation_profil) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
        // ==============================================
    }

    private void siapkanDataMataPelajaran() {
        listMataPelajaran = new ArrayList<>();
        listMataPelajaran.add(new MataPelajaran("Pemrograman Mobile", "Android, Activity, UI XML", "Bangun aplikasi Android dari layout, intent, list, hingga navigasi.", R.drawable.pemrograman_mobile));
        listMataPelajaran.add(new MataPelajaran("Dasar Pemrograman", "Algoritma, Java, OOP", "Mulai dari logika dasar, struktur data sederhana, dan konsep OOP.", R.drawable.dasar_pemrograman));
        listMataPelajaran.add(new MataPelajaran("Website Development", "HTML, CSS, JavaScript", "Belajar membuat halaman web responsif dan interaktif dari awal.", R.drawable.website_development));
        listMataPelajaran.add(new MataPelajaran("Backend dan API", "REST API, JSON, Server", "Pahami cara aplikasi berkomunikasi dengan server dan database.", R.drawable.backend_dan_api));
        listMataPelajaran.add(new MataPelajaran("Basis Data", "SQL, ERD, Relasi", "Rancang database, relasi tabel, dan query untuk kebutuhan aplikasi.", R.drawable.basis_data));
        listMataPelajaran.add(new MataPelajaran("Jaringan Komputer", "TCP/IP, OSI, Routing", "Pelajari pondasi jaringan, alamat IP, protokol, dan keamanan dasar.", R.drawable.jaringan_komputer));
        listMataPelajaran.add(new MataPelajaran("Git dan GitHub", "Version Control", "Kelola perubahan kode, branch, commit, dan kolaborasi project.", R.drawable.github));
        listMataPelajaran.add(new MataPelajaran("Kecerdasan Buatan", "AI, Machine Learning", "Kenali konsep AI modern dan penerapan sederhana dalam aplikasi.", R.drawable.kecerdasan_buatan));
    }
}
