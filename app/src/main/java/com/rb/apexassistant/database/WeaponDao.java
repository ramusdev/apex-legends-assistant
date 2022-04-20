package com.rb.apexassistant.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.rb.apexassistant.model.WeaponEntity;

import java.util.List;

@Dao
public abstract class WeaponDao {
    @Insert
    public abstract long insert(WeaponEntity weaponEntity);

    @Insert
    public abstract void insertAll(List<WeaponEntity> weaponEntities);

    @Delete
    public abstract void delete(WeaponEntity weaponEntity);

    @Query("SELECT * FROM weapons WHERE type = :type")
    public abstract List<WeaponEntity> findAllByType(String type);

    // @Query("SELECT id, name, damage FROM weapons WHERE type = :type ORDER BY :sort")
    // public abstract List<WeaponEntity> findAllByTypeAndSort(String type, String sort);

    @Query("SELECT * FROM weapons")
    public abstract List<WeaponEntity> selectAll();

    public void deleteAll() {
        List<WeaponEntity> weaponEntities = selectAll();

        for (WeaponEntity weaponEntity : weaponEntities) {
            delete(weaponEntity);
        }
    }
}
