package com.rb.apexassistant.tasks;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.stats.client.ApiClient;
import com.rb.apexassistant.stats.settings.ApiConfiguration;

import java.util.List;
import java.util.concurrent.Callable;

public class StatsTask implements Callable<Integer> {

    public String playerName;

    public StatsTask(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public Integer call() throws Exception {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        ApiClient apiClient = new ApiClient(ApiConfiguration.AUTH_KEY);
        String json = apiClient.getResponse(this.playerName);

        if (json == null) {
            return 404;
        }

        PlayerEntity playerEntity = apiClient.getPlayerInfo(json, this.playerName);
        List<LegendEntity> legendEntities = apiClient.getPlayerLegends(json, this.playerName);

        playerStatsDao.insertPlayerAndLegends(playerEntity, legendEntities);

        return 200;
    }
}
