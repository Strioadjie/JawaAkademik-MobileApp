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

public class AbsensiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);

        ImageView btnBack = findViewById(R.id.btnBackAbsensi);
        RecyclerView rvAbsensi = findViewById(R.id.rvAbsensi);

        btnBack.setOnClickListener(v -> finish());

        // Buat Data List Sesuai Gambar No 9
        List<DataAbsen> listData = new ArrayList<>();
        listData.add(new DataAbsen("Pertemuan 1 - Pengenalan Android", "Senin, 12 Feb 2024", true));
        listData.add(new DataAbsen("Pertemuan 2 - Layout Dasar", "Senin, 19 Feb 2024", true));
        listData.add(new DataAbsen("Pertemuan 3 - Komponen UI", "Senin, 26 Feb 2024", true));
        listData.add(new DataAbsen("Pertemuan 4 - Intent & Activity", "Senin, 4 Mar 2024", true));
        listData.add(new DataAbsen("Pertemuan 5 - RecyclerView", "Senin, 11 Mar 2024", false)); // False berarti lingkaran kosong

        rvAbsensi.setLayoutManager(new LinearLayoutManager(this));
        rvAbsensi.setAdapter(new AbsensiAdapter(listData));

        // ==============================================
        // LOGIKA BOTTOM NAVIGATION
        // ==============================================
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        if (bottomNav != null) {
            // Tandai tombol "Kelas" sebagai menu yang sedang aktif
            bottomNav.setSelectedItemId(R.id.navigation_kelas);

            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_beranda) {
                    startActivity(new android.content.Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.navigation_kelas) {
                    return true; // Tetap di halaman terkait Kelas
                } else if (itemId == R.id.navigation_profil) {
                    startActivity(new android.content.Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
        // ==============================================
    }

    // Model Data
    private static class DataAbsen {
        String namaPertemuan, tanggal;
        boolean isHadir;

        DataAbsen(String namaPertemuan, String tanggal, boolean isHadir) {
            this.namaPertemuan = namaPertemuan;
            this.tanggal = tanggal;
            this.isHadir = isHadir;
        }
    }

    // Adapter RecyclerView
    private class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.ViewHolder> {
        private List<DataAbsen> dataList;
        AbsensiAdapter(List<DataAbsen> dataList) { this.dataList = dataList; }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absensi, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DataAbsen absensi = dataList.get(position);
            holder.tvNama.setText(absensi.namaPertemuan);
            holder.tvTanggal.setText(absensi.tanggal);

            // LOGIKA CEKLIS OTOMATIS
            if (absensi.isHadir) {
                holder.imgStatus.setImageResource(android.R.drawable.checkbox_on_background);
            } else {
                holder.imgStatus.setImageResource(android.R.drawable.checkbox_off_background);
            }
        }

        @Override
        public int getItemCount() { return dataList.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNama, tvTanggal;
            ImageView imgStatus;
            ViewHolder(View iv) {
                super(iv);
                tvNama = iv.findViewById(R.id.tvNamaPertemuan);
                tvTanggal = iv.findViewById(R.id.tvTanggalPertemuan);
                imgStatus = iv.findViewById(R.id.imgStatusCeklis);
            }
        }
    }
}
