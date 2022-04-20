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

        AppDatabase appDatabase = AppDatabase.getInstance();
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
            List<LegendEntity> legendsNew = apiClient.getPlayerLegends(json, playerName);
            PlayerLegend playerLegend = playerStatsDao.getPlayerLegendById(playerId);

            setIdToLegends(legendsNew, playerLegend);
            player.setId(playerId);

            PlayerLegend playerLegendUpdate = new PlayerLegend();
            playerLegendUpdate.setPlayer(player);
            playerLegendUpdate.setLegends(legendsNew);

            playerStatsDao.updatePlayerLegend(playerLegendUpdate);
            Thread.sleep(10000);
        }

        return 200;
    }

    public void setIdToLegends(List<LegendEntity> legends, PlayerLegend playerLegend) {
        List<LegendEntity> legendsOld = playerLegend.getLegends();
        long playerId = playerLegend.getPlayer().getId();
        for (LegendEntity legend : legends) {
            legend.setPlayerId(playerId);
            for (LegendEntity legendOld : legendsOld) {
                if (legend.getName().contains(legendOld.getName())) {
                    legend.setId(legendOld.getId());
                    break;
                }
            }
        }
    }
}
