package com.rb.apexassistant;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.rb.apexassistant.data.DataDbHelper;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.tasks.PopulatorWeaponsTask;
import com.rb.apexassistant.tasks.StatsUpdateTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public DataDbHelper dbHelper;
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_VERSION = "version";
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        AdmobInterstitialAd admobInterstitialAd = new AdmobInterstitialAd.Builder()
                .show(true)
                .activity(this)
                .testMode(false)
                .adId(ADMOB_INTERSTITIAL_ID)
                .build();

        */

        // admobInterstitialAd.show();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_news));
        setSupportActionBar(toolbar);

        // Open connection to db
        dbHelper = new DataDbHelper(this);

        // Tasks after create
        createTasks();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId() == R.id.nav_item_news) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, NewsFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_donate) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, DonateFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_about) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, AboutFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_twitter) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, TweetsFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_wallpaper) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, WallpaperFragment.class, null).commit();
                        }
                    }, 275);
                }

                /*
                if (item.getItemId() == R.id.nav_item_settings) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, SettingsFragment.class, null).commit();
                        }
                    }, 275);
                }
                */

                if (item.getItemId() == R.id.nav_item_legends) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, LegendsFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_stats) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // item.setChecked(true);

                            isFirstPlayerInBd();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_statsoptions) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, StatisticOptionsFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_weapons) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, WeaponsTabFragment.class, null).commit();
                        }
                    }, 275);
                }



                return false;
            }
        });

        // Set window top and bottom color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.red));
        window.setNavigationBarColor(this.getResources().getColor(R.color.red));

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, NewsFragment.class, null).commit();
    }

    public void isFirstPlayerInBd() {
        AppDatabase appDatabase = AppDatabase.getInstance();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        TaskRunner<PlayerEntity> taskRunner = new TaskRunner<PlayerEntity>();
        taskRunner.executeAsync(new Callable<PlayerEntity>() {
            @Override
            public PlayerEntity call() throws InterruptedException {
                return playerStatsDao.getFirstPlayer();
            }
        }, new TaskRunner.TaskRunnerCallback<PlayerEntity>() {
            @Override
            public void execute(PlayerEntity playerEntity) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                if (playerEntity == null) {
                    transaction.replace(R.id.nav_host_fragment, StatisticOptionsFragment.class, null).commit();
                } else {
                    transaction.replace(R.id.nav_host_fragment, StatisticFragment.class, null).commit();
                }
            }
        });
    }

    public void createTasks() {

        /*
        Log.d("MyTag", "create task -->");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                AppOpenManager appOpenManager = null;
                appOpenManager = new AppOpenManager(MyApplication.getInstance());
            }
        });
        */



        NewsLoader newsLoader = new NewsLoader(this.getApplicationContext());
        List<News> news = newsLoader.load();

        TweetsLoader tweetsLoader = new TweetsLoader(this.getApplicationContext());
        List<Tweet> tweets = tweetsLoader.load();

        TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();

        // Callable statsUpdateCallable = new StatsUpdateTask();
        // taskRunner.executeAsync(statsUpdateCallable);
        // Callable statsTask = new StatsTask();
        // taskRunner.executeAsync(statsTask);

        if (news.size() <= 0) {
            Callable callable = new NewsUpdateCallable(5);
            taskRunner.executeAsync(callable);
        }

        if (tweets.size() <= 0) {
            Callable callable = new TweetsUpdateCallable();
            taskRunner.executeAsync(callable);
        }

        SharedPreferences sharedPreferences = MyApplicationContext.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String currentVersion = sharedPreferences.getString(APP_PREFERENCES_VERSION, "");

        if (! currentVersion.equals(getVersionName())) {
            Log.d("MyTag", "Current version ------------------------->");
            Callable populatorWallpapper = new DatabasePopulatorCallable<Wallpaper>("Wallpapers/wallpapers.json", Wallpaper.class);
            taskRunner.executeAsync(populatorWallpapper);

            Callable populatorLegend = new DatabasePopulatorCallable<Legend>(getResources().getString(R.string.legends_json_dir), Legend.class);
            taskRunner.executeAsync(populatorLegend);

            Callable populatorWeaponsTask = new PopulatorWeaponsTask();
            taskRunner.executeAsync(populatorWeaponsTask);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(APP_PREFERENCES_VERSION, getVersionName());
            editor.apply();
        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            );
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }

    public String getVersionName() {
        String versionName = null;

        try {
            ComponentName componentName = new ComponentName(MyApplicationContext.getAppContext(), MyApplicationContext.getAppContext().getClass());
            PackageInfo packageInfo = MyApplicationContext.getAppContext().getPackageManager().getPackageInfo(componentName.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("MyTag", "Error: getVersionName" + e.getMessage());
        }

        return versionName;
    }
}