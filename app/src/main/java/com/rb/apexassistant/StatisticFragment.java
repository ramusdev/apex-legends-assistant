package com.rb.apexassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.rb.apexassistant.service.StatisticFragmentService;

public class StatisticFragment extends Fragment {

    private View view;
    private ViewGroup container;

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
        this.container = container;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Navigation
        NavigationView navigationView = this.getActivity().findViewById(R.id.navigation_view);
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_item_stats);
        menuItem.setChecked(true);

        StatisticFragmentService statisticFragmentService = new StatisticFragmentService(this);
        statisticFragmentService.showData();
    }
}