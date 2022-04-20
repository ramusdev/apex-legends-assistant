package com.rb.apexassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class WeaponsTabFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 7;
    private static final String BUNDLE_MODE = "bundle_mode";
    private static final String BUNDLE_ID = "bundle_id";
    // private long playerId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPager.setId(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        // playerId = getArguments().getLong(BUNDLE_ID);

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {
        // final Bundle arguments = new Bundle();
        // final StatisticsFragment statisticsFragment = new StatisticsFragment();

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {

            final Bundle arguments = new Bundle();
            final WeaponsFragment weaponsFragment = new WeaponsFragment();

            switch (position){
                case 0 :
                    arguments.putString(BUNDLE_MODE, "energy");
                    break;
                case 1 :
                    arguments.putString(BUNDLE_MODE, "heavy");
                    break;
                case 2:
                    arguments.putString(BUNDLE_MODE, "light");
                    break;
                case 3:
                    arguments.putString(BUNDLE_MODE, "shotgun");
                    break;
                case 4:
                    arguments.putString(BUNDLE_MODE, "sniper");
                    break;
                case 5:
                    arguments.putString(BUNDLE_MODE, "unique");
                    break;
                case 6:
                    arguments.putString(BUNDLE_MODE, "arrow");
                    break;
            }

            weaponsFragment.setArguments(arguments);
            return weaponsFragment;
        }


        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public String getPageTitle(int position) {

            switch (position){
                case 0 :
                    return MyApplicationContext.getAppContext().getResources().getString(R.string.weaponstab_energy);
                case 1 :
                    return MyApplicationContext.getAppContext().getResources().getString(R.string.weaponstab_heavy);
                case 2 :
                    return MyApplicationContext.getAppContext().getResources().getString(R.string.weaponstab_light);
                case 3 :
                    return MyApplicationContext.getAppContext().getResources().getString(R.string.weaponstab_shotgun);
                case 4 :
                    return MyApplicationContext.getAppContext().getResources().getString(R.string.weaponstab_sniper);
                case 5 :
                    return MyApplicationContext.getAppContext().getResources().getString(R.string.weaponstab_unique);
                case 6 :
                    return MyApplicationContext.getAppContext().getResources().getString(R.string.weaponstab_arrow);

            }

            return null;
        }

    }
}
