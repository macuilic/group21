package layout;


import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.text.Html;
import android.text.Layout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
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

    String name, game, region;


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
        GridLayout grid = (GridLayout) getView().findViewById(R.id.tourGrid);
        final LinearLayout filterLayout = (LinearLayout)getView().findViewById(R.id.filters);
        filterLayout.setVisibility(View.GONE);
        setupFilters();

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Button[] myButtons = new Button[20];
        for (Button n : myButtons){
            n = new Button(getContext());
            n.setText(Html.fromHtml("String I am testing<br/><small>(with a sub comment)</small>"));
            n.setWidth(wm.getDefaultDisplay().getWidth() / 2);
            n.setHeight(wm.getDefaultDisplay().getHeight() / 6);
            grid.addView(n);
        }

        final Button filterButton = (Button)getView().findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filterButton.setVisibility(View.GONE);
                filterLayout.setVisibility(View.VISIBLE);

            }});
        final LinearLayout hideFilters = (LinearLayout)getView().findViewById(R.id.hideFilterButton);
        hideFilters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filterButton.setVisibility(View.VISIBLE);
                filterLayout.setVisibility(View.GONE);

            }});
    }

    public void setupFilters(){
        Filter[] filters = new Filter[2];

        String[] games = {"All", "CSGO", "Hearthstone", "DOTA", "League of Legends"};
        String[] regions = {"All", "EUW", "NA", "RU", "EUNE"};
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

}
