package com.rb.apexassistant.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weapons")
public class WeaponEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String type;
    private String image;
    private String damage;
    private String dps;
    private String rpm;
    private String magazine;
    private String reload;

    public WeaponEntity(long id, String name, String type, String image, String damage, String dps, String rpm, String magazine, String reload) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.image = image;
        this.damage = damage;
        this.dps = dps;
        this.rpm = rpm;
        this.magazine = magazine;
        this.reload = reload;
    }

    public WeaponEntity() {

    }

    public String getDps() {
        return dps;
    }

    public void setDps(String dps) {
        this.dps = dps;
    }

    public String getRpm() {
        return rpm;
    }

    public void setRpm(String rpm) {
        this.rpm = rpm;
    }

    public String getMagazine() {
        return magazine;
    }

    public void setMagazine(String magazine) {
        this.magazine = magazine;
    }

    public String getReload() {
        return reload;
    }

    public void setReload(String reload) {
        this.reload = reload;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }
}
