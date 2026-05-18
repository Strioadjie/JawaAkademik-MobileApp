package id.kelompok5.jawaakademik;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        findViewById(R.id.btnBackVideo).setOnClickListener(v -> finish());

        TextView tvTitle = findViewById(R.id.tvVideoTitle);
        Button btnYoutube = findViewById(R.id.btnYoutube);

        // Ambil data judul dari intent sebelumnya
        String judul = getIntent().getStringExtra("JUDUL_VIDEO");
        if (judul != null) {
            tvTitle.setText(judul);
        }

        btnYoutube.setOnClickListener(v -> {
            // Contoh membuka link YouTube (Sesuaikan linknya nanti)
            String videoUrl = "https://www.youtube.com/watch?v=ZGdxshD0UbY&list=PLjRBWix725xq87NI2WcSc_k7uBW35pF6E";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            startActivity(intent);
        });
    }
}