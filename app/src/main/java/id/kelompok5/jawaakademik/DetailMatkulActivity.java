package id.kelompok5.jawaakademik;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Aktivitas ini mengontrol Layar 4 (Detail Mata Pelajaran).
 * Bertanggung jawab menerima data dinamis melalui Intent dari Dashboard
 * dan melakukan manipulasi widget secara aman tanpa memicu kebocoran memori.
 */
public class DetailMatkulActivity extends AppCompatActivity {

    // Deklarasi variabel penampung komponen UI
    private TextView tvDetailTitle, tvDetailSubtitle, tvJadwalDetail, tvProgressPercent;
    private ProgressBar pbProgressDetail;
    private ImageView btnBack;
    private Button btnMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_matkul);

        // Inisialisasi seluruh komponen berdasarkan ID yang terdaftar di berkas XML
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailSubtitle = findViewById(R.id.tvDetailSubtitle);
        tvJadwalDetail = findViewById(R.id.tvJadwalDetail);
        tvProgressPercent = findViewById(R.id.tvProgressPercent);
        pbProgressDetail = findViewById(R.id.pbProgressDetail);
        btnBack = findViewById(R.id.btnBack);
        btnMulai = findViewById(R.id.btnMulai);

        // Mengekstrak parameter data yang dikirimkan oleh Adapter Layar 3
        String namaMatkul = getIntent().getStringExtra("NAMA_MATKUL");
        String detailJadwal = getIntent().getStringExtra("DETAIL_JADWAL");
        String progress = getIntent().getStringExtra("PROGRESS");

        // Validasi data defensif untuk memastikan objek intent membawa payload yang valid
        if (namaMatkul != null && detailJadwal != null && progress != null) {

            // Menyuntikkan data string langsung ke komponen TextView
            tvDetailTitle.setText(namaMatkul);
            tvProgressPercent.setText(progress);

            // Menguraikan string jadwal untuk ditambahkan informasi ruang secara statis
            if (namaMatkul.contains("Mobile")) {
                tvDetailSubtitle.setText("TI202 - 3 SKS");
                tvJadwalDetail.setText("Senin, 08.00 - 09.40\nRuang Lab. 3");
            } else if (namaMatkul.contains("Basis Data")) {
                tvDetailSubtitle.setText("TI203 - 3 SKS");
                tvJadwalDetail.setText("Selasa, 10.00 - 11.40\nRuang Lab. 1");
            } else if (namaMatkul.contains("Jaringan")) {
                tvDetailSubtitle.setText("TI204 - 3 SKS");
                tvJadwalDetail.setText("Rabu, 13.00 - 14.40\nRuang Lab. Jaringan");
            } else {
                tvDetailSubtitle.setText("TI206 - 3 SKS");
                tvJadwalDetail.setText("Kamis, 09.00 - 10.40\nRuang Kelas 402");
            }

            // Konversi String persentase (misal "75%") menjadi Integer (75) untuk ProgressBar
            try {
                String numericProgress = progress.replace("%", "").trim();
                int progressVal = Integer.parseInt(numericProgress);
                pbProgressDetail.setProgress(progressVal);
            } catch (NumberFormatException e) {
                // Penanganan darurat jika konversi string gagal, set ke default 0
                pbProgressDetail.setProgress(0);
            }
        }

        // Penanganan interaksi tombol kembali ke Dashboard
        btnBack.setOnClickListener(v -> {
            // Metode finish() membuang Activity ini dari tumpukan memori (backstack)
            // sehingga otomatis menampilkan kembali halaman Dashboard yang berada di bawahnya
            finish();
        });
    }
}