package com.rb.apexassistant.tasks;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.model.PlayerLegend;
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

        List<PlayerEntity> playerStatsEntities = playerStatsDao.getAll();

        if (playerStatsEntities.size() <= 0) {
            return 404;
        }

        ApiClient apiClient = new ApiClient(ApiConfiguration.AUTH_KEY);


        for (PlayerEntity playerEntity : playerStatsEntities) {
            String playerName = playerEntity.getName();
            long playerId = playerEntity.getId();

            String json = apiClient.getResponse(playerName);

            if (json == null) {
                continue;
            }

            PlayerEntity player = apiClient.getPlayerInfo(json, playerName);
            List<LegendEntity> legends = apiClient.getPlayerLegends(json, playerName);

            player.setId(playerId);

            PlayerLegend playerLegend = new PlayerLegend();
            playerLegend.setPlayer(player);
            playerLegend.setLegends(legends);
            
            Thread.sleep(10000);
        }



        return 200;
    }
}
