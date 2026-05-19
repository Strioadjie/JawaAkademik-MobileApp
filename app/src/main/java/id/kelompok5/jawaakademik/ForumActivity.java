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

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {

    private RecyclerView rvForum;
    private List<Diskusi> listDiskusi;
    private String discussionTopic;
    private String discussionMaterial;
    private String discussionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnAddForum = findViewById(R.id.btnAddForum);
        TextView tvForumContext = findViewById(R.id.tvForumContext);
        rvForum = findViewById(R.id.rvForum);

        discussionTopic = getIntent().getStringExtra("DISCUSSION_TOPIC");
        discussionMaterial = getIntent().getStringExtra("DISCUSSION_MATERIAL");
        discussionInfo = getIntent().getStringExtra("DISCUSSION_INFO");

        btnBack.setOnClickListener(v -> finish());
        btnAddForum.setOnClickListener(v ->
                Toast.makeText(this, "Tambah thread akan aktif setelah database dipasang", Toast.LENGTH_SHORT).show()
        );

        listDiskusi = siapkanDataDiskusi();
        if (discussionMaterial == null || discussionMaterial.trim().isEmpty()) {
            tvForumContext.setVisibility(View.GONE);
        } else {
            tvForumContext.setVisibility(View.VISIBLE);
            tvForumContext.setText("Thread materi: " + discussionMaterial);
        }

        rvForum.setLayoutManager(new LinearLayoutManager(this));
        rvForum.setAdapter(new ForumAdapter(listDiskusi));

        setupBottomNavigation();
    }

    private List<Diskusi> siapkanDataDiskusi() {
        List<Diskusi> data = new ArrayList<>();

        if (discussionMaterial != null && !discussionMaterial.trim().isEmpty()) {
            String username = getSharedPreferences("USER_DATA", MODE_PRIVATE)
                    .getString("USERNAME", "Pelajar");
            if (username == null || username.trim().isEmpty()) {
                username = "Pelajar";
            }

            data.add(new Diskusi(
                    "Diskusi: " + discussionMaterial,
                    username,
                    "baru saja",
                    "0",
                    "Gunakan thread ini untuk membahas materi " + discussionMaterial + ". Tulis pertanyaan, error, rangkuman, atau contoh latihan yang ingin didiskusikan.",
                    discussionTopic,
                    discussionMaterial
            ));
            data.add(new Diskusi(
                    "Ada yang sudah mencoba materi ini?",
                    "Raka Pratama",
                    "12 menit lalu",
                    "4",
                    "Saya ingin membandingkan catatan belajar dan contoh latihan dari materi ini.",
                    discussionTopic,
                    discussionMaterial
            ));
            data.add(new Diskusi(
                    "Bagian tersulit dari " + discussionMaterial,
                    "Nadia Putri",
                    "35 menit lalu",
                    "2",
                    "Saya masih perlu contoh langkah demi langkah supaya konsepnya lebih kebayang.",
                    discussionTopic,
                    discussionMaterial
            ));
            return data;
        }

        data.add(new Diskusi("Thread Website Development", "Andi Saputra", "2 jam lalu", "6", "Diskusi umum seputar HTML, CSS, JavaScript, dan struktur website.", "Website Development", "Website Development"));
        data.add(new Diskusi("Thread Pemrograman Mobile", "Siti Aisyah", "5 jam lalu", "4", "Tempat bertanya seputar Android, Activity, Intent, dan UI XML.", "Pemrograman Mobile", "Pemrograman Mobile"));
        data.add(new Diskusi("Thread Backend dan API", "Dimas Pratama", "1 hari lalu", "5", "Bahas REST API, JSON, request-response, dan koneksi aplikasi ke server.", "Backend dan API", "Backend dan API"));
        data.add(new Diskusi("Thread Jaringan Komputer", "Rina Kartika", "2 hari lalu", "3", "Diskusi OSI layer, IP address, subnetting, dan simulasi jaringan.", "Jaringan Komputer", "Jaringan Komputer"));
        return data;
    }

    private void setupBottomNavigation() {
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_forum);

            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_beranda) {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_kelas) {
                    startActivity(new Intent(getApplicationContext(), MateriActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_forum) {
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

    private static class Diskusi {
        String judul, penulis, waktu, jmlBalasan, isi, topik, materi;

        Diskusi(String judul, String penulis, String waktu, String jmlBalasan, String isi, String topik, String materi) {
            this.judul = judul;
            this.penulis = penulis;
            this.waktu = waktu;
            this.jmlBalasan = jmlBalasan;
            this.isi = isi;
            this.topik = topik == null || topik.trim().isEmpty() ? "Umum" : topik;
            this.materi = materi == null || materi.trim().isEmpty() ? "Forum Umum" : materi;
        }
    }

    private class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHolder> {
        private List<Diskusi> dataList;

        ForumAdapter(List<Diskusi> dataList) {
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
            holder.tvInfoForum.setText(diskusi.penulis + " - " + diskusi.waktu + " - " + diskusi.topik);
            holder.tvJmlBalasan.setText(diskusi.jmlBalasan);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(ForumActivity.this, DetailForumActivity.class);
                intent.putExtra("THREAD_TITLE", diskusi.judul);
                intent.putExtra("THREAD_BODY", diskusi.isi);
                intent.putExtra("THREAD_AUTHOR", diskusi.penulis);
                intent.putExtra("THREAD_TIME", diskusi.waktu);
                intent.putExtra("THREAD_REPLY_COUNT", diskusi.jmlBalasan);
                intent.putExtra("THREAD_TOPIC", diskusi.topik);
                intent.putExtra("THREAD_MATERIAL", diskusi.materi);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvJudulForum, tvInfoForum, tvJmlBalasan;

            ViewHolder(View itemView) {
                super(itemView);
                tvJudulForum = itemView.findViewById(R.id.tvJudulForum);
                tvInfoForum = itemView.findViewById(R.id.tvInfoForum);
                tvJmlBalasan = itemView.findViewById(R.id.tvJmlBalasan);
            }
        }
    }
}
