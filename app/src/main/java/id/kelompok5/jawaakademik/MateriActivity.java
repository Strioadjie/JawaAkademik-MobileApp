package id.kelompok5.jawaakademik;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class MateriActivity extends AppCompatActivity {

    private RecyclerView rvMateri;
    private MateriAdapter adapter;
    private List<DataMateri> semuaMateri;
    private List<DataMateri> materiDifilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

        findViewById(R.id.btnBackMateri).setOnClickListener(v -> finish());

        rvMateri = findViewById(R.id.rvMateri);
        TabLayout tabLayout = findViewById(R.id.tabLayoutMateri);

        siapkanDataMateri();

        rvMateri.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MateriAdapter(materiDifilter);
        rvMateri.setAdapter(adapter);

        // Logika Filter Berdasarkan Tab yang diklik
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterData(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // --- BOTTOM NAVIGATION LOGIC ---
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_kelas);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_beranda) {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(0, 0); finish(); return true;
                } else if (itemId == R.id.navigation_kelas) {
                    return true;
                } else if (itemId == R.id.navigation_forum) {
                    startActivity(new Intent(getApplicationContext(), ForumActivity.class));
                    overridePendingTransition(0, 0); finish(); return true;
                } else if (itemId == R.id.navigation_profil) {
                    Toast.makeText(MateriActivity.this, "Halaman Profil Segera Hadir!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
        }
    }

    private void siapkanDataMateri() {
        semuaMateri = new ArrayList<>();
        // Tipe: 0 = PDF, 1 = Web, 2 = Video

        // --- Pemrograman Mobile ---
        semuaMateri.add(new DataMateri("[Mobile] Pengantar Pemrograman Mobile", "PDF - 1.2 MB", 0));
        semuaMateri.add(new DataMateri("[Mobile] Android Studio Dasar", "Web Link", 1));
        semuaMateri.add(new DataMateri("[Mobile] Tutorial Layouting XML", "Video - 10 Menit", 2));

        // --- Basis Data ---
        semuaMateri.add(new DataMateri("[Basis Data] Konsep Database & ERD", "PDF - 2.5 MB", 0));
        semuaMateri.add(new DataMateri("[Basis Data] Instalasi MySQL", "Video - 15 Menit", 2));

        // --- Jaringan Komputer ---
        semuaMateri.add(new DataMateri("[Jaringan] Pengenalan OSI Layer", "PDF - 1.5 MB", 0));
        semuaMateri.add(new DataMateri("[Jaringan] Simulasi Cisco Packet Tracer", "Web Link", 1));

        // --- Kecerdasan Buatan ---
        semuaMateri.add(new DataMateri("[AI] Konsep Machine Learning", "PDF - 3.0 MB", 0));
        semuaMateri.add(new DataMateri("[AI] Dasar Supervised Learning", "Video - 12 Menit", 2));

        materiDifilter = new ArrayList<>(semuaMateri);
    }

    private void filterData(int tabPosition) {
        materiDifilter.clear();
        if (tabPosition == 0) {
            materiDifilter.addAll(semuaMateri); // Tab "Semua"
        } else {
            // Urutan Tab: 1=PDF, 2=Web, 3=Video. Tipe data: 0=PDF, 1=Web, 2=Video.
            int tipeTarget = tabPosition - 1;
            for (DataMateri m : semuaMateri) {
                if (m.tipe == tipeTarget) materiDifilter.add(m);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private static class DataMateri {
        String judul, info; int tipe;
        DataMateri(String judul, String info, int tipe) { this.judul = judul; this.info = info; this.tipe = tipe; }
    }

    private class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ViewHolder> {
        private List<DataMateri> data;
        MateriAdapter(List<DataMateri> data) { this.data = data; }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materi, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DataMateri m = data.get(position);
            holder.judul.setText(m.judul);
            holder.info.setText(m.info);

            // Logika ganti ikon berdasarkan tipe
            if (m.tipe == 0) { // PDF
                holder.imgTipe.setImageResource(android.R.drawable.ic_menu_gallery);
            } else if (m.tipe == 1) { // WEB
                holder.imgTipe.setImageResource(android.R.drawable.ic_menu_search);
            } else if (m.tipe == 2) { // VIDEO
                holder.imgTipe.setImageResource(android.R.drawable.ic_media_play);
            }

            holder.itemView.setOnClickListener(v -> {
                if (m.tipe == 2) {
                    // PINDAH KE LAYAR 8 (VIDEO)
                    Intent intent = new Intent(MateriActivity.this, VideoActivity.class);
                    intent.putExtra("JUDUL_VIDEO", m.judul);
                    startActivity(intent);
                } else {
                    Toast.makeText(MateriActivity.this, "Membuka File: " + m.judul, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() { return data.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView judul, info; ImageView imgTipe;
            ViewHolder(View v) {
                super(v);
                judul = v.findViewById(R.id.tvJudulMateri);
                info = v.findViewById(R.id.tvInfoMateri);
                imgTipe = v.findViewById(R.id.imgTipeMateri);
            }
        }
    }
}