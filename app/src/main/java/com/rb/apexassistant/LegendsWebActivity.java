package com.rb.apexassistant;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LegendsWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_second);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_legend));
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        // Set window top and bottop color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.red));
        window.setNavigationBarColor(this.getResources().getColor(R.color.red));

        // Web view
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setBackgroundColor(0);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebChromeClient(new WebChromeClient());

        String link = getIntent().getStringExtra("BUNDLE_LINK");

        // Style after move to parser
        /*
        String style = "<link type=\"text/css\" rel=\"stylesheet\" media=\"all\" href=\"https://edgenews.ru/android/apexlegends/legends/static/style.css\">";
        StringBuilder stringBuilder = new StringBuilder(text)
            .insert(0, "<html>")
            .insert(6, titleHtml)
            .append(style)
            .append("</html>");

        */

        webView.loadUrl(link);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}