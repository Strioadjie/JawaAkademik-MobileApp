package id.kelompok5.jawaakademik;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    // Deklarasi variabel komponen antarmuka
    private TextView tvNim;
    private RecyclerView rvMataPelajaran;

    // Deklarasi variabel untuk data dan jembatan (adapter)
    private MataPelajaranAdapter adapter;
    private List<MataPelajaran> listMataPelajaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Menghubungkan logika Java dengan tata letak visual Dashboard
        setContentView(R.layout.activity_dashboard);

        // --- 1. Konfigurasi Header ---
        tvNim = findViewById(R.id.tvNim);

        // Menangkap data NIM yang dikirimkan secara dinamis dari LoginActivity
        String nim = getIntent().getStringExtra("NIM");
        if (nim != null && !nim.isEmpty()) {
            tvNim.setText("NIM " + nim);
        }

        // --- 2. Konfigurasi RecyclerView ---
        rvMataPelajaran = findViewById(R.id.rvMataPelajaran);

        // LinearLayoutManager digunakan agar daftar berjejer rapi dari atas ke bawah (vertikal)
        rvMataPelajaran.setLayoutManager(new LinearLayoutManager(this));

        // --- 3. Menyiapkan Data Mata Pelajaran ---
        siapkanDataMataPelajaran();

        // --- 4. Memasang Adapter ke RecyclerView ---
        // Menyerahkan data yang sudah disiapkan ke Adapter agar dicetak ke layar
        adapter = new MataPelajaranAdapter(listMataPelajaran);
        rvMataPelajaran.setAdapter(adapter);
    }

    /**
     * Metode khusus untuk memisahkan logika pengisian data.
     * Data ini disusun semirip mungkin dengan purwarupa (Figma) Layar 3 Anda.
     */
    private void siapkanDataMataPelajaran() {
        listMataPelajaran = new ArrayList<>();

        // Perhatikan parameter ke-4 sekarang memanggil R.drawable.nama_gambar_anda
        listMataPelajaran.add(new MataPelajaran("Pemrograman Mobile", "TI202 - 3 SKS\nSenin, 08.00 - 09.40", "75%", R.drawable.img_mobile));
        listMataPelajaran.add(new MataPelajaran("Basis Data", "TI203 - 3 SKS\nSelasa, 10.00 - 11.40", "60%", R.drawable.img_basisdata));
        listMataPelajaran.add(new MataPelajaran("Jaringan Komputer", "TI204 - 3 SKS\nRabu, 13.00 - 14.40", "40%", R.drawable.img_jaringan));
        listMataPelajaran.add(new MataPelajaran("Kecerdasan Buatan", "TI206 - 3 SKS\nKamis, 09.00 - 10.40", "20%", R.drawable.img_ai));
    }
}