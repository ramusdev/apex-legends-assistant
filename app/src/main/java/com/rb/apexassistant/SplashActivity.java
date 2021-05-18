package com.rb.apexassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.rb.apexassistant.data.DataDbHelper;
import java.util.List;
import java.util.concurrent.Callable;

public class SplashActivity extends AppCompatActivity {

    private int splashTime;
    public DataDbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View decorView = getWindow().getDecorView();
        final int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(flags);

        setContentView(R.layout.activity_splash);

        // Tasks after create
        createTasks();
    }

    public void updateNewsIfNotExists() {
        dbHelper = new DataDbHelper(this.getApplicationContext());
        NewsLoader newsLoader = new NewsLoader(this.getApplicationContext());
        List<News> news = newsLoader.load();

        if (news.size() <= 0) {
            final TaskRunner.TaskRunnerCallback<Integer> task = new TaskRunner.TaskRunnerCallback<Integer>() {
                @Override
                public void execute(Integer taskAfterDone) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                    SplashActivity.this.finish();
                }
            };

            TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();
            Callable callable = new NewsUpdateCallable(2);
            taskRunner.executeAsync(callable, task);
        } else {
            closeSplash();
        }
    }

    public void closeSplash() {
        splashTime = 1000;
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