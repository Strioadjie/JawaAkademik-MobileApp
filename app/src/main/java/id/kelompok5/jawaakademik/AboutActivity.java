package id.kelompok5.jawaakademik;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    TextView txtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        txtBack = findViewById(R.id.txtBack);

        txtBack.setOnClickListener(v -> {
            finish();
        });
    }
}