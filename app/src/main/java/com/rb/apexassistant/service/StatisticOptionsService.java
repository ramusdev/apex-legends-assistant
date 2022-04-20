package com.rb.apexassistant.service;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.rb.apexassistant.MyApplication;
import com.rb.apexassistant.MyApplicationContext;
import com.rb.apexassistant.R;
import com.rb.apexassistant.StatisticFragment;
import com.rb.apexassistant.TaskRunner;
import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.PlayerStatsDao;
import com.rb.apexassistant.model.PlayerEntity;
import com.rb.apexassistant.tasks.StatsTask;
import com.rb.apexassistant.viewmodel.StatisticOptionsView;

import java.util.List;
import java.util.concurrent.Callable;

public class StatisticOptionsService {
    private static final String BUNDLE_ID = "bundle_id";
    public Fragment fragment;
    StatisticOptionsView statisticOptionsView;

    public StatisticOptionsService(Fragment fragment) {
        this.fragment = fragment;
    }

    public void showPlayers() {
        statisticOptionsView = ViewModelProviders.of(fragment).get(StatisticOptionsView.class);
        MutableLiveData<List<PlayerEntity>> playerStatsEntityMutableLiveData = statisticOptionsView.getPlayerStatsEntity();
        playerStatsEntityMutableLiveData.observe(fragment.getViewLifecycleOwner(), new Observer<List<PlayerEntity>>() {
            @Override
            public void onChanged(List<PlayerEntity> playerStatsEntities) {
                clearPlayerView();
                updatePlayerView(playerStatsEntities);
            }
        });
    }

    public void setTextView() {
        final TextView textView = fragment.getView().findViewById(R.id.statistics_text);
        String text = MyApplicationContext.getAppContext().getResources().getString(R.string.statisticoptions_text);
        textView.setText(Html.fromHtml(text));
    }

    public void handleSubmitButton() {
        final Button submitButton = fragment.getView().findViewById(R.id.button_submit);
        submitButton.setOnClickListener(submitButtonListener());
    }

    private View.OnClickListener submitButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MyTag", "on click button --->");

                Button button = fragment.getView().findViewById(R.id.button_submit);
                EditText editText = fragment.getView().findViewById(R.id.edit_text);
                String playerName = editText.getText().toString();

                InputMethodManager inputMethodManager = (InputMethodManager) MyApplicationContext.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                button.setEnabled(false);

                if (playerName.trim().isEmpty()) {
                    button.setEnabled(true);
                    editText.setText("");
                    showSubmitMessage(MyApplicationContext.getAppContext().getResources().getString(R.string.statisticoptions_field_empty));
                    return;
                }

                getCallToApi(playerName);
            }
        };
    }

    public void getCallToApi(String playerName) {
        EditText editText = fragment.getView().findViewById(R.id.edit_text);

        TaskRunner<Integer> taskRunner = new TaskRunner();
        Callable callable = new StatsTask(playerName);
        taskRunner.executeAsync(callable, new TaskRunner.TaskRunnerCallback<Integer>() {
            @Override
            public void execute(Integer status) {
                if (status == 200) {
                    statisticOptionsView.loadData();
                    editText.setText("");
                    showSubmitMessage(MyApplicationContext.getAppContext().getResources().getString(R.string.statisticoptions_field_added));
                } else {
                    showSubmitMessage(MyApplicationContext.getAppContext().getResources().getString(R.string.statisticoptions_field_notfound));
                }

                Button button = fragment.getView().findViewById(R.id.button_submit);
                button.setEnabled(true);
            }
        });
    }



    private void updatePlayerView(List<PlayerEntity> playerStatsEntities) {
        LinearLayout playersBlock = fragment.getView().findViewById(R.id.players_block);

        for (PlayerEntity playerEntity : playerStatsEntities) {
            LinearLayout viewBlock = (LinearLayout) fragment.getLayoutInflater().inflate(R.layout.player_block, null);
            playersBlock.addView(viewBlock);

            String name = playerEntity.getName();
            long id = playerEntity.getId();

            TextView textView = viewBlock.findViewById(R.id.player_name);
            textView.setText(name);

            Button buttonInfo = viewBlock.findViewById(R.id.player_info);
            buttonInfo.setOnClickListener(infoButtonListener(id));

            Button buttonDelete = viewBlock.findViewById(R.id.player_delete);
            buttonDelete.setOnClickListener(deleteButtonListener(id));
        }
    }

    private void clearPlayerView() {
        LinearLayout playersBlock = fragment.getView().findViewById(R.id.players_block);
        playersBlock.removeAllViews();
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

    private View.OnClickListener deleteButtonListener(long id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase appDatabase = AppDatabase.getInstance();
                PlayerStatsDao playerStatsDao = appDatabase.PlayerStatsDao();

                TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();
                taskRunner.executeAsync(new Callable<Integer>() {
                    @Override
                    public Integer call() throws InterruptedException {
                        PlayerEntity playerEntity = new PlayerEntity();
                        playerEntity.setId(id);
                        playerStatsDao.delete(playerEntity);
                        return 1;
                    }
                }, new TaskRunner.TaskRunnerCallback<Integer>() {
                    @Override
                    public void execute(Integer data) {
                        statisticOptionsView.loadData();
                    }
                });
            }
        };
    }

    private void showSubmitMessage(String message) {
        Snackbar snackbar = Snackbar.make(fragment.getView(), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(fragment.getResources().getColor(R.color.red));
        snackbar.setTextColor(fragment.getResources().getColor(R.color.white));
        snackbar.show();
    }

}
