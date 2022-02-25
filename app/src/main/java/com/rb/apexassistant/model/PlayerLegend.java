package com.rb.apexassistant.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PlayerLegend {
    @Embedded
    public PlayerEntity player;
    @Relation(parentColumn = "id", entityColumn = "playerId")
    public List<LegendEntity> legends;

    public PlayerLegend(PlayerEntity player, List<LegendEntity> legends) {
        this.player = player;
        this.legends = legends;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public List<LegendEntity> getLegends() {
        return legends;
    }

    public void setLegends(List<LegendEntity> legends) {
        this.legends = legends;
    }
}
