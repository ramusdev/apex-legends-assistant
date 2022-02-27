package com.rb.apexassistant.database;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.model.PlayerLegend;

import java.util.List;

@Dao
public abstract class PlayerStatsDao {
    @Query("SELECT * FROM stats WHERE name = :name")
    public abstract PlayerEntity getByName(String name);

    @Query("SELECT * FROM stats WHERE id = :playerId")
    public abstract PlayerEntity getById(long playerId);

    @Query("SELECT * FROM stats")
    public abstract List<PlayerEntity> getAll();

    @Insert
    public abstract long insertPlayer(PlayerEntity playerEntity);

    @Insert
    public abstract void insertAllLegends(List<LegendEntity> legends);

    @Delete
    public abstract void delete(PlayerEntity playerEntity);

    @Query("SELECT * FROM stats LIMIT 1")
    public abstract PlayerEntity getFirstPlayer();

    @Update
    public abstract void update(PlayerEntity playerEntity);

    @Transaction
    @Query("SELECT * FROM stats WHERE id = :id")
    public abstract PlayerLegend getPlayerLegendById(long id);

    @Transaction
    @Query("SELECT * FROM stats LIMIT 1")
    public abstract PlayerLegend getFirstPlayerLegend();

    @Transaction
    public void updatePlayerLegend(PlayerLegend playerLegend) {
        this.updatePlayer(playerLegend.getPlayer());
        this.updateLegend(playerLegend.getLegends());
    }

    @Update
    public abstract void updatePlayer(PlayerEntity playerEntity);

    @Update
    public abstract void updateLegend(List<LegendEntity> legendEntity);

    public void insertPlayerAndLegends(PlayerEntity player, List<LegendEntity> legends) {
        long id = this.insertPlayer(player);
        for (LegendEntity legend : legends) {
            legend.setPlayerId(id);
        }
        this.insertAllLegends(legends);
    }
}
