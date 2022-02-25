package com.rb.apexassistant.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "stats")
public class PlayerEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    private int kills;
    private int gamesPlayed;
    private int barrageDamage;
    private int topThree;
    private int huntKills;
    private int damage;
    private int droppedItems;
    private int pistolKills;
    private int beaconsScanned;
    private int arKills;
    private double kd;

    public PlayerEntity(long id, String name, int kills, int gamesPlayed, int barrageDamage, int topThree, int huntKills, int damage, int droppedItems, int pistolKills, int beaconsScanned, int arKills, double kd) {
        this.id = id;
        this.name = name;
        this.kills = kills;
        this.gamesPlayed = gamesPlayed;
        this.barrageDamage = barrageDamage;
        this.topThree = topThree;
        this.huntKills = huntKills;
        this.damage = damage;
        this.droppedItems = droppedItems;
        this.pistolKills = pistolKills;
        this.beaconsScanned = beaconsScanned;
        this.arKills = arKills;
        this.kd = kd;
    }

    public PlayerEntity() {

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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getBarrageDamage() {
        return barrageDamage;
    }

    public void setBarrageDamage(int barrageDamage) {
        this.barrageDamage = barrageDamage;
    }

    public int getTopThree() {
        return topThree;
    }

    public void setTopThree(int topThree) {
        this.topThree = topThree;
    }

    public int getHuntKills() {
        return huntKills;
    }

    public void setHuntKills(int huntKills) {
        this.huntKills = huntKills;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDroppedItems() {
        return droppedItems;
    }

    public void setDroppedItems(int droppedItems) {
        this.droppedItems = droppedItems;
    }

    public int getPistolKills() {
        return pistolKills;
    }

    public void setPistolKills(int pistolKills) {
        this.pistolKills = pistolKills;
    }

    public int getBeaconsScanned() {
        return beaconsScanned;
    }

    public void setBeaconsScanned(int beaconsScanned) {
        this.beaconsScanned = beaconsScanned;
    }

    public int getArKills() {
        return arKills;
    }

    public void setArKills(int arKills) {
        this.arKills = arKills;
    }

    public double getKd() {
        return kd;
    }

    public void setKd(double kd) {
        this.kd = kd;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "kills=" + kills +
                ", gamesPlayed=" + gamesPlayed +
                ", barrageDamage=" + barrageDamage +
                ", topThree=" + topThree +
                ", huntKills=" + huntKills +
                ", damage=" + damage +
                ", droppedItems=" + droppedItems +
                ", pistolKills=" + pistolKills +
                ", beaconsScanned=" + beaconsScanned +
                ", arKills=" + arKills +
                ", kd=" + kd +
                '}';
    }
}
