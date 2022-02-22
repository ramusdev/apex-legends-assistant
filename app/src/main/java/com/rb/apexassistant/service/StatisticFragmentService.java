package com.rb.apexassistant.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.R;
import com.rb.apexassistant.StatisticFragment;
import com.rb.apexassistant.StatisticOptionsFragment;
import com.rb.apexassistant.TaskRunner;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerStatsEntity;

import java.util.concurrent.Callable;

public class StatisticFragmentService {
    private static final String BUNDLE_ID = "bundle_id";
    private Fragment fragment;

    public StatisticFragmentService(Fragment fragment) {
        this.fragment = fragment;
    }

    public void showData() {
        Bundle bundle = fragment.getArguments();

        if (bundle == null) {
            loadFirstPlayer();
        } else {
            long id = bundle.getLong(BUNDLE_ID);
            loadPlayer(id);
        }
    }


    private void loadFirstPlayer() {

        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        TaskRunner<PlayerStatsEntity> taskRunner = new TaskRunner<PlayerStatsEntity>();
        taskRunner.executeAsync(new Callable<PlayerStatsEntity>() {
            @Override
            public PlayerStatsEntity call() throws InterruptedException {
                return playerStatsDao.getFirstPlayer();
            }
        }, new TaskRunner.TaskRunnerCallback<PlayerStatsEntity>() {
            @Override
            public void execute(PlayerStatsEntity playerStatsEntity) {
                updateDataView(playerStatsEntity);
            }
        });
    }

    private void loadPlayer(long id) {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        TaskRunner<PlayerStatsEntity> taskRunner = new TaskRunner<PlayerStatsEntity>();
        taskRunner.executeAsync(new Callable<PlayerStatsEntity>() {
            @Override
            public PlayerStatsEntity call() throws InterruptedException {
                return playerStatsDao.getById(id);
            }
        }, new TaskRunner.TaskRunnerCallback<PlayerStatsEntity>() {
            @Override
            public void execute(PlayerStatsEntity playerStatsEntity) {
                updateDataView(playerStatsEntity);
            }
        });
    }

    private void updateDataView(PlayerStatsEntity playerStatsEntity) {
        View view = fragment.getView();

        TextView userName = view.findViewById(R.id.user_name);
        TextView gamesPlayed = view.findViewById(R.id.data_gamesplayed);
        TextView creepingBarrageDamage = view.findViewById(R.id.data_creepingbarragedamage);
        TextView topThree = view.findViewById(R.id.data_topthree);
        TextView damage = view.findViewById(R.id.data_damage);
        TextView droppedItems = view.findViewById(R.id.data_droppeditems);
        TextView beaconsScanned = view.findViewById(R.id.data_beaconsscanned);
        TextView kills = view.findViewById(R.id.data_kills);
        TextView pistolKills = view.findViewById(R.id.data_pistolkills);
        TextView arKills = view.findViewById(R.id.data_arkills);
        TextView beastOfTheHuntKills = view.findViewById(R.id.data_beastofthehuntkills);
        TextView kd = view.findViewById(R.id.data_kd);

        userName.setText(playerStatsEntity.getName());
        gamesPlayed.setText(String.valueOf(playerStatsEntity.getGamesPlayed()));
        creepingBarrageDamage.setText(String.valueOf(playerStatsEntity.getBarrageDamage()));
        topThree.setText(String.valueOf(playerStatsEntity.getTopThree()));
        damage.setText(String.valueOf(playerStatsEntity.getDamage()));
        droppedItems.setText(String.valueOf(playerStatsEntity.getDroppedItems()));
        beaconsScanned.setText(String.valueOf(playerStatsEntity.getBeaconsScanned()));
        kills.setText(String.valueOf(playerStatsEntity.getKills()));
        pistolKills.setText(String.valueOf(playerStatsEntity.getPistolKills()));
        arKills.setText(String.valueOf(playerStatsEntity.getArKills()));
        beastOfTheHuntKills.setText(String.valueOf(playerStatsEntity.getHuntKills()));
        kd.setText(String.valueOf(playerStatsEntity.getKd()));
    }
}
