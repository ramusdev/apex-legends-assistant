package com.rb.apexassistant.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.TaskRunner;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerStatsEntity;

import java.util.List;
import java.util.concurrent.Callable;

public class StatisticOptionsView extends ViewModel {
    private MutableLiveData<List<PlayerStatsEntity>> playerStatsEntityMutableLiveData;

    public MutableLiveData<List<PlayerStatsEntity>> getPlayerStatsEntity() {
        if (playerStatsEntityMutableLiveData == null) {
            this.playerStatsEntityMutableLiveData = new MutableLiveData<List<PlayerStatsEntity>>();
            loadData();
            Log.d("MyTag", "if null --->");
        }

        Log.d("MyTag", "near return --->");
        return playerStatsEntityMutableLiveData;
    }

    public void loadData() {

        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        TaskRunner<List<PlayerStatsEntity>> taskRunner = new TaskRunner<List<PlayerStatsEntity>>();
        taskRunner.executeAsync(new Callable<List<PlayerStatsEntity>>() {
            @Override
            public List<PlayerStatsEntity> call() throws InterruptedException {
                return playerStatsDao.getAll();
            }
        }, new TaskRunner.TaskRunnerCallback<List<PlayerStatsEntity>>() {
            @Override
            public void execute(List<PlayerStatsEntity> data) {
                playerStatsEntityMutableLiveData.postValue(data);
            }
        });
    }
}
