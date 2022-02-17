package com.rb.apexassistant.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rb.apexassistant.model.PlayerStatsEntity;

@Dao
public abstract class PlayerStatsDao {
    @Query("SELECT * FROM stats WHERE name = :name")
    public abstract PlayerStatsEntity getByName(String name);

    @Insert
    public abstract long insert(PlayerStatsEntity playerStatsEntity);
}
