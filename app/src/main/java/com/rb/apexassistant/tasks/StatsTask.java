package com.rb.apexassistant.tasks;

import android.util.Log;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerStatsEntity;
import com.rb.apexassistant.stats.client.ApiClient;
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

        Log.d("MyTag", "Before call --->");
        ApiClient apiClient = new ApiClient(ApiConfiguration.AUTH_KEY);
        PlayerStatsEntity playerStatsEntity = apiClient.getPlayerInfo(this.playerName);
        Log.d("MyTag", "After call --->");


        if (playerStatsEntity == null) {
            Log.d("MyTag", "404 --->");
            return 404;
        }

        // playerStatsDao.insert(playerStatsEntity);
        Log.d("MyTag", "200 --->");

        return 200;
    }
}
