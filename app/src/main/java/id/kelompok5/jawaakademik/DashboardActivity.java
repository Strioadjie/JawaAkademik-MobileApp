package id.kelompok5.jawaakademik;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvNim;
    private RecyclerView rvMataPelajaran;
    private MataPelajaranAdapter adapter;
    private List<MataPelajaran> listMataPelajaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // --- 1. Konfigurasi Header ---
        tvNim = findViewById(R.id.tvNim);
        String nim = getIntent().getStringExtra("NIM");
        if (nim != null && !nim.isEmpty()) {
            tvNim.setText("NIM " + nim);
        }

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
                    startActivity(new android.content.Intent(getApplicationContext(), MateriActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_forum) {
                    startActivity(new android.content.Intent(getApplicationContext(), ForumActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_profil) {
                    android.widget.Toast.makeText(DashboardActivity.this, "Halaman Profil Segera Hadir!", android.widget.Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
        }
        // ==============================================
    }

    private void siapkanDataMataPelajaran() {
        listMataPelajaran = new ArrayList<>();
        listMataPelajaran.add(new MataPelajaran("Pemrograman Mobile", "TI202 - 3 SKS\nSenin, 08.00 - 09.40", "75%", R.drawable.img_mobile));
        listMataPelajaran.add(new MataPelajaran("Basis Data", "TI203 - 3 SKS\nSelasa, 10.00 - 11.40", "60%", R.drawable.img_basisdata));
        listMataPelajaran.add(new MataPelajaran("Jaringan Komputer", "TI204 - 3 SKS\nRabu, 13.00 - 14.40", "40%", R.drawable.img_jaringan));
        listMataPelajaran.add(new MataPelajaran("Kecerdasan Buatan", "TI206 - 3 SKS\nKamis, 09.00 - 10.40", "20%", R.drawable.img_ai));
    }
}