package com.rb.apexassistant.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.model.PlayerEntity;

@Database(entities = {PlayerEntity.class, LegendEntity.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerStatsDao PlayerStatsDao();
}
