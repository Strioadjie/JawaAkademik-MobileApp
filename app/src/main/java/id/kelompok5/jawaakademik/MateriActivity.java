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
    private static final int TIPE_PDF = 0;
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
        menu.add(Menu.NONE, TIPE_PDF, Menu.NONE, "PDF");
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
        if (tipe == TIPE_PDF) return "PDF";
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
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_kelas) {
                    return true;
                } else if (itemId == R.id.navigation_forum) {
                    startActivity(new Intent(getApplicationContext(), ForumActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_profil) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
                return false;
            });
        }
    }

    private void siapkanDataMateri() {
        semuaMateri = new ArrayList<>();

        tambah("Pemrograman Mobile", "Pengantar Android dan Android Studio", "Web - Developer Android", TIPE_WEB, R.drawable.img_mobile, "https://developer.android.com/courses");
        tambah("Pemrograman Mobile", "Membuat UI Android dengan XML", "PDF - Modul layout Android", TIPE_PDF, R.drawable.img_mobile, "https://developer.android.com/develop/ui/views/layout/declaring-layout");
        tambah("Pemrograman Mobile", "Intent, Activity, dan Navigasi", "Web - Dokumentasi Android", TIPE_WEB, R.drawable.img_mobile, "https://developer.android.com/guide/components/intents-filters");
        tambah("Pemrograman Mobile", "RecyclerView untuk Daftar Data", "Video - YouTube", TIPE_VIDEO, R.drawable.img_mobile, "https://www.youtube.com/results?search_query=android+recyclerview+tutorial+bahasa+indonesia");

        tambah("Dasar Pemrograman", "Algoritma dan Flowchart", "Web - Dasar coding", TIPE_WEB, R.drawable.image_student, "https://www.freecodecamp.org/news/what-is-an-algorithm-definition-for-beginners/");
        tambah("Dasar Pemrograman", "Variabel, Tipe Data, dan Kondisi", "PDF - Modul Java dasar", TIPE_PDF, R.drawable.image_student, "https://dev.java/learn/");
        tambah("Dasar Pemrograman", "Object Oriented Programming", "Web - Java OOP", TIPE_WEB, R.drawable.image_student, "https://dev.java/learn/oop/");
        tambah("Dasar Pemrograman", "Latihan Java untuk Pemula", "Video - YouTube", TIPE_VIDEO, R.drawable.image_student, "https://www.youtube.com/results?search_query=java+programming+for+beginners+bahasa+indonesia");

        tambah("Website Development", "HTML untuk Struktur Halaman", "Web - MDN", TIPE_WEB, R.drawable.img_ai, "https://developer.mozilla.org/en-US/docs/Learn/HTML");
        tambah("Website Development", "CSS Layout dan Responsive Design", "PDF - Modul CSS", TIPE_PDF, R.drawable.img_ai, "https://developer.mozilla.org/en-US/docs/Learn/CSS");
        tambah("Website Development", "JavaScript DOM Dasar", "Web - MDN", TIPE_WEB, R.drawable.img_ai, "https://developer.mozilla.org/en-US/docs/Learn/JavaScript");
        tambah("Website Development", "Membuat Website Portfolio", "Video - YouTube", TIPE_VIDEO, R.drawable.img_ai, "https://www.youtube.com/results?search_query=membuat+website+portfolio+html+css+javascript");

        tambah("Backend dan API", "Konsep REST API dan JSON", "Web - MDN", TIPE_WEB, R.drawable.img_basisdata, "https://developer.mozilla.org/en-US/docs/Glossary/REST");
        tambah("Backend dan API", "HTTP Method dan Status Code", "PDF - Modul HTTP", TIPE_PDF, R.drawable.img_basisdata, "https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods");
        tambah("Backend dan API", "Membuat API Sederhana", "Video - YouTube", TIPE_VIDEO, R.drawable.img_basisdata, "https://www.youtube.com/results?search_query=membuat+rest+api+pemula+bahasa+indonesia");

        tambah("Basis Data", "Konsep Database dan ERD", "Web - Database design", TIPE_WEB, R.drawable.img_basisdata, "https://www.lucidchart.com/pages/er-diagrams");
        tambah("Basis Data", "SQL SELECT, INSERT, UPDATE, DELETE", "PDF - Modul SQL", TIPE_PDF, R.drawable.img_basisdata, "https://www.w3schools.com/sql/");
        tambah("Basis Data", "Normalisasi Database", "Web - Relasi tabel", TIPE_WEB, R.drawable.img_basisdata, "https://www.geeksforgeeks.org/dbms/normal-forms-in-dbms/");
        tambah("Basis Data", "Belajar SQL dari Nol", "Video - YouTube", TIPE_VIDEO, R.drawable.img_basisdata, "https://www.youtube.com/results?search_query=belajar+sql+dari+nol+bahasa+indonesia");

        tambah("Jaringan Komputer", "Pengenalan OSI Layer", "Web - Network basics", TIPE_WEB, R.drawable.img_jaringan, "https://www.cloudflare.com/learning/ddos/glossary/open-systems-interconnection-model-osi/");
        tambah("Jaringan Komputer", "IP Address dan Subnetting", "PDF - Modul subnetting", TIPE_PDF, R.drawable.img_jaringan, "https://www.cloudflare.com/learning/network-layer/what-is-an-ip-address/");
        tambah("Jaringan Komputer", "Simulasi Cisco Packet Tracer", "Video - YouTube", TIPE_VIDEO, R.drawable.img_jaringan, "https://www.youtube.com/results?search_query=cisco+packet+tracer+dasar+bahasa+indonesia");

        tambah("Git dan GitHub", "Git Dasar: Commit, Branch, Merge", "Web - GitHub Docs", TIPE_WEB, R.drawable.img_mobile, "https://docs.github.com/en/get-started/using-git/about-git");
        tambah("Git dan GitHub", "Kolaborasi Project di GitHub", "PDF - Modul GitHub", TIPE_PDF, R.drawable.img_mobile, "https://docs.github.com/en/get-started/start-your-journey");
        tambah("Git dan GitHub", "Belajar Git dan GitHub", "Video - YouTube", TIPE_VIDEO, R.drawable.img_mobile, "https://www.youtube.com/results?search_query=belajar+git+github+bahasa+indonesia");

        tambah("Kecerdasan Buatan", "Apa Itu Artificial Intelligence", "Web - Google AI", TIPE_WEB, R.drawable.img_ai, "https://ai.google/responsibility/");
        tambah("Kecerdasan Buatan", "Konsep Machine Learning", "PDF - Modul ML", TIPE_PDF, R.drawable.img_ai, "https://developers.google.com/machine-learning/crash-course");
        tambah("Kecerdasan Buatan", "Machine Learning untuk Pemula", "Video - YouTube", TIPE_VIDEO, R.drawable.img_ai, "https://www.youtube.com/results?search_query=machine+learning+untuk+pemula+bahasa+indonesia");
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

            holder.btnDiskusiMateri.setOnClickListener(v -> {
                Intent intent = new Intent(MateriActivity.this, ForumActivity.class);
                intent.putExtra("DISCUSSION_TOPIC", materi.topik);
                intent.putExtra("DISCUSSION_MATERIAL", materi.judul);
                intent.putExtra("DISCUSSION_INFO", materi.info);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView judul, info, tvTipeMateri, btnDiskusiMateri;
            ImageView imgMateri;

            ViewHolder(View v) {
                super(v);
                judul = v.findViewById(R.id.tvJudulMateri);
                info = v.findViewById(R.id.tvInfoMateri);
                tvTipeMateri = v.findViewById(R.id.tvTipeMateri);
                btnDiskusiMateri = v.findViewById(R.id.btnDiskusiMateri);
                imgMateri = v.findViewById(R.id.imgMateri);
            }
        }
    }
}
