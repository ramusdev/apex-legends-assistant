package com.rb.apexlegendsassistant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.rb.apexlegendsassistant.data.DataDbHelper;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private int splashTime;
    public DataDbHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        final int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        setContentView(R.layout.activity_splash);

        // Open connection to db
        dbHelper = new DataDbHelper(this);

        // Tasks after create
        createTasks();
    }

    public void updateNewsIfNotExists() {
        NewsLoader newsLoader = new NewsLoader(this.getApplicationContext());
        List<News> news = newsLoader.load();

        if (news.size() <= 0) {
            UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(this.getApplicationContext());
            updateNewsAsync.execute();

            splashTime = 9000;
        } else {
            splashTime = 1000;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, splashTime);
    }

    public void createTasks() {
        updateNewsIfNotExists();
    }

    /*
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
    */
}