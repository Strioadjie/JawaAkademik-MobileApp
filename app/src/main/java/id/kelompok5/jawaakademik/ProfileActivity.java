package id.kelompok5.jawaakademik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREF_NAME = "USER_DATA";
    private static final String KEY_USERNAME = "USERNAME";

    private TextView tvProfileName;
    private EditText edtProfileName;
    private SharedPreferences userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userData = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        tvProfileName = findViewById(R.id.tvProfileName);
        edtProfileName = findViewById(R.id.edtProfileName);
        Button btnSaveProfile = findViewById(R.id.btnSaveProfile);

        String username = getSavedUsername();
        tvProfileName.setText(username);
        edtProfileName.setText(username);

        findViewById(R.id.btnBackProfile).setOnClickListener(v -> finish());
        btnSaveProfile.setOnClickListener(v -> saveUsername());
        setupBottomNavigation();
    }

    private String getSavedUsername() {
        String username = userData.getString(KEY_USERNAME, "");
        if (username == null || username.trim().isEmpty()) {
            return "Pelajar";
        }
        return username.trim();
    }

    private void saveUsername() {
        String newUsername = edtProfileName.getText().toString().trim();
        if (newUsername.isEmpty()) {
            edtProfileName.setError("Nama wajib diisi");
            return;
        }

        userData.edit()
                .putString(KEY_USERNAME, newUsername)
                .apply();

        tvProfileName.setText(newUsername);
        Toast.makeText(this, "Nama profil berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.navigation_profil);

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
                startActivity(new Intent(getApplicationContext(), ForumActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_profil) {
                return true;
            }

            return false;
        });
    }
}
