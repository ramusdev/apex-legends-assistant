package com.rb.apexassistant.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rb.apexassistant.R;
import com.rb.apexassistant.StatisticFragment;
import com.rb.apexassistant.TaskRunner;
import com.rb.apexassistant.model.PlayerStatsEntity;
import com.rb.apexassistant.tasks.StatsTask;
import com.rb.apexassistant.viewmodel.StatisticOptionsView;

import java.util.List;
import java.util.concurrent.Callable;

public class StatisticOptionsService {
    private static final String BUNDLE_ID = "bundle_id";
    public Fragment fragment;

    public StatisticOptionsService(Fragment fragment) {
        this.fragment = fragment;
    }

    public void showPlayers() {
        StatisticOptionsView statisticOptionsView = ViewModelProviders.of(fragment).get(StatisticOptionsView.class);
        MutableLiveData<List<PlayerStatsEntity>> playerStatsEntityMutableLiveData = statisticOptionsView.getPlayerStatsEntity();
        playerStatsEntityMutableLiveData.observe(fragment.getViewLifecycleOwner(), new Observer<List<PlayerStatsEntity>>() {
            @Override
            public void onChanged(List<PlayerStatsEntity> playerStatsEntities) {
                updatePlayerView(playerStatsEntities);
            }
        });
    }

    public void handleSubmitButton() {
        final Button submitButton = fragment.getView().findViewById(R.id.button_submit);
        submitButton.setOnClickListener(submitButtonListener());
    }

    private View.OnClickListener submitButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MyTag", "on click button");

                EditText editText = fragment.getView().findViewById(R.id.edit_text);
                String playerName = editText.getText().toString();

                getCallToApi(playerName);
            }
        };
    }

    public void getCallToApi(String playerName) {
        TaskRunner<Integer> taskRunner = new TaskRunner();
        Callable callable = new StatsTask(playerName);
        taskRunner.executeAsync(callable, new TaskRunner.TaskRunnerCallback<Integer>() {
            @Override
            public void execute(Integer taskAfterDone) {
                Log.d("MyTag", "Call to api --->");
                // Log.d("MyTag", String.valueOf(taskAfterDone));
            }
        });
    }

    private void updatePlayerView(List<PlayerStatsEntity> playerStatsEntities) {
        LinearLayout playersBlock = fragment.getView().findViewById(R.id.players_block);

        for (PlayerStatsEntity playerStatsEntity : playerStatsEntities) {
            LinearLayout viewBlock = (LinearLayout) fragment.getLayoutInflater().inflate(R.layout.player_block, null);
            playersBlock.addView(viewBlock);

            String name = playerStatsEntity.getName();
            long id = playerStatsEntity.getId();

            TextView textView = viewBlock.findViewById(R.id.player_name);
            textView.setText(name);

            Button buttonInfo = viewBlock.findViewById(R.id.player_info);
            buttonInfo.setOnClickListener(infoButtonListener(id));

            // Button buttonDelete = viewBlock.findViewById(R.id.player_delete);
            // buttonDelete.setOnClickListener(deleteButtonListener(id, playersBlock, viewBlock));
        }
    }

    private View.OnClickListener infoButtonListener(long id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MainActivity mainActivity = (MainActivity) fragment.getActivity();
                // MenuItem menuItem = mainActivity.navigationView.getMenu().findItem(R.id.nav_item_statistics);
                // menuItem.setChecked(true);

                Bundle bundle = new Bundle();
                bundle.putLong(BUNDLE_ID, id);

                StatisticFragment statisticFragment = new StatisticFragment();
                statisticFragment.setArguments(bundle);

                FragmentTransaction transaction = fragment.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.replace(R.id.nav_host_fragment, statisticFragment).commit();
            }
        };
    }

}
