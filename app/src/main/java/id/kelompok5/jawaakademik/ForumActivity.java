package id.kelompok5.jawaakademik;

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

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {

    private RecyclerView rvForum;
    private ForumAdapter adapter;
    private List<Diskusi> listDiskusi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnAddForum = findViewById(R.id.btnAddForum);
        rvForum = findViewById(R.id.rvForum);

        btnBack.setOnClickListener(v -> finish());
        btnAddForum.setOnClickListener(v ->
                Toast.makeText(this, "Fitur Tambah Diskusi Segera Hadir!", Toast.LENGTH_SHORT).show()
        );

        listDiskusi = new ArrayList<>();
        listDiskusi.add(new Diskusi("Pertanyaan seputar RecyclerView di Android", "Andi Saputra", "2 jam lalu", "3"));
        listDiskusi.add(new Diskusi("Error saat build project", "Siti Aisyah", "5 jam lalu", "1"));
        listDiskusi.add(new Diskusi("Rekomendasi Library Android Terbaik", "Dimas Pratama", "1 hari lalu", "5"));
        listDiskusi.add(new Diskusi("Kesulitan memahami Retrofit", "Rina Kartika", "2 hari lalu", "2"));

        rvForum.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ForumAdapter(listDiskusi);
        rvForum.setAdapter(adapter);

        // ==============================================
        // LOGIKA BOTTOM NAVIGATION
        // ==============================================
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // Tandai tombol "Forum" sebagai menu yang sedang aktif di halaman ini
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_forum);

            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_beranda) {
                    // Pindah ke halaman Beranda
                    startActivity(new android.content.Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(0, 0); // Menghilangkan animasi kedip saat pindah
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_kelas) {
                    // Nanti arahkan ke Activity Daftar Kelas (Mata Pelajaran)
                    return true;
                } else if (itemId == R.id.navigation_forum) {
                    // Tetap di halaman forum
                    return true;
                } else if (itemId == R.id.navigation_profil) {
                    // Nanti arahkan ke Activity Profil
                    return true;
                }
                return false;
            });
        }
        // ==============================================
    }

    private static class Diskusi {
        String judul, penulis, waktu, jmlBalasan;

        public Diskusi(String judul, String penulis, String waktu, String jmlBalasan) {
            this.judul = judul;
            this.penulis = penulis;
            this.waktu = waktu;
            this.jmlBalasan = jmlBalasan;
        }
    }

    private class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
        private List<Diskusi> dataList;

        public ForumAdapter(List<Diskusi> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Diskusi diskusi = dataList.get(position);
            holder.tvJudulForum.setText(diskusi.judul);
            holder.tvInfoForum.setText(diskusi.penulis + " • " + diskusi.waktu);
            holder.tvJmlBalasan.setText(diskusi.jmlBalasan);

            holder.itemView.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(ForumActivity.this, DetailForumActivity.class);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvJudulForum, tvInfoForum, tvJmlBalasan;

            public ViewHolder(View itemView) {
                super(itemView);
                tvJudulForum = itemView.findViewById(R.id.tvJudulForum);
                tvInfoForum = itemView.findViewById(R.id.tvInfoForum);
                tvJmlBalasan = itemView.findViewById(R.id.tvJmlBalasan);
            }
        }
    }
}