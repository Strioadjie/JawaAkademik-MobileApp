package id.kelompok5.jawaakademik;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtNim, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNim = findViewById(R.id.edtNim);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String nim = edtNim.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (nim.isEmpty()) {
                edtNim.setError("NIM wajib diisi");
                return;
            }

            if (password.isEmpty()) {
                edtPassword.setError("Password wajib diisi");
                return;
            }

            if (password.equals("12345")) {
                Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("NIM", nim);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Password salah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}