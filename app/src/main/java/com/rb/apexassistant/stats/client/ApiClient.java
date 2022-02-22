package com.rb.apexassistant.stats.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rb.apexassistant.model.PlayerStatsEntity;
import com.rb.apexassistant.stats.deserializer.StatsDeserializer;
import com.rb.apexassistant.stats.model.ErrorCode;
import com.rb.apexassistant.stats.settings.ApiConfiguration;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

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

        GsonBuilder gsonBuilderError = new GsonBuilder();
        Gson gsonError = gsonBuilderError.create();
        ErrorCode errorCode = gsonError.fromJson(response.get("response"), ErrorCode.class);

        if (! response.get("code").equals("200")) {
            return null;
        }

        if (errorCode.getError() != null) {
            return null;
        }

        String json = response.get("response");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PlayerStatsEntity.class, new StatsDeserializer.PlayerDeserializer());
        PlayerStatsEntity playerStatsEntity = gsonBuilder.create().fromJson(json, PlayerStatsEntity.class);

        playerStatsEntity.setName(playerName);

        return playerStatsEntity;
    }
}
