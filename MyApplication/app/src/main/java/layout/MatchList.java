package layout;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.johnmilsom.ggleaguesv1local.R;


public class MatchList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public MatchList() {
        // Required empty public constructor
    }

    public static MatchList newInstance(String param1, String param2) {
        MatchList fragment = new MatchList();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        LinearLayout matchList = (LinearLayout) getView().findViewById(R.id.matchList);
        LinearLayout[] myButtons = new LinearLayout[20];
        for (int i = 0; i < myButtons.length;i++){
            final LinearLayout n = matchListItem("TeamA", "TeamB", "01/12/2016");
            matchList.addView(n);
            myButtons[i] = n;
        }
    }

    //Creates list very similar to the tournament list to display the matches in a tournament
    public LinearLayout matchListItem(String teamA, String teamB, String date){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //overall layout
        LinearLayout item = new LinearLayout(getContext());
        item.setLayoutParams(lp);
        item.setOrientation(LinearLayout.VERTICAL);
        LinearLayout info = new LinearLayout(getContext());
        info.setLayoutParams(lp);
        info.setOrientation(LinearLayout.HORIZONTAL);

        //info
        TextView nameView = new TextView(getContext());
        nameView.setText(teamA);
        nameView.setTextColor(Color.DKGRAY);
        TextView dateView = new TextView(getContext());
        dateView.setText(date);
        TextView regionView = new TextView(getContext());
        regionView.setText(teamB);
        regionView.setTextColor(Color.DKGRAY);
        regionView.setGravity(Gravity.RIGHT);

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
