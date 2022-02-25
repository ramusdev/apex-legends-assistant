package com.rb.apexassistant;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.rb.apexassistant.database.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MyApplication extends Application {

    private static AppOpenManager appOpenManager;
    public AppDatabase database;
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(this, AppDatabase.class, "databaseroom")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        MyApplicationContext myApplicationContext = new MyApplicationContext(this);

        /*
        MobileAds.initialize(MyApplicationContext.getAppContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                appOpenManager = new AppOpenManager(MyApplication.this);
            }
        });
        */

        // appOpenManager = new AppOpenManager(this);

        Log.d("MyTag", "My application start class --->");
        WorkManager workManager = WorkManager.getInstance(this);
        ListenableFuture<List<WorkInfo>> statuses = workManager.getWorkInfosByTag("task_worker7");

        try {
            List<WorkInfo> workInfoList = statuses.get();

            if (workInfoList.size() <= 0) {
                PeriodicWorkCreator periodicWorkCreator = new PeriodicWorkCreator((Application) getApplicationContext());
                periodicWorkCreator.create();
            }
        } catch(ExecutionException | InterruptedException e) {
            Log.d("MyTag", e.getMessage());
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
