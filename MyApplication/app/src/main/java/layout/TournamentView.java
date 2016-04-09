package layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.johnmilsom.ggleaguesv1local.R;


public class TournamentView extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static TextView tvTourNameView;
    public static TextView tvTourDate;

    //When a new TournamentView is created, it contains the data of the relevant tournament.
    private static Tournament_Data tData;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    private String mParam1;
    private String mParam2;

    @SuppressLint("ValidFragment")
    public TournamentView() {
    }
    public static TournamentView newInstance(String param1, String param2) {
        TournamentView fragment = new TournamentView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Creating a tab layout to display each round in the tournament, which will display lists of matches
        /**
         *Inflate tab_layout and setup Views.
         */

        View x =  inflater.inflate(R.layout.fragment_tournament_view,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        tvTourNameView = (TextView) x.findViewById(R.id.textViewTourView);
        tvTourDate = (TextView) x.findViewById(R.id.textViewTourDate);

        tvTourNameView.setText(tData.getTour_name());
        tvTourDate.setText(tData.getTour_Date());

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;
    }

    public void setTournamentData (Tournament_Data tournament_data) {
        this.tData = tournament_data;
    }
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            return new MatchList();
        }

        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        public CharSequence getPageTitle(int position) {
            return ("Round " + position);
        }
    }
}
