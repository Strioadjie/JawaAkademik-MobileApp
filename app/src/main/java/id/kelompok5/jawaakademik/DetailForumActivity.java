package id.kelompok5.jawaakademik;

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

import java.util.ArrayList;
import java.util.List;

public class DetailForumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_forum);

        ImageView btnBackDetail = findViewById(R.id.btnBackDetail);
        RecyclerView rvBalasan = findViewById(R.id.rvBalasan);
        TextView tvDetailJudul = findViewById(R.id.tvDetailJudul);
        TextView tvDetailIsi = findViewById(R.id.tvDetailIsi);
        TextView tvJumlahBalasan = findViewById(R.id.tvJumlahBalasan);

        btnBackDetail.setOnClickListener(v -> finish());

        String threadTitle = getIntent().getStringExtra("THREAD_TITLE");
        String threadBody = getIntent().getStringExtra("THREAD_BODY");
        String replyCount = getIntent().getStringExtra("THREAD_REPLY_COUNT");
        String threadMaterial = getIntent().getStringExtra("THREAD_MATERIAL");

        if (threadTitle != null && !threadTitle.isEmpty()) {
            tvDetailJudul.setText(threadTitle);
        }
        if (threadBody != null && !threadBody.isEmpty()) {
            tvDetailIsi.setText(threadBody);
        }
        if (replyCount != null && !replyCount.isEmpty()) {
            tvJumlahBalasan.setText(replyCount + " Balasan");
        }

        String materi = threadMaterial == null || threadMaterial.isEmpty() ? "materi ini" : threadMaterial;
        List<Komentar> listKomentar = new ArrayList<>();
        listKomentar.add(new Komentar("Siti Aisyah", "1 jam lalu", "Aku biasanya mulai dari membaca ringkasan " + materi + ", lalu bikin catatan kecil sebelum praktik."));
        listKomentar.add(new Komentar("Dimas Pratama", "30 menit lalu", "Kalau ada error, kirim potongan kode atau bagian materi yang membingungkan supaya lebih mudah dibahas."));
        listKomentar.add(new Komentar("Andi", "Baru saja", "Setuju. Thread seperti ini enak dipakai untuk kumpulin pertanyaan per materi."));

        rvBalasan.setLayoutManager(new LinearLayoutManager(this));
        rvBalasan.setAdapter(new KomentarAdapter(listKomentar));
    }

    private static class Komentar {
        String nama, waktu, isi;

        Komentar(String nama, String waktu, String isi) {
            this.nama = nama;
            this.waktu = waktu;
            this.isi = isi;
        }
    }

    private class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.ViewHolder> {
        private List<Komentar> data;

        KomentarAdapter(List<Komentar> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int vt) {
            View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_komentar, p, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
            Komentar k = data.get(pos);
            h.nama.setText(k.nama);
            h.waktu.setText("- " + k.waktu);
            h.isi.setText(k.isi);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nama, waktu, isi;

            ViewHolder(View iv) {
                super(iv);
                nama = iv.findViewById(R.id.tvNamaKomentar);
                waktu = iv.findViewById(R.id.tvWaktuKomentar);
                isi = iv.findViewById(R.id.tvIsiKomentar);
            }
        }
    }
}
