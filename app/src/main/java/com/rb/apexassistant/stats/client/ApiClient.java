package com.rb.apexassistant.stats.client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rb.apexassistant.model.PlayerStatsEntity;
import com.rb.apexassistant.stats.deserializer.StatsDeserializer;
import com.rb.apexassistant.stats.model.ErrorCode;
import com.rb.apexassistant.stats.model.PlayerStats;
import com.rb.apexassistant.stats.settings.ApiConfiguration;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class ApiClient extends ApiClientAbstract {

    public ApiClient(String apiKey) {
        super(apiKey);
    }

    @Override
    public PlayerStatsEntity getPlayerInfo(String playerName) {

        String url = ApiConfiguration.API_ENDPOINT;

        ArrayList<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("version", "5"));
        parameters.add(new BasicNameValuePair("platform", "PC"));
        parameters.add(new BasicNameValuePair("player", playerName));

        Map<String, String> response = super.makeGetCall(url, parameters);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ErrorCode errorCode = gson.fromJson(response.get("response"), ErrorCode.class);

        if (! response.get("code").equals("200")) {
            return null;
        }

        if (errorCode.getError() != null) {
            return null;
        }

        Type typeToken = new TypeToken<PlayerStatsEntity>() {}.getType();
        gsonBuilder.registerTypeAdapter(typeToken, new StatsDeserializer.PlayerDeserializer());

        PlayerStatsEntity playerStatsEntity = gson.fromJson(response.get("response"), typeToken);
        playerStatsEntity.setName(playerName);

        return playerStatsEntity;
    }
}
