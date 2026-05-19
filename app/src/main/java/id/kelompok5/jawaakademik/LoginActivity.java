package id.kelompok5.jawaakademik;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtNim, edtPassword;
    Button btnLogin;
    TextView tvHubungiAdminJawa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNim = findViewById(R.id.edtNim);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvHubungiAdminJawa = findViewById(R.id.tvHubungiAdminJawa);

        btnLogin.setOnClickListener(v -> {
            String username = edtNim.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty()) {
                edtNim.setError("USERNAME wajib diisi");
                return;
            }

            if (password.isEmpty()) {
                edtPassword.setError("Password wajib diisi");
                return;
            }

            if (password.equals("12345")) {
                Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();

                // Simpan username agar tidak hilang saat pindah menu
                getSharedPreferences("USER_DATA", MODE_PRIVATE)
                        .edit()
                        .putString("USERNAME", username)
                        .apply();

                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);

                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Password salah", Toast.LENGTH_SHORT).show();
            }
        });

        tvHubungiAdminJawa.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:sofyanagung54321@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Pendaftaran Akun Jawa Akademik");
            intent.putExtra(Intent.EXTRA_TEXT, "Halo Admin Jawa, saya ingin membuat akun Jawa Akademik.");

            try {
                startActivity(Intent.createChooser(intent, "Kirim email menggunakan"));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Tidak ada aplikasi email yang tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }
}