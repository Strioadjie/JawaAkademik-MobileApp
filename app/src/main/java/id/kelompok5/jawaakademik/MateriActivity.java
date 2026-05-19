package id.kelompok5.jawaakademik;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MateriActivity extends AppCompatActivity {

    private static final int TIPE_SEMUA = 3;
    private static final int TIPE_WEB = 1;
    private static final int TIPE_VIDEO = 2;

    private RecyclerView rvMateri;
    private MateriAdapter adapter;
    private List<DataMateri> semuaMateri;
    private List<DataMateri> materiDifilter;
    private TextView tvMateriTitle, tvFilterLabel;
    private String topikDipilih;
    private int tipeFilter = TIPE_SEMUA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

        topikDipilih = getIntent().getStringExtra("TOPIK_BELAJAR");
        if (topikDipilih == null || topikDipilih.trim().isEmpty()) {
            topikDipilih = "Semua Topik";
        }

        tvMateriTitle = findViewById(R.id.tvMateriTitle);
        tvFilterLabel = findViewById(R.id.tvFilterLabel);
        ImageButton btnFilterMateri = findViewById(R.id.btnFilterMateri);

        tvMateriTitle.setText(topikDipilih);
        findViewById(R.id.btnBackMateri).setOnClickListener(v -> finish());
        btnFilterMateri.setOnClickListener(this::showFilterDropdown);

        rvMateri = findViewById(R.id.rvMateri);
        rvMateri.setLayoutManager(new LinearLayoutManager(this));

        siapkanDataMateri();
        materiDifilter = new ArrayList<>();
        adapter = new MateriAdapter(materiDifilter);
        rvMateri.setAdapter(adapter);

        filterData(TIPE_SEMUA);
        setupBottomNavigation();
    }

    private void showFilterDropdown(View anchor) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        Menu menu = popupMenu.getMenu();
        menu.add(Menu.NONE, TIPE_SEMUA, Menu.NONE, "Semua");
        menu.add(Menu.NONE, TIPE_WEB, Menu.NONE, "Web");
        menu.add(Menu.NONE, TIPE_VIDEO, Menu.NONE, "Video");

        popupMenu.setOnMenuItemClickListener(item -> {
            filterData(item.getItemId());
            return true;
        });
        popupMenu.show();
    }

    private void filterData(int tipe) {
        tipeFilter = tipe;
        materiDifilter.clear();

        for (DataMateri materi : semuaMateri) {
            boolean cocokTopik = "Semua Topik".equals(topikDipilih) || materi.topik.equals(topikDipilih);
            boolean cocokTipe = tipeFilter == TIPE_SEMUA || materi.tipe == tipeFilter;
            if (cocokTopik && cocokTipe) {
                materiDifilter.add(materi);
            }
        }

        tvFilterLabel.setText(getFilterText(tipeFilter));
        adapter.notifyDataSetChanged();
    }

    private String getFilterText(int tipe) {
        if (tipe == TIPE_WEB) return "Web";
        if (tipe == TIPE_VIDEO) return "Video";
        return "Semua";
    }

    private void setupBottomNavigation() {
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_kelas);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_beranda) {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.navigation_kelas) {
                    return true;
                } else if (itemId == R.id.navigation_profil) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
    }

    private void siapkanDataMateri() {
        semuaMateri = new ArrayList<>();

        tambah("Pemrograman Mobile", "Pengantar Android dan Android Studio", "Web - Developer Android", TIPE_WEB, R.drawable.pemrograman_mobile, "https://developer.android.com/studio/intro?hl=id");
        tambah("Pemrograman Mobile", "Membuat UI Android dengan XML", "Web - Dokumentasi Android", TIPE_WEB, R.drawable.pemrograman_mobile, "https://developer.android.com/codelabs/basic-android-kotlin-training-xml-layouts?hl=id#0");
        tambah("Pemrograman Mobile", "RecyclerView untuk Daftar Data", "Video - YouTube", TIPE_VIDEO, R.drawable.pemrograman_mobile, "https://youtu.be/TAEbP_ccjsk?si=mMyHaDpc4S64EHAM");

        tambah("Dasar Pemrograman", "Algoritma dan Flowchart", "Web - Dasar coding", TIPE_WEB, R.drawable.dasar_pemrograman, "https://binus.ac.id/malang/2024/02/5-contoh-algoritma-pemrograman-dilengkapi-dengan-flowchart/");
        tambah("Dasar Pemrograman", "Object Oriented Programming", "Web - Java OOP", TIPE_WEB, R.drawable.dasar_pemrograman, "https://www.codepolitan.com/blog/apa-itu-object-oriented-programming-oop-pengertian-dan-contohnya/");
        tambah("Dasar Pemrograman", "Latihan Java untuk Pemula", "Video - YouTube", TIPE_VIDEO, R.drawable.dasar_pemrograman, "https://www.youtube.com/watch?v=uHyfQV0kbgo&list=PLZS-MHyEIRo51w0Hmqi0C8h2KWNzDfo6F");

        tambah("Website Development", "HTML untuk Struktur Halaman", "Web - MDN", TIPE_WEB, R.drawable.website_development, "https://www.jagoanhosting.com/blog/struktur-html/");
        tambah("Website Development", "JavaScript DOM Dasar", "Web - MDN", TIPE_WEB, R.drawable.website_development, "https://www.petanikode.com/javascript-dom/");
        tambah("Website Development", "Membuat Website Portfolio", "Video - YouTube", TIPE_VIDEO, R.drawable.website_development, "https://youtu.be/LkR-9Z1sle8?si=C1f1ru6ZtCS5jEKA");

        tambah("Backend dan API", "Konsep REST API dan JSON", "Web - MDN", TIPE_WEB, R.drawable.backend_dan_api, "https://terapan-ti.vokasi.unesa.ac.id/post/api-rest-dan-json-konsep-dasar-yang-wajib-dikuasai-developer");
        tambah("Backend dan API", "HTTP Method dan Status Code", "Web - MDN", TIPE_WEB, R.drawable.backend_dan_api, "https://www.hostinger.com/id/tutorial/http-status-code");
        tambah("Backend dan API", "Membuat API Sederhana", "Video - YouTube", TIPE_VIDEO, R.drawable.backend_dan_api, "https://www.youtube.com/watch?v=vQJJ_K1JbEA&list=PLFIM0718LjIW7AsIbnhFg15t9yx4H-sQ0");

        tambah("Basis Data", "Konsep Database dan ERD", "Web - Database design", TIPE_WEB, R.drawable.basis_data, "https://www.dicoding.com/blog/memahami-erd/");
        tambah("Basis Data", "Normalisasi Database", "Web - Relasi tabel", TIPE_WEB, R.drawable.basis_data, "https://www.ibm.com/id-id/think/topics/database-normalization");
        tambah("Basis Data", "Belajar SQL dari Nol", "Video - YouTube", TIPE_VIDEO, R.drawable.basis_data, "https://www.youtube.com/watch?v=OfrTiLzHv3g&list=PLTbTZ9z52SzMi5EmUGqVceaIVGuk426on");

        tambah("Jaringan Komputer", "Pengenalan OSI Layer", "Web - Network basics", TIPE_WEB, R.drawable.jaringan_komputer, "https://codingstudio.id/blog/osi-layer-adalah/");
        tambah("Jaringan Komputer", "IP Address dan Subnetting", "Web - Network basics", TIPE_WEB, R.drawable.jaringan_komputer, "https://www.idn.id/ipv4/");
        tambah("Jaringan Komputer", "Simulasi Cisco Packet Tracer", "Video - YouTube", TIPE_VIDEO, R.drawable.jaringan_komputer, "https://youtu.be/CAWjdVqXd1I?si=ee1qjwAEdUkbVXuB");

        tambah("Git dan GitHub", "Git Dasar: Commit, Branch, Merge", "Web - GitHub Docs", TIPE_WEB, R.drawable.github, "https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging");
        tambah("Git dan GitHub", "Kolaborasi Project di GitHub", "Web - GitHub Docs", TIPE_WEB, R.drawable.github, "https://www.dicoding.com/blog/cara-berkolaborasi-di-repositori-github/");
        tambah("Git dan GitHub", "Belajar Git dan GitHub", "Video - YouTube", TIPE_VIDEO, R.drawable.github, "https://www.youtube.com/watch?v=lTMZxWMjXQU&list=PLFIM0718LjIVknj6sgsSceMqlq242-jNf");

        tambah("Kecerdasan Buatan", "Apa Itu Artificial Intelligence", "Web - Google AI", TIPE_WEB, R.drawable.kecerdasan_buatan, "https://www.ruangguru.com/blog/apa-itu-artificial-intelligence");
        tambah("Kecerdasan Buatan", "Konsep Machine Learning", "Web - Google ML", TIPE_WEB, R.drawable.kecerdasan_buatan, "https://www.dicoding.com/blog/machine-learning-adalah/");
        tambah("Kecerdasan Buatan", "Machine Learning untuk Pemula", "Video - YouTube", TIPE_VIDEO, R.drawable.kecerdasan_buatan, "https://www.youtube.com/watch?v=mEwoAV5_dcA");
    }

    private void tambah(String topik, String judul, String info, int tipe, int gambar, String url) {
        semuaMateri.add(new DataMateri(topik, judul, info, tipe, gambar, url));
    }

    private static class DataMateri {
        String topik, judul, info, url;
        int tipe, gambar;

        DataMateri(String topik, String judul, String info, int tipe, int gambar, String url) {
            this.topik = topik;
            this.judul = judul;
            this.info = info;
            this.tipe = tipe;
            this.gambar = gambar;
            this.url = url;
        }
    }

    private class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ViewHolder> {
        private List<DataMateri> data;

        MateriAdapter(List<DataMateri> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materi, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DataMateri materi = data.get(position);
            holder.judul.setText(materi.judul);
            holder.info.setText(materi.info);
            holder.imgMateri.setImageResource(materi.gambar);
            holder.tvTipeMateri.setText(getFilterText(materi.tipe));

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(MateriActivity.this, WebContentActivity.class);
                intent.putExtra("WEB_TITLE", materi.judul);
                intent.putExtra("WEB_URL", materi.url);
                startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView judul, info, tvTipeMateri;
            ImageView imgMateri;

            ViewHolder(View v) {
                super(v);
                judul = v.findViewById(R.id.tvJudulMateri);
                info = v.findViewById(R.id.tvInfoMateri);
                tvTipeMateri = v.findViewById(R.id.tvTipeMateri);
                imgMateri = v.findViewById(R.id.imgMateri);
            }
        }
    }
}
