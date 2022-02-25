package com.rb.apexassistant.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.R;
import com.rb.apexassistant.TaskRunner;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.LegendEntity;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.model.PlayerLegend;

import java.util.List;
import java.util.concurrent.Callable;

public class StatisticFragmentService {
    private static final String BUNDLE_ID = "bundle_id";
    private Fragment fragment;
    private ViewGroup container;

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

        TaskRunner<PlayerLegend> taskRunner = new TaskRunner<PlayerLegend>();
        taskRunner.executeAsync(new Callable<PlayerLegend>() {
            @Override
            public PlayerLegend call() throws InterruptedException {
                return playerStatsDao.getFirstPlayerLegend();
            }
        }, new TaskRunner.TaskRunnerCallback<PlayerLegend>() {
            @Override
            public void execute(PlayerLegend playerLegend) {
                updateDataView(playerLegend);
            }
        });
    }

    private void loadPlayer(long id) {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

        TaskRunner<PlayerLegend> taskRunner = new TaskRunner<PlayerLegend>();
        taskRunner.executeAsync(new Callable<PlayerLegend>() {
            @Override
            public PlayerLegend call() throws InterruptedException {
                return playerStatsDao.getPlayerLegendById(id);
            }
        }, new TaskRunner.TaskRunnerCallback<PlayerLegend>() {
            @Override
            public void execute(PlayerLegend playerLegend) {
                updateDataView(playerLegend);
            }
        });
    }

    private void addLegendsView(List<LegendEntity> legends) {
        LinearLayout mainLayout = fragment.getView().findViewById(R.id.main_layout);
        ViewGroup viewGroup = (ViewGroup) fragment.getView().getParent();
        LinearLayout spaceBlock = (LinearLayout) fragment.getLayoutInflater().inflate(R.layout.space_block, viewGroup, false);

        for (LegendEntity legend : legends) {
            if (legend.getKills() > 0) {

                LinearLayout legendBlock = (LinearLayout) fragment.getLayoutInflater().inflate(R.layout.legend_block, viewGroup, false);
                mainLayout.addView(legendBlock);

                setDataToBlockView(legend, legendBlock);
            }
        }

        mainLayout.addView(spaceBlock);
    }

    public void setDataToBlockView(LegendEntity legend, LinearLayout view) {
        TextView legendName = view.findViewById(R.id.legend_title);
        TextView gamesPlayed = view.findViewById(R.id.data_gamesplayed);
        TextView headshots = view.findViewById(R.id.data_headshots);
        TextView topThree = view.findViewById(R.id.data_topthree);
        TextView damage = view.findViewById(R.id.data_damage);
        TextView droppedItems = view.findViewById(R.id.data_droppeditems);
        TextView kills = view.findViewById(R.id.data_kills);
        TextView pistolKills = view.findViewById(R.id.data_pistolkills);

        legendName.setText(legend.getName());
        gamesPlayed.setText(String.valueOf(legend.getGamesPlayed()));
        headshots.setText(String.valueOf(legend.getHeadshots()));
        topThree.setText(String.valueOf(legend.getTopThree()));
        damage.setText(String.valueOf(legend.getDamage()));
        droppedItems.setText(String.valueOf(legend.getDroppedItems()));
        kills.setText(String.valueOf(legend.getKills()));
        pistolKills.setText(String.valueOf(legend.getPistolKills()));
    }

    private void updateDataView(PlayerLegend playerLegend) {
        PlayerEntity player = playerLegend.getPlayer();
        List<LegendEntity> legends = playerLegend.getLegends();

        addLegendsView(legends);

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

        userName.setText(player.getName());
        gamesPlayed.setText(String.valueOf(player.getGamesPlayed()));
        creepingBarrageDamage.setText(String.valueOf(player.getBarrageDamage()));
        topThree.setText(String.valueOf(player.getTopThree()));
        damage.setText(String.valueOf(player.getDamage()));
        droppedItems.setText(String.valueOf(player.getDroppedItems()));
        beaconsScanned.setText(String.valueOf(player.getBeaconsScanned()));
        kills.setText(String.valueOf(player.getKills()));
        pistolKills.setText(String.valueOf(player.getPistolKills()));
        arKills.setText(String.valueOf(player.getArKills()));
        beastOfTheHuntKills.setText(String.valueOf(player.getHuntKills()));
        kd.setText(String.valueOf(player.getKd()));
    }
}
