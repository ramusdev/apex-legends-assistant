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

public class StatisticOptionsFragment extends Fragment {

    private View view;
    private static final String BUNDLE_ID = "bundle_id";

    public static StatisticOptionsFragment newInstance() {
        return new StatisticOptionsFragment();
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
        statisticOptionsService.setTextView();
        statisticOptionsService.showPlayers();
        statisticOptionsService.handleSubmitButton();
    }


}
