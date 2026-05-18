package id.kelompok5.jawaakademik;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MataPelajaranAdapter extends RecyclerView.Adapter<MataPelajaranAdapter.ViewHolder> {

    private List<MataPelajaran> listMataPelajaran;

    public MataPelajaranAdapter(List<MataPelajaran> listMataPelajaran) {
        this.listMataPelajaran = listMataPelajaran;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mata_pelajaran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MataPelajaran matkul = listMataPelajaran.get(position);

        // Memasukkan teks
        holder.tvNamaMatkul.setText(matkul.getNamaMatkul());
        holder.tvDetailMatkul.setText(matkul.getDetailJadwal());
        holder.tvProgressInfo.setText(matkul.getProgressBelajar());

        holder.ivIkonMatkul.setImageResource(matkul.getGambarMatkul());

        // Mengatur panjang Progress Bar mini di Dashboard
        try {
            // Menghilangkan tanda "%" agar sisa angkanya saja yang bisa dibaca ProgressBar
            String cleanProgress = matkul.getProgressBelajar().replace("%", "").trim();
            holder.pbMiniProgress.setProgress(Integer.parseInt(cleanProgress));
        } catch (Exception e) {
            holder.pbMiniProgress.setProgress(0);
        }

        // Fungsi klik untuk pindah ke Layar 4 (Detail Matkul)
        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(v.getContext(), DetailMatkulActivity.class);
            intent.putExtra("NAMA_MATKUL", matkul.getNamaMatkul());
            intent.putExtra("DETAIL_JADWAL", matkul.getDetailJadwal());
            intent.putExtra("PROGRESS", matkul.getProgressBelajar());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listMataPelajaran.size();
    }

    // Hanya ada SATU class ViewHolder di sini
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaMatkul, tvDetailMatkul, tvProgressInfo;
        ProgressBar pbMiniProgress;
        android.widget.ImageView ivIkonMatkul; // Deklarasi variabel gambar

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaMatkul = itemView.findViewById(R.id.tvNamaMatkul);
            tvDetailMatkul = itemView.findViewById(R.id.tvDetailMatkul);
            tvProgressInfo = itemView.findViewById(R.id.tvProgressInfo);
            pbMiniProgress = itemView.findViewById(R.id.pbMiniProgress);
            ivIkonMatkul = itemView.findViewById(R.id.ivIkonMatkul); // Sambungkan dengan ID di XML
        }
    }
}