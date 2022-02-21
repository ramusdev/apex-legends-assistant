package com.rb.apexassistant.tasks;

import android.os.SystemClock;
import android.util.Log;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerStatsEntity;
import com.rb.apexassistant.stats.client.ApiClient;
import com.rb.apexassistant.stats.settings.ApiConfiguration;

import java.util.List;
import java.util.concurrent.Callable;

public class StatsUpdateTask implements Callable<Integer> {

    public String playerName;

    public StatsUpdateTask() {
    }

    @Override
    public Integer call() throws Exception {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        List<PlayerStatsEntity> playerStatsEntities = playerStatsDao.getAll();

        if (playerStatsEntities.size() <= 0) {
            return 404;
        }

        ApiClient apiClient = new ApiClient(ApiConfiguration.AUTH_KEY);
        for (PlayerStatsEntity playerStatsEntity : playerStatsEntities) {
            PlayerStatsEntity playerStatsEntityNew = apiClient.getPlayerInfo(playerStatsEntity.getName());
            if (playerStatsEntityNew == null) {
                break;
            }
            playerStatsEntityNew.setId(playerStatsEntity.getId());
            playerStatsDao.update(playerStatsEntityNew);
            Thread.sleep(10000);
        }

        return 200;
    }
}
