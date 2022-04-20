package com.rb.apexassistant.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.MyApplicationContext;
import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.model.WeaponEntity;

@Database(entities = {PlayerEntity.class, LegendEntity.class, WeaponEntity.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerStatsDao PlayerStatsDao();
    public abstract WeaponDao WeaponDao();
    public static AppDatabase instance;
    public static final String DB_NAME = "databaseroom";

    public static synchronized AppDatabase getInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(MyApplicationContext.getAppContext(), AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
