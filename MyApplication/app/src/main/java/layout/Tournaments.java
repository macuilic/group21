package layout;


import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.johnmilsom.ggleaguesv1local.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tournaments extends Fragment {

    TabHost mTabHost;

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
        //GridLayout grid = (GridLayout) getView().findViewById(R.id.tourGrid);
        //initialiseTabHost();
        /*
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
        */
    }

    private void initialiseTabHost() {
        mTabHost = (TabHost) getView().findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("Tab1"));
    }

}
