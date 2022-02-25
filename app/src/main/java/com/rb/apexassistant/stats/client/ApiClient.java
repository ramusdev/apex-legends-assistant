package com.rb.apexassistant.stats.client;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.stats.deserializer.LegendDeserializer;
import com.rb.apexassistant.stats.deserializer.StatsDeserializer;

import java.lang.reflect.Type;
import java.util.List;

public class ApiClient extends ApiClientAbstract {

    public ApiClient(String apiKey) {
        super(apiKey);
    }

    @Override
    public PlayerEntity getPlayerInfo(String json, String playerName) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PlayerEntity.class, new StatsDeserializer.PlayerDeserializer());
        PlayerEntity playerEntity = gsonBuilder.create().fromJson(json, PlayerEntity.class);

        playerEntity.setName(playerName);

        return playerEntity;
    }

    @Override
    public List<LegendEntity> getPlayerLegends(String json, String playerName) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Type typeToken = new TypeToken<List<LegendEntity>>() {}.getType();
        gsonBuilder.registerTypeAdapter(typeToken, new LegendDeserializer.Deserializer());
        List<LegendEntity> legendEntities = gsonBuilder.create().fromJson(json, typeToken);

        return legendEntities;
    }
}
