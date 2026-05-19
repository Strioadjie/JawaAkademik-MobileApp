package id.kelompok5.jawaakademik;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.tvDeskripsiMatkul.setText(matkul.getDeskripsi());
        holder.ivIkonMatkul.setImageResource(matkul.getGambarMatkul());

        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(v.getContext(), MateriActivity.class);
            intent.putExtra("TOPIK_BELAJAR", matkul.getNamaMatkul());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listMataPelajaran.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaMatkul, tvDetailMatkul, tvDeskripsiMatkul;
        android.widget.ImageView ivIkonMatkul;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaMatkul = itemView.findViewById(R.id.tvNamaMatkul);
            tvDetailMatkul = itemView.findViewById(R.id.tvDetailMatkul);
            tvDeskripsiMatkul = itemView.findViewById(R.id.tvDeskripsiMatkul);
            ivIkonMatkul = itemView.findViewById(R.id.ivIkonMatkul);
        }
    }
}
