package com.rb.apexassistant.stats.deserializer;

import com.google.gson.*;
import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.stats.enumarate.LegendEnum;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegendDeserializer {
    public static class Deserializer implements JsonDeserializer<List<LegendEntity>> {

        @Override
        public List<LegendEntity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement jsonElementLegends = json.getAsJsonObject().get("legends");
            JsonElement jsonElementAll = jsonElementLegends.getAsJsonObject().get("all");
            List<LegendEntity> legendEntities = new ArrayList<>();

            for (LegendEnum legendEnum : LegendEnum.values()) {
                String legendName = legendEnum.getName();
                JsonElement jsonElementLegend = jsonElementAll.getAsJsonObject().get(legendName);

                if (jsonElementLegend.getAsJsonObject().get("data") != null) {
                    JsonElement data = jsonElementLegend.getAsJsonObject().get("data");
                    JsonArray jsonArray = data.getAsJsonArray().getAsJsonArray();

                    Map<String, String> valueMap = new HashMap<>();
                    for (JsonElement jsonItem : jsonArray) {
                        String value = jsonItem.getAsJsonObject().get("value").getAsString();
                        String key = jsonItem.getAsJsonObject().get("key").getAsString();
                        valueMap.put(key, value);
                    }

                    LegendEntity legendEntity = createObjectFromMap(valueMap);
                    legendEntity.setName(legendName);
                    legendEntities.add(legendEntity);
                } else {
                    LegendEntity legendEntity = new LegendEntity();
                    legendEntity.setName(legendName);
                    legendEntities.add(legendEntity);
                }
            }

            return legendEntities;
        }

        private LegendEntity createObjectFromMap(Map<String, String> map) {

            LegendEntity legendEntity = new LegendEntity();
            legendEntity.setKills(Integer.parseInt(map.getOrDefault("kills", "0")));
            legendEntity.setGamesPlayed(Integer.parseInt(map.getOrDefault("games_played", "0")));
            legendEntity.setHeadshots(Integer.parseInt(map.getOrDefault("headshots", "0")));
            legendEntity.setTopThree(Integer.parseInt(map.getOrDefault("top_3", "0")));
            legendEntity.setDroppedItems(Integer.parseInt(map.getOrDefault("dropped_items_for_squadmates", "0")));
            legendEntity.setDamage(Integer.parseInt(map.getOrDefault("damage", "0")));
            legendEntity.setPistolKills(Integer.parseInt(map.getOrDefault("pistol_kills", "0")));

            return legendEntity;
        }
    }
}
