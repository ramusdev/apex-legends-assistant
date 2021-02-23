package com.rb.apexlegendsassistant;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DonateFragment extends Fragment {

    private DonateViewModel mViewModel;
    View view;

    public static DonateFragment newInstance() {
        return new DonateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_donate));

        view = inflater.inflate(R.layout.donate_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DonateViewModel.class);
        final TextView textView = view.findViewById(R.id.main_text);

        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Spanned>() {
            @Override
            public void onChanged(Spanned s) {
                textView.setText(s);
            }
        });
        // TODO: Use the ViewModel
    }

}