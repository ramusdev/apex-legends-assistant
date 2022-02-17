package com.rb.apexassistant.stats.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.rb.apexassistant.model.PlayerStatsEntity;

import java.lang.reflect.Type;

public class StatsDeserializer {
    public static class PlayerDeserializer implements JsonDeserializer<PlayerStatsEntity> {

        @Override
        public PlayerStatsEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement jsonElementMain = json.getAsJsonObject().get("total");

            JsonElement jsonElementKills = jsonElementMain.getAsJsonObject().get("kills");
            int kills = jsonElementKills.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementGamesPlayed = jsonElementMain.getAsJsonObject().get("games_played");
            int gamesPlayed = jsonElementGamesPlayed.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementGamesBarrageDamage = jsonElementMain.getAsJsonObject().get("creeping_barrage_damage");
            int barrageDamage = jsonElementGamesBarrageDamage.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementTopThree = jsonElementMain.getAsJsonObject().get("top_3");
            int topThree = jsonElementTopThree.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementHuntKills = jsonElementMain.getAsJsonObject().get("beast_of_the_hunt_kills");
            int huntKills = jsonElementHuntKills.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementDamage = jsonElementMain.getAsJsonObject().get("damage");
            int damage = jsonElementDamage.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementDroppedItems = jsonElementMain.getAsJsonObject().get("dropped_items_for_squadmates");
            int droppedItems = jsonElementDroppedItems.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementPistolKills = jsonElementMain.getAsJsonObject().get("pistol_kills");
            int pistolKills = jsonElementPistolKills.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementBeaconsScanned = jsonElementMain.getAsJsonObject().get("beacons_scanned");
            int beaconsScanned = jsonElementBeaconsScanned.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementArKills = jsonElementMain.getAsJsonObject().get("ar_kills");
            int arKills = jsonElementArKills.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementKd = jsonElementMain.getAsJsonObject().get("kd");
            double kd = jsonElementKd.getAsJsonObject().get("value").getAsDouble();

            PlayerStatsEntity playerStatsEntity = new PlayerStatsEntity();
            playerStatsEntity.setKills(kills);
            playerStatsEntity.setGamesPlayed(gamesPlayed);
            playerStatsEntity.setBarrageDamage(barrageDamage);
            playerStatsEntity.setTopThree(topThree);
            playerStatsEntity.setHuntKills(huntKills);
            playerStatsEntity.setDamage(damage);
            playerStatsEntity.setDroppedItems(droppedItems);
            playerStatsEntity.setPistolKills(pistolKills);
            playerStatsEntity.setBeaconsScanned(beaconsScanned);
            playerStatsEntity.setArKills(arKills);
            playerStatsEntity.setKd(kd);

            return playerStatsEntity;
        }
    }
}
