package com.rb.apexassistant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.rb.apexassistant.database.AppDatabase;
import com.rb.apexassistant.database.WeaponDao;
import com.rb.apexassistant.model.WeaponEntity;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * A fragment representing a list of Items.
 */
public class WeaponsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String BUNDLE_MODE = "bundle_mode";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private WeaponDao weaponDao;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeaponsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WeaponsFragment newInstance(int columnCount) {
        WeaponsFragment fragment = new WeaponsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        AppDatabase appDatabase = AppDatabase.getInstance();
        this.weaponDao = appDatabase.WeaponDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String mode = getArguments().getString(BUNDLE_MODE);

        View view = inflater.inflate(R.layout.news_list, container, false);

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_weapons));

        List<WeaponEntity> weaponEntities = this.weaponDao.findAllByType(mode);

        /*
        TaskRunner<List<WeaponEntity>> taskRunner = new TaskRunner();
        taskRunner.executeAsync(new Callable<List<WeaponEntity>>() {
            @Override
            public List<WeaponEntity> call() throws Exception {
                List<WeaponEntity> weaponEntities = WeaponsFragment.this.weaponDao.findAllByType(mode);
                return weaponEntities;
            }
        }, new TaskRunner.TaskRunnerCallback<List<WeaponEntity>>() {
            @Override
            public void execute(List<WeaponEntity> weaponEntities) {
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    recyclerView.setItemViewCacheSize(20);
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }

                    WeaponsViewAdapter adapter = new WeaponsViewAdapter(getContext(), weaponEntities);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        */

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setItemViewCacheSize(20);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            WeaponsViewAdapter adapter = new WeaponsViewAdapter(getContext(), weaponEntities);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}