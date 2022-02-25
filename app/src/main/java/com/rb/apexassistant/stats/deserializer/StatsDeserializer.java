package com.rb.apexassistant.stats.deserializer;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.rb.apexassistant.model.PlayerEntity;

import java.lang.reflect.Type;

public class StatsDeserializer {
    public static class PlayerDeserializer implements JsonDeserializer<PlayerEntity> {

        @Override
        public PlayerEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PlayerEntity playerEntity = new PlayerEntity();

            JsonElement jsonElementMain = json.getAsJsonObject().get("total");

            if (jsonElementMain.getAsJsonObject().get("kills") != null) {
                Log.d("MyTag", "Inside deserializer");
                JsonElement jsonElement1 = jsonElementMain.getAsJsonObject().get("kills");
                int value1 = jsonElement1.getAsJsonObject().get("value").getAsInt();
                playerEntity.setKills(value1);
            }
            if (jsonElementMain.getAsJsonObject().get("games_played") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("games_played");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setGamesPlayed(value);
            }
            if (jsonElementMain.getAsJsonObject().get("creeping_barrage_damage") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("creeping_barrage_damage");
                int value  = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setBarrageDamage(value );
            }
            if (jsonElementMain.getAsJsonObject().get("beast_of_the_hunt_kills") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("beast_of_the_hunt_kills");
                int value  = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setHuntKills(value);
            }
            if (jsonElementMain.getAsJsonObject().get("pistol_kills") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("pistol_kills");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setPistolKills(value);
            }
            if (jsonElementMain.getAsJsonObject().get("dropped_items_for_squadmates") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("dropped_items_for_squadmates");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setDroppedItems(value);
            }
            if (jsonElementMain.getAsJsonObject().get("beacons_scanned") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("beacons_scanned");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setBeaconsScanned(value);
            }
            if (jsonElementMain.getAsJsonObject().get("ar_kills") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("ar_kills");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setArKills(value);
            }
            if (jsonElementMain.getAsJsonObject().get("top_3") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("top_3");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setTopThree(value);
            }
            if (jsonElementMain.getAsJsonObject().get("damage") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("damage");
                int value = jsonElement.getAsJsonObject().get("value").getAsInt();
                playerEntity.setDamage(value);
            }
            if (jsonElementMain.getAsJsonObject().get("kd") != null) {
                JsonElement jsonElement = jsonElementMain.getAsJsonObject().get("kd");
                double value = jsonElement.getAsJsonObject().get("value").getAsDouble();
                playerEntity.setKd(value);
            }

            return playerEntity;
        }
    }
}
