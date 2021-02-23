package com.rb.apexlegendsassistant;

import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.material.navigation.NavigationView;
import com.rb.apexlegendsassistant.data.DataDbHelper;

import java.io.IOException;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public DataDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Open connection to db
        dbHelper = new DataDbHelper(this);

        // Tasks after create
        createTasks();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // Set window top and bottop color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.red));
        window.setNavigationBarColor(this.getResources().getColor(R.color.red));

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, NewsFragment.class, null).commit();
        // getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, DonateFragment.class, null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateNewsIfNotExists() {
        NewsLoader newsLoader = new NewsLoader(this.getApplicationContext());
        List<News> news = newsLoader.load();

        if (news.size() <= 0) {
            UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(this.getApplicationContext());
            updateNewsAsync.execute();
        }
    }

    public void createTasks() {
        updateNewsIfNotExists();
        // createPeriodicTask();
    }
}