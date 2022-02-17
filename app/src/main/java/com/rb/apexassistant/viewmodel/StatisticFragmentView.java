package com.rb.apexassistant.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.TaskRunner;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerStatsEntity;
import java.util.concurrent.Callable;

public class StatisticFragmentView extends ViewModel {
    private MutableLiveData<PlayerStatsEntity> playerStatsEntityMutableLiveData;

    public MutableLiveData<PlayerStatsEntity> getPlayerStatsEntity() {
        if (playerStatsEntityMutableLiveData == null) {
            this.playerStatsEntityMutableLiveData = new MutableLiveData<PlayerStatsEntity>();
            loadData();
            Log.d("MyTag", "if null --->");
        }

        Log.d("MyTag", "near return --->");
        return playerStatsEntityMutableLiveData;
    }

    public void loadData() {

        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        TaskRunner<PlayerStatsEntity> taskRunner = new TaskRunner<PlayerStatsEntity>();
        taskRunner.executeAsync(new Callable<PlayerStatsEntity>() {
            @Override
            public PlayerStatsEntity call() throws InterruptedException {
                return playerStatsDao.getByName("HeyImLifeline");
            }
        }, new TaskRunner.TaskRunnerCallback<PlayerStatsEntity>() {
            @Override
            public void execute(PlayerStatsEntity data) {
                playerStatsEntityMutableLiveData.postValue(data);
            }
        });
    }
}
