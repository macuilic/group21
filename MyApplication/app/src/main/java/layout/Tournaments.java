package layout;


import android.app.ActionBar;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnmilsom.ggleaguesv1local.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tournaments extends Fragment {

    TabHost mTabHost;
    Filter[] filters;

    public Tournaments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tournament, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        final LinearLayout tourList = (LinearLayout) getView().findViewById(R.id.tourList);
        //declaring layout to hold the actual filter options, then hiding it
        //it will be shown when the "filterButton" in the tournament fragment is clicked, and the button will be hidden
        final LinearLayout filterLayout = (LinearLayout)getView().findViewById(R.id.filters);
        filterLayout.setVisibility(View.GONE);

        // This makes a server request to our tournaments database.
        // @getApi :    is now our instance of the tournament request, and it contains
        //              an interface that waits for the connection to finish, and returns
        //              a Tournament_Data array containing each entry of the database.
        //              E.g. Every command that wants to use the returned value from
        //              the server request must happen within onDataReceived.
        Tour_Server_Request getApi = new Tour_Server_Request();
        getApi.GetApi(getContext(), new Tour_Server_Request.DataListener() {
            @Override
            public void onDataReceived(final Tournament_Data[] tData) {
                setupFilters(tData);

                //create array of layouts that will act as items in a list of tournaments, when cliked they will open the tournament's details
                LinearLayout[] myButtons;
                if (tData.length < 20) {
                    myButtons = new LinearLayout[tData.length];
                } else {
                    myButtons = new LinearLayout[20];
                }

                for (int i = 0; i < myButtons.length; i++) {
                    final LinearLayout n = tourListItem(tData[i].getTour_name(), tData[i].getTour_Date(),
                            tData[i].getNum_players() + "/" + tData[i].getMax_p(), tData[i].getTour_region());
                    tourList.addView(n);        //adding to the list viewer in the fragment

                    final int pressedTour = i;
                    n.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            //setting clickables for list items
                            boolean down = false;
                            boolean up = false;
                            boolean drag = false;
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    down = true;
                                    break;
                                case MotionEvent.ACTION_UP:
                                    up = true;
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    drag = true;
                            }
                            if (down && !drag) {
                                //setting colour on press but not scroll
                                n.setBackgroundColor(Color.LTGRAY);
                            } else {
                                //setting colour back to transparent for if dragged or released
                                n.setBackgroundColor(Color.TRANSPARENT);
                            }

                            if (up) {
                                //open tournament details fragment (TournamentView)

                                TournamentView tourFrag = new TournamentView();
                                tourFrag.setTournamentData(tData[pressedTour]);
                                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, tourFrag);
                                fragmentTransaction.addToBackStack("myscreen");    //adding to "backstack" for when back button is pressed
                                fragmentTransaction.commit();
                            }
                            return false;
                        }
                    });
                    //added to list for further access if neccessary
                    myButtons[i] = n;
                }

                final Button filterButton = (Button) getView().findViewById(R.id.filterButton);

                filterButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //hiding "filterButton" and show filters
                        filterButton.setVisibility(View.GONE);
                        filterLayout.setVisibility(View.VISIBLE);
                    }
                });
                final LinearLayout hideFilters = (LinearLayout) getView().findViewById(R.id.hideFilterButton);
                hideFilters.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //hide filerts and show "filterButton"
                        filterButton.setVisibility(View.VISIBLE);
                        filterLayout.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public void setupFilters(Tournament_Data[] tData){
        Filter[] filters = new Filter[2];

        //MAking the size of the array one bigger in order to contain "All" in our choices.
        String[] games = new String[tData.length+1];
        String[] regions = new String[tData.length+1];
        games[0] = "All";
        regions[0] = "All";
        for (int i = 0; i < tData.length; i++) {
            games[i+1] = tData[i].getTour_game();
            regions[i+1] = tData[i].getTour_region();
        }

        filters[0] = new Filter("Game", games);
        filters[1] = new Filter("Region",regions);
        final LinearLayout filterLayout = (LinearLayout)getView().findViewById(R.id.filters);
        for (Filter f : filters){
            LinearLayout newFilter = new LinearLayout(getContext());
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            newFilter.setLayoutParams(LLParams);
            filterLayout.addView(newFilter,0);
            TextView newText = new TextView(getContext());
            newText.setText(f.getName());
            Spinner newSpinner = new Spinner(getContext());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, f.getOptions());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            newSpinner.setAdapter(adapter);
            newFilter.addView(newText);
            newFilter.addView(newSpinner);
        }
    }

    //Filter class to simplify fields in a single filter
    public class Filter{
        private String filterName;
        private String[] options;
        public Filter(String filterName, String[] options){
            this.filterName = filterName;
            this.options = options;
        }
        public String getName(){
            return filterName;
        }
        public String[] getOptions(){
            return options;
        }
    }

    //tournament list item class, each item is displayed with the following 4 parameters on screen.
    public LinearLayout tourListItem(String name, String startDate, String players, String region){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //overall clickable layout
        LinearLayout item = new LinearLayout(getContext());
        item.setLayoutParams(lp);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setClickable(true);
        LinearLayout info = new LinearLayout(getContext());
        info.setLayoutParams(lp);
        info.setOrientation(LinearLayout.HORIZONTAL);

        //info
        TextView nameView = new TextView(getContext());
        nameView.setText(name);
        nameView.setTextColor(Color.DKGRAY);
        TextView dateView = new TextView(getContext());
        dateView.setText(startDate);
        TextView regionView = new TextView(getContext());
        regionView.setText(region);
        regionView.setTextColor(Color.DKGRAY);
        regionView.setGravity(Gravity.RIGHT);
        TextView playersView = new TextView(getContext());
        playersView.setText(players);
        playersView.setGravity(Gravity.RIGHT);

        //individual layouts
        LinearLayout.LayoutParams lphalf = new LinearLayout.LayoutParams(display.getWidth()/2-40,LinearLayout.LayoutParams.WRAP_CONTENT);
        lphalf.setMargins(20,0,20,0);
        LinearLayout nameDate = new LinearLayout(getContext());
        nameDate.setOrientation(LinearLayout.VERTICAL);
        nameDate.addView(nameView);
        nameDate.addView(dateView);
        nameDate.setLayoutParams(lphalf);

        LinearLayout regionPlayers = new LinearLayout(getContext());
        regionPlayers.setOrientation(LinearLayout.VERTICAL);
        regionPlayers.addView(regionView);
        regionPlayers.addView(playersView);
        regionPlayers.setGravity(Gravity.RIGHT);
        regionPlayers.setLayoutParams(lphalf);

        //Divider
        ImageView divider = new ImageView(getContext());
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        lp2.setMargins(10, 10, 10, 10);
        divider.setLayoutParams(lp2);
        divider.setBackgroundColor(0xF0E0E0E0);

        //adding all items
        info.addView(nameDate);
        info.addView(regionPlayers);
        item.addView(info);
        item.addView(divider);

        return item;
    }
}
