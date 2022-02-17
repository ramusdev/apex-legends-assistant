package com.rb.apexassistant.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.rb.apexassistant.model.PlayerStatsEntity;

@Database(entities = {PlayerStatsEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerStatsDao PlayerStatsDao();
}
