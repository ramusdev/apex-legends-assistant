package com.rb.apexassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.rb.apexassistant.service.StatisticOptionsService;

public class StatsOptionsFragment extends Fragment {

    private View view;
    private static final String BUNDLE_ID = "bundle_id";

    public static StatsOptionsFragment newInstance() {
        return new StatsOptionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_statsoptions));

        // View
        view = inflater.inflate(R.layout.statsoptions_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StatisticOptionsService statisticOptionsService = new StatisticOptionsService(this);
        statisticOptionsService.showPlayers();
        statisticOptionsService.handleSubmitButton();
    }







    private void showSubmitMessage(String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(getResources().getColor(R.color.red));
        snackbar.setTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }
}
