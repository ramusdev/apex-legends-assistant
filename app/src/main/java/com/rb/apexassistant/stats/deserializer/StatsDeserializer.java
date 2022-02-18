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
            PlayerStatsEntity playerStatsEntity = new PlayerStatsEntity();

            if (! jsonElementMain.getAsJsonObject().get("kills").isJsonNull()) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("kills");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerStatsEntity.setKills(value);
            }
            if (! jsonElementMain.getAsJsonObject().get("games_played").isJsonNull()) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("games_played");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerStatsEntity.setGamesPlayed(value);
            }
            if (! jsonElementMain.getAsJsonObject().get("creeping_barrage_damage").isJsonNull()) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("creeping_barrage_damage");
                int value  = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerStatsEntity.setBarrageDamage(value );
            }
            if (! jsonElementMain.getAsJsonObject().get("beast_of_the_hunt_kills").isJsonNull()) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("beast_of_the_hunt_kills");
                int value  = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerStatsEntity.setHuntKills(value);
            }
            if (! jsonElementMain.getAsJsonObject().get("pistol_kills").isJsonNull()) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("pistol_kills");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerStatsEntity.setPistolKills(value);
            }
            if (! jsonElementMain.getAsJsonObject().get("dropped_items_for_squadmates").isJsonNull()) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("dropped_items_for_squadmates");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerStatsEntity.setDroppedItems(value);
            }
            if (! jsonElementMain.getAsJsonObject().get("beacons_scanned").isJsonNull()) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("beacons_scanned");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerStatsEntity.setBeaconsScanned(value);
            }
            if (! jsonElementMain.getAsJsonObject().get("ar_kills").isJsonNull()) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("ar_kills");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerStatsEntity.setArKills(value);
            }

            JsonElement jsonElementTopThree = jsonElementMain.getAsJsonObject().get("top_3");
            int topThree = jsonElementTopThree.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementDamage = jsonElementMain.getAsJsonObject().get("damage");
            int damage = jsonElementDamage.getAsJsonObject().get("value").getAsInt();
            JsonElement jsonElementKd = jsonElementMain.getAsJsonObject().get("kd");
            double kd = jsonElementKd.getAsJsonObject().get("value").getAsDouble();

            playerStatsEntity.setTopThree(topThree);
            playerStatsEntity.setDamage(damage);
            playerStatsEntity.setKd(kd);

            return playerStatsEntity;
        }
    }
}
