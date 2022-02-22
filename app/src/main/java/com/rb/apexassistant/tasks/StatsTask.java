package com.rb.apexassistant.tasks;

import android.util.Log;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerStatsEntity;
import com.rb.apexassistant.stats.client.ApiClient;
import com.rb.apexassistant.stats.model.PlayerStats;
import com.rb.apexassistant.stats.settings.ApiConfiguration;
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
        PlayerStatsEntity playerStatsEntity = apiClient.getPlayerInfo(this.playerName);

        if (playerStatsEntity == null) {
            return 404;
        }

        playerStatsDao.insert(playerStatsEntity);

        return 200;
    }
}
