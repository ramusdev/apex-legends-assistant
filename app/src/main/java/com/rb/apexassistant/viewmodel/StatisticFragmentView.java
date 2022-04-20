package com.rb.apexassistant.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.TaskRunner;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerEntity;

import java.util.concurrent.Callable;

public class StatisticFragmentView extends ViewModel {
    private MutableLiveData<PlayerEntity> playerStatsEntityMutableLiveData;
    // private MutableLiveData<Integer> playerId;

    public MutableLiveData<PlayerEntity> getPlayerStatsEntity(long playerIdNew) {
        if (playerStatsEntityMutableLiveData == null) {
            this.playerStatsEntityMutableLiveData = new MutableLiveData<PlayerEntity>();
            loadData(playerIdNew);
            return playerStatsEntityMutableLiveData;
        } else if (playerStatsEntityMutableLiveData.getValue().getId() == playerIdNew) {
            return playerStatsEntityMutableLiveData;
        } else {
            loadData(playerIdNew);
            return playerStatsEntityMutableLiveData;
        }
    }

    public void loadData(long playerId) {

        AppDatabase appDatabase = AppDatabase.getInstance();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        TaskRunner<PlayerEntity> taskRunner = new TaskRunner<PlayerEntity>();
        taskRunner.executeAsync(new Callable<PlayerEntity>() {
            @Override
            public PlayerEntity call() throws InterruptedException {
                return playerStatsDao.getById(playerId);
            }
        }, new TaskRunner.TaskRunnerCallback<PlayerEntity>() {
            @Override
            public void execute(PlayerEntity data) {
                playerStatsEntityMutableLiveData.postValue(data);
            }
        });
    }
}
