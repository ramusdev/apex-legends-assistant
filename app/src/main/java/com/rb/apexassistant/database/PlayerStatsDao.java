package com.rb.apexassistant.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rb.apexassistant.model.PlayerStatsEntity;

import java.util.List;

@Dao
public abstract class PlayerStatsDao {
    @Query("SELECT * FROM stats WHERE name = :name")
    public abstract PlayerStatsEntity getByName(String name);

    @Query("SELECT * FROM stats WHERE id = :playerId")
    public abstract PlayerStatsEntity getById(long playerId);

    @Query("SELECT * FROM stats")
    public abstract List<PlayerStatsEntity> getAll();

    @Insert
    public abstract long insert(PlayerStatsEntity playerStatsEntity);
}
