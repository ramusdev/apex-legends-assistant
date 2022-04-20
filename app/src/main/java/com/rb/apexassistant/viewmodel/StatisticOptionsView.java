package com.rb.apexassistant.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.TaskRunner;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerEntity;

import java.util.List;
import java.util.concurrent.Callable;

public class StatisticOptionsView extends ViewModel {
    private MutableLiveData<List<PlayerEntity>> playerStatsEntityMutableLiveData;

    public MutableLiveData<List<PlayerEntity>> getPlayerStatsEntity() {
        if (playerStatsEntityMutableLiveData == null) {
            this.playerStatsEntityMutableLiveData = new MutableLiveData<List<PlayerEntity>>();
            loadData();
            Log.d("MyTag", "if null --->");
        }

        Log.d("MyTag", "near return --->");
        return playerStatsEntityMutableLiveData;
    }

    public void loadData() {

        AppDatabase appDatabase = AppDatabase.getInstance();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        TaskRunner<List<PlayerEntity>> taskRunner = new TaskRunner<List<PlayerEntity>>();
        taskRunner.executeAsync(new Callable<List<PlayerEntity>>() {
            @Override
            public List<PlayerEntity> call() throws InterruptedException {
                return playerStatsDao.getAll();
            }
        }, new TaskRunner.TaskRunnerCallback<List<PlayerEntity>>() {
            @Override
            public void execute(List<PlayerEntity> data) {
                playerStatsEntityMutableLiveData.postValue(data);
            }
        });
    }
}
