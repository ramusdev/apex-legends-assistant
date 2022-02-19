package com.rb.apexassistant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rb.apexassistant.model.PlayerStatsEntity;
import com.rb.apexassistant.viewmodel.StatisticFragmentView;

public class StatisticFragment extends Fragment {

    private static final String BUNDLE_ID = "bundle_id";
    private AboutViewModel mViewModel;
    private View view;

    public static StatisticFragment newInstance() {
        return new StatisticFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_about));

        // View
        view = inflater.inflate(R.layout.stats_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        long playerId = getArguments().getLong(BUNDLE_ID);

        Log.d("MyTag", "Inside stats");
        Log.d("MyTag", String.valueOf(playerId));


        StatisticFragmentView statisticFragmentView = ViewModelProviders.of(getActivity()).get(StatisticFragmentView.class);
        LiveData<PlayerStatsEntity> playerStatsEntityLiveData = statisticFragmentView.getPlayerStatsEntity(playerId);
        playerStatsEntityLiveData.observe(getViewLifecycleOwner(), new Observer<PlayerStatsEntity>() {
            @Override
            public void onChanged(PlayerStatsEntity playerStatsEntity) {
                updateData(playerStatsEntity);
                Log.d("MyTag", "on change --->");
            }
        });


        // tatsFragmentService statsFragmentService = new StatsFragmentService();
        // PlayerStatsEntity playerStatsEntity = statsFragmentService.loadStats();

        // mViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        // final TextView textView = view.findViewById(R.id.main_text);
        // textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());

        /*
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Spanned>() {
            @Override
            public void onChanged(Spanned s) {
                textView.setText(s);
            }
        });
        */
    }

    public void updateData(PlayerStatsEntity playerStatsEntity) {
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
        topThree.setText(String.valueOf(playerStatsEntity.getBarrageDamage()));
        damage.setText(String.valueOf(playerStatsEntity.getBarrageDamage()));
        droppedItems.setText(String.valueOf(playerStatsEntity.getDroppedItems()));
        beaconsScanned.setText(String.valueOf(playerStatsEntity.getDroppedItems()));
        kills.setText(String.valueOf(playerStatsEntity.getKills()));
        pistolKills.setText(String.valueOf(playerStatsEntity.getPistolKills()));
        arKills.setText(String.valueOf(playerStatsEntity.getArKills()));
        beastOfTheHuntKills.setText(String.valueOf(playerStatsEntity.getHuntKills()));
        kd.setText(String.valueOf(playerStatsEntity.getKd()));
    }

}