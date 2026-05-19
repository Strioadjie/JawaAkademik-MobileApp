package id.kelompok5.jawaakademik;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ForumActivity extends AppCompatActivity {

    private RecyclerView rvForum;
    private List<Diskusi> listDiskusi;
    private List<Diskusi> diskusiTampil;
    private ForumAdapter adapter;
    private String discussionTopic;
    private String discussionMaterial;
    private String discussionInfo;
    private int filterTabAktif = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnAddForum = findViewById(R.id.btnAddForum);
        TextView tvForumContext = findViewById(R.id.tvForumContext);
        TabLayout tabLayoutForum = findViewById(R.id.tabLayoutForum);
        rvForum = findViewById(R.id.rvForum);

        discussionTopic = getIntent().getStringExtra("DISCUSSION_TOPIC");
        discussionMaterial = getIntent().getStringExtra("DISCUSSION_MATERIAL");
        discussionInfo = getIntent().getStringExtra("DISCUSSION_INFO");

        btnBack.setOnClickListener(v -> finish());
        btnAddForum.setOnClickListener(v ->
                AppToast.show(this, "Fitur forum dinonaktifkan")
        );

        listDiskusi = siapkanDataDiskusi();
        if (discussionMaterial == null || discussionMaterial.trim().isEmpty()) {
            tvForumContext.setVisibility(View.GONE);
        } else {
            tvForumContext.setVisibility(View.VISIBLE);
            tvForumContext.setText("Thread materi: " + discussionMaterial);
        }

        rvForum.setLayoutManager(new LinearLayoutManager(this));
        diskusiTampil = new ArrayList<>();
        adapter = new ForumAdapter(diskusiTampil);
        rvForum.setAdapter(adapter);
        filterDiskusi();

        tabLayoutForum.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterTabAktif = tab.getPosition();
                filterDiskusi();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        setupBottomNavigation();
    }

    private void filterDiskusi() {
        diskusiTampil.clear();
        for (Diskusi diskusi : listDiskusi) {
            if (filterTabAktif == 1 && diskusi.sudahDibaca) {
                continue;
            }
            if (filterTabAktif == 2 && !diskusi.milikSaya) {
                continue;
            }
            diskusiTampil.add(diskusi);
        }
        adapter.notifyDataSetChanged();
    }

    private Set<String> getThreadTerbaca() {
        return getSharedPreferences("FORUM_DATA", MODE_PRIVATE)
                .getStringSet("READ_THREADS", new HashSet<>());
    }

    private void tandaiSudahDibaca(Diskusi diskusi) {
        Set<String> threadTerbaca = new HashSet<>(getThreadTerbaca());
        threadTerbaca.add(diskusi.id);
        getSharedPreferences("FORUM_DATA", MODE_PRIVATE)
                .edit()
                .putStringSet("READ_THREADS", threadTerbaca)
                .apply();
        diskusi.sudahDibaca = true;
        filterDiskusi();
    }

    private List<Diskusi> siapkanDataDiskusi() {
        List<Diskusi> data = new ArrayList<>();
        Set<String> threadTerbaca = getThreadTerbaca();
        String username = getSharedPreferences("USER_DATA", MODE_PRIVATE)
                .getString("USERNAME", "Pelajar");
        if (username == null || username.trim().isEmpty()) {
            username = "Pelajar";
        }

        if (discussionMaterial != null && !discussionMaterial.trim().isEmpty()) {
            data.add(new Diskusi(
                    "Diskusi: " + discussionMaterial,
                    username,
                    "baru saja",
                    "0",
                    "Gunakan thread ini untuk membahas materi " + discussionMaterial + ". Tulis pertanyaan, error, rangkuman, atau contoh latihan yang ingin didiskusikan.",
                    discussionTopic,
                    discussionMaterial,
                    true,
                    false
            ));
            data.add(new Diskusi(
                    "Ada yang sudah mencoba materi ini?",
                    "Raka Pratama",
                    "12 menit lalu",
                    "4",
                    "Saya ingin membandingkan catatan belajar dan contoh latihan dari materi ini.",
                    discussionTopic,
                    discussionMaterial,
                    false,
                    false
            ));
            data.add(new Diskusi(
                    "Bagian tersulit dari " + discussionMaterial,
                    "Nadia Putri",
                    "35 menit lalu",
                    "2",
                    "Saya masih perlu contoh langkah demi langkah supaya konsepnya lebih kebayang.",
                    discussionTopic,
                    discussionMaterial,
                    false,
                    false
            ));
            for (Diskusi diskusi : data) {
                diskusi.sudahDibaca = threadTerbaca.contains(diskusi.id);
            }
            return data;
        }

        data.add(new Diskusi("Thread Website Development", "Andi Saputra", "2 jam lalu", "6", "Diskusi umum seputar HTML, CSS, JavaScript, dan struktur website.", "Website Development", "Website Development", false, false));
        data.add(new Diskusi("Thread Pemrograman Mobile", "Siti Aisyah", "5 jam lalu", "4", "Tempat bertanya seputar Android, Activity, Intent, dan UI XML.", "Pemrograman Mobile", "Pemrograman Mobile", false, false));
        data.add(new Diskusi("Thread Backend dan API", "Dimas Pratama", "1 hari lalu", "5", "Bahas REST API, JSON, request-response, dan koneksi aplikasi ke server.", "Backend dan API", "Backend dan API", false, false));
        data.add(new Diskusi("Thread Jaringan Komputer", "Rina Kartika", "2 hari lalu", "3", "Diskusi OSI layer, IP address, subnetting, dan simulasi jaringan.", "Jaringan Komputer", "Jaringan Komputer", false, false));
        data.add(new Diskusi("Pertanyaan Saya: Cara mulai belajar coding?", username, "baru saja", "0", "Saya ingin tahu urutan belajar yang paling cocok untuk pemula.", "Dasar Pemrograman", "Dasar Pemrograman", true, false));
        for (Diskusi diskusi : data) {
            diskusi.sudahDibaca = threadTerbaca.contains(diskusi.id);
        }
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
                    return true;
                } else if (itemId == R.id.navigation_kelas) {
                    startActivity(new Intent(getApplicationContext(), MateriActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.navigation_forum) {
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

    private static class Diskusi {
        String judul, penulis, waktu, jmlBalasan, isi, topik, materi;
        String id;
        boolean milikSaya, sudahDibaca;

        Diskusi(String judul, String penulis, String waktu, String jmlBalasan, String isi, String topik, String materi, boolean milikSaya, boolean sudahDibaca) {
            this.judul = judul;
            this.penulis = penulis;
            this.waktu = waktu;
            this.jmlBalasan = jmlBalasan;
            this.isi = isi;
            this.topik = topik == null || topik.trim().isEmpty() ? "Umum" : topik;
            this.materi = materi == null || materi.trim().isEmpty() ? "Forum Umum" : materi;
            this.milikSaya = milikSaya;
            this.sudahDibaca = sudahDibaca;
            this.id = this.materi + "|" + this.judul;
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
            holder.tvJudulForum.setAlpha(diskusi.sudahDibaca ? 0.65f : 1f);
            holder.tvInfoForum.setAlpha(diskusi.sudahDibaca ? 0.65f : 1f);

            holder.itemView.setOnClickListener(v -> {
                tandaiSudahDibaca(diskusi);
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
