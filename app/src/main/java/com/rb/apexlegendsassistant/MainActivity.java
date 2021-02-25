package com.rb.apexlegendsassistant;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.material.navigation.NavigationView;
import com.rb.apexlegendsassistant.data.DataDbHelper;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentTransaction;
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
        toolbar.setTitle(getResources().getString(R.string.donate_text));
        setSupportActionBar(toolbar);

        // Open connection to db
        dbHelper = new DataDbHelper(this);

        // Tasks after create
        createTasks();

        // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        // mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();

        // navigationView.setNavigationItemSelectedListener(this);
        // ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        // drawerLayout.setDrawerListener(mDrawerToggle);
        // mDrawerToggle.syncState();
        // Navigation
        // DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();

                Log.d("MyTag", String.valueOf(item.getItemId()));
                Log.d("MyTag", "text");

                if (item.getItemId() == R.id.nav_item_news) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment, NewsFragment.class, null).commit();
                        }
                    }, 500);
                }

                if (item.getItemId() == R.id.nav_item_donate) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment, DonateFragment.class, null).commit();
                        }
                    }, 500);
                }


                return false;
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // mAppBarConfiguration = new AppBarConfiguration.Builder(
                // R.id.nav_item_news, R.id.nav_item_donate)
                // .setDrawerLayout(drawerLayout)
                // .build();

        // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        // NavigationUI.setupWithNavController(navigationView, navController);


        // Set window top and bottop color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.red));
        window.setNavigationBarColor(this.getResources().getColor(R.color.red));

        // getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, NewsFragment.class, null).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, DonateFragment.class, null).commit();
    }

    /*
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
    */

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