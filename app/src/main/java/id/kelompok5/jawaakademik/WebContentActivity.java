package id.kelompok5.jawaakademik;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WebContentActivity extends AppCompatActivity {

    private ProgressBar progressWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_content);

        TextView tvWebTitle = findViewById(R.id.tvWebTitle);
        WebView webView = findViewById(R.id.webViewContent);
        progressWeb = findViewById(R.id.progressWeb);

        String title = getIntent().getStringExtra("WEB_TITLE");
        String url = getIntent().getStringExtra("WEB_URL");

        tvWebTitle.setText(title == null || title.isEmpty() ? "Materi" : title);
        findViewById(R.id.btnBackWeb).setOnClickListener(v -> {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressWeb.setProgress(newProgress);
                progressWeb.setVisibility(newProgress >= 100 ? View.GONE : View.VISIBLE);
            }
        });

        if (url == null || url.isEmpty()) {
            AppToast.show(this, "Link materi belum tersedia");
        } else {
            webView.loadUrl(url);
        }
    }
}
