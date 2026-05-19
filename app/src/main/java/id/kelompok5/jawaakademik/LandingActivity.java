package id.kelompok5.jawaakademik;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.ActivityNotFoundException;


public class LandingActivity extends AppCompatActivity {

    Button btnMulai, btnLogin;
    TextView txtMenu;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        btnMulai = findViewById(R.id.btnMulai);
        btnLogin = findViewById(R.id.btnLogin);
        txtMenu = findViewById(R.id.txtMenu);


        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        btnMulai.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        txtMenu.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.END);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menuTentang) {
                Intent intent = new Intent(LandingActivity.this, AboutActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }

            if (id == R.id.menuKontak) {
                bukaEmailAdmin();
                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }

            return false;
        });

        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menuTentang) {
                Intent intent = new Intent(LandingActivity.this, TentangAplikasiActivity.class);
                startActivity(intent);

                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }

            if (id == R.id.menuKontak) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:sofyanagung54321@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Kontak Admin Jawa Akademik");
                intent.putExtra(Intent.EXTRA_TEXT, "Halo Admin Jawa, saya ingin bertanya tentang aplikasi Jawa Akademik.");

                try {
                    startActivity(Intent.createChooser(intent, "Kirim email menggunakan"));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Tidak ada aplikasi email yang tersedia", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }

            return false;
        });
    }

    private void bukaEmailAdmin() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:sofyangagung54321@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Kontak Admin Jawa Akademik");
        intent.putExtra(Intent.EXTRA_TEXT, "Halo Admin, saya ingin bertanya mengenai aplikasi Jawa Akademik.");

        try {
            startActivity(Intent.createChooser(intent, "Kirim email menggunakan"));
        } catch (Exception e) {
            Toast.makeText(this, "Aplikasi email tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
}