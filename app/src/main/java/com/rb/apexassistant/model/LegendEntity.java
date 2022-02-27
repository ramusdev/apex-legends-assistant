package com.rb.apexassistant.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "legends", foreignKeys = @ForeignKey(
            entity = PlayerEntity.class,
            parentColumns = "id",
            childColumns = "playerId",
            onDelete = CASCADE)
)
public class LegendEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int kills;
    private int damage;
    private int gamesPlayed;
    private int topThree;
    private int droppedItems;
    private int headshots;
    private int pistolKills;
    private long playerId;

    @Override
    public String toString() {
        return "LegendEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", kills=" + kills +
                ", damage=" + damage +
                ", gamesPlayed=" + gamesPlayed +
                ", topThree=" + topThree +
                ", droppedItems=" + droppedItems +
                ", headshots=" + headshots +
                ", pistolKills=" + pistolKills +
                ", playerId=" + playerId +
                '}';
    }

    public LegendEntity(long id, String name, int kills, int damage, int gamesPlayed, int topThree, int droppedItems, int headshots, int pistolKills, long playerId) {
        this.id = id;
        this.name = name;
        this.kills = kills;
        this.damage = damage;
        this.gamesPlayed = gamesPlayed;
        this.topThree = topThree;
        this.droppedItems = droppedItems;
        this.headshots = headshots;
        this.pistolKills = pistolKills;
        this.playerId = playerId;
    }

    public LegendEntity() {

    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getPistolKills() {
        return pistolKills;
    }

    public void setPistolKills(int pistolKills) {
        this.pistolKills = pistolKills;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getTopThree() {
        return topThree;
    }

    public void setTopThree(int topThree) {
        this.topThree = topThree;
    }

    public int getDroppedItems() {
        return droppedItems;
    }

    public void setDroppedItems(int droppedItems) {
        this.droppedItems = droppedItems;
    }
}
