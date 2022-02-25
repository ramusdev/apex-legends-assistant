package com.rb.apexassistant.tasks;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerEntity;
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
        /*
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        List<PlayerEntity> playerStatsEntities = playerStatsDao.getAll();

        if (playerStatsEntities.size() <= 0) {
            return 404;
        }

        ApiClient apiClient = new ApiClient(ApiConfiguration.AUTH_KEY);
        for (PlayerEntity playerEntity : playerStatsEntities) {
            PlayerEntity playerEntityNew = apiClient.getPlayerInfo(playerEntity.getName());
            if (playerEntityNew == null) {
                break;
            }
            playerEntityNew.setId(playerEntity.getId());
            playerStatsDao.update(playerEntityNew);
            Thread.sleep(10000);
        }

         */

        return 200;
    }
}
